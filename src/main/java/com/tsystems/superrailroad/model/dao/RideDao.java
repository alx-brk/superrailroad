package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Ride;
import main.java.com.tsystems.superrailroad.model.entity.Route;

import java.util.List;

public interface RideDao {
    void create (Ride ride);
    Ride read(Integer id);
    void update(Ride ride);
    void delete(Integer id);
    List<Ride> getAll();
    boolean routeUsed(Route route);
}
