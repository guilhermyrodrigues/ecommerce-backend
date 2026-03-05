package com.guilhermy.ecommerce.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KafkaConfigTest {

    private final KafkaConfig kafkaConfig = new KafkaConfig();

    @Test
    void producerFactoryShouldUseBootstrapFromKafkaProperties() {
        KafkaProperties properties = new KafkaProperties();
        properties.setBootstrapServers(List.of("localhost:29092"));

        ProducerFactory<String, Object> producerFactory = kafkaConfig.producerFactory(properties);
        Map<String, Object> producerProps = ((DefaultKafkaProducerFactory<String, Object>) producerFactory)
                .getConfigurationProperties();

        assertEquals("localhost:29092", producerProps.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    @Test
    void consumerFactoryShouldRespectProvidedGroupId() {
        KafkaProperties properties = new KafkaProperties();
        properties.setBootstrapServers(List.of("localhost:29092"));
        properties.getConsumer().setGroupId("custom-group");

        ConsumerFactory<String, Object> consumerFactory = kafkaConfig.consumerFactory(properties);
        Map<String, Object> consumerProps = ((DefaultKafkaConsumerFactory<String, Object>) consumerFactory)
                .getConfigurationProperties();

        assertEquals("custom-group", consumerProps.get(ConsumerConfig.GROUP_ID_CONFIG));
    }
}
