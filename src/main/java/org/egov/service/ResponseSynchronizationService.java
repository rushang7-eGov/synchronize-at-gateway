package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.ApplicationProperties;
import org.egov.repository.CorrelationIdResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ResponseSynchronizationService {

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private CorrelationIdService correlationIdService;
    @Autowired
    private CorrelationIdResponseRepository correlationIdResponseRepository;

    public void saveResponse(JsonNode response) {
        DocumentContext documentContext = JsonPath.parse(response.toString());
        String correlationId = documentContext.read(applicationProperties.getCorrelationIdJsonPathKafkaMessage());
        if(correlationIdService.checkIfCorrelationIdExists(correlationId))
            correlationIdResponseRepository.saveResponse(correlationId, response);
    }

    public JsonNode getAsyncResponseForCorrelationId(String correlationId) throws InterruptedException {
        Optional<JsonNode> response = correlationIdResponseRepository.getResponse(correlationId);
        while (!response.isPresent()) {
            Thread.sleep(applicationProperties.getPollTime());
            response = correlationIdResponseRepository.getResponse(correlationId);
        }
        correlationIdResponseRepository.deleteResponse(correlationId);
        return response.get();
    }

}
