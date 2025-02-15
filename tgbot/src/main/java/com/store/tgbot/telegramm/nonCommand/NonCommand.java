package com.store.tgbot.telegramm.nonCommand;
/**
 * Обработка сообщения, не являющегося командой (т.е. обычного текста не начинающегося с "/")
 */
public class NonCommand {
    public String nonCommandExecute(Long chatId, String userName, String text) {

        return "Ок";
    }
}