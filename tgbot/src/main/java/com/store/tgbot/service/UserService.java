package com.store.tgbot.service;

import com.store.tgbot.model.User;
import com.store.tgbot.repository.UserRepository;
import com.store.tgbot.telegramm.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private Bot tgBot;

    @Transactional
    public void createUser(Long userId, String userName, Chat chat) {
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setChatId(chat.getId());
        user.setIsActive(true);
        userRepository.save(user);
    }

    public void sendMessage(String text) {
        List<User> userList = userRepository.getUserByIsActiveTrue();
        userList.forEach(u -> tgBot.setAnswer(u.getChatId(), u.getName(), text));
    }
}