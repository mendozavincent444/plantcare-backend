CREATE TABLE subscription_type(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  subscription_benefits VARCHAR(512) NOT NULL,
  price DECIMAL(19,2) NOT NULL,
  PRIMARY KEY (id)
);