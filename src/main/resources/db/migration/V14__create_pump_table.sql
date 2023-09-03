CREATE TABLE pump(
    id INT NOT NULL,
    farm_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (farm_id) REFERENCES farm(id),
    PRIMARY KEY (id)
);