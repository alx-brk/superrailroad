package main.java.com.tsystems.superrailroad.model.dto;

import java.util.List;

public class StationInfoDto {
    private int rideId;
    private String datetime;
    private List<StationDateDto> stations;

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public List<StationDateDto> getStations() {
        return stations;
    }

    public void setStations(List<StationDateDto> stations) {
        this.stations = stations;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
