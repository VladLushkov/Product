package com.store.tgbot.configuration;


import com.store.tgbot.telegramm.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@Slf4j
public class ApplicationConfiguration {
    @Value("${tg.bot.name}")
    private String tgBotName;

    @Value("${tg.bot.token}")
    private String tgBotToken;

    @Value("${integration.internal.host.storehouse}")
    String storeHouseHost;

    @Autowired
    @Lazy
    private Bot tgBot;

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(tgBot);
        return botsApi;
    }

    @Bean
    public String tgBotName() {
        return tgBotName;
    }

    @Bean
    public String tgBotToken() {
        return tgBotToken;
    }

    @Bean
    public String storeHouseHost() {
        log.info(" storeHouseHost : {}", storeHouseHost);
        return storeHouseHost;
    }
}
