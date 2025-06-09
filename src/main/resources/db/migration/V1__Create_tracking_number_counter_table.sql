CREATE TABLE IF NOT EXISTS tracking_number_counter (
    id VARCHAR AUTO_INCREMENT PRIMARY KEY,
    counter BIGINT NOT NULL,
    last_timestamp VARCHAR(255)
);

INSERT INTO tracking_number_counter (id, counter, last_timestamp)
VALUES (1, 0, '202506091230');

CREATE TABLE IF NOT EXISTS tracking_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    origin_country_id VARCHAR(255) NOT NULL,
    destination_country_id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_slug VARCHAR(255) NOT NULL,
    weight DOUBLE NOT NULL,
    tracking_number VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);


