ALTER TABLE users
    ADD COLUMN if not exists reset_token text;