package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Ride;
import main.java.com.tsystems.superrailroad.model.entity.RideHasStation;
import main.java.com.tsystems.superrailroad.model.entity.Station;

import java.util.Date;
import java.util.List;

public interface RideHasStationDao {
    void create (RideHasStation rideHasStation);
    RideHasStation read(Integer id);
    void update(RideHasStation rideHasStation);
    void delete(Integer id);
    List<Ride> findRideByStations(Station stationFrom, Station stationTo);
    RideHasStation findByRideAndStation(Ride ride, Station station);
    int sumPrice(Ride ride, Date dateFrom, Date dateTo);
    RideHasStation findFirstByRide(Ride ride);
    RideHasStation findLastByRide(Ride ride);
    List<RideHasStation> getRidesByStation(Station station);
    List<RideHasStation> getStationsByRide(Ride ride);
}
