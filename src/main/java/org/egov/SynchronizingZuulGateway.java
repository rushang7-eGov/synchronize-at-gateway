package org.egov;

import org.egov.config.ApplicationProperties;
import org.egov.filters.post.SynchronizeFilter;
import org.egov.filters.pre.CorrelationIdFilter;
import org.egov.filters.pre.RecordCorrelationIdFilter;
import org.egov.service.CorrelationIdService;
import org.egov.service.ResponseSynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class SynchronizingZuulGateway {

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private CorrelationIdService correlationIdService;
    @Autowired
    private ResponseSynchronizationService responseSynchronizationService;

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter(applicationProperties, correlationIdService);
    }

    @Bean
    public RecordCorrelationIdFilter recordCorrelationIdFilter() {
        return new RecordCorrelationIdFilter(applicationProperties, correlationIdService);
    }

    @Bean
    public SynchronizeFilter synchronizeFilter() {
        return new SynchronizeFilter(applicationProperties, responseSynchronizationService, correlationIdService);
    }

    public static void main(String args[]) {
        SpringApplication.run(SynchronizingZuulGateway.class, args);
    }
}
