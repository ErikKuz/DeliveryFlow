-- Stage 2 draft: Russia only, one hub per served city.
-- Hub records and delivery-to-hub references will be added separately.
CREATE TABLE hubs (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL CHECK (name ~ '[^[:space:]]'),
    type VARCHAR(16) NOT NULL,

    country_code VARCHAR(2) NOT NULL,
    city VARCHAR(120) NOT NULL CHECK (city ~ '[^[:space:]]'),
    street VARCHAR(160) NOT NULL CHECK (street ~ '[^[:space:]]'),
    building VARCHAR(30) NOT NULL CHECK (building ~ '[^[:space:]]'),
    apartment VARCHAR(30),

    CONSTRAINT chk_hubs_type CHECK (type IN (
        'MAIN_MOSCOW',
        'REGIONAL'
    )),
    CONSTRAINT chk_hubs_country_code CHECK (country_code = 'RU'),
    CONSTRAINT chk_hubs_type_city CHECK (
        (type = 'MAIN_MOSCOW' AND lower(btrim(city)) = 'москва')
        OR
        (type = 'REGIONAL' AND lower(btrim(city)) <> 'москва')
    )
);

-- Compare cities after trimming outer spaces, ignoring case and ё/е.
-- The original city spelling is preserved in the table.
CREATE UNIQUE INDEX uq_hubs_country_city
    ON hubs (country_code, replace(lower(btrim(city)), 'ё', 'е'));
