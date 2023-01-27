INSERT INTO users (username, password)VALUES ('user', '$2a$12$1.jqtZbqAjTbBMMQ6iffpuBp4mGwOTTb1ZJaHM8llpjGZG0mcNzBi');
INSERT INTO users (username, password)VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK');

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO account(first_name, last_name, zip_code, address, phone_number, email)VALUES ('silas', 'donders', '1112', 'hoofdstraat 14', '15369874589', 'donders@test.nl');

INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1001, 'cumcumber', 2.50, 14, 'vegetables');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1002, 'apple', 3.00, 89, 'fruit');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1003, 'carrot', 5.00, 48, 'vegetable');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1004, 'tomato', 6.15, 14, 'vegetables');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1005, 'mint leaves', 7.95, 89, 'herbs');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1006, 'dry herbs', 8.12, 14, 'herbs');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1007, 'lettuce', 9.50, 1124, 'vegetables');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1008, 'broccoli', 10.00, 36, 'vegetables');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1009, 'pear', 11.85, 78, 'fruit');
INSERT INTO products(id, product_name, price, available_stock, category)VALUES (1010, 'cherries', 12.60, 98, 'fruit');



