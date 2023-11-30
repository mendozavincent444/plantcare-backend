CREATE TABLE subscription(
  id INT NOT NULL AUTO_INCREMENT,
  subscription_type_id INT NOT NULL,
  start_date DATETIME(6) NOT NULL,
  end_date DATETIME(6) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (subscription_type_id) REFERENCES subscription_type(id)
);