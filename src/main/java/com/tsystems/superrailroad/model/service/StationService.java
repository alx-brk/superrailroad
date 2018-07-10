package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dto.StationDto;
import main.java.com.tsystems.superrailroad.model.dto.StationGraphDto;

import java.util.List;

public interface StationService {
    StationDto getStation(Integer id);
    StationDto getStation(String name);
    List<StationDto> getAllStations();
    void createEndStation(StationGraphDto stationGraphDto);
    List<StationDto> getConnectedStations(StationDto stationDto);
}
