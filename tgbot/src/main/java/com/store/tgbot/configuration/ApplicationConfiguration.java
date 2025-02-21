package com.store.tgbot.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;


@Configuration
@Slf4j
public class ApplicationConfiguration {

    @Value("${integration.internal.host.storehouse}")
    String storeHouseHost;

    @Value("${integration.internal.host.history}")
    String historyHouseHost;

    @Value("${integration.internal.host.tg.sender}")
    String tgSenderHost;

    @Bean
    public String storeHouseHost() {
        log.info(" storeHouseHost : {}", storeHouseHost);
        return storeHouseHost;
    }

    @Bean
    public String historyHouseHost() {
        log.info(" historyHouseHost : {}", historyHouseHost);
        return historyHouseHost;
    }

    @Bean
    public String tgSenderHost() {
        log.info(" tgSenderHost : {}", tgSenderHost);
        return tgSenderHost;
    }

    @Bean
    public JsonMapper jsonMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
    }
}
