package org.egov.service;

import org.egov.repository.CorrelationIdResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CorrelationIdService {

    @Autowired
    private CorrelationIdResponseRepository correlationIdResponseRepository;

    public void recordCorrelationId(String correlationId) {
        correlationIdResponseRepository.recordCorrelationId(correlationId);
    }

    public boolean checkIfCorrelationIdExists(String correlationId) {
        return correlationIdResponseRepository.checkIfCorrelationIdExists(correlationId);
    }

    public String generateNewCorrelationId() {
        return UUID.randomUUID().toString();
    }

}
