package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Passenger;
import main.java.com.tsystems.superrailroad.model.entity.Ride;

import java.util.Date;
import java.util.List;

public interface PassengerDao {
    void create (Passenger passenger);
    Passenger read(Integer id);
    void update(Passenger passenger);
    void delete(Integer id);
    Passenger find(String firstName, String lastName, Date birthDate);
    List<Passenger> findByRide(Ride ride);
}
