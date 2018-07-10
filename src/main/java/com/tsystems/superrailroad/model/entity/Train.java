package main.java.com.tsystems.superrailroad.model.entity;

import javax.persistence.*;

@Entity(name = "train")
@Table(name = "train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private int traintId;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "price_for_km")
    private float priceForKm;

    @Column(name = "speed")
    private int speed;

    @OneToOne(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Route route;

    public int getTraintId() {
        return traintId;
    }

    public void setTraintId(int traintId) {
        this.traintId = traintId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getPriceForKm() {
        return priceForKm;
    }

    public void setPriceForKm(float priceForKm) {
        this.priceForKm = priceForKm;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
