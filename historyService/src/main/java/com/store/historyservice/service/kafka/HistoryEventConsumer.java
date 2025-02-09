package com.store.historyservice.service.kafka;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.store.historyservice.dto.kafka.HistoryProducerMessageDto;
import com.store.historyservice.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryEventConsumer {
    private final JsonMapper jsonMapper;
    private final String historyConsumerTopic;
    private final HistoryService historyService;

    @KafkaListener(topics = {"#{historyConsumerTopic}"}, groupId = "#{historyConsumerGroupId}", containerFactory = "listenerContainerFactory")
    public void listen(String message) {
        try {
            log.info("Получено сообщение {} из топика {}", message, historyConsumerTopic);
            HistoryProducerMessageDto dto = jsonMapper.readValue(message, HistoryProducerMessageDto.class);
            historyService.saveHistory(dto);
        }
        catch (Exception e) {
            log.error("Ошибка при обработке сообщения {} из топика {}", message, historyConsumerTopic);
        }
    }
}
