package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.ApplicationProperties;
import org.egov.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResponseSynchronizationService {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ResponseRepository responseRepository;

    public void saveResponse(JsonNode response) {
        DocumentContext documentContext = JsonPath.parse(response.toString());
        String correlationId = documentContext.read(applicationProperties.getCorrelationIdJsonPath());
        responseRepository.saveResponse(correlationId, response);
    }

    public JsonNode getAsyncResponseForCorrelationId(String correlationId) throws InterruptedException {
        JsonNode response = responseRepository.getResponse(correlationId);
        while (response == null) {
            Thread.sleep(applicationProperties.getPollTime());
            response = responseRepository.getResponse(correlationId);
        }
        return response;
    }

}
