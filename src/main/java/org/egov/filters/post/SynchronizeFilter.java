package org.egov.filters.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;
import org.egov.config.ApplicationProperties;
import org.egov.service.CorrelationIdService;
import org.egov.service.ResponseSynchronizationService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class SynchronizeFilter extends ZuulFilter {

    private ApplicationProperties applicationProperties;
    private ResponseSynchronizationService responseSynchronizationService;
    private CorrelationIdService correlationIdService;

    public SynchronizeFilter(ApplicationProperties applicationProperties,
                             ResponseSynchronizationService responseSynchronizationService,
                             CorrelationIdService correlationIdService) {
        this.applicationProperties = applicationProperties;
        this.responseSynchronizationService = responseSynchronizationService;
        this.correlationIdService = correlationIdService;
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        String requestURI = RequestContext.getCurrentContext().getRequest().getRequestURI();
        return applicationProperties.getPathsToBeSynced().contains(requestURI);
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            String payload = IOUtils.toString(ctx.getResponseDataStream());
            DocumentContext documentContext = JsonPath.parse(payload);
            String correlationId = documentContext.read(applicationProperties.getCorrelationIdJsonPathResponse());

            if(applicationProperties.getResponseCodesToBeSynced().contains(ctx.getResponseStatusCode())) {
                JsonNode responseBody = responseSynchronizationService.getAsyncResponseForCorrelationId(correlationId);;
                String responseBodyString = responseBody.toString();
                ctx.setResponseBody(responseBodyString);
            } else {
                correlationIdService.deleteCorrelationId(correlationId);
                ctx.setResponseBody(payload);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
