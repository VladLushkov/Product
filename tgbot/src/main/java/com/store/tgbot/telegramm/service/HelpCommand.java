package com.store.tgbot.telegramm.service;

import com.store.tgbot.Utils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Команда "Помощь"
 */
@Component
public class HelpCommand extends ServiceCommand {
    public HelpCommand() {
        super("help", "Помощь");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Я бот, который поможет Вашим детям быстро научиться считать в уме\n\n");
    }
}