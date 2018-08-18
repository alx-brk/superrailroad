package main.java.com.tsystems.superrailroad.model.entity;

import javax.persistence.*;

@Entity(name = "StationGraph")
@Table(name = "station_graph")
public class StationGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "distance")
    private Integer distance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ref_station_id")
    private Station stationRef;

    public StationGraph(){}

    public StationGraph(Station station, Station stationRef, int distance){
        this.station = station;
        this.stationRef = stationRef;
        this.distance = distance;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Station getStationRef() {
        return stationRef;
    }

    public void setStationRef(Station stationRef) {
        this.stationRef = stationRef;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
