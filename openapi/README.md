# DeliveryFlow API: первый контракт

Файл [`delivery-api.v1.yaml`](delivery-api.v1.yaml) — источник истины для публичного REST API. Пока MarketFlow не подключён, тестовый клиент передаёт JSON из примера `CreateDeliveryRequest`.

Одна доставка относится к одной части заказа продавца (`sellerOrderId`). Поэтому у общего `orderId` впоследствии может быть несколько доставок. Запрос лишь регистрирует заявку: статус `PICKED_UP` наступит только после подтверждения курьером фактического получения товара.

`POST /api/v1/deliveries` возвращает `201` для новой доставки и `200` при точном повторе по `sellerOrderId`. Если повтор содержит другие адреса, контакты, `orderId` или `sellerId`, он возвращает `409`. Сравниваются нормализованные исходные данные, а не JSON как строка. Авторизацию токеном реализуем на отдельном этапе; сейчас она зафиксирована как требование контракта.

## План таблицы `deliveries`

Схему PostgreSQL задаёт миграция [`V1__create_deliveries.sql`](../delivery-service/src/main/resources/db/migration/V1__create_deliveries.sql). В ней есть:

| Группа | Колонки | Зачем |
| --- | --- | --- |
| Идентификаторы | `id` UUID, `order_id` BIGINT, `seller_order_id` BIGINT, `seller_id` BIGINT | Связь с частью заказа без доступа к БД MarketFlow |
| Пункт забора | `pickup_contact_name`, `pickup_contact_phone`, `pickup_country_code`, `pickup_city`, `pickup_street`, `pickup_building`, `pickup_apartment` | Снимок данных для забора товара |
| Получатель | `recipient_name`, `recipient_phone`, `delivery_country_code`, `delivery_city`, `delivery_street`, `delivery_building`, `delivery_apartment` | Снимок данных для вручения |
| Состояние | `status` VARCHAR, `courier_id` UUID, `version` BIGINT | Статус, ссылка на курьера и защита от конфликтующих обновлений |
| Время | `created_at`, `updated_at` TIMESTAMPTZ | Аудит изменений |

Уникальное ограничение на `seller_order_id` защищает от двух доставок при одновременных повторных запросах. Индекс на `order_id` поможет позднее находить все доставки общего заказа. `courier_id` остаётся обычным значением без внешнего ключа на базу `courier-service`.
