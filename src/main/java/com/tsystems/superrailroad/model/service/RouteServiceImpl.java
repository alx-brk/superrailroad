package main.java.com.tsystems.superrailroad.model.service;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import main.java.com.tsystems.superrailroad.model.dao.*;
import main.java.com.tsystems.superrailroad.model.dto.*;
import main.java.com.tsystems.superrailroad.model.entity.*;
import main.java.com.tsystems.superrailroad.model.excep.RideException;
import main.java.com.tsystems.superrailroad.model.excep.RouteException;
import main.java.com.tsystems.superrailroad.model.excep.StationException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeoutException;

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
            log.info("Route " + route.getRouteId() + " was created");
        } else {
            throw new RouteException();
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
            log.info("Ride " + ride.getRideId() + " was created");

            sendMessageAdd(ride);
        } else {
            throw new RideException();
        }
    }

    @Override
    @Transactional
    public void changeRide(RideDto rideDto) {
        if (rideDto.getDeparture().after(new Date())){
            Ride ride = rideDao.read(rideDto.getRideId());
            Calendar oldDate = Calendar.getInstance();
            oldDate.setTime(ride.getDeparture());
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(rideDto.getDeparture());
            long timeGap = newDate.getTimeInMillis() - oldDate.getTimeInMillis();
            ride.setDeparture(rideDto.getDeparture());
            rideDao.update(ride);

            for (RideHasStation rideHasStation : rideHasStationDao.getStationsByRide(ride)){
                Calendar departure = Calendar.getInstance();
                departure.setTime(rideHasStation.getDeparture());
                departure.add(Calendar.SECOND, (int) (timeGap/1000));
                rideHasStation.setDeparture(departure.getTime());
                rideHasStationDao.update(rideHasStation);
            }

            sendMessageUpdate(ride);
        } else {
            throw new RideException();
        }
    }

    private void sendMessageUpdate(Ride ride){
        List<RideStationDto> rideStationDtoList = getRideStationDtoList(ride);
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("rideId", ride.getRideId());
        messageJSON.put("type", "update");
        JSONObject stations = new JSONObject();

        for (RideStationDto rideStationDto : rideStationDtoList){
            stations.put(rideStationDto.getStation(), rideStationDto.getDateTime());
        }

        messageJSON.put("stations", stations);
        sendMessage(messageJSON);
    }

    private void sendMessageDelete(Ride ride){
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("rideId", ride.getRideId());
        messageJSON.put("type", "delete");
        sendMessage(messageJSON);
    }

    private void sendMessageAdd(Ride ride){
        List<RideStationDto> rideStationDtoList = getRideStationDtoList(ride);
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("rideId", ride.getRideId());
        messageJSON.put("type", "add");
        JSONArray stations = new JSONArray();

        for (RideStationDto rideStationDto : rideStationDtoList){
            JSONObject station = new JSONObject();
            station.put("station", rideStationDto.getStation());
            station.put("time", rideStationDto.getDateTime());
            stations.put(station);
        }

        messageJSON.put("stations", stations);
        sendMessage(messageJSON);
    }

    private void sendMessage(JSONObject jsonObject){
        String queueName = "queue";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.exchangeDeclare(queueName, BuiltinExchangeType.FANOUT);
            String message = jsonObject.toString();
            channel.basicPublish(queueName, "", null, message.getBytes());

        } catch (TimeoutException|IOException e){
            log.error(e);
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
                // this is fine
            }
            passenger.setFirstName(passengerDto.getFirstName());
            passenger.setLastName(passengerDto.getLastName());
            passenger.setBirthDate(passengerDto.getBirthDate());

            Ride ride = rideDao.read(passengerDto.getRideId());
            int ticketsCount = (int) ticketDao.countByRide(ride);
            int capacity = ride.getRoute().getTrain().getCapacity();

            if (ticketsCount < capacity) {
                Ticket ticket = new Ticket();
                ticket.setPassenger(passenger);
                ticket.setRide(ride);

                ticketDao.create(ticket);
                log.info(passengerDto.getFirstName() + " " + passengerDto.getLastName() + " bought ticket to ride " + ride.getRideId());
                return true;
            } else {
                return false;
            }
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
        format.setTimeZone(TimeZone.getTimeZone("UTC+03:00"));
        List<StationInfoDto> stationInfoDtoList = new ArrayList<>();

        Station currentStation = stationDao.find(stationDto.getName());
        List<RideHasStation> rideHasStationList = rideHasStationDao.getRidesByStation(currentStation);

        for (RideHasStation rideHasStation : rideHasStationList){
            Calendar today = Calendar.getInstance();
            Calendar rideDay = Calendar.getInstance();
            rideDay.setTime(rideHasStation.getDeparture());
            if (today.get(Calendar.YEAR) == rideDay.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == rideDay.get(Calendar.DAY_OF_YEAR)) {
                StationInfoDto stationInfoDto = new StationInfoDto();
                Ride ride = rideHasStation.getRide();
                stationInfoDto.setRideId(ride.getRideId());
                stationInfoDto.setDatetime(format.format(rideHasStation.getDeparture()));
                List<StationDateDto> stationDateDtoList = new ArrayList<>();

                List<RideHasStation> rideHasStations = rideHasStationDao.getStationsByRide(ride);
                for (RideHasStation rhs : rideHasStations) {
                    stationDateDtoList.add(new StationDateDto(rhs.getStation().getName(), format.format(rhs.getDeparture())));
                }

                stationInfoDto.setStations(stationDateDtoList);
                stationInfoDtoList.add(stationInfoDto);
            }
        }

        return stationInfoDtoList;
    }

    private List<RideStationDto> getRideStationDtoList(Ride ride){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("UTC+03:00"));
        List<RideStationDto> rideStationDtoList = new ArrayList<>();

        List<RideHasStation> rideHasStationList = rideHasStationDao.getStationsByRide(ride);
        for (RideHasStation rideHasStation : rideHasStationList){
            RideStationDto rideStationDto = new RideStationDto();
            rideStationDto.setRideId(ride.getRideId());
            rideStationDto.setStation(rideHasStation.getStation().getName());
            rideStationDto.setDateTime(format.format(rideHasStation.getDeparture()));
            rideStationDtoList.add(rideStationDto);
        }

        return rideStationDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public RideDto getRideById(int rideId) {
        return mapper.map(rideDao.read(rideId), RideDto.class);
    }

    @Override
    @Transactional
    public boolean deleteRide(Integer rideId) {
        boolean result = false;
        Ride ride = rideDao.read(rideId);
        long countTickets = ticketDao.countByRide(ride);

        if (countTickets == 0){
            List<RideHasStation> rideHasStationList = rideHasStationDao.getStationsByRide(ride);
            for (RideHasStation rideHasStation : rideHasStationList){
                rideHasStationDao.delete(rideHasStation.getId());
            }

            rideDao.delete(rideId);
            sendMessageDelete(ride);
            result = true;
            log.info("Ride " + rideId + " was deleted");
        }

        return result;
    }

    @Override
    @Transactional
    public void deleteStation(StationDto stationDto) {
        Station station = stationDao.read(stationDto.getStationId());
        if (routeHasStationDao.stationUsed(station)){
            throw new StationException();
        } else {
            List<StationGraph> stationGraphList = stationGraphDao.findAllByStation(station);
            for (StationGraph stationGraph : stationGraphList){
                stationGraphDao.delete(stationGraph.getId());
            }
            stationDao.delete(stationDto.getStationId());
            log.info("Station " + stationDto.getName() + " was deleted");
        }
    }

    @Override
    @Transactional
    public void deleteRoute(RouteDto routeDto) {
        Route route = routeDao.read(routeDto.getRouteId());
        if (rideDao.routeUsed(route)){
            throw new RouteException();
        } else {
            List<RouteHasStation> routeHasStationList = routeHasStationDao.getStationsByRoute(route);
            for (RouteHasStation routeHasStation : routeHasStationList){
                routeHasStationDao.delete(routeHasStation.getId());
            }
            routeDao.delete(route.getRouteId());
            log.info("Route " + route.getRouteId() + " was deleted");
        }
    }
}
