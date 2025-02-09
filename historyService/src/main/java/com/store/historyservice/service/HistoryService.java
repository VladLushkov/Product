package com.store.historyservice.service;

import com.store.historyservice.dto.kafka.HistoryProducerMessageDto;
import com.store.historyservice.model.History;
import com.store.historyservice.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional
    public void saveHistory(HistoryProducerMessageDto dto) {
        History history = new History();
        history.setNewCount(dto.getNewCount());
        history.setOldCount(dto.getOldCount());
        history.setProductName(dto.getProductName());
        history.setOperationType(dto.getOperationType());
        historyRepository.save(history);
    }
}
