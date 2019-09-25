package org.egov.service;

import org.egov.repository.CorrelationIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CorrelationIdService {

    @Autowired
    private CorrelationIdRepository correlationIdRepository;

    public void recordCorrelationId(String correlationId) {
        correlationIdRepository.recordCorrelationId(correlationId);
    }

    public boolean checkIfCorrelationIdExists(String correlationId) {
        return correlationIdRepository.checkIfCorrelationIdExists(correlationId);
    }

    public void deleteCorrelationId(String correlationId) {
        correlationIdRepository.deleteCorrelationId(correlationId);
    }

    public String generateNewCorrelationId() {
        return UUID.randomUUID().toString();
    }

}
