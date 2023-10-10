CREATE TABLE user_notification(
    notification_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (notification_id, user_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (notification_id) REFERENCES notification(id)
);