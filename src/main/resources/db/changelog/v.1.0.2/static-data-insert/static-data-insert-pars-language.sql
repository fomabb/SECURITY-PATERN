INSERT INTO pars_language_data (language, title, info, news_id)
VALUES ('el', 'Τίτλος 1 για νέα 1', 'Πληροφορίες 1 για νέα 1', 1),
       ('el', 'Τίτλος 1 για νέα 2', 'Πληροφορίες 1 για νέα 2', 2),
       ('el', 'Τίτλος 1 για νέα 3', 'Πληροφορίες 1 για νέα 3', 3);


comment on table pars_language_data is 'Таблица для добавления нового языка';
comment on column pars_language_data.language is 'язык';
comment on column pars_language_data.title is 'название новости';
comment on column pars_language_data.info is 'Информация по новости';
comment on column pars_language_data.news_id is 'Уникальный номер новости';
