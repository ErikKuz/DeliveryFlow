-- A registered delivery has not yet been physically received by the Moscow hub.
-- Existing advanced deliveries require manual review; resetting them would erase facts.
ALTER TABLE deliveries
    ADD COLUMN origin_hub_id UUID,
    ADD COLUMN destination_hub_id UUID,
    ADD COLUMN accepted_at TIMESTAMPTZ,
    ADD COLUMN accepted_by VARCHAR(120);

DO $$
BEGIN
    IF (SELECT count(*) FROM hubs WHERE type = 'MAIN_MOSCOW') <> 1 THEN
        RAISE EXCEPTION 'Stage 2 migration needs exactly one MAIN_MOSCOW hub';
    END IF;

    IF EXISTS (
        SELECT 1 FROM deliveries
        WHERE status NOT IN ('CREATED', 'WAITING_FOR_COURIER')
           OR courier_id IS NOT NULL
    ) THEN
        RAISE EXCEPTION 'Stage 2 migration requires manual review of advanced legacy deliveries';
    END IF;

    IF EXISTS (
        SELECT 1 FROM deliveries d
        WHERE d.pickup_country_code <> 'RU'
           OR d.delivery_country_code <> 'RU'
           OR NOT EXISTS (
               SELECT 1 FROM hubs h
               WHERE h.country_code = d.delivery_country_code
                 AND replace(lower(btrim(h.city)), 'ё', 'е') =
                     replace(lower(btrim(d.delivery_city)), 'ё', 'е')
           )
    ) THEN
        RAISE EXCEPTION 'Stage 2 migration requires manual review of unsupported legacy destinations';
    END IF;
END $$;

ALTER TABLE deliveries DROP CONSTRAINT chk_deliveries_status;

UPDATE deliveries d
SET origin_hub_id = main.id,
    destination_hub_id = destination.id,
    status = 'WAITING_FOR_ACCEPTANCE',
    updated_at = CURRENT_TIMESTAMP
FROM hubs main, hubs destination
WHERE main.type = 'MAIN_MOSCOW'
  AND destination.country_code = d.delivery_country_code
  AND replace(lower(btrim(destination.city)), 'ё', 'е') =
      replace(lower(btrim(d.delivery_city)), 'ё', 'е');

ALTER TABLE deliveries
    ALTER COLUMN origin_hub_id SET NOT NULL,
    ALTER COLUMN destination_hub_id SET NOT NULL,
    ADD CONSTRAINT fk_deliveries_origin_hub
        FOREIGN KEY (origin_hub_id) REFERENCES hubs (id),
    ADD CONSTRAINT fk_deliveries_destination_hub
        FOREIGN KEY (destination_hub_id) REFERENCES hubs (id),
    ADD CONSTRAINT chk_deliveries_status
        CHECK (status IN ('WAITING_FOR_ACCEPTANCE', 'ACCEPTED_AT_ORIGIN_HUB')),
    ADD CONSTRAINT chk_deliveries_acceptance
        CHECK (
            (status = 'WAITING_FOR_ACCEPTANCE'
                AND accepted_at IS NULL AND accepted_by IS NULL)
            OR
            (status = 'ACCEPTED_AT_ORIGIN_HUB'
                AND accepted_at IS NOT NULL
                AND accepted_by IS NOT NULL
                AND accepted_by ~ '[^[:space:]]')
        ),
    ADD CONSTRAINT chk_deliveries_no_courier_before_acceptance
        CHECK (status <> 'WAITING_FOR_ACCEPTANCE' OR courier_id IS NULL);
