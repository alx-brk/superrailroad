drop schema railroad;
CREATE SCHEMA IF NOT EXISTS railroad;
create user if not exists 'admin' identified by 'admin';
SET GLOBAL time_zone = '+00:00';

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

    CREATE TABLE IF NOT EXISTS `railroad`.`ride` (
  `ride_id` INT NOT NULL AUTO_INCREMENT,
  `route_id` INT NOT NULL,
  `departure` DATETIME NOT NULL,
  PRIMARY KEY (`ride_id`),
  INDEX `fk_ride_route1_idx` (`route_id` ASC),
  CONSTRAINT `fk_ride_route1`
    FOREIGN KEY (`route_id`)
    REFERENCES `railroad`.`route` (`route_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    CREATE TABLE IF NOT EXISTS `railroad`.`ride_has_station` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ride_id` INT NOT NULL,
  `station_id` INT NOT NULL,
  `departure` DATETIME NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ride_has_station_ride1_idx` (`ride_id` ASC),
  INDEX `fk_ride_has_station_Station1_idx` (`station_id` ASC),
  UNIQUE INDEX `rs_uq` (`ride_id` ASC, `station_id` ASC),
  CONSTRAINT `fk_ride_has_station_ride1`
    FOREIGN KEY (`ride_id`)
    REFERENCES `railroad`.`ride` (`ride_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ride_has_station_Station1`
    FOREIGN KEY (`station_id`)
    REFERENCES `railroad`.`Station` (`station_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    CREATE TABLE IF NOT EXISTS `railroad`.`Passenger` (
  `passenger_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthdate` DATE NOT NULL,
  UNIQUE INDEX `passenger_id_UNIQUE` (`passenger_id` ASC),
  UNIQUE INDEX `passenger_uq` (`first_name` ASC, `last_name` ASC, `birthdate` ASC),
  PRIMARY KEY (`passenger_id`));

    CREATE TABLE IF NOT EXISTS `railroad`.`Ticket` (
  `ticket_id` INT NOT NULL AUTO_INCREMENT,
  `passenger_id` INT NOT NULL,
  `ride_id` INT NOT NULL,
  PRIMARY KEY (`ticket_id`),
  UNIQUE INDEX `ticket_id_UNIQUE` (`ticket_id` ASC),
  INDEX `fk_ticket_Passenger_idx` (`passenger_id` ASC),
  INDEX `fk_Ticket_ride1_idx` (`ride_id` ASC),
  CONSTRAINT `fk_ticket_Passenger`
    FOREIGN KEY (`passenger_id`)
    REFERENCES `railroad`.`Passenger` (`passenger_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ticket_ride1`
    FOREIGN KEY (`ride_id`)
    REFERENCES `railroad`.`ride` (`ride_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

   CREATE TABLE IF NOT EXISTS `railroad`.`User_` (
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `user_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC));

  CREATE TABLE IF NOT EXISTS `railroad`.`role` (
  `role` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`role_id`));

  CREATE TABLE IF NOT EXISTS `railroad`.`User__has_role` (
  `User__user_id` INT NOT NULL,
  `role_role_id` INT NOT NULL,
  PRIMARY KEY (`User__user_id`, `role_role_id`),
  INDEX `fk_User__has_role_role1_idx` (`role_role_id` ASC),
  INDEX `fk_User__has_role_User_1_idx` (`User__user_id` ASC),
  CONSTRAINT `fk_User__has_role_User_1`
    FOREIGN KEY (`User__user_id`)
    REFERENCES `railroad`.`User_` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User__has_role_role1`
    FOREIGN KEY (`role_role_id`)
    REFERENCES `railroad`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    insert into `railroad`.role(role_id, role) values (1,'ROLE_USER');
	insert into `railroad`.role(role_id, role) values (2,'ROLE_ADMIN');
    insert into `railroad`.user_(user_id, login, password) values (1, 'admin','$2a$11$ZTo/yYRSs3RJkktcMh/dl.t6SvGvEdU0GZwC4owZ.0dPlfrspBxT.');
    insert into `railroad`.user__has_role(user__user_id, role_role_id) values (1,2);

    insert into `railroad`.station(station_id, name) values (1, 'Saint-Petersburg');
    insert into `railroad`.station(station_id, name) values (2, 'Pushkin');
    insert into `railroad`.station(station_id, name) values (3, 'Viborg');
    insert into `railroad`.station(station_id, name) values (4, 'Gatchina');
    insert into `railroad`.station(station_id, name) values (5, 'Narva');
    insert into `railroad`.station(station_id, name) values (6, 'Pskov');

    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (1, 27100, 2);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (1, 136000, 3);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (2, 30400, 4);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (2, 27100, 1);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (3, 136000, 1);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (4, 30400, 2);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (4, 128000, 5);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (4, 244000, 6);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (5, 128000, 4);
    insert into `railroad`.station_graph(station_id, distance, ref_station_id) values (6, 244000, 4);

    insert into `railroad`.train(train_id, capacity, price_for_km, speed) values (1, 20, 1.00, 35);
    insert into `railroad`.train(train_id, capacity, price_for_km, speed) values (2, 30, 0.80, 30);

    insert into `railroad`.route(route_id, train_id) values (1, 1);
    insert into `railroad`.route(route_id, train_id) values (2, 2);

    insert into `railroad`.route_has_station(route_id, station_order, station_id) values (1, 1, 3);
    insert into `railroad`.route_has_station(route_id, station_order, station_id) values (1, 2, 1);
    insert into `railroad`.route_has_station(route_id, station_order, station_id) values (1, 3, 2);
    insert into `railroad`.route_has_station(route_id, station_order, station_id) values (1, 4, 4);
    insert into `railroad`.route_has_station(route_id, station_order, station_id) values (2, 1, 5);
    insert into `railroad`.route_has_station(route_id, station_order, station_id) values (2, 2, 4);
    insert into `railroad`.route_has_station(route_id, station_order, station_id) values (2, 3, 6);

    insert into `railroad`.ride(ride_id, route_id, departure) values (1, 1, STR_TO_DATE('2018-07-19 12:00:00', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride(ride_id, route_id, departure) values (2, 1, STR_TO_DATE('2018-07-19 18:00:00', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride(ride_id, route_id, departure) values (3, 2, STR_TO_DATE('2018-07-19 15:00:00', '%Y-%m-%d %H:%i:%S'));

    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (1, 3, 0.00, STR_TO_DATE('2018-07-19 12:00:00', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (1, 1, 136.00, STR_TO_DATE('2018-07-19 13:04:45', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (1, 2, 27.10, STR_TO_DATE('2018-07-19 13:17:39', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (1, 4, 30.40, STR_TO_DATE('2018-07-19 13:32:07', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (2, 3, 0.00, STR_TO_DATE('2018-07-19 18:00:00', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (2, 1, 136.00, STR_TO_DATE('2018-07-19 19:04:45', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (2, 2, 27.10, STR_TO_DATE('2018-07-19 19:17:39', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (2, 4, 30.40, STR_TO_DATE('2018-07-19 19:32:07', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (3, 5, 0.00, STR_TO_DATE('2018-07-19 15:00:00', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (3, 4, 102.40, STR_TO_DATE('2018-07-19 16:11:06', '%Y-%m-%d %H:%i:%S'));
    insert into `railroad`.ride_has_station(ride_id, station_id, price, departure) values (3, 6, 195.20, STR_TO_DATE('2018-07-19 18:26:39', '%Y-%m-%d %H:%i:%S'));

    insert into `railroad`.passenger(passenger_id, first_name, last_name, birthdate) values (1, 'Alexey', 'Bobrik', STR_TO_DATE('1990-05-13', '%Y-%m-%d'));
    insert into `railroad`.passenger(passenger_id, first_name, last_name, birthdate) values (2, 'Chuck', 'Norris', STR_TO_DATE('1940-03-10', '%Y-%m-%d'));

    insert into `railroad`.ticket(passenger_id, ride_id) values (1, 1);
    insert into `railroad`.ticket(passenger_id, ride_id) values (2, 1);

	commit;