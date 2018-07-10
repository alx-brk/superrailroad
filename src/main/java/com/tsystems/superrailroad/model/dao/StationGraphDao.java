package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Station;
import main.java.com.tsystems.superrailroad.model.entity.StationGraph;

import java.util.List;

public interface StationGraphDao {
    void create (StationGraph stationGraph);
    StationGraph read(Integer id);
    void update(StationGraph stationGraph);
    void delete(Integer id);
    List<Station> getConnectedStations(Station station);
}
