CREATE TABLE farm(
    id INT NOT NULL AUTO_INCREMENT,
    owner_id INT NOT NULL,
    room_temp_and_humidity_sensor_id INT NULL,
    location VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES user(id)
);