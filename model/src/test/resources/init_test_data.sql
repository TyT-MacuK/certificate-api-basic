DELETE DROM certificate.gift_certificate;
INSERT INTO certificate.gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('photosession', 'beautiful photos on memory', 10.5, 5, '2021-12-01 12:00:00', '2021-12-01 12:00:00');

INSERT INTO certificate.gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('spa', 'relax', 40, 30, '2021-01-10 14:00:00', '2021-01-10 14:00:00');

INSERT INTO certificate.gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('restaurant', 'delicious food', 17.5, 20, '2021-05-05 15:00:00', '2021-05-05 15:00:00');

INSERT INTO certificate.tag (name) VALUES ('food');

INSERT INTO certificate.tag (name) VALUES ('health');

INSERT INTO certificate.tag (name) VALUES ('beauty');

INSERT INTO certificate.tag (name) VALUES ('speed');

INSERT INTO certificate.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 2);

INSERT INTO certificate.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 3);

INSERT INTO certificate.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 1);