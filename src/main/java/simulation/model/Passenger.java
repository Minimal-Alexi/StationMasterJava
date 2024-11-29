package simulation.model;

public class Passenger {
    private static int nr = 0;
    private int id;
    private long arrivalTime;
    private long departureTime;
    private long serviceEntryTime;
    private long serviceExitTime;
    public Passenger(long arrivalTime) {
        this.id = nr++;
        this.arrivalTime = arrivalTime;
    }
    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setDepartureTime(long departureTime) {
        this.departureTime = departureTime;
    }
    public void setServiceEntryTime(long serviceEntryTime) {
        this.serviceEntryTime = serviceEntryTime;
    }
    public void setServiceExitTime(long serviceExitTime) {
        this.serviceExitTime = serviceExitTime;
    }
    public int getId(){
        return this.id;
    }
    public long getStationTime(){
        return this.departureTime-this.arrivalTime;
    }
    public long getServiceTime(){
        return this.serviceExitTime - this.serviceEntryTime;
    }

}
