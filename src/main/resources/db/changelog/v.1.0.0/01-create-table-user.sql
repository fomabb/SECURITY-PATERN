CREATE TABLE users
(
    id               uuid PRIMARY KEY,
    username         VARCHAR(50)  not null,
    password         VARCHAR(255)  not null,
    email            varchar(100)  not null,
    role             varchar(20)  not null,
    date_create_user TIMESTAMP(6) not null
);