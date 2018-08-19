package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Route;

import java.util.List;

public interface RouteDao {
    void create (Route route);
    Route read(Integer id);
    void update(Route route);
    void delete(Integer id);
    List<Route> readAll();
}
