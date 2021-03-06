-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema fc_learn_db_schema
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `fc_learn_db_schema` ;

-- -----------------------------------------------------
-- Schema fc_learn_db_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fc_learn_db_schema` DEFAULT CHARACTER SET utf8 ;
USE `fc_learn_db_schema` ;

-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`users` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `photo` VARCHAR(500) NULL,
  `status` ENUM('admin', 'active', 'baned') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `u_login_UNIQUE` ON `fc_learn_db_schema`.`users` (`login` ASC);

CREATE UNIQUE INDEX `u_id_UNIQUE` ON `fc_learn_db_schema`.`users` (`id` ASC);

CREATE UNIQUE INDEX `u_email_UNIQUE` ON `fc_learn_db_schema`.`users` (`email` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`topics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`topics` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`topics` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(500) NULL,
  `status` ENUM('private', 'public', 'req') NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `fc_learn_db_schema`.`topics` (`name` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`collections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`collections` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`collections` (
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
  CONSTRAINT `fk_topics_course1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_schema`.`topics` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_collections_users1`
    FOREIGN KEY (`author_id`)
    REFERENCES `fc_learn_db_schema`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `t_id_UNIQUE` ON `fc_learn_db_schema`.`collections` (`id` ASC);

CREATE UNIQUE INDEX `t_name_UNIQUE` ON `fc_learn_db_schema`.`collections` (`name` ASC);

CREATE INDEX `fk_topics_course1_idx` ON `fc_learn_db_schema`.`collections` (`topic_id` ASC);

CREATE INDEX `fk_collections_users1_idx` ON `fc_learn_db_schema`.`collections` (`author_id` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`cards` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `collection_id` BIGINT NOT NULL,
  `question` VARCHAR(4500) NULL,
  `answer` VARCHAR(4500) NULL,
  `question_image` VARCHAR(500) NULL,
  `answer_image` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cards_topics`
    FOREIGN KEY (`collection_id`)
    REFERENCES `fc_learn_db_schema`.`collections` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `c_id_UNIQUE` ON `fc_learn_db_schema`.`cards` (`id` ASC);

CREATE INDEX `fk_cards_topics_idx` ON `fc_learn_db_schema`.`cards` (`collection_id` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`categories` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`categories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(500) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `fc_learn_db_schema`.`categories` (`name` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`user_collections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`user_collections` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`user_collections` (
  `collection_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `rating` ENUM('like', 'nolike') NULL DEFAULT 'nolike',
  PRIMARY KEY (`collection_id`, `user_id`),
  CONSTRAINT `fk_topics_has_users_topics1`
    FOREIGN KEY (`collection_id`)
    REFERENCES `fc_learn_db_schema`.`collections` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_topics_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fc_learn_db_schema`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_topics_has_users_users1_idx` ON `fc_learn_db_schema`.`user_collections` (`user_id` ASC);

CREATE INDEX `fk_topics_has_users_topics1_idx` ON `fc_learn_db_schema`.`user_collections` (`collection_id` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`user_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`user_cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`user_cards` (
  `users_id` BIGINT NOT NULL,
  `cards_id` BIGINT NOT NULL,
  `card_status` ENUM('new', 'low', 'medium', 'high', 'never') NULL,
  `low_count` INT NULL,
  `last_viwed` TIMESTAMP NULL,
  PRIMARY KEY (`users_id`, `cards_id`),
  CONSTRAINT `fk_users_has_cards_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `fc_learn_db_schema`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_cards_cards1`
    FOREIGN KEY (`cards_id`)
    REFERENCES `fc_learn_db_schema`.`cards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_users_has_cards_cards1_idx` ON `fc_learn_db_schema`.`user_cards` (`cards_id` ASC);

CREATE INDEX `fk_users_has_cards_users1_idx` ON `fc_learn_db_schema`.`user_cards` (`users_id` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`topic_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`topic_categories` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`topic_categories` (
  `topic_id` BIGINT NOT NULL,
  `category_id` BIGINT NOT NULL,
  PRIMARY KEY (`topic_id`, `category_id`),
  CONSTRAINT `fk_categories_has_topics_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `fc_learn_db_schema`.`categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_categories_has_topics_topics1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_schema`.`topics` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_categories_has_topics_topics1_idx` ON `fc_learn_db_schema`.`topic_categories` (`topic_id` ASC);

CREATE INDEX `fk_categories_has_topics_categories1_idx` ON `fc_learn_db_schema`.`topic_categories` (`category_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
