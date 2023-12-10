CREATE TABLE user_notification(
    user_id INT NOT NULL,
    notification_id INT NOT NULL,
    PRIMARY KEY (user_id, notification_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (notification_id) REFERENCES notification(id)
);