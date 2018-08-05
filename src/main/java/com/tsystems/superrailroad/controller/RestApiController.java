package main.java.com.tsystems.superrailroad.controller;

import main.java.com.tsystems.superrailroad.model.dto.*;
import main.java.com.tsystems.superrailroad.model.excep.PassengerExistException;
import main.java.com.tsystems.superrailroad.model.service.RouteService;
import main.java.com.tsystems.superrailroad.model.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestApiController {
    private StationService stationService;
    private RouteService routeService;

    @Autowired
    public RestApiController(StationService stationService, RouteService routeService){
        this.stationService = stationService;
        this.routeService = routeService;
    }

    @RequestMapping(value = "/admin/createStation", method = RequestMethod.POST, consumes = {"application/json"})
    public void createStation(@RequestBody StationGraphDto stationGraphDto){
        stationService.createEndStation(stationGraphDto);
    }

    @RequestMapping(value = "/admin/getConnectedStations", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody List<StationDto> getConnectedStations(@RequestBody StationDto stationDto){
        return stationService.getConnectedStations(stationDto);
    }

    @RequestMapping(value = "/admin/createTrain", method = RequestMethod.POST, consumes = {"application/json"})
    public void createTrain(@RequestBody RouteDto routeDto){
        routeService.createRoute(routeDto);
    }

    @RequestMapping(value = "/admin/createRide", method = RequestMethod.POST, consumes = {"application/json"})
    public void createRide(@RequestBody RideDto rideDto){
        routeService.createRide(rideDto);
    }

    @RequestMapping(value = "/admin/confirmChangeRide", method = RequestMethod.POST, consumes = {"application/json"})
    public void changeRide(@RequestBody RideDto rideDto){
        routeService.changeRide(rideDto);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody List<SearchResultDto> performSearch(@RequestBody SearchDto searchDto){
        return routeService.performSearch(searchDto);
    }

    @RequestMapping(value = "/stationInfo", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody List<StationInfoDto> performStationSearch(@RequestBody StationDto stationDto){
        return routeService.getStationInfoDtos(stationDto);
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST, consumes = {"application/json"})
    public void buyTicket(@RequestBody PassengerDto passengerDto) throws PassengerExistException{
        if (!routeService.buyTicket(passengerDto)){
            throw new PassengerExistException("Such passenger already bought ticket to this train");
        }
    }
}
