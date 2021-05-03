INSERT INTO customer(id, name) VALUES (nextval('hibernate_sequence'), 'Debbie Hall');
INSERT INTO customer(id, name) VALUES (nextval('hibernate_sequence'), 'Gary Parmenter');
INSERT INTO customer(id, name) VALUES (nextval('hibernate_sequence'), 'Mary Shoestring');
INSERT INTO customer(id, name) VALUES (nextval('hibernate_sequence'), 'Virginia Mayweather');

INSERT INTO orders(id, customerId, description, total) VALUES (nextval('hibernate_sequence'), 1, 'Three seater sofa', 2389.32);
INSERT INTO orders(id, customerId, description, total) VALUES (nextval('hibernate_sequence'), 1, 'Curtains', 240.98);
INSERT INTO orders(id, customerId, description, total) VALUES (nextval('hibernate_sequence'), 2, 'BBQ Grill', 183.99);
INSERT INTO orders(id, customerId, description, total) VALUES (nextval('hibernate_sequence'), 3, '6 seater dining table', 1344.99);
