ALTER TABLE transaction
MODIFY COLUMN shipping_address_id INT NULL,
MODIFY COLUMN description VARCHAR(512) NOT NULL;