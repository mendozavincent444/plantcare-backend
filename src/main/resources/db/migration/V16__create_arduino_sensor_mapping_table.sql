CREATE TABLE arduino_sensor_mapping(
    id INT NOT NULL AUTO_INCREMENT,
    arduino_board_id INT NOT NULL,
    sensor_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (arduino_board_id) REFERENCES arduino_board(id),
    FOREIGN KEY (sensor_id) REFERENCES sensor(id)
);