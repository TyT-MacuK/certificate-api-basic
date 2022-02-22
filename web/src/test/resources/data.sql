INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('photosession', 'beautiful photos on memory', 10.5, 5, '2021-12-01 12:00:00', '2021-12-01 12:00:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('spa', 'relax', 40, 30, '2021-01-10 14:00:00', '2021-01-10 14:00:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('restaurant', 'delicious food', 17.5, 20, '2021-05-05 15:00:00', '2021-05-05 15:00:00');

INSERT INTO tag (name) VALUES ('food');
INSERT INTO tag (name) VALUES ('health');
INSERT INTO tag (name) VALUES ('beauty');
INSERT INTO tag (name) VALUES ('speed');

INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 2);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 3);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 1);

INSERT INTO role VALUES (1,'ROLE_ADMIN');
INSERT INTO role VALUES (2,'ROLE_USER');

INSERT INTO users (name, email, password, role_id) VALUES ('Jack', 'jack@gmail.com', '$2a$10$Ue6OB0vbVRdtHgf1pv79kOuOovCh0qEql6lQ9tA0V0Vb88hpLIYDu', 1);
INSERT INTO users (name, email, password, role_id) VALUES ('Tom', 'tom@gmail.com', '$2a$10$Ue6OB0vbVRdtHgf1pv79kOuOovCh0qEql6lQ9tA0V0Vb88hpLIYDu', 2);

INSERT INTO orders (create_date, cost, gift_certificate_id, user_id) VALUES ('2021-12-27 14:00:00', 10.50, 1, 1);
INSERT INTO orders (create_date, cost, gift_certificate_id, user_id) VALUES ('2021-12-25 10:00:00', 40, 2, 2);
INSERT INTO orders (create_date, cost, gift_certificate_id, user_id) VALUES ('2021-12-27 14:00:00', 40, 2, 1);