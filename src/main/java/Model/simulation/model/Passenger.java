package Model.simulation.model;

import Model.distributions.Bernoulli;
import Model.simulation.framework.Clock;

public class Passenger {
    private static int nr = 0;
    private static double TRAIN_METRO_RATIO = 0.3;
    private static Bernoulli bernoulli = new Bernoulli(TRAIN_METRO_RATIO);
    private final int id;
    private final PassengerType type;
    private long arrivalTime;
    private long departureTime;
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
    public void setDepartureTime() {
        this.departureTime = Clock.getInstance().getTime();
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
    public static void setTrainMetroRatio(double trainMetroRatio){
        Passenger.TRAIN_METRO_RATIO = trainMetroRatio;
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
