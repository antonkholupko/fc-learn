-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema fc_learn_db_test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `fc_learn_db_test` ;

-- -----------------------------------------------------
-- Schema fc_learn_db_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fc_learn_db_test` DEFAULT CHARACTER SET utf8 ;
USE `fc_learn_db_test` ;

-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`users` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `photo` VARCHAR(500) NULL,
  `status` ENUM('admin', 'active', 'baned') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `u_login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `u_id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `u_email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`topics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`topics` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`topics` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(500) NULL,
  `status` ENUM('private', 'public', 'req') NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`collections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`collections` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`collections` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `author_id` BIGINT NOT NULL,
  `topic_id` BIGINT NOT NULL,
  `name` VARCHAR(300) NOT NULL,
  `description` VARCHAR(1500) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `modified` TIMESTAMP NULL,
  `image` VARCHAR(500) NULL,
  `status` ENUM('private', 'public', 'req') NOT NULL,
  `rating` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `t_id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `t_name_UNIQUE` (`name` ASC),
  INDEX `fk_topics_course1_idx` (`topic_id` ASC),
  INDEX `fk_collections_users1_idx` (`author_id` ASC),
  CONSTRAINT `fk_topics_course1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_test`.`topics` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_collections_users1`
    FOREIGN KEY (`author_id`)
    REFERENCES `fc_learn_db_test`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`cards` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `collection_id` BIGINT NOT NULL,
  `question` VARCHAR(4500) NULL,
  `answer` VARCHAR(4500) NULL,
  `question_image` VARCHAR(500) NULL,
  `answer_image` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `c_id_UNIQUE` (`id` ASC),
  INDEX `fk_cards_topics_idx` (`collection_id` ASC),
  CONSTRAINT `fk_cards_topics`
    FOREIGN KEY (`collection_id`)
    REFERENCES `fc_learn_db_test`.`collections` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`categories` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`categories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`user_collections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`user_collections` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`user_collections` (
  `collection_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `rating` ENUM('like', 'nolike') NULL DEFAULT 'nolike',
  PRIMARY KEY (`collection_id`, `user_id`),
  INDEX `fk_topics_has_users_users1_idx` (`user_id` ASC),
  INDEX `fk_topics_has_users_topics1_idx` (`collection_id` ASC),
  CONSTRAINT `fk_topics_has_users_topics1`
    FOREIGN KEY (`collection_id`)
    REFERENCES `fc_learn_db_test`.`collections` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_topics_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fc_learn_db_test`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`user_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`user_cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`user_cards` (
  `users_id` BIGINT NOT NULL,
  `cards_id` BIGINT NOT NULL,
  `card_status` ENUM('new', 'low', 'medium', 'high', 'never') NULL,
  `low_count` INT NULL,
  PRIMARY KEY (`users_id`, `cards_id`),
  INDEX `fk_users_has_cards_cards1_idx` (`cards_id` ASC),
  INDEX `fk_users_has_cards_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_users_has_cards_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `fc_learn_db_test`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_cards_cards1`
    FOREIGN KEY (`cards_id`)
    REFERENCES `fc_learn_db_test`.`cards` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`topic_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`topic_categories` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`topic_categories` (
  `topic_id` BIGINT NOT NULL,
  `category_id` BIGINT NOT NULL,
  PRIMARY KEY (`topic_id`, `category_id`),
  INDEX `fk_categories_has_topics_topics1_idx` (`topic_id` ASC),
  INDEX `fk_categories_has_topics_categories1_idx` (`category_id` ASC),
  CONSTRAINT `fk_categories_has_topics_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `fc_learn_db_test`.`categories` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_categories_has_topics_topics1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_test`.`topics` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
