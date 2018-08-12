package main.java.com.tsystems.superrailroad.controller;


import main.java.com.tsystems.superrailroad.model.dto.UserDto;
import main.java.com.tsystems.superrailroad.model.service.RouteService;
import main.java.com.tsystems.superrailroad.model.service.StationService;
import main.java.com.tsystems.superrailroad.model.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    private static final Logger log = Logger.getLogger(MainController.class);
    private StationService stationService;
    private RouteService routeService;
    private UserService userService;
    private String stationJSPList = "stationJSPList";

    @Autowired
    public MainController(StationService stationService, RouteService routeService, UserService userService){
        this.stationService = stationService;
        this.routeService = routeService;
        this.userService = userService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject(stationJSPList, stationService.getAllStations());

        return modelAndView;
    }

    @RequestMapping(value = "/stationSearch", method = RequestMethod.GET)
    public ModelAndView showStationSearch(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationSearch");
        modelAndView.addObject(stationJSPList, stationService.getAllStations());

        return modelAndView;
    }

    @RequestMapping(value = "/search/{rideId}", method = RequestMethod.GET)
    public ModelAndView passengerForm(@PathVariable("rideId") int rideId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passenger");
        modelAndView.addObject("rideIdJSP", rideId);

        return modelAndView;
    }

    @RequestMapping(value = "admin/changeRide/{rideId}", method = RequestMethod.GET)
    public ModelAndView changeRide(@PathVariable("rideId") int rideId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminChangeRide");
        modelAndView.addObject("rideIdJSP", routeService.getRideById(rideId));

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(WebRequest request, Model model){
        model.addAttribute("userJSP", new UserDto());

        return "registration";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView showError(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("error", "Something went wrong");

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("userJSP") UserDto userDto){
        ModelAndView modelAndView = new ModelAndView();
        if (userDto.getPassword().equals(userDto.getRepeatPassword())){
            if (userService.userExists(userDto)){
                modelAndView.setViewName("error");
                modelAndView.addObject("error", "User with such login already exists");
            } else {
                modelAndView.setViewName("login");
                userService.createUser(userDto);
            }
        } else {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Password doesn't match");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(WebRequest request, Model model){

        return "login";
    }

    @RequestMapping(value = "/admin/createStation", method = RequestMethod.GET)
    public ModelAndView adminCreateStation(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminCreateStation");
        modelAndView.addObject(stationJSPList, stationService.getAllStations());

        return modelAndView;
    }

    @RequestMapping(value = "/admin/createTrain", method = RequestMethod.GET)
    public ModelAndView adminCreateTrain(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminCreateTrain");

        return modelAndView;
    }

    @RequestMapping(value = "/admin/createRide", method = RequestMethod.GET)
    public ModelAndView adminCreateRide(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminCreateRide");

        return modelAndView;
    }

    @RequestMapping(value = "/admin/trainInfo", method = RequestMethod.GET)
    public ModelAndView showTrainInfo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminTrainInfo");
        modelAndView.addObject("rideInfoJSP", routeService.getRideInfo());

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("error", "Something went wrong");

        log.warn("Exception handler");
        return modelAndView;
    }
}
