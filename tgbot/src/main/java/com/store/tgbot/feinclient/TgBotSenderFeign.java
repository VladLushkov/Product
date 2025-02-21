package com.store.tgbot.feinclient;

import com.store.tgbot.dto.StatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "tgbotsender", url = "${integration.internal.host.tg.sender}")
public interface TgBotSenderFeign {
    @PostMapping("/health-check")
    ResponseEntity<HttpStatus> sendStatus(StatusDto status);
}
