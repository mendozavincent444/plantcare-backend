CREATE TABLE notification(
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    date DATETIME(6) NOT NULL,
    content VARCHAR(255) NOT NULL,
    title VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);