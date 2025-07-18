create table if not exists delivery_location
(
    id                 UUID           NOT NULL PRIMARY KEY UNIQUE,
    version            BIGINT         NOT NULL,
    city               VARCHAR(50)    NOT NULL,
    district           VARCHAR(50)    NOT NULL,
    price_rub          NUMERIC(10, 2) NOT NULL,
    fill               VARCHAR(10),
    polygon            GEOMETRY(Polygon, 4326),
    created_date       TIMESTAMP(6)   NOT NULL,
    last_modified_date TIMESTAMP(6)
);

create index polygons_geom_idx on delivery_location using gist (polygon)