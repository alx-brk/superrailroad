package main.java.com.tsystems.superrailroad.controller;

import main.java.com.tsystems.superrailroad.model.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    private StationService stationService;
    private String stationJSPList = "stationJSPList";

    @Autowired
    public MainController(StationService stationService){
        this.stationService = stationService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject(stationJSPList, stationService.getAllStations());

        return modelAndView;
    }

    @RequestMapping(value = "/adminCreateStation", method = RequestMethod.GET)
    public ModelAndView adminCreateStation(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminCreateStation");
        modelAndView.addObject(stationJSPList, stationService.getAllStations());

        return modelAndView;
    }

    @RequestMapping(value = "/adminCreateTrain", method = RequestMethod.GET)
    public ModelAndView adminCreateTrain(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminCreateTrain");
        modelAndView.addObject(stationJSPList, stationService.getAllStations());

        return modelAndView;
    }
}
