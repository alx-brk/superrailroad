package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dto.*;

import java.util.List;

public interface RouteService {
    void createRoute(RouteDto routeDto);
    List<RouteDto> getAllRoutes();
    void createRide(RideDto rideDto);
    List<SearchResultDto> performSearch(SearchDto searchDto);
    boolean buyTicket(PassengerDto passengerDto);
    List<RideInfoDto> getRideInfo();
    List<StationInfoDto> getStationInfoDtos(StationDto stationDto);
}
