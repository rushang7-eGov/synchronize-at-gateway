package org.egov.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public class CorrelationIdRepository {

    HashSet<String> toBeSyncedRequests;

    @Autowired
    public CorrelationIdRepository() {
        toBeSyncedRequests = new HashSet<>();
    }

    public boolean checkIfCorrelationIdExists(String correlationId) {
        return toBeSyncedRequests.contains(correlationId);
    }

    public void recordCorrelationId(String correlationId) {
        toBeSyncedRequests.add(correlationId);
    }

    public void deleteCorrelationId(String correlationId) {
        toBeSyncedRequests.remove(correlationId);
    }

}
