CREATE TABLE order_item(
    id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(19,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);