CREATE TABLE news_translations
(
    id       BIGSERIAL PRIMARY KEY,
    news_id  BIGINT REFERENCES news (id) ON DELETE CASCADE,
    language VARCHAR(5),       -- Например, 'en', 'ru', 'de'
    title    VARCHAR(255),
    info     TEXT,
    UNIQUE (news_id, language) -- Уникальное сочетание новости и языка
);

comment on table news_translations is 'Таблица, где хранятся данные новостей на разных языках';
comment on column news_translations.news_id is 'Связующее поле переводов с новостью';
comment on column news_translations.language is 'Указания языка на каком написана новость';
comment on column news_translations.title is 'Информация по новости';
comment on column news_translations.info is 'Контент новости';