package simulation.model;

import simulation.model.Passenger;

import java.util.ArrayList;

public class ServicePoint {
    protected final String serviceName;
    protected final ArrayList<Passenger> queue;
    protected int responseTime;
    protected int servicedCustomers;
    public ServicePoint(String serviceName){
        this.serviceName = serviceName;
        this.queue = new ArrayList<>();
        this.servicedCustomers = 0;
    }
    public void addPassenger(Passenger passenger){
        queue.add(passenger);
    }
    public void servePassenger(){
        Passenger passenger = queue.remove(0);
        servicedCustomers++;
    }
}