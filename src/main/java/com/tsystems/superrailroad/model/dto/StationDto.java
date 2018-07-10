package main.java.com.tsystems.superrailroad.model.dto;


public class StationDto {
    private Integer stationId;
    private String name;

    public StationDto(){}

    public StationDto(String name){
        this.name = name;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
