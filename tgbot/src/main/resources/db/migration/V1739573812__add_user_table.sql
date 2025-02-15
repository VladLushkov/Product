create table users
(
    id         bigserial primary key,
    name       varchar(50) not null,
    chat_id    bigserial   not null,
    is_active  boolean     not null default true,

    created_at timestamp            default CURRENT_TIMESTAMP,
    update_at  timestamp            default CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_user_id_chat_id ON users (id, chat_id);

COMMENT
ON TABLE users IS 'Таблица с пользователями телеграмм бота';
COMMENT
ON COLUMN users.id IS 'Идентификатор';
COMMENT
ON COLUMN users.name IS 'Имя пользователя';
COMMENT
ON COLUMN users.chat_id IS 'Идентификатор чата пользователя';
COMMENT
ON COLUMN users.created_at IS 'Дата создания';
COMMENT
ON COLUMN users.update_at IS 'Даиа обновления';