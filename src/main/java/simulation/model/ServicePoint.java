package simulation.model;

import simulation.model.Passenger;
import simulation.framework.Clock;

import java.util.Random;
import java.util.ArrayList;

public class ServicePoint {
    protected final String serviceName;
    protected final ArrayList<Passenger> queue;
    protected final ArrayList<ServicePoint> nextPoints;
    protected int responseTime;
    protected int servicedCustomers;
    protected float averageResponseTime;
    protected Random rand = new Random();
    protected int minimumResponseTime,maximumResponseTime;

    public ServicePoint(String serviceName, ArrayList<ServicePoint> nextPoints, int minimumResponseTime, int maximumResponseTime) {
        this.serviceName = serviceName;
        this.queue = new ArrayList<>();
        this.servicedCustomers = 0;
        this.averageResponseTime = 0;
        this.nextPoints = nextPoints;
        this.minimumResponseTime = minimumResponseTime;
        this.maximumResponseTime = maximumResponseTime;
    }
    public void addPassenger(Passenger passenger, Clock clock){
        passenger.setServiceEntryTime(clock.getTime());
        queue.add(passenger);
    }
    public void servePassenger(Clock clock){
        Passenger passenger = queue.remove(0);
        if(nextPoints.isEmpty()){
            passenger.setServiceExitTime(clock.getTime());
            passenger.setDepartureTime(clock.getTime());
        }
        else{
            passenger.setServiceExitTime(clock.getTime());
        }
        this.averageResponseTime += passenger.getServiceTime();
        this.servicedCustomers++;
    }
    public void generateResponseTime(){
        this.responseTime = rand.nextInt(maximumResponseTime-minimumResponseTime) + minimumResponseTime;
    }
    public void setResponseTime(int responseTime){
        this.responseTime = responseTime;
    }
    public int getResponseTime(){
        return responseTime;
    }
    public float getAverageResponseTime(){
        averageResponseTime /= servicedCustomers;
        return averageResponseTime;
    }
}