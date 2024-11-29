package simulation.model;

import eduni.distributions.Bernoulli;
import simulation.framework.Clock;
import simulation.framework.SeedSingleton;

public class Passenger {
    private static int nr = 0;
    private static final double TRAIN_METRO_RATIO = 0.3;
    private static Bernoulli bernoulli = new Bernoulli(TRAIN_METRO_RATIO);
    private final int id;
    private final PassengerType type;
    private long arrivalTime;
    private long departureTime;
    private long serviceEntryTime;
    private long serviceExitTime;
    public Passenger() {
        this.id = nr++;
        this.arrivalTime = Clock.getInstance().getTime();
        if(bernoulli.sample() == 1){
            this.type = PassengerType.Train;
        }
        else{
            this.type = PassengerType.Metro;
        }
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
    public PassengerType getType(){
        return this.type;
    }
    public long getStationTime(){
        return this.departureTime-this.arrivalTime;
    }
    public long getServiceTime(){
        return this.serviceExitTime - this.serviceEntryTime;
    }
    public static void main(String[] args){
        int metro = 0,train = 0;
        for(int i=0; i<100;++i){
            Passenger passenger = new Passenger();
            if(passenger.getType() == PassengerType.Train){
                train++;
            }
            else if(passenger.getType() == PassengerType.Metro){
                metro++;
            }
        }
        System.out.println(train);
        System.out.println(metro);
    }
}
