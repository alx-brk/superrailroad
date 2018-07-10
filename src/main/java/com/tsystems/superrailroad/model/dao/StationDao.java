package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Station;

import java.util.List;

public interface StationDao {
    void create (Station station);
    Station read(Integer id);
    void update(Station station);
    void delete(Integer id);
    List<Station> readAll();
    Station find(String name);
}
