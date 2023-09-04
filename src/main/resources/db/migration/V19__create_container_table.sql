CREATE TABLE container(
    id INT NOT NULL AUTO_INCREMENT,
    arduino_board_id INT,
    plant_id INT,
    farm_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (arduino_board_id) REFERENCES arduino_board(id),
    FOREIGN KEY (plant_id) REFERENCES plant(id),
    FOREIGN KEY (farm_id) REFERENCES farm(id)
);