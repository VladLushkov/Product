package com.store.tgbot.job;

import com.store.tgbot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.*;

@Service
@Slf4j
public class CheckStoreHouseAccessibility {
    private String urlString;
    private UserService userService;
    private final String positiveAnswer = "Получен положительный от сервиса storehouse с кодом : ";
    private final String negativeAnswer = "Получен отрицательный от сервиса storehouse с кодом : ";
    private final String errorAnswer = "Ошибка при создании http соединения : ";

    @Autowired
    public CheckStoreHouseAccessibility(String storeHouseHost, UserService userService) {
        urlString = storeHouseHost + "/api/v1/product/hello";
        this.userService = userService;
    }

    @Scheduled(fixedRate = 4000)
    @SchedulerLock(name = "storeHouseAccessibility")
    public void computePrice() {
        HttpURLConnection connection = null;
        int responseCode = -9999;
        String textAnswer;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            log.error("Ошибка при создании http соединения : {}", e.getMessage());
            textAnswer = errorAnswer + responseCode;
            userService.sendMessage(textAnswer);
            log.error(textAnswer + " " + e.getMessage());
            return;
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            textAnswer = positiveAnswer + responseCode;
            userService.sendMessage(textAnswer);
            log.info(textAnswer);
        } else {
            textAnswer = negativeAnswer + responseCode;
            userService.sendMessage(textAnswer);
            log.info(textAnswer);
        }

    }
}
