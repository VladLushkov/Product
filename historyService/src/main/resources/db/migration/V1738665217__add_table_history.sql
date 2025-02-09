create table history
(
    id             serial primary key,
    operation_type varchar(50) not null,
    product_name   VARCHAR(50) not null,
    old_count      int         not null,
    new_count      int         not null,

    created_at     timestamp default CURRENT_TIMESTAMP
);

COMMENT
ON TABLE history IS 'Таблица с историей изменений товаров';
COMMENT
ON COLUMN history.id IS 'Идентификатор';
COMMENT
ON COLUMN history.operation_type IS 'Тип операции';
COMMENT
ON COLUMN history.product_name IS 'Название товара';
COMMENT
ON COLUMN history.old_count IS 'Старое количество';
COMMENT
ON COLUMN history.new_count IS 'Новое количество';
COMMENT
ON COLUMN history.created_at IS 'Дата создания';