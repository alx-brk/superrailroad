package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dto.RouteDto;

import java.util.List;

public interface RouteService {
    void createRoute(RouteDto routeDto);
    List<RouteDto> getAllRoutes();
}
