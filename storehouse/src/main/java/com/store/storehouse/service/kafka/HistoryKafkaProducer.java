package com.store.storehouse.service.kafka;

import jakarta.websocket.SendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class HistoryKafkaProducer {
    private final String topic;
    private final KafkaTemplate kafkaTemplate;

    public HistoryKafkaProducer(@Qualifier("historyKafkaTemplate") KafkaTemplate<String, String> historyKafkaTemplate,
                                @Qualifier("producerHistoryProperties") Properties producerHistoryProperties) {
        this.topic = producerHistoryProperties.getProperty("spring.kafka.topics.request");
        this.kafkaTemplate = historyKafkaTemplate;
    }

    public CompletableFuture<SendResult> sendMessage(String message, String key) {
        try {
            CompletableFuture<SendResult> future = kafkaTemplate.send(topic, key, message);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Сообщение " + message + " с ключом " + key + " успешно оптарвлено в топик " + topic);
                } else {
                    log.info("Не удалось отправить сообщение " + message + " с ключом " + key + " успешно оптарвлено в топик " + topic);
                }
            });
            return future;
        } catch (org.apache.kafka.common.KafkaException | org.springframework.kafka.KafkaException e) {
            log.error("Ошибка отпарвки сообщения в топик {}", topic, e);
        }
        return null;
    }
}
