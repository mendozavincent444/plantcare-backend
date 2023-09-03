CREATE TABLE product(
    id INT NOT NULL AUTO_INCREMENT,
    sku VARCHAR(20) NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100) NOT NULL,
    unit_price DECIMAL(19, 2) NOT NULL,
    active TINYINT(1) NOT NULL,
    units_in_stock INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);