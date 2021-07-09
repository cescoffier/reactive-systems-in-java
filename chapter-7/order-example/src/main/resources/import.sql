INSERT INTO userProfile(id, name) VALUES (nextval('hibernate_sequence'), 'Bob');
INSERT INTO userProfile(id, name) VALUES (nextval('hibernate_sequence'), 'Alice');
INSERT INTO userProfile(id, name) VALUES (nextval('hibernate_sequence'), 'Tom');


INSERT INTO product(id, name) VALUES (nextval('hibernate_sequence'), 'Hat');
INSERT INTO product(id, name) VALUES (nextval('hibernate_sequence'), 'Pen');
INSERT INTO product(id, name) VALUES (nextval('hibernate_sequence'), 'Notebook');
INSERT INTO product(id, name) VALUES (nextval('hibernate_sequence'), 'Tee-shirt');
INSERT INTO product(id, name) VALUES (nextval('hibernate_sequence'), 'Sticker');


-- INSERT INTO orders(id, userId, products) VALUES (nextval('hibernate_sequence'), 1, 1);