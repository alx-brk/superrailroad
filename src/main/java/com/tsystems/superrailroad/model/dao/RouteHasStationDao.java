package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Route;
import main.java.com.tsystems.superrailroad.model.entity.RouteHasStation;

import java.util.List;

public interface RouteHasStationDao {
    void create (RouteHasStation routeHasStation);
    RouteHasStation read(Integer id);
    void update(RouteHasStation routeHasStation);
    void delete(Integer id);
    List<RouteHasStation> getStationsByRoute(Route route);
}
