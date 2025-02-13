package com.store.storehouse.feinclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "history", url = "${integration.internal.host.history}")
public interface HistoryFeinClient {
    @GetMapping("/api/v1/history/count")
    ResponseEntity<Long> getHistoryCount();
}
