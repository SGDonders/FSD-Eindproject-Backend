INSERT INTO users (username, password)VALUES ('user', '$2a$12$1.jqtZbqAjTbBMMQ6iffpuBp4mGwOTTb1ZJaHM8llpjGZG0mcNzBi');
INSERT INTO users (username, password)VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK');

INSERT INTO account(first_name, last_name, zip_code, address, phone_number, email)VALUES ('silas', 'donders', '1112', 'hoofdstraat 14', '15369874589', 'donders@test.nl');

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');



--checken voor toevoegen van role