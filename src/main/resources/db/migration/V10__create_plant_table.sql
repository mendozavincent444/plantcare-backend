CREATE TABLE plant(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    maximum_ec DECIMAL(5,2) NOT NULL,
    maximum_ph DECIMAL(5,2) NOT NULL,
    minimum_ec DECIMAL(5,2) NOT NULL,
    minimum_ph DECIMAL(5,2) NOT NULL,
    days_to_maturity VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);