CREATE TABLE user_farm(
    user_id INT NOT NULL,
    farm_id INT NOT NULL,
    PRIMARY KEY (user_id, farm_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (farm_id) REFERENCES farm(id)
);