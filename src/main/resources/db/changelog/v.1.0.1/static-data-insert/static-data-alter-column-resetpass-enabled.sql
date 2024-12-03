ALTER TABLE users
    ADD COLUMN if not exists reset_token text;
ALTER TABLE users
    ADD COLUMN if not exists enabled boolean;