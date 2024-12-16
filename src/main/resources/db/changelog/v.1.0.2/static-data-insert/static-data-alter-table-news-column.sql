ALTER TABLE news
    ADD COLUMN update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

comment on table news is 'Таблица новостей';
comment on column news.update_at is 'Время и дата Обновления новости';
