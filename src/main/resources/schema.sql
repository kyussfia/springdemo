SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema springdemo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema springdemo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `springdemo` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

-- -----------------------------------------------------
-- Table `springdemo`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springdemo`.`users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45)  CHARSET utf8 COLLATE utf8_bin NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `last_login` DATETIME NULL,
  `login_failures` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springdemo`.`groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springdemo`.`groups` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springdemo`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springdemo`.`roles` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springdemo`.`user_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springdemo`.`user_group` (
  `user_id` INT UNSIGNED NOT NULL,
  `group_id` INT UNSIGNED NOT NULL,
  INDEX `group_id_idx` (`group_id` ASC) VISIBLE,
  UNIQUE INDEX `user_group_id_UNIQUE` USING BTREE (`user_id`, `group_id`) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_group_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `springdemo`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_group_group`
    FOREIGN KEY (`group_id`)
    REFERENCES `springdemo`.`groups` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `springdemo`.`group_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springdemo`.`group_role` (
  `group_id` INT UNSIGNED NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `group_role_id_UNIQUE` USING BTREE (`group_id`, `role_id`) VISIBLE,
  INDEX `group_id_idx` (`group_id` ASC) VISIBLE,
  CONSTRAINT `group_role_group`
    FOREIGN KEY (`group_id`)
    REFERENCES `springdemo`.`groups` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `group_role_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `springdemo`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;