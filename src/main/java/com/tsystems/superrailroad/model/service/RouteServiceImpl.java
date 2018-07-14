package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dao.RouteDao;
import main.java.com.tsystems.superrailroad.model.dao.RouteHasStationDao;
import main.java.com.tsystems.superrailroad.model.dao.StationDao;
import main.java.com.tsystems.superrailroad.model.dao.TrainDao;
import main.java.com.tsystems.superrailroad.model.dto.RouteDto;
import main.java.com.tsystems.superrailroad.model.dto.RouteHasStationDto;
import main.java.com.tsystems.superrailroad.model.dto.StationDto;
import main.java.com.tsystems.superrailroad.model.dto.TrainDto;
import main.java.com.tsystems.superrailroad.model.entity.Route;
import main.java.com.tsystems.superrailroad.model.entity.RouteHasStation;
import main.java.com.tsystems.superrailroad.model.entity.Station;
import main.java.com.tsystems.superrailroad.model.entity.Train;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private StationDao stationDao;
    private TrainDao trainDao;
    private RouteHasStationDao routeHasStationDao;
    private RouteDao routeDao;
    private ModelMapper mapper;

    @Autowired
    public RouteServiceImpl(StationDao stationDao, TrainDao trainDao, RouteHasStationDao routeHasStationDao, RouteDao routeDao){
        this.stationDao = stationDao;
        this.trainDao = trainDao;
        this.routeHasStationDao = routeHasStationDao;
        this.routeDao = routeDao;
        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional
    public void createRoute(RouteDto routeDto) {
        Route route = new Route();
        Train train = mapper.map(routeDto.getTrainDto(), Train.class);
        train.setRoute(route);
        route.setTrain(train);
        routeDao.create(route);

        List<RouteHasStationDto> routeHasStationDtoList = routeDto.getRouteHasStationDtoList();

        for (RouteHasStationDto routeHasStationDto : routeHasStationDtoList){
            RouteHasStation routeHasStation = new RouteHasStation();
            routeHasStation.setStationOrder(routeHasStationDto.getStationOrder());
            Station station = stationDao.find(routeHasStationDto.getStationDto().getName());
            routeHasStation.setStation(station);
            routeHasStation.setRoute(route);
            routeHasStationDao.create(routeHasStation);
        }
    }

    @Override
    @Transactional
    public List<RouteDto> getAllRoutes() {
        List<RouteDto> routeDtoList = new ArrayList<>();
        List<Route> routes = routeDao.readAll();

        for (Route route : routes){
            RouteDto routeDto = new RouteDto();
            routeDto.setRouteId(route.getRouteId());
            TrainDto trainDto = mapper.map(route.getTrain(), TrainDto.class);
            routeDto.setTrainDto(trainDto);

            List<RouteHasStationDto> routeHasStationDtoList = new ArrayList<>();
            List<RouteHasStation> routeHasStationList = routeHasStationDao.getStationsByRoute(route);

            for (RouteHasStation routeHasStation : routeHasStationList){
                RouteHasStationDto routeHasStationDto = new RouteHasStationDto();
                routeHasStationDto.setId(routeHasStation.getId());
                routeHasStationDto.setStationOrder(routeHasStation.getStationOrder());
                routeHasStationDto.setStationDto(mapper.map(routeHasStation.getStation(), StationDto.class));
                routeHasStationDtoList.add(routeHasStationDto);
            }

            routeDto.setRouteHasStationDtoList(routeHasStationDtoList);
            routeDtoList.add(routeDto);
        }

        return routeDtoList;
    }
}
