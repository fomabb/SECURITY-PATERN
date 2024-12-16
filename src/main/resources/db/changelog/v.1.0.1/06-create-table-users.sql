CREATE TABLE users
(
    id               uuid PRIMARY KEY,
    first_name       VARCHAR(50)  not null,
    username         varchar(100) not null,
    password         VARCHAR(255) not null,
    role             varchar(20)  not null,
    date_create_user timestamp(6)
);

comment on table users is 'Таблица пользователей';
comment on column users.first_name is 'Имя пользователя';
comment on column users.username is 'Поля для логина пользователя email/login';
comment on column users.password is 'Поле для пароля пользователя';
comment on column users.role is 'Поле для роли пользователя admin/client/employee';
comment on column users.date_create_user is 'Дата и время создания пользователя';