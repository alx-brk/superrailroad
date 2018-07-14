package main.java.com.tsystems.superrailroad.model.dto;

import java.util.Date;

public class RideDto {
    private int rideId;
    private int route;
    private Date departure;

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }
}
