CREATE TABLE comments
(
    id      BIGSERIAL PRIMARY KEY,
    content TEXT   NOT NULL,
    news_id BIGINT NOT NULL,
    user_id uuid   NOT NULL,
    CONSTRAINT fk_news_id FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

comment on table comments is 'Таблица для комментариев пользователей';
comment on column comments.id is 'Идентификационный номер контента';
comment on column comments.content is 'Контент';
comment on column comments.news_id is 'Связующее поле с новостью';
comment on column comments.user_id is 'Связующее поле с пользователем';