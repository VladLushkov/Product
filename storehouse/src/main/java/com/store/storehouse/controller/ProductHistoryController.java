package com.store.storehouse.controller;

import com.store.storehouse.feinclient.HistoryFeinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product-history")
public class ProductHistoryController {
    @Autowired
    private HistoryFeinClient historyFeinClient;

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(historyFeinClient.getHistoryCount().getBody());
    }
}
