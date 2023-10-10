CREATE TABLE order_item(
    id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    transaction_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(19,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (transaction_id) REFERENCES transaction(id)
);