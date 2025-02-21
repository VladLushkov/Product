package com.store.tgbot.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.store.tgbot.Status;
import com.store.tgbot.dto.StatusDto;
import com.store.tgbot.feinclient.TgBotSenderFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.*;
import java.util.concurrent.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class HealthCheckJob {
    private final String storeHouseHost;
    private final String historyHouseHost;
    private final TgBotSenderFeign tgBotSenderFeign;

    private final JsonMapper jsonMapper;

    private final String healthPath = "/actuator/health";

    @Scheduled(fixedRate = 1000)
    public void printMessage() throws ExecutionException, InterruptedException {
        log.info("start HealthCheckJob");
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest storeHouseRequest = HttpRequest.newBuilder()
                .uri(URI.create(storeHouseHost + healthPath))
                .build();
        HttpRequest historyRequest = HttpRequest.newBuilder()
                .uri(URI.create(historyHouseHost + healthPath))
                .build();

        CompletableFuture<HttpResponse<String>> futureStoreHouse = client.sendAsync(storeHouseRequest, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> futureHistory = client.sendAsync(historyRequest, HttpResponse.BodyHandlers.ofString());

        StringBuilder statusMessage = new StringBuilder();


        String storeHouseCheck = futureStoreHouse.handle((result, e) -> {
            if (e != null) {
                log.error("Ошибка при обработке запроса storehouse " + e.getMessage() + e.getStackTrace());
                return "Ошибка при обработке запроса storehouse \n";
            } else {
                StatusDto storeHouseStatus = null;
                try {
                    storeHouseStatus = jsonMapper.readValue(result.body(), StatusDto.class);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                if (storeHouseStatus.getStatus().equals(Status.UP.name())) {
                    return "storehouse isActive \n";
                } else {
                    return "storehouse isUnActive \n";
                }
            }
        }).get();

        String historyCheck = futureHistory.handle((result, e) -> {
            if (e != null) {
                log.error("Ошибка при обработке запроса history " + e.getMessage() + e.getStackTrace());
                return "Ошибка при обработке запроса history \n";
            } else {
                StatusDto storeHouseStatus = null;
                try {
                    storeHouseStatus = jsonMapper.readValue(result.body(), StatusDto.class);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                if (storeHouseStatus.getStatus().equals(Status.UP.name())) {
                    return "history isActive \n";
                } else {
                    return "\"history isUnActive \n";
                }
            }
        }).get();

        try {
            tgBotSenderFeign.sendStatus(new StatusDto(storeHouseCheck + historyCheck));
        } catch (Exception e) {
            log.error("Ошибка при отправке запроса в тг бот " + e.getMessage() + e.getStackTrace());
        }
    }
}
