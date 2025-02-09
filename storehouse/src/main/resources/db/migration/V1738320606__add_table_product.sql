create table product
(
    id         serial primary key,
    name       varchar(50) not null unique,
    type       VARCHAR(50) not null,
    count      int         not null,

    created_at timestamp default CURRENT_TIMESTAMP,
    update_at  timestamp default CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_product_name ON product (name);

COMMENT ON TABLE product IS 'Таблица с товарами на складе';
COMMENT ON COLUMN product.id IS 'Идентификатор';
COMMENT ON COLUMN product.name IS 'Название товара';
COMMENT ON COLUMN product.type IS 'Тип товара';
COMMENT ON COLUMN product.count IS 'Количество товара';
COMMENT ON COLUMN product.created_at IS 'Дата создания';
COMMENT ON COLUMN product.update_at IS 'Даиа обновления';

create table product_type
(
    id        serial primary key,
    name      varchar(50) not null,
    is_enable BOOLEAN default true
);

COMMENT ON TABLE product_type IS 'Таблица типами товара';
COMMENT ON COLUMN product_type.id IS 'Идентификатор';
COMMENT ON COLUMN product_type.name IS 'Название типа товара';
COMMENT ON COLUMN product_type.is_enable IS 'Активен/Не активен тип';

insert into product_type (name)
values ('FOOD'), ('FURNITURE'), ('CLOTH');

