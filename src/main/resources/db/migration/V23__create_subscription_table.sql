CREATE TABLE subscription(
  id INT NOT NULL AUTO_INCREMENT,
  subscription_type VARCHAR(10) NOT NULL,
  start_date DATETIME(6) NOT NULL,
  end_date DATETIME(6) NOT NULL,
  PRIMARY KEY (id)
);