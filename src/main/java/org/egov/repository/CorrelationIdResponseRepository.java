package org.egov.repository;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CorrelationIdResponseRepository {

    private Map<String, JsonNode> correlationIdToResponseMap = new HashMap<>();

    public void recordCorrelationId(String correlationId) {
        correlationIdToResponseMap.put(correlationId, null);
    }

    public boolean checkIfCorrelationIdExists(String correlationId) {
        return correlationIdToResponseMap.containsKey(correlationId);
    }

    public void saveResponse(String correlationId, JsonNode response) {
        correlationIdToResponseMap.put(correlationId, response);
    }

    public Optional<JsonNode> getResponse(String correlationId) {
        if(correlationIdToResponseMap.get(correlationId) != null) {
            JsonNode response = correlationIdToResponseMap.get(correlationId);
            return Optional.ofNullable(response);
        }
        return Optional.empty();
    }

    public void deleteResponse(String correaltionId) {
        correlationIdToResponseMap.remove(correaltionId);
    }

}
