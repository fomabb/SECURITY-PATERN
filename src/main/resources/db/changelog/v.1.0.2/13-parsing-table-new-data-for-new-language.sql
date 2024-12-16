CREATE TABLE pars_language_data
(
    id       BIGSERIAL PRIMARY KEY,
    language VARCHAR(5),
    title    VARCHAR(255),
    info     TEXT,
    news_id  BIGINT
);

comment on table pars_language_data is 'Таблица для pars новых новостей со свойством добавления нового языка';
comment on column pars_language_data.id is 'ID новости';
comment on column pars_language_data.language is 'Колонка для указания, на каком языке новость';
comment on column pars_language_data.title is 'Колонка для названия новости';
comment on column pars_language_data.info is 'Колонка для информации в посту';
comment on column pars_language_data.news_id is 'Связующее поле для таблицы news_translation и pars_language_data';