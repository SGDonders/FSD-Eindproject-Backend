INSERT INTO account(user_name, first_name, last_name, zip_code, address, phone_number, email)VALUES ('HermanDeBoer', 'Herman', 'De jager', '3485 GL', 'Achterweg 14 Baarngoot', '0854698255', 'HermanDeJager@gmail.nl');
INSERT INTO account(user_name, first_name, last_name, zip_code, address, phone_number, email)VALUES ('LtSourTangie', 'Rik', 'De snikkel', '1211 AD', 'celebes 10 Hilversum', '0628018915', 'RikMZdikkeSnikkel@hotmail.com');


INSERT INTO users (username, password, account_user_name)VALUES ('HermanDeBoer', '$2a$12$BL8/kI4gbs5Lr5sgnforpe2Jx2UcKqi7KgkGR8OcjptO/yowrk5Kq', 'HermanDeBoer'); --password
INSERT INTO users (username, password, account_user_name)VALUES ('LtSourTangie', '$2a$12$5zf6RaczBTJ/5w/Zt7ICauzLFFyk2wF9RJXzDnuA9QHF/lJbaUuwu', 'LtSourTangie'); --UserPassword


INSERT INTO authorities (username, authority) VALUES ('LtSourTangie', 'ROLE_USER');
-- INSERT INTO authorities (username, authority) VALUES ('HermanDeBoer', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('HermanDeBoer', 'ROLE_ADMIN');


INSERT INTO products(product_name, id,  price, available_stock, category)VALUES ('cucumber', 101, 2.55, 14, 'vegetable');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('apple', 102, 2.45, 89, 'fruit');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('carrot', 103, 3.75, 48, 'vegetable');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('tomato', 104, 6.15, 14, 'vegetable');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('mint leaves', 105, 7.95, 89, 'herbs');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('dry herbs', 106, 8.12, 14, 'herbs');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('lettuce', 107, 9, 1124, 'vegetable');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('broccoli', 108, 10.00, 36, 'vegetable');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('pear', 109, 11.85, 78, 'fruit');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('cherries', 110, 12, 98, 'fruit');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('pepper', 111, 1, 78, 'vegetable');
INSERT INTO products(product_name, id, price, available_stock, category)VALUES ('onion', 112, 2, 98, 'vegetable');


