package org.egov.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class ApplicationProperties {

    @Value("#{'${paths.to.be.synced}'.split(',')}")
    private List<String> pathsToBeSynced;

    @Value("#{'${response.codes.to.be.synced}'.split(',')}")
    private List<Integer> responseCodesToBeSynced;

    @Value("${response.topics}")
    private String[] responseTopics;

    @Value("${response.listener.consumer.group.id}")
    private String responseListenerConsumerGroupId;

    @Value("${kafka.bootstrap.server}")
    private String kafkaBootstrapServer;

    @Value("${correlationid.jsonpath.request}")
    private String correlationIdJsonPathRequest;

    @Value("${correlationid.jsonpath.response}")
    private String correlationIdJsonPathResponse;

    @Value("${correlationid.jsonpath.kafka.message}")
    private String correlationIdJsonPathKafkaMessage;

    @Value("${add.correlationid.enable}")
    private Boolean addCorrelationIdEnabled;

    @Value("${path.to.correlationids.parent.object}")
    private String pathToCorrelationIdsParentObject;

    @Value("${correlationid.field.name}")
    private String correlationIdFieldName;

    @Value("${poll.time.milliseconds}")
    private Long pollTime;

}
