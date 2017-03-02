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
-- Table `fc_learn_db_test`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`categories` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`categories` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`courses` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`courses` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `category_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_course_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_course_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `fc_learn_db_test`.`categories` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`topics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`topics` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`topics` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `course_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `image` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `t_id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `t_name_UNIQUE` (`name` ASC),
  INDEX `fk_topics_course1_idx` (`course_id` ASC),
  CONSTRAINT `fk_topics_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `fc_learn_db_test`.`courses` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`cards` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `topic_id` BIGINT(20) NOT NULL,
  `question` VARCHAR(300) NOT NULL,
  `answer` VARCHAR(4000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `c_id_UNIQUE` (`id` ASC),
  INDEX `fk_cards_topics_idx` (`topic_id` ASC),
  CONSTRAINT `fk_cards_topics`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_test`.`topics` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`users` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `u_login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `u_id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `u_email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`users_cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`users_cards` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`users_cards` (
  `users_id` BIGINT(20) NOT NULL,
  `cards_id` BIGINT(20) NOT NULL,
  `card_status` ENUM('new', 'low', 'medium', 'high') NULL DEFAULT NULL,
  PRIMARY KEY (`users_id`, `cards_id`),
  INDEX `fk_users_has_cards_cards1_idx` (`cards_id` ASC),
  INDEX `fk_users_has_cards_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_users_has_cards_cards1`
    FOREIGN KEY (`cards_id`)
    REFERENCES `fc_learn_db_test`.`cards` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_cards_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `fc_learn_db_test`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fc_learn_db_test`.`users_topics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fc_learn_db_test`.`users_topics` ;

CREATE TABLE IF NOT EXISTS `fc_learn_db_test`.`users_topics` (
  `topic_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`topic_id`, `user_id`),
  INDEX `fk_topics_has_users_users1_idx` (`user_id` ASC),
  INDEX `fk_topics_has_users_topics1_idx` (`topic_id` ASC),
  CONSTRAINT `fk_topics_has_users_topics1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `fc_learn_db_test`.`topics` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_topics_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `fc_learn_db_test`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
