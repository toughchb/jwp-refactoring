ALTER TABLE order_line_item ADD COLUMN menu_name varchar(255) NOT NULL;
ALTER TABLE order_line_item ADD COLUMN menu_price DECIMAL(19, 2) NOT NULL;
