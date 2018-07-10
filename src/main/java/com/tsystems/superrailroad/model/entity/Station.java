package main.java.com.tsystems.superrailroad.model.entity;

import javax.persistence.*;

@Entity(name = "Station")
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private int stationId;

    @Column(name = "name")
    private String name;

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
