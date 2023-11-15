ALTER TABLE user
ADD reset_token VARCHAR(50) NULL,
ADD token_expiration DATETIME NULL;