package main.java.com.tsystems.superrailroad.model.dto;

public class RouteHasStationDto {
    private int id;
    private int stationOrder;
    private StationDto stationDto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationOrder() {
        return stationOrder;
    }

    public void setStationOrder(int stationOrder) {
        this.stationOrder = stationOrder;
    }

    public StationDto getStationDto() {
        return stationDto;
    }

    public void setStationDto(StationDto stationDto) {
        this.stationDto = stationDto;
    }
}
