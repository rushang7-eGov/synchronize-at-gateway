package org.egov.filters.pre;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;
import org.egov.config.ApplicationProperties;
import org.egov.service.CorrelationIdService;

import java.io.IOException;

public class RecordCorrelationIdFilter extends ZuulFilter {

    private ApplicationProperties applicationProperties;

    private CorrelationIdService correlationIdService;

    public RecordCorrelationIdFilter(ApplicationProperties applicationProperties, CorrelationIdService correlationIdService) {
        this.applicationProperties = applicationProperties;
        this.correlationIdService = correlationIdService;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
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
            String payload = IOUtils.toString(ctx.getRequest().getInputStream());
            DocumentContext documentContext = JsonPath.parse(payload);
            String correlationId = documentContext.read(applicationProperties.getCorrelationIdJsonPathRequest());
            correlationIdService.recordCorrelationId(correlationId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
