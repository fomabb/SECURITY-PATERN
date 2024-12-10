CREATE TABLE news_translations
(
    id       BIGSERIAL PRIMARY KEY,
    news_id  BIGINT REFERENCES news (id) ON DELETE CASCADE,
    language VARCHAR(5),       -- Например, 'en', 'ru', 'de'
    title    VARCHAR(255),
    info     TEXT,
    UNIQUE (news_id, language) -- Уникальное сочетание новости и языка
);