CREATE TABLE task(
    id INT NOT NULL AUTO_INCREMENT,
    farm_id INT NOT NULL,
    plant_id INT NOT NULL,
    container_id INT NOT NULL,
    date_planted DATETIME(6) NOT NULL,
    harvest_date DATETIME(6) NOT NULL,
    status VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (farm_id) REFERENCES farm(id),
    FOREIGN KEY (plant_id) REFERENCES plant(id),
    FOREIGN KEY (container_id) REFERENCES container(id)
);