CREATE SCHEMA IF NOT EXISTS railroad;
create user if not exists 'admin' identified by 'admin';
SET GLOBAL time_zone = '+03:00';

CREATE TABLE IF NOT EXISTS `railroad`.`Station` (
  `station_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`station_id`),
  UNIQUE INDEX `station_id_UNIQUE` (`station_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
  
CREATE TABLE IF NOT EXISTS `railroad`.`station_graph` (
  `station_id` INT NOT NULL,
  `distance` INT NOT NULL,
  `ref_station_id` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  INDEX `fk_station_graph_Station1_idx` (`station_id` ASC),
  UNIQUE INDEX `ref_uq` (`station_id` ASC),
  INDEX `fk_station_graph_Station2_idx` (`ref_station_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_station_graph_Station1`
    FOREIGN KEY (`station_id`)
    REFERENCES `railroad`.`Station` (`station_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_station_graph_Station2`
    FOREIGN KEY (`ref_station_id`)
    REFERENCES `railroad`.`Station` (`station_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE IF NOT EXISTS `railroad`.`Train` (
  `train_id` INT NOT NULL AUTO_INCREMENT,
  `capacity` INT NOT NULL,
  `price_for_km` DECIMAL(10,2) NOT NULL,
  `speed` INT NOT NULL,
  PRIMARY KEY (`train_id`),
  UNIQUE INDEX `train_id_UNIQUE` (`train_id` ASC));
  
  CREATE TABLE IF NOT EXISTS `railroad`.`route` (
  `route_id` INT NOT NULL AUTO_INCREMENT,
  `train_id` INT NOT NULL,
  PRIMARY KEY (`route_id`),
  INDEX `fk_route_Train1_idx` (`train_id` ASC),
  CONSTRAINT `fk_route_Train1`
    FOREIGN KEY (`train_id`)
    REFERENCES `railroad`.`Train` (`train_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE IF NOT EXISTS `railroad`.`route_has_Station` (
  `route_id` INT NOT NULL,
  `station_id` INT NOT NULL,
  `station_order` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  INDEX `fk_route_has_Station_Station1_idx` (`station_id` ASC),
  INDEX `fk_route_has_Station_route1_idx` (`route_id` ASC),
  UNIQUE INDEX `rs_uq` (`route_id` ASC, `station_id` ASC),
  UNIQUE INDEX `ro_uq` (`station_order` ASC, `route_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_route_has_Station_route1`
    FOREIGN KEY (`route_id`)
    REFERENCES `railroad`.`route` (`route_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_route_has_Station_Station1`
    FOREIGN KEY (`station_id`)
    REFERENCES `railroad`.`Station` (`station_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    insert into station(name) values ("Saint-Petersburg");