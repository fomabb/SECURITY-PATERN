CREATE SEQUENCE IF NOT EXISTS shop_seq
    INCREMENT BY 1
    MINVALUE 1
    START WITH 1
    CACHE 1;

CREATE TABLE shop
(
    id            BIGINT                NOT NULL PRIMARY KEY,
    name          VARCHAR,
    address       VARCHAR,
    lat           FLOAT                 NOT NULL,
    lon           FLOAT                 NOT NULL,
    working_hours VARCHAR,
    position      geometry(POINT, 4326) NOT NULL
);