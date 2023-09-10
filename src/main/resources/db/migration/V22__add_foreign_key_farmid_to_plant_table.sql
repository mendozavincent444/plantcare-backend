ALTER TABLE plant
ADD farm_id INT NOT NULL,
ADD FOREIGN KEY (farm_id) references farm(id);