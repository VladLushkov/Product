package com.store.tgbot.repository;

import com.store.tgbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getUserByIsActiveTrue();
}