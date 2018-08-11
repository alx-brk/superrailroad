package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dao.StationDaoImpl;
import main.java.com.tsystems.superrailroad.model.dao.StationGraphDaoImpl;
import main.java.com.tsystems.superrailroad.model.dto.StationDto;
import main.java.com.tsystems.superrailroad.model.dto.StationGraphDto;
import main.java.com.tsystems.superrailroad.model.entity.Station;
import main.java.com.tsystems.superrailroad.model.excep.CreateStationException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class StationServiceImplTest {
    private StationDaoImpl stationDao = mock(StationDaoImpl.class);
    private StationGraphDaoImpl stationGraphDao = mock(StationGraphDaoImpl.class);
    private StationServiceImpl stationService = new StationServiceImpl(stationDao, stationGraphDao);

    @Test
    public void getAllStations() {
        Station station = new Station();
        station.setName("ololo");
        when(stationDao.readAll()).thenReturn(Arrays.asList(station));
        List<StationDto> allStations = stationService.getAllStations();
        assertEquals("ololo", allStations.get(0).getName());
    }

    @Test
    public void getStationById() {
        Station station = new Station();
        station.setName("ololo");
        when(stationDao.read(1)).thenReturn(station);
        StationDto stationDto = stationService.getStation(1);
        assertEquals("ololo", stationDto.getName());
    }

    @Test
    public void getStationByName() {
        Station station = new Station();
        station.setName("ololo");
        when(stationDao.find("ololo")).thenReturn(station);
        StationDto stationDto = stationService.getStation("ololo");

        assertEquals("ololo", stationDto.getName());
    }

    @Test(expected = CreateStationException.class)
    public void createEndStation() {
        StationGraphDto stationGraphDto = new StationGraphDto();
        stationGraphDto.setDistance(0);
        stationService.createEndStation(stationGraphDto);
    }

    @Test
    public void getConnectedStations() {
        Station startStation = new Station();
        Station station = new Station();
        startStation.setName("ololo");
        station.setName("trololo");
        StationDto stationDto = new StationDto();
        stationDto.setName("ololo");
        when(stationDao.find("ololo")).thenReturn(startStation);
        when(stationGraphDao.getConnectedStations(startStation)).thenReturn(Arrays.asList(station));

        assertEquals("trololo", stationService.getConnectedStations(stationDto).get(0).getName());
    }
}