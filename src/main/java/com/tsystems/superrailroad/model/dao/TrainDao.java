package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Train;

public interface TrainDao {
    void create (Train train);
    Train read(Integer id);
    void update(Train train);
    void delete(Integer id);
}
