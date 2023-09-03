CREATE TABLE harvest_log(
    id INT NOT NULL AUTO_INCREMENT,
    task_id INT NOT NULL,
    farmer_id INT NOT NULL,
    harvested_date DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES task(id),
    FOREIGN KEY (farmer_id) REFERENCES user(id)
);