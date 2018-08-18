package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dao.StationDao;
import main.java.com.tsystems.superrailroad.model.dao.StationGraphDao;
import main.java.com.tsystems.superrailroad.model.dto.StationDto;
import main.java.com.tsystems.superrailroad.model.dto.StationGraphDto;
import main.java.com.tsystems.superrailroad.model.entity.Station;
import main.java.com.tsystems.superrailroad.model.entity.StationGraph;
import main.java.com.tsystems.superrailroad.model.excep.StationException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class StationServiceImpl implements StationService {
    private static final Logger log = Logger.getLogger(StationServiceImpl.class);
    private StationDao stationDao;
    private StationGraphDao stationGraphDao;
    private ModelMapper mapper;

    @Autowired
    public StationServiceImpl(StationDao stationDao, StationGraphDao stationGraphDao){
        this.stationDao = stationDao;
        this.stationGraphDao = stationGraphDao;
        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional(readOnly = true)
    public StationDto getStation(Integer id) {
        return mapper.map(stationDao.read(id), StationDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public StationDto getStation(String name) {
        return mapper.map(stationDao.find(name), StationDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StationDto> getAllStations() {
        List<StationDto> stationDtoList = new ArrayList<>();
        for (Station station : stationDao.readAll()){
            stationDtoList.add(mapper.map(station, StationDto.class));
        }
        return stationDtoList;
    }

    @Override
    @Transactional
    public void createEndStation(StationGraphDto stationGraphDto) {
        if (stationGraphDto.getDistance() > 0) {
            Station station = stationDao.find(stationGraphDto.getStation());

            StationDto stationDto = new StationDto(stationGraphDto.getNewStation());
            Station newStation = mapper.map(stationDto, Station.class);
            stationDao.create(newStation);
            newStation = stationDao.find(stationGraphDto.getNewStation());

            StationGraph stationGraphForward = new StationGraph(station, newStation, stationGraphDto.getDistance());
            StationGraph stationGraphBackward = new StationGraph(newStation, station, stationGraphDto.getDistance());

            stationGraphDao.create(stationGraphForward);
            stationGraphDao.create(stationGraphBackward);
            log.info("New station was created " + newStation.getName());
        } else {
            throw new StationException("Distance must be > 0");
        }
    }

    @Override
    @Transactional
    public List<StationDto> getConnectedStations(StationDto stationDto) {
        List<StationDto> stationDtoList  = new ArrayList<>();
        Station startStation = stationDao.find(stationDto.getName());
        List<Station> stations = stationGraphDao.getConnectedStations(startStation);

        for (Station station : stations){
            stationDtoList.add(mapper.map(station, StationDto.class));
        }

        return stationDtoList;
    }
}
