CREATE TABLE pars_language_data
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    info  TEXT,
    news_id BIGINT
);