package org.egov.repository;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ResponseRepository {

    private Map<String, JsonNode> correlationIdToResponseMap = new HashMap<>();

    public void saveResponse(String correlationId, JsonNode response) {
        correlationIdToResponseMap.put(correlationId, response);
    }

    public JsonNode getResponse(String correlationId) {
        if(correlationIdToResponseMap.containsKey(correlationId)) {
            JsonNode response = correlationIdToResponseMap.get(correlationId);
            correlationIdToResponseMap.remove(correlationId);
            return response;
        }
        return null;
    }

    public void deleteResponse(String correaltionId) {
        correlationIdToResponseMap.remove(correaltionId);
    }
}
