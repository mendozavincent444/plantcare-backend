CREATE TABLE notification(
    id INT NOT NULL AUTO_INCREMENT,
    sender_id INT NOT NULL,
    date DATETIME(6) NOT NULL,
    content VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    type VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES user(id)
);