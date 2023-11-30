ALTER TABLE farm
ADD FOREIGN KEY (main_arduino_board_id) REFERENCES arduino_board(id);