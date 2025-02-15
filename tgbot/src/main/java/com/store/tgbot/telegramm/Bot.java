package com.store.tgbot.telegramm;

import com.store.tgbot.Utils;
import com.store.tgbot.telegramm.nonCommand.NonCommand;
import com.store.tgbot.telegramm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * Собственно, бот
 */
@Slf4j
@Component
public class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private final NonCommand nonCommand;

    @Autowired
    public Bot(String tgBotName, String tgBotToken, StartCommand startCommand, HelpCommand helpCommand) {
        super();
        log.debug("Конструктор суперкласса отработал");
        this.BOT_NAME = tgBotName;
        this.BOT_TOKEN = tgBotToken;

        this.nonCommand = new NonCommand();
        register(startCommand);
        register(helpCommand);

        log.info("Бот создан!");
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = Utils.getUserName(msg);

        String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
    }


    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    public void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }
}