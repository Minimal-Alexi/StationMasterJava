package model;

import java.util.ArrayList;

public class ServicePoint {
    protected final String serviceName;
    protected final ArrayList<Passenger> queue;
    protected int servicedCustomers;
    public ServicePoint(String serviceName){
        this.serviceName = serviceName;
        this.queue = new ArrayList<>();
        this.servicedCustomers = 0;
    }
    public void addPassenger(Passenger c){
        queue.add(c);
    }
    public void servePassenger(){
        Passenger passenger = queue.remove(0);
        servicedCustomers++;
    }
}