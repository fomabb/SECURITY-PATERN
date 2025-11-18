CREATE TABLE group_chats
(
    id                 UUID PRIMARY KEY,
    title              VARCHAR(100)             NOT NULL,
    description        TEXT,
    cover_url          VARCHAR(255),
    type               VARCHAR(50)              NOT NULL,
    is_club_group      BOOLEAN                  NOT NULL DEFAULT FALSE,
    club_id            UUID,
    created_by_user_id UUID                     NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_group_chat_type ON group_chats (type);
CREATE INDEX idx_group_chat_created_at ON group_chats (created_at DESC);