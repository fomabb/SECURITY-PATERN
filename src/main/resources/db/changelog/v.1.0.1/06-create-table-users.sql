CREATE TABLE users
(
    id         uuid PRIMARY KEY,
    first_name VARCHAR(50)  not null,
    username   varchar(100) not null,
    password   VARCHAR(255) not null,
    role       varchar(20)  not null
-- ,
--     date_create_user timestamp(6)
);