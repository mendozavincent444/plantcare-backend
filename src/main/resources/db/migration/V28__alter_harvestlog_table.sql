ALTER TABLE harvest_log
DROP FOREIGN KEY harvest_log_ibfk_1,
DROP COLUMN task_id,
ADD plant_name VARCHAR(50) NOT NULL;
