ALTER TABLE user
ADD subscription_id INT NULL,
ADD FOREIGN KEY (subscription_id) REFERENCES subscription(id);