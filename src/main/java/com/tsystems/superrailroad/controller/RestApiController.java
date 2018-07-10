package main.java.com.tsystems.superrailroad.controller;

import main.java.com.tsystems.superrailroad.model.dto.RouteDto;
import main.java.com.tsystems.superrailroad.model.dto.StationDto;
import main.java.com.tsystems.superrailroad.model.dto.StationGraphDto;
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
}
