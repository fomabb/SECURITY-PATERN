CREATE TABLE news
(
    id   BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

comment on column news.id is 'Идентификационный номер новости';
comment on column news.date is 'Время поста';