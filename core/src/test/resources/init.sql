-- ---
-- Globals
-- ---

-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET FOREIGN_KEY_CHECKS=0;

-- ---
-- Table 'checikng_account'
-- 
-- ---

DROP TABLE IF EXISTS `checikng_account`;
		
CREATE TABLE `checikng_account` (
  `id` VARCHAR(50) NOT NULL,
  `customer_id` VARCHAR(50) NOT NULL,
  `opening_date` BIGINT NOT NULL,
  `state` VARCHAR(10) NOT NULL,
  `balance` DECIMAL NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'customer'
-- 
-- ---

DROP TABLE IF EXISTS `customer`;
		
CREATE TABLE `customer` (
  `id` VARCHAR(50) NOT NULL,
  `firstName` VARCHAR(50) NULL DEFAULT NULL,
  `lastName` VARCHAR(50) NULL DEFAULT NULL,
  `personalNumber` VARCHAR(50) NULL DEFAULT NULL,
  `personalPhoneNumber` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Foreign Keys 
-- ---

--ALTER TABLE `checikng_account` ADD FOREIGN KEY (customer_id) REFERENCES `customer` (`id`);

-- ---
-- Table Properties
-- ---

-- ALTER TABLE `checikng_account` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `customer` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ---
-- Test Data
-- ---

-- INSERT INTO `checikng_account` (`id`,`customer_id`,`opening_date`,`state`,`balance`) VALUES
-- ('','','','','');
-- INSERT INTO `customer` (`id`,`firstName`,`lastName`,`personalNumber`,`personalPhoneNumber`) VALUES
-- ('','','','','');

INSERT INTO `customer` (`id`,`firstName`,`lastName`,`personalNumber`,`personalPhoneNumber`) VALUES ('2','Gustavo','Tinello','1234','4321');
INSERT INTO `checikng_account` (`id`,`customer_id`,`opening_date`,`state`,`balance`) VALUES ('2','2',1603459719420,'OPEN',0);