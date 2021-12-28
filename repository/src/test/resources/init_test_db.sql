USE certificate;
CREATE TABLE IF NOT EXISTS `certificate`.`gift_certificate`
(
    `id`               INT          NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(45)  NOT NULL,
    `description`      VARCHAR(100) NOT NULL,
    `price`            DECIMAL      NOT NULL,
    `duration`         INT          NOT NULL,
    `create_date`      TIMESTAMP    NOT NULL,
    `last_update_date` TIMESTAMP    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `certificate`.`tag`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `certificate`.`gift_certificate_has_tag`
(
    `gift_certificate_id` INT NOT NULL,
    `tag_id`              INT NOT NULL,
    PRIMARY KEY (`tag_id`, `gift_certificate_id`),
    CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
        FOREIGN KEY (`gift_certificate_id`)
            REFERENCES `certificate`.`gift_certificate` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_gift_certificate_has_tag_tag1`
        FOREIGN KEY (`tag_id`)
            REFERENCES `certificate`.`tag` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS `certificate`.`user`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `certificate`.`orders`
(
    `id`                  INT       NOT NULL AUTO_INCREMENT,
    `create_date`         TIMESTAMP NOT NULL,
    `cost`                DECIMAL    NOT NULL,
    `gift_certificate_id` INT       NOT NULL,
    `user_id`             INT       NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_gift_certificate1`
        FOREIGN KEY (`gift_certificate_id`)
            REFERENCES `certificate`.`gift_certificate` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_order_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `certificate`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
