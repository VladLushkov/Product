package com.store.historyservice.dto.kafka;

import lombok.*;

@Getter
@Setter
public class HistoryProducerMessageDto {
    private String operationType;

    private String productName;

    private Integer oldCount;

    private Integer newCount;
}
