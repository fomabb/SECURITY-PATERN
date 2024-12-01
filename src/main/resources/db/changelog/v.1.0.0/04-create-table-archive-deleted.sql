CREATE TABLE archive_deleted_user
(
    id                       BIGSERIAL PRIMARY KEY,
    message                  TEXT NOT NULL,
    add_date_time_to_archive VARCHAR(50)
);