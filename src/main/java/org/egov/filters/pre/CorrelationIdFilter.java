package org.egov.filters.pre;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;
import org.egov.config.ApplicationProperties;
import org.egov.service.CorrelationIdService;
import org.egov.util.CustomRequestWrapper;

import java.io.IOException;

public class CorrelationIdFilter extends ZuulFilter {

    private ApplicationProperties applicationProperties;

    private CorrelationIdService correlationIdService;

    public CorrelationIdFilter(ApplicationProperties applicationProperties, CorrelationIdService correlationIdService) {
        this.applicationProperties = applicationProperties;
        this.correlationIdService = correlationIdService;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return applicationProperties.getAddCorrelationIdEnabled();
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            String correlationId = correlationIdService.generateNewCorrelationId();
            String payload = IOUtils.toString(ctx.getRequest().getInputStream());

            DocumentContext documentContext = JsonPath.parse(payload);
            documentContext.put(JsonPath.compile(applicationProperties.getPathToCorrelationIdsParentObject()),
                    applicationProperties.getCorrelationIdFieldName(), correlationId);

            CustomRequestWrapper requestWrapper = new CustomRequestWrapper(ctx.getRequest());
            requestWrapper.setPayload(documentContext.jsonString());
            ctx.setRequest(requestWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
