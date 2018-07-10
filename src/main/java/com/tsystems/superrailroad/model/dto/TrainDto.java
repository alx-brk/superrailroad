package main.java.com.tsystems.superrailroad.model.dto;

public class TrainDto {
    private int trainId;
    private int capacity;
    private float priceForKm;
    private int speed;

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
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
}
