package main.java.com.tsystems.superrailroad.model.dto;

import java.util.List;

public class RouteDto {
    private int routeId;
    private TrainDto trainDto;
    private List<RouteHasStationDto> routeHasStationDtoList;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public TrainDto getTrainDto() {
        return trainDto;
    }

    public void setTrainDto(TrainDto trainDto) {
        this.trainDto = trainDto;
    }

    public List<RouteHasStationDto> getRouteHasStationDtoList() {
        return routeHasStationDtoList;
    }

    public void setRouteHasStationDtoList(List<RouteHasStationDto> routeHasStationDtoList) {
        this.routeHasStationDtoList = routeHasStationDtoList;
    }
}
