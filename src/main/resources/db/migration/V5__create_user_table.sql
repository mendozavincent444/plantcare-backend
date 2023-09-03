CREATE TABLE user(
    id INT NOT NULL AUTO_INCREMENT,
    role_id INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);