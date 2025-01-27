-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema watchlist
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `watchlist` ;

-- -----------------------------------------------------
-- Schema watchlist
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `watchlist` DEFAULT CHARACTER SET utf8mb3 ;
SHOW WARNINGS;
USE `watchlist` ;

-- -----------------------------------------------------
-- Table `watchlist`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `watchlist`.`roles` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `watchlist`.`roles` (
  `rol_id` INT NOT NULL AUTO_INCREMENT,
  `rol_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`rol_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;

SHOW WARNINGS;
CREATE UNIQUE INDEX `rol_name_UNIQUE` ON `watchlist`.`roles` (`rol_name` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `watchlist`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `watchlist`.`users` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `watchlist`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` CHAR(60) NOT NULL,
  `email` VARCHAR(90) NOT NULL,
  `Roles_rol_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_Users_Roles`
    FOREIGN KEY (`Roles_rol_id`)
    REFERENCES `watchlist`.`roles` (`rol_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

SHOW WARNINGS;
CREATE UNIQUE INDEX `Username_UNIQUE` ON `watchlist`.`users` (`username` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_Users_Roles_idx` ON `watchlist`.`users` (`Roles_rol_id` ASC) VISIBLE;

SHOW WARNINGS;
CREATE UNIQUE INDEX `email_UNIQUE` ON `watchlist`.`users` (`email` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `watchlist`.`dnis`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `watchlist`.`dnis` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `watchlist`.`dnis` (
  `dni_id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(9) NOT NULL,
  `front_img` VARCHAR(45) NULL DEFAULT NULL,
  `back_img` VARCHAR(45) NULL DEFAULT NULL,
  `Users_user_id` INT NOT NULL,
  PRIMARY KEY (`dni_id`, `Users_user_id`),
  CONSTRAINT `fk_dnis_users1`
    FOREIGN KEY (`Users_user_id`)
    REFERENCES `watchlist`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

SHOW WARNINGS;
CREATE UNIQUE INDEX `number_UNIQUE` ON `watchlist`.`dnis` (`number` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_dnis_users1_idx` ON `watchlist`.`dnis` (`Users_user_id` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `watchlist`.`films`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `watchlist`.`films` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `watchlist`.`films` (
  `film_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `release_date` DATE NOT NULL,
  PRIMARY KEY (`film_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

SHOW WARNINGS;
CREATE UNIQUE INDEX `title_UNIQUE` ON `watchlist`.`films` (`title` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `watchlist`.`users_haswatched_films`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `watchlist`.`users_haswatched_films` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `watchlist`.`users_haswatched_films` (
  `Users_user_id` INT NOT NULL,
  `Films_film_id` INT NOT NULL,
  PRIMARY KEY (`Users_user_id`, `Films_film_id`),
  CONSTRAINT `fk_Users_has_Films_Films1`
    FOREIGN KEY (`Films_film_id`)
    REFERENCES `watchlist`.`films` (`film_id`),
  CONSTRAINT `fk_Users_has_Films_Users1`
    FOREIGN KEY (`Users_user_id`)
    REFERENCES `watchlist`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

SHOW WARNINGS;
CREATE INDEX `fk_Users_has_Films_Films1_idx` ON `watchlist`.`users_haswatched_films` (`Films_film_id` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_Users_has_Films_Users1_idx` ON `watchlist`.`users_haswatched_films` (`Users_user_id` ASC) VISIBLE;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;