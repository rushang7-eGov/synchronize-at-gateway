package org.egov.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationProperties {

    @Value("${paths.to.be.synced}")
    private String[] pathsToBeSynced;

    @Value("${response.topics}")
    private String[] responseTopics;

    @Value("${response.listener.consumer.group.id}")
    private String responseListenerConsumerGroupId;

    @Value("${kafka.bootstrap.server}")
    private String kafkaBootstrapServer;

    @Value("${correlationid.jsonpath}")
    private String correlationIdJsonPath;

    @Value("${add.correlationid.enable}")
    private Boolean addCorrelationIdEnabled;

    @Value("${path.to.correlationids.parent.object}")
    private String pathToCorrelationIdsParentObject;

    @Value("${correlationid.field.name}")
    private String correlationIdFieldName;

    @Value("${poll.time.milliseconds}")
    private Long pollTime;

}
