package com.store.tgbot.dto;

import lombok.*;

@Getter
@Setter
public class StatusDto {
    private String status;

    public StatusDto() {

    }

    public StatusDto(String status) {
        this.status = status;
    }
}
