CREATE TABLE farm(
    id INT NOT NULL AUTO_INCREMENT,
    room_temp_and_humidity_sensor_id INT,
    location VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (room_temp_and_humidity_sensor_id) REFERENCES sensor(id)
);