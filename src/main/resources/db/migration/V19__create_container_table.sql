CREATE TABLE container(
    id INT NOT NULL AUTO_INCREMENT,
    arduino_board_id INT,
    plant_id INT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (arduino_board_id) REFERENCES arduino_board(id),
    FOREIGN KEY (plant_id) REFERENCES plant(id)
);