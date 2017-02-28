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
  `login` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `photo` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `u_login_UNIQUE` ON `fc_learn_db_schema`.`users` (`login` ASC);

CREATE UNIQUE INDEX `u_id_UNIQUE` ON `fc_learn_db_schema`.`users` (`id` ASC);

CREATE UNIQUE INDEX `u_email_UNIQUE` ON `fc_learn_db_schema`.`users` (`email` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`categories` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`categories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `name_UNIQUE` ON `fc_learn_db_schema`.`categories` (`name` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`courses` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`courses` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `category_id` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_course_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `fc_learn_db_schema`.`categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_course_category1_idx` ON `fc_learn_db_schema`.`courses` (`category_id` ASC);

CREATE UNIQUE INDEX `name_UNIQUE` ON `fc_learn_db_schema`.`courses` (`name` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`topics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`topics` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`topics` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `course_id` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `image` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_topics_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `fc_learn_db_schema`.`courses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `t_id_UNIQUE` ON `fc_learn_db_schema`.`topics` (`id` ASC);

CREATE UNIQUE INDEX `t_name_UNIQUE` ON `fc_learn_db_schema`.`topics` (`name` ASC);

CREATE INDEX `fk_topics_course1_idx` ON `fc_learn_db_schema`.`topics` (`course_id` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`cards` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `topic_id` BIGINT NOT NULL,
  `question` VARCHAR(300) NOT NULL,
  `answer` VARCHAR(4000) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cards_topics`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_schema`.`topics` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `c_id_UNIQUE` ON `fc_learn_db_schema`.`cards` (`id` ASC);

CREATE INDEX `fk_cards_topics_idx` ON `fc_learn_db_schema`.`cards` (`topic_id` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`users_topics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`users_topics` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`users_topics` (
  `topic_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`topic_id`, `user_id`),
  CONSTRAINT `fk_topics_has_users_topics1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_schema`.`topics` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_topics_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fc_learn_db_schema`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_topics_has_users_users1_idx` ON `fc_learn_db_schema`.`users_topics` (`user_id` ASC);

CREATE INDEX `fk_topics_has_users_topics1_idx` ON `fc_learn_db_schema`.`users_topics` (`topic_id` ASC);


-- -----------------------------------------------------
-- Table `fc_learn_db_schema`.`users_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_schema`.`users_cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_schema`.`users_cards` (
  `users_id` BIGINT NOT NULL,
  `cards_id` BIGINT NOT NULL,
  `card_status` ENUM('new', 'low', 'medium', 'high') NULL,
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

CREATE INDEX `fk_users_has_cards_cards1_idx` ON `fc_learn_db_schema`.`users_cards` (`cards_id` ASC);

CREATE INDEX `fk_users_has_cards_users1_idx` ON `fc_learn_db_schema`.`users_cards` (`users_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
