package Model.simulation.model;

import Model.distributions.ContinuousGenerator;
import Model.simulation.framework.Clock;
import Model.simulation.framework.Event;
import Model.simulation.framework.EventList;

import java.util.ArrayList;

/*
The train station class is an extension of the service point class.
It takes the reserved variable from the super class and uses it to mark if the train station/platform is in use.
It has an event for departure of the train, and one for the arrival of it.

*/

public class TrainStation extends ServicePoint{
    private int totalTrainCapacity;
    private int totalTrains;
    private int currentCapacity;
    private int totalLoadedCapacity;
    private long totalTrainTravelTime;
    private long lastDepartureTime;
    private final ContinuousGenerator trainCapacityGenerator;

    public TrainStation(String name, ContinuousGenerator stationWaitTime, ContinuousGenerator trainCapacity, EventList tl, EventType departureType) {
        super(name,stationWaitTime,tl,departureType);
        this.trainCapacityGenerator = trainCapacity;
        this.totalTrainCapacity = 0;
        this.totalTrains = 0;
        this.totalTrainTravelTime = 0;
        this.lastDepartureTime = 0;
        this.totalLoadedCapacity = 0;
    }
    public ArrayList<Passenger> loadTrain(){
        ArrayList<Passenger> loadedPassengers = new ArrayList<>();
        lastDepartureTime = Clock.getInstance().getTime();
        while(!queue.isEmpty() && currentCapacity > 0){
            Passenger passenger = super.queue.remove();
            customerServiced++;
            currentCapacity--;
            loadedPassengers.add(passenger);
        }
        reserved = false;
        if(!loadedPassengers.isEmpty()){
            System.out.printf("%sTrain leaved from %s. The train loaded %d passengers, leaving the station with : %d passengers. %s\n",ServicePoint.GREEN,super.name,loadedPassengers.size(),queue.size(),ServicePoint.WHITE);
            totalLoadedCapacity += loadedPassengers.size();
            return loadedPassengers;
        }
        return null;
    }

    @Override
    public void beginService(){
        currentCapacity = (int) trainCapacityGenerator.sample();
        this.totalTrainCapacity += currentCapacity;
        this.totalTrains++;
        System.out.printf("%sTrain arrived at %s, capacity of %d.\nThe station currently has: %d passengers.%s\n",ServicePoint.GREEN,super.name,currentCapacity,queue.size(),ServicePoint.WHITE);
        long arrivalTime = Clock.getInstance().getTime();
        addTravelTime(arrivalTime-lastDepartureTime);
        long serviceTime = (long) super.generator.sample();
        super.reserved = true;
        super.serviceTimeSum += serviceTime;
        eventList.add(new Event(super.eventTypeScheduled, Clock.getInstance().getTime() + serviceTime));
    }
    private void addTravelTime(long newTravelTime){
        totalTrainTravelTime += newTravelTime;
    }
    public int getTotalTrains(){
        return totalTrains;
    }
    public double getMeanTrainCapacity(){
        return (double) totalTrainCapacity / totalTrains;
    }
    public double getMeanTravelTime(){return (double) totalTrainTravelTime / totalTrains;}
    public double getMeanLoadedCapacity(){return (double) totalLoadedCapacity / totalTrains;}
    @Override
    public double getMeanServiceTime() {
        if(totalTrains == 0) return 0;
        return serviceTimeSum / totalTrains;
    }
}
