package com.store.storehouse.configuration.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.kafka.core.*;

import java.util.*;

@Configuration
public class ProducerConfiguration {
    @Bean
    public KafkaTemplate<String, String> historyKafkaTemplate(@Qualifier("producerHistoryProperties") Properties producerHistoryProperties) {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>((Map) producerHistoryProperties));
    }
}
