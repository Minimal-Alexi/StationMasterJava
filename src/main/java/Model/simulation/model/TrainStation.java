package Model.simulation.model;

import Model.distributions.ContinuousGenerator;
import Model.simulation.framework.Clock;
import Model.simulation.framework.Event;
import Model.simulation.framework.EventList;

import java.util.ArrayList;

/**
 * The TrainStation class is an extension of the ServicePoint class.
 * It takes the reserved variable from the superclass and uses it to mark if the train station/platform is in use.
 * It has an event for the departure of the train and one for the arrival of it.
 */
public class TrainStation extends ServicePoint {
    private int totalTrainCapacity;
    private int totalTrains;
    private int currentCapacity;
    private int totalLoadedCapacity;
    private long totalTrainTravelTime;
    private long lastDepartureTime;
    private final ContinuousGenerator trainCapacityGenerator;

    /**
     * Constructs a TrainStation with the specified parameters.
     *
     * @param name the name of the train station
     * @param stationWaitTime the generator for station wait times
     * @param trainCapacity the generator for train capacities
     * @param tl the event list
     * @param departureType the event type for train departures
     */
    public TrainStation(String name, ContinuousGenerator stationWaitTime, ContinuousGenerator trainCapacity, EventList tl, EventType departureType) {
        super(name, stationWaitTime, tl, departureType);
        this.trainCapacityGenerator = trainCapacity;
        this.totalTrainCapacity = 0;
        this.totalTrains = 0;
        this.totalTrainTravelTime = 0;
        this.lastDepartureTime = 0;
        this.totalLoadedCapacity = 0;
    }

    /**
     * Loads passengers onto the train and returns the list of loaded passengers.
     *
     * @return the list of loaded passengers
     */
    public ArrayList<Passenger> loadTrain() {
        ArrayList<Passenger> loadedPassengers = new ArrayList<>();
        lastDepartureTime = Clock.getInstance().getTime();
        while (!queue.isEmpty() && currentCapacity > 0) {
            Passenger passenger = super.queue.remove();
            customerServiced++;
            currentCapacity--;
            loadedPassengers.add(passenger);
        }
        reserved = false;
        if (!loadedPassengers.isEmpty()) {
            System.out.printf("%sTrain left from %s. The train loaded %d passengers, leaving the station with: %d passengers.%s\n", ServicePoint.GREEN, super.name, loadedPassengers.size(), queue.size(), ServicePoint.WHITE);
            totalLoadedCapacity += loadedPassengers.size();
            return loadedPassengers;
        }
        return null;
    }

    /**
     * Begins servicing the next train at the station.
     * Schedules the next event based on the service time.
     */
    @Override
    public void beginService() {
        currentCapacity = (int) trainCapacityGenerator.sample();
        this.totalTrainCapacity += currentCapacity;
        this.totalTrains++;
        System.out.printf("%sTrain arrived at %s, capacity of %d.\nThe station currently has: %d passengers.%s\n", ServicePoint.GREEN, super.name, currentCapacity, queue.size(), ServicePoint.WHITE);
        long arrivalTime = Clock.getInstance().getTime();
        addTravelTime(arrivalTime - lastDepartureTime);
        long serviceTime = (long) super.generator.sample();
        super.reserved = true;
        super.serviceTimeSum += serviceTime;
        eventList.add(new Event(super.eventTypeScheduled, Clock.getInstance().getTime() + serviceTime));
    }

    /**
     * Adds the travel time of the train to the total travel time.
     *
     * @param newTravelTime the travel time to add
     */
    private void addTravelTime(long newTravelTime) {
        totalTrainTravelTime += newTravelTime;
    }

    /**
     * Returns the total number of trains that have arrived at the station.
     *
     * @return the total number of trains
     */
    public int getTotalTrains() {
        return totalTrains;
    }

    /**
     * Returns the mean capacity of the trains that have arrived at the station.
     *
     * @return the mean train capacity
     */
    public double getMeanTrainCapacity() {
        return (double) totalTrainCapacity / totalTrains;
    }

    /**
     * Returns the mean travel time of the trains that have arrived at the station.
     *
     * @return the mean travel time
     */
    public double getMeanTravelTime() {
        return (double) totalTrainTravelTime / totalTrains;
    }

    /**
     * Returns the mean loaded capacity of the trains that have arrived at the station.
     *
     * @return the mean loaded capacity
     */
    public double getMeanLoadedCapacity() {
        return (double) totalLoadedCapacity / totalTrains;
    }

    /**
     * Returns the current capacity of the train at the station.
     *
     * @return the current capacity
     */
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    /**
     * Returns the mean service time of the trains at the station.
     *
     * @return the mean service time
     */
    @Override
    public double getMeanServiceTime() {
        if (totalTrains == 0) return 0;
        return serviceTimeSum / totalTrains;
    }
}