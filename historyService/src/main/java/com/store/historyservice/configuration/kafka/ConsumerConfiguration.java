package com.store.historyservice.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class ConsumerConfiguration {
    private final Properties consumerHistoryProperties;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>((Map) consumerHistoryProperties));
        factory.setConcurrency(Integer.parseInt(consumerHistoryProperties.getProperty("consumer.concurrency")));
        return factory;
    }

    @Bean
    public String historyConsumerTopic() {
        return consumerHistoryProperties.getProperty("consumer.topic");
    }


    @Bean
    public String historyConsumerGroupId() {
        return consumerHistoryProperties.getProperty("group.id");
    }
}
