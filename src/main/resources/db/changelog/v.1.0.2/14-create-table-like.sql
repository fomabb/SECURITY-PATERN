CREATE TABLE likes
(
    id      BIGSERIAL PRIMARY KEY,
    news_id BIGINT NOT NULL,
    user_id uuid   NOT NULL,
    CONSTRAINT fk_news_id FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

comment on table likes is 'Таблица для лайков пользователей';
comment on column likes.id is 'Идентификационный номер лайка';
comment on column likes.news_id is 'Связующее поле с новостью';
comment on column likes.user_id is 'Связующее поле с пользователем';