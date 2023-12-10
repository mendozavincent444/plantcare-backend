ALTER TABLE notification
ADD is_read_notification TINYINT(1) NOT NULL,
DROP CONSTRAINT notification_ibfk_1,
DROP COLUMN user_id;
