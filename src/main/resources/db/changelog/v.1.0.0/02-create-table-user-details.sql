CREATE TABLE user_details
(
    id      uuid primary key,
    address varchar(255),
    phone   varchar(16)
);

ALTER TABLE users
    ADD COLUMN user_details_id uuid
        CONSTRAINT user_test REFERENCES user_details (id);
