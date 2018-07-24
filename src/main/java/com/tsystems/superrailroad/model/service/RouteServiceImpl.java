package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dao.*;
import main.java.com.tsystems.superrailroad.model.dto.*;
import main.java.com.tsystems.superrailroad.model.entity.*;
import main.java.com.tsystems.superrailroad.model.excep.CreateRideException;
import main.java.com.tsystems.superrailroad.model.excep.CreateRouteException;
import main.java.com.tsystems.superrailroad.model.excep.PassengerExistException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private static final Logger log = Logger.getLogger(RouteServiceImpl.class);
    private StationDao stationDao;
    private RouteHasStationDao routeHasStationDao;
    private RouteDao routeDao;
    private RideDao rideDao;
    private RideHasStationDao rideHasStationDao;
    private StationGraphDao stationGraphDao;
    private TicketDao ticketDao;
    private PassengerDao passengerDao;
    private ModelMapper mapper;

    @Autowired
    public RouteServiceImpl(StationDao stationDao, RouteHasStationDao routeHasStationDao, RouteDao routeDao, PassengerDao passengerDao, RideDao rideDao, RideHasStationDao rideHasStationDao, StationGraphDao stationGraphDao, TicketDao ticketDao){
        this.stationDao = stationDao;
        this.routeHasStationDao = routeHasStationDao;
        this.routeDao = routeDao;
        this.rideDao = rideDao;
        this.rideHasStationDao = rideHasStationDao;
        this.stationGraphDao = stationGraphDao;
        this.ticketDao = ticketDao;
        this.passengerDao = passengerDao;
        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional
    public void createRoute(RouteDto routeDto) {
        if (routeDto.getTrainDto().getCapacity() > 0
                && routeDto.getTrainDto().getPriceForKm() > 0
                && routeDto.getTrainDto().getSpeed() > 0
                && routeDto.getRouteHasStationDtoList().size() > 1) {
            Route route = new Route();
            Train train = mapper.map(routeDto.getTrainDto(), Train.class);
            train.setRoute(route);
            route.setTrain(train);
            routeDao.create(route);

            List<RouteHasStationDto> routeHasStationDtoList = routeDto.getRouteHasStationDtoList();

            for (RouteHasStationDto routeHasStationDto : routeHasStationDtoList) {
                RouteHasStation routeHasStation = new RouteHasStation();
                routeHasStation.setStationOrder(routeHasStationDto.getStationOrder());
                Station station = stationDao.find(routeHasStationDto.getStationDto().getName());
                routeHasStation.setStation(station);
                routeHasStation.setRoute(route);
                routeHasStationDao.create(routeHasStation);
            }
            log.info("Route " + routeDto.getRouteId() + " was created");
        } else {
            throw new CreateRouteException();
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

    @Override
    @Transactional
    public void createRide(RideDto rideDto) {
        if (rideDto.getDeparture().after(new Date())) {
            Ride ride = new Ride();
            Route route = routeDao.read(rideDto.getRoute());
            Train train = route.getTrain();
            ride.setRoute(route);
            ride.setDeparture(rideDto.getDeparture());
            rideDao.create(ride);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(rideDto.getDeparture());
            Station previousStation = null;

            List<RouteHasStation> routeHasStationList = routeHasStationDao.getStationsByRoute(route);
            for (RouteHasStation routeHasStation : routeHasStationList) {
                RideHasStation rideHasStation = new RideHasStation();
                rideHasStation.setRide(ride);
                if (routeHasStation.getStationOrder() == 1) {
                    rideHasStation.setPrice(0);
                } else {
                    Integer distance = stationGraphDao.find(previousStation, routeHasStation.getStation()).getDistance();
                    float price = (float) distance / 1000 * train.getPriceForKm();
                    rideHasStation.setPrice(price);
                    calendar.add(Calendar.SECOND, distance / train.getSpeed());
                }
                previousStation = routeHasStation.getStation();
                rideHasStation.setDeparture(calendar.getTime());
                rideHasStation.setStation(routeHasStation.getStation());
                rideHasStationDao.create(rideHasStation);
            }
            log.info("Ride " + rideDto.getRideId() + " was created");
        } else {
            throw new CreateRideException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchResultDto> performSearch(SearchDto searchDto) {
        List<SearchResultDto> searchResultDtoList = new ArrayList<>();
        Station stationFrom = stationDao.find(searchDto.getStationFrom());
        Station stationTo = stationDao.find(searchDto.getStationTo());
        Calendar searchDate = Calendar.getInstance();
        searchDate.setTime(searchDto.getDate());
        Calendar departureDate = Calendar.getInstance();
        List<Ride> rideList = rideHasStationDao.findRideByStations(stationFrom, stationTo);
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MINUTE, 10);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        for (Ride ride : rideList){
            Train train = ride.getRoute().getTrain();
            int capacity = train.getCapacity();
            int ticketsCount = (int) ticketDao.countByRide(ride);

            if (ticketsCount < capacity) {
                RideHasStation rideHasStation = rideHasStationDao.findByRideAndStation(ride, stationFrom);
                departureDate.setTime(rideHasStation.getDeparture());

                if (searchDate.get(Calendar.YEAR) == departureDate.get(Calendar.YEAR)
                        && searchDate.get(Calendar.DAY_OF_YEAR) == departureDate.get(Calendar.DAY_OF_YEAR)
                        && departureDate.after(currentDate)) {

                    SearchResultDto resultDto = new SearchResultDto();
                    resultDto.setRideId(ride.getRideId());
                    resultDto.setTrainId(train.getTraintId());
                    resultDto.setCapacity(capacity);
                    resultDto.setTicketsLeft(capacity - ticketsCount);
                    Date departure = departureDate.getTime();
                    Date arrival = rideHasStationDao.findByRideAndStation(ride, stationTo).getDeparture();
                    resultDto.setDeparture(format.format(departure));
                    resultDto.setArrival(format.format(arrival));
                    resultDto.setPrice(rideHasStationDao.sumPrice(ride, departure, arrival));
                    searchResultDtoList.add(resultDto);
                }
            }
        }
        log.info("User searched for trains. " + searchResultDtoList.size() + " rides returned");
        return searchResultDtoList;
    }

    @Override
    @Transactional
    public boolean buyTicket(PassengerDto passengerDto) {
        if (passengerDto.getFirstName() == null
                || passengerDto.getLastName() == null
                || passengerDto.getBirthDate() == null
                || passengerDto.getFirstName().trim().isEmpty()
                || passengerDto.getLastName().trim().isEmpty()
                || passengerDto.getBirthDate().after(new Date())){
            return false;
        } else {
            Passenger passenger = new Passenger();
            try {
                passengerDao.find(passengerDto.getFirstName(), passengerDto.getLastName(), passengerDto.getBirthDate());
                return false;
            } catch (NoResultException e) {

            }
            passenger.setFirstName(passengerDto.getFirstName());
            passenger.setLastName(passengerDto.getLastName());
            passenger.setBirthDate(passengerDto.getBirthDate());

            Ride ride = rideDao.read(passengerDto.getRideId());

            Ticket ticket = new Ticket();
            ticket.setPassenger(passenger);
            ticket.setRide(ride);

            ticketDao.create(ticket);
            log.info(passengerDto.getFirstName() + " " + passengerDto.getLastName() + " bought ticket to ride " + ride.getRideId());
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RideInfoDto> getRideInfo() {
        List<RideInfoDto> rideInfoDtoList = new ArrayList<>();

        for (Ride ride : rideDao.getAll()){
            RideInfoDto rideInfoDto = new RideInfoDto();
            RideHasStation rideHasStationFirst = rideHasStationDao.findFirstByRide(ride);
            RideHasStation rideHasStationLast = rideHasStationDao.findLastByRide(ride);

            rideInfoDto.setRideId(ride.getRideId());
            rideInfoDto.setDepartureStation(rideHasStationFirst.getStation().getName());
            rideInfoDto.setArrivalStation(rideHasStationLast.getStation().getName());
            rideInfoDto.setDepartureDate(rideHasStationFirst.getDeparture());
            rideInfoDto.setArrivalDate(rideHasStationLast.getDeparture());

            List<PassengerDto> passengerDtoList = new ArrayList<>();
            List<Passenger> passengers = passengerDao.findByRide(ride);
            for (Passenger passenger : passengers){
                passengerDtoList.add(mapper.map(passenger, PassengerDto.class));
            }

            rideInfoDto.setPassengers(passengerDtoList);
            rideInfoDtoList.add(rideInfoDto);
        }
        log.info("User searched rides info. " + rideInfoDtoList.size() + " results returned");
        return rideInfoDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StationInfoDto> getStationInfoDtos(StationDto stationDto) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        List<StationInfoDto> stationInfoDtoList = new ArrayList<>();

        Station currentStation = stationDao.find(stationDto.getName());
        List<RideHasStation> rideHasStationList = rideHasStationDao.getRidesByStation(currentStation);

        for (RideHasStation rideHasStation : rideHasStationList){
            StationInfoDto stationInfoDto = new StationInfoDto();
            Ride ride = rideHasStation.getRide();
            stationInfoDto.setRideId(ride.getRideId());
            stationInfoDto.setDatetime(format.format(rideHasStation.getDeparture()));
            List<StationDateDto> stationDateDtoList = new ArrayList<>();

            List<RideHasStation> rideHasStations = rideHasStationDao.getStationsByRide(ride);
            for (RideHasStation rhs : rideHasStations){
                stationDateDtoList.add(new StationDateDto(rhs.getStation().getName(), format.format(rhs.getDeparture())));
            }

            stationInfoDto.setStations(stationDateDtoList);
            stationInfoDtoList.add(stationInfoDto);
        }

        return stationInfoDtoList;
    }
}
