package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dao.*;
import main.java.com.tsystems.superrailroad.model.dto.*;
import main.java.com.tsystems.superrailroad.model.entity.*;
import main.java.com.tsystems.superrailroad.model.excep.RideException;
import main.java.com.tsystems.superrailroad.model.excep.RouteException;
import main.java.com.tsystems.superrailroad.model.excep.StationException;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouteServiceImplTest {
    private StationDaoImpl stationDao = mock(StationDaoImpl.class);
    private RouteHasStationDaoImpl routeHasStationDao = mock(RouteHasStationDaoImpl.class);
    private RouteDaoImpl routeDao = mock(RouteDaoImpl.class);
    private PassengerDaoImpl passengerDao = mock(PassengerDaoImpl.class);
    private RideDaoImpl rideDao = mock(RideDaoImpl.class);
    private RideHasStationDaoImpl rideHasStationDao = mock(RideHasStationDaoImpl.class);
    private StationGraphDaoImpl stationGraphDao = mock(StationGraphDaoImpl.class);
    private TicketDaoImpl ticketDao = mock(TicketDaoImpl.class);
    private RouteServiceImpl routeService = new RouteServiceImpl(stationDao, routeHasStationDao, routeDao, passengerDao, rideDao, rideHasStationDao, stationGraphDao, ticketDao);

    @Test(expected = RouteException.class)
    public void createRoute() {
        RouteDto routeDto = new RouteDto();
        TrainDto trainDto = new TrainDto();
        trainDto.setCapacity(0);
        routeDto.setTrainDto(trainDto);
        routeService.createRoute(routeDto);
    }

    @Test
    public void getAllRoutes() {
        RouteDto routeDto = new RouteDto();
        routeDto.setRouteId(1);

        Route route = new Route();
        route.setRouteId(1);
        route.setTrain(new Train());
        RouteHasStation routeHasStation = new RouteHasStation();
        routeHasStation.setId(1);
        routeHasStation.setStationOrder(1);
        routeHasStation.setStation(new Station());
        routeHasStation.setRoute(route);

        when(routeDao.readAll()).thenReturn(Arrays.asList(route));
        when(routeHasStationDao.getStationsByRoute(route)).thenReturn(Arrays.asList(routeHasStation));

        assertEquals(1, routeService.getAllRoutes().get(0).getRouteId());
    }

    @Test(expected = RideException.class)
    public void createRide() {
        RideDto rideDto = new RideDto();
        rideDto.setDeparture(new Date(0));
        routeService.createRide(rideDto);
    }

    @Test(expected = RideException.class)
    public void changeRide() {
        RideDto rideDto = new RideDto();
        rideDto.setDeparture(new Date(0));
        routeService.changeRide(rideDto);
    }

    @Test
    public void performSearch() {
        Date date = new Date();
        SearchResultDto searchResultDto = new SearchResultDto();
        searchResultDto.setRideId(1);

        Station stationFrom = new Station();
        stationFrom.setName("from");
        Station stationTo = new Station();
        stationTo.setName("to");

        SearchDto searchDto = new SearchDto();
        searchDto.setStationFrom("from");
        searchDto.setStationTo("to");
        searchDto.setDate(date);

        Train train = new Train();
        train.setCapacity(1);
        train.setTraintId(1);
        Route route = new Route();
        route.setTrain(train);
        Ride ride = new Ride();
        ride.setRoute(route);
        ride.setRideId(1);

        RideHasStation rideHasStation = new RideHasStation();
        rideHasStation.setDeparture(date);

        when(stationDao.find("from")).thenReturn(stationFrom);
        when(stationDao.find("to")).thenReturn(stationTo);
        when(rideHasStationDao.findRideByStations(stationFrom, stationTo)).thenReturn(Arrays.asList(ride));
        when(ticketDao.countByRide(ride)).thenReturn(1L);
        when(rideHasStationDao.findByRideAndStation(ride, stationFrom)).thenReturn(rideHasStation);
        when(rideHasStationDao.findByRideAndStation(ride, stationTo)).thenReturn(rideHasStation);
        when(rideHasStationDao.sumPrice(ride, date, date)).thenReturn(1);
        assertEquals(0, routeService.performSearch(searchDto).size());
    }

    @Test
    public void buyTicketTrue() {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setFirstName("ololo");
        passengerDto.setLastName("ololo");
        passengerDto.setBirthDate(new Date(0));
        passengerDto.setRideId(1);
        Train train = new Train();
        train.setCapacity(2);
        Route route = new Route();
        route.setTrain(train);
        Ride ride = new Ride();
        ride.setRideId(1);
        ride.setRoute(route);


        when(passengerDao.find(passengerDto.getFirstName(), passengerDto.getLastName(), passengerDto.getBirthDate())).thenThrow(NoResultException.class);
        when(rideDao.read(passengerDto.getRideId())).thenReturn(ride);
        when(ticketDao.countByRide(ride)).thenReturn(1L);
        assertTrue(routeService.buyTicket(passengerDto));
    }



    @Test
    public void buyTicketFalse1() {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setFirstName("ololo");
        passengerDto.setLastName("ololo");
        passengerDto.setBirthDate(new Date(0));
        passengerDto.setRideId(1);
        Train train = new Train();
        train.setCapacity(2);
        Route route = new Route();
        route.setTrain(train);
        Ride ride = new Ride();
        ride.setRideId(1);
        ride.setRoute(route);


        when(passengerDao.find(passengerDto.getFirstName(), passengerDto.getLastName(), passengerDto.getBirthDate())).thenThrow(NoResultException.class);
        when(rideDao.read(passengerDto.getRideId())).thenReturn(ride);
        when(ticketDao.countByRide(ride)).thenReturn(3L);
        assertFalse(routeService.buyTicket(passengerDto));
    }

    @Test
    public void buyTicketFalse2() {
        PassengerDto passengerDto = new PassengerDto();
        assertFalse(routeService.buyTicket(passengerDto));
    }

    @Test
    public void getRideInfo() {
        Ride ride = new Ride();
        ride.setRideId(1);

        Station station = new Station();
        station.setName("ololo");
        RideHasStation rideHasStation = new RideHasStation();
        rideHasStation.setStation(station);
        rideHasStation.setDeparture(new Date());

        when(rideDao.getAll()).thenReturn(Arrays.asList(ride));
        when(rideHasStationDao.findFirstByRide(ride)).thenReturn(rideHasStation);
        when(rideHasStationDao.findLastByRide(ride)).thenReturn(rideHasStation);
        when(passengerDao.findByRide(ride)).thenReturn(Arrays.asList(new Passenger()));

        assertEquals(1, routeService.getRideInfo().size());
        assertEquals(1, routeService.getRideInfo().get(0).getRideId());
    }

    @Test
    public void getStationInfoDtos() {
        StationDto stationDto = new StationDto();
        stationDto.setName("ololo");

        Station station = new Station();

        Ride ride = new Ride();
        ride.setRideId(1);

        RideHasStation rideHasStation = new RideHasStation();
        rideHasStation.setDeparture(new Date());
        rideHasStation.setRide(ride);
        rideHasStation.setStation(station);

        when(stationDao.find(stationDto.getName())).thenReturn(station);
        when(rideHasStationDao.getRidesByStation(station)).thenReturn(Arrays.asList(rideHasStation));
        when(rideHasStationDao.getStationsByRide(ride)).thenReturn(Arrays.asList(rideHasStation));

        assertEquals(1, routeService.getStationInfoDtos(stationDto).size());
        assertEquals(1, routeService.getStationInfoDtos(stationDto).get(0).getRideId());
    }

    @Test
    public void getRideById() {
        Ride ride = new Ride();
        ride.setRideId(1);

        when(rideDao.read(1)).thenReturn(ride);

        assertEquals(1, routeService.getRideById(1).getRideId());
    }

    @Test
    public void deleteRideTrue() {
        Ride ride = new Ride();

        when(rideDao.read(1)).thenReturn(ride);
        when(ticketDao.countByRide(ride)).thenReturn(0L);
        assertTrue(routeService.deleteRide(1));
    }

    @Test
    public void deleteRideFalse() {
        Ride ride = new Ride();

        when(rideDao.read(1)).thenReturn(ride);
        when(ticketDao.countByRide(ride)).thenReturn(1L);
        assertFalse(routeService.deleteRide(1));
    }

    @Test(expected = StationException.class)
    public void deleteStation() {
        StationDto stationDto = new StationDto();
        stationDto.setStationId(1);

        Station station = new Station();
        station.setStationId(1);

        when(stationDao.read(1)).thenReturn(station);
        when(routeHasStationDao.stationUsed(station)).thenReturn(true);

        routeService.deleteStation(stationDto);
    }

    @Test(expected = RouteException.class)
    public void deleteRoute() {
        RouteDto routeDto = new RouteDto();
        routeDto.setRouteId(1);

        Route route = new Route();
        route.setRouteId(1);

        when(routeDao.read(1)).thenReturn(route);
        when(rideDao.routeUsed(route)).thenReturn(true);

        routeService.deleteRoute(routeDto);
    }
}