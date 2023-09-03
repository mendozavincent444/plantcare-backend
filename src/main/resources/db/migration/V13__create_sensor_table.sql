CREATE TABLE sensor(
    id INT NOT NULL AUTO_INCREMENT,
    sensor_type_id INT NOT NULL,
    farm_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sensor_type_id) REFERENCES sensor_type(id),
    FOREIGN KEY (farm_id) REFERENCES farm(id)
);