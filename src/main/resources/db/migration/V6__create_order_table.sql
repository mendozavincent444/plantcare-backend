CREATE TABLE orders(
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    billing_address_id INT NOT NULL,
    shipping_address_id INT NOT NULL,
    total_quantity INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    date_created DATETIME(6) NOT NULL,
    last_updated DATETIME(6) NOT NULL,
    total_price DECIMAL(19,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (billing_address_id) REFERENCES address(id),
    FOREIGN KEY (shipping_address_id) REFERENCES address(id)
);