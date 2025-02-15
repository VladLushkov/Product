package com.store.tgbot.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;

    @Column
    @NotBlank
    private String name;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}