CREATE TABLE archive_deleted_user
(
    id                       BIGSERIAL PRIMARY KEY,
    message                  TEXT NOT NULL,
    add_date_time_to_archive VARCHAR(50)
);

comment on table archive_deleted_user is 'Архив удаленных пользователей';
comment on column archive_deleted_user.id is 'Идентификационный номер архива';
comment on column archive_deleted_user.message is 'Информация об удаленном пользователе';
comment on column archive_deleted_user.add_date_time_to_archive is 'Фактическое время удаления пользователя';