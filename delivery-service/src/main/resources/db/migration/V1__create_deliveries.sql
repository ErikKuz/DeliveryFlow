CREATE TABLE deliveries (
    id UUID PRIMARY KEY,
    order_id BIGINT NOT NULL CHECK (order_id > 0),
    seller_order_id BIGINT NOT NULL CHECK (seller_order_id > 0),
    seller_id BIGINT NOT NULL CHECK (seller_id > 0),

    pickup_contact_name VARCHAR(120) NOT NULL,
    pickup_contact_phone VARCHAR(16) NOT NULL,
    pickup_country_code CHAR(2) NOT NULL,
    pickup_city VARCHAR(120) NOT NULL,
    pickup_street VARCHAR(160) NOT NULL,
    pickup_building VARCHAR(30) NOT NULL,
    pickup_apartment VARCHAR(30),

    recipient_name VARCHAR(120) NOT NULL,
    recipient_phone VARCHAR(16) NOT NULL,
    delivery_country_code CHAR(2) NOT NULL,
    delivery_city VARCHAR(120) NOT NULL,
    delivery_street VARCHAR(160) NOT NULL,
    delivery_building VARCHAR(30) NOT NULL,
    delivery_apartment VARCHAR(30),

    status VARCHAR(32) NOT NULL,
    courier_id UUID,
    version BIGINT NOT NULL DEFAULT 0 CHECK (version >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_deliveries_seller_order_id UNIQUE (seller_order_id),
    CONSTRAINT chk_deliveries_status CHECK (status IN (
        'CREATED',
        'WAITING_FOR_COURIER',
        'COURIER_ASSIGNED',
        'PICKED_UP',
        'IN_TRANSIT',
        'DELIVERED',
        'ASSIGNMENT_FAILED',
        'DELIVERY_FAILED',
        'CANCELLED'
    ))
);

CREATE INDEX idx_deliveries_order_id ON deliveries (order_id);
