ALTER TABLE farm
ADD FOREIGN KEY (room_temp_and_humidity_sensor_id) REFERENCES sensor(id);