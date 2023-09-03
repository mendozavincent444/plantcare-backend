CREATE TABLE transaction (
    id INT NOT NULL AUTO_INCREMENT,
    date DATETIME(6) NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);