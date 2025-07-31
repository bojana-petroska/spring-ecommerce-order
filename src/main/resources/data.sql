INSERT INTO products (name, price, image_url) VALUES ('Iron Man', 1000, 'https://alexnsan.comics/imageurl/1');
INSERT INTO products (name, price, image_url) VALUES ('X-men', 1000, 'https://alexnsan.comics/imageurl/2');
INSERT INTO products (name, price, image_url) VALUES ('Superman', 1000, 'https://alexnsan.comics/imageurl/3');
INSERT INTO products (name, price, image_url) VALUES ('Naruto', 1000, 'https://alexnsan.comics/imageurl/4');
INSERT INTO products (name, price, image_url) VALUES ('Full Metal Alchemist', 1000, 'https://alexnsan.comics/imageurl/5');
INSERT INTO products (name, price, image_url) VALUES ('Batman', 1000, 'https://alexnsan.comics/imageurl/6');
INSERT INTO products (name, price, image_url) VALUES ('Man', 1000, 'https://alexnsan.comics/imageurl/7');
;

INSERT INTO members (email, password, role) VALUES ( 'san@htc.com', 'san1234', 'admin');
INSERT INTO members (email, password, role) VALUES ( 'dan@htc.com', 'dan1234', 'admin');
INSERT INTO members (email, password) VALUES ( 'ann@htc.com', 'ann1234');
INSERT INTO members (email, password) VALUES ( 'min@htc.com', 'min1234');
;

-- Cart Item Events with varying timestamps
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (1, 1, 1, CURRENT_TIMESTAMP - INTERVAL '5' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (1, 2, 1, CURRENT_TIMESTAMP - INTERVAL '3' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (2, 2, 1, CURRENT_TIMESTAMP - INTERVAL '2' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (3, 2, 1, CURRENT_TIMESTAMP - INTERVAL '1' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (1, 3, 1, CURRENT_TIMESTAMP - INTERVAL '10' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (2, 3, 1, CURRENT_TIMESTAMP - INTERVAL '20' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (3, 3, 1, CURRENT_TIMESTAMP - INTERVAL '25' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (1, 4, 1, CURRENT_TIMESTAMP - INTERVAL '6' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (2, 5, 1, CURRENT_TIMESTAMP - INTERVAL '7' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (3, 5, 1, CURRENT_TIMESTAMP - INTERVAL '8' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (1, 5, 1, CURRENT_TIMESTAMP - INTERVAL '9' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (1, 6, 1, CURRENT_TIMESTAMP - INTERVAL '6' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (2, 6, 1, CURRENT_TIMESTAMP - INTERVAL '7' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (3, 3, 1, CURRENT_TIMESTAMP - INTERVAL '8' DAY);
INSERT INTO cart_items (member_id, product_id, quantity, created_at) VALUES (1, 1, 1, CURRENT_TIMESTAMP - INTERVAL '9' DAY);
;