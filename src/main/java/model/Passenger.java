package model;

public class Passenger {
    private int id;
    private int arrivalTime;
    public Passenger(int id, int arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }
    public int getId() {
        return id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
}
