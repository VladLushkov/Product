package com.store.tgbot.telegramm.service;

import com.store.tgbot.Utils;
import com.store.tgbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Команда "Старт"
 */
@Component
public class StartCommand extends ServiceCommand {
    @Autowired
    private UserService userService;

    public StartCommand() {
        super("start", "Старт");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        userService.createUser(user.getId(), userName, chat);

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Давайте начнём! Если Вам нужна помощь, нажмите /help");
    }
}