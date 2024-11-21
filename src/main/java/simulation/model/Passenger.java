package simulation.model;

public class Passenger {
    private int id;
    private int arrivalTime;
    private int departureTime;
    private int serviceEntryTime;
    private int serviceExitTime;
    public Passenger(int id, int arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }
    public void setServiceEntryTime(int serviceEntryTime) {
        this.serviceEntryTime = serviceEntryTime;
    }
    public void setServiceExitTime(int serviceExitTime) {
        this.serviceExitTime = serviceExitTime;
    }
    public int getId(){
        return this.id;
    }
    public int getStationTime(){
        return this.departureTime-this.arrivalTime;
    }
    public int getServiceTime(){
        return this.serviceExitTime - this.serviceEntryTime;
    }

}
