package main.java.com.tsystems.superrailroad.model.dto;

public class StationGraphDto {
    private String station;
    private String newStation;
    private Integer distance;

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getNewStation() {
        return newStation;
    }

    public void setNewStation(String newStation) {
        this.newStation = newStation;
    }
}
