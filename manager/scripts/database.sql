
CREATE DATABASE IF NOT EXISTS `SHOPPING`;

USE `SHOPPING`;

CREATE TABLE IF NOT EXISTS `tblUsers`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `hash_password` VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tblPasswordResets`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL,
    `token` VARCHAR(255) NOT NULL UNIQUE,
    `created` DATETIME NOT NULL DEFAULT NOW(),
    `available` BIT NOT NULL DEFAULT 1
);


CREATE TABLE IF NOT EXISTS `tblCategories`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tblProducts`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_name` VARCHAR(255) NOT NULL DEFAULT '',
    `price` DECIMAL(5,2) NOT NULL,
    `image_url` VARCHAR(255) NOT NULL DEFAULT '',
    `category_id` INT(11) NOT NULL,
    FOREIGN KEY(`category_id`) REFERENCES tblCategories(`id`)
);


INSERT INTO `tblUsers` (`hash_password`, `email`) VALUES ('$2y$10$/hWiMdB10Aw8bYcc4uPk.eaGjwq1OorgPfc.1cv93fhuYveJaEP8u', 'channn3@fpt.edu.vn');

INSERT INTO `tblCategories`(`category_name`) VALUES ('Laptop'), ('Mobile'), ('Desktop'), ('Accessories'), ('Tablet');

