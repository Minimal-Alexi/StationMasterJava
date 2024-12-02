package simulation.model;

import eduni.distributions.ContinuousGenerator;
import simulation.framework.Clock;
import simulation.framework.Event;
import simulation.framework.EventList;

import java.util.LinkedList;

public class ServicePoint {
    protected static final String GREEN = "\033[0;32m";
    protected static final String WHITE = "\033[0;37m";
    protected LinkedList<Passenger> queue;
    protected ContinuousGenerator generator;
    protected EventList eventList;
    protected EventType eventTypeScheduled;
    protected String name;
    protected double serviceTimeSum;
    protected int customerServiced;
    protected boolean reserved = false;

    public ServicePoint(String name, ContinuousGenerator g, EventList tl, EventType type) {
        this.name = name;
        this.generator = g;
        this.eventList = tl;
        this.eventTypeScheduled = type;
        this.serviceTimeSum = 0;
        this.customerServiced = 0;

        queue = new LinkedList<>();
    }

    public void addToQueue(Passenger a) {
        queue.addFirst(a);
    }

    public Passenger removeFromQueue() {
        if (queue.size() > 0) {
            Passenger a = queue.removeLast();
            customerServiced++;
            reserved = false;
            return a;
        } else
            return null;
    }

    public void beginService() {
        System.out.printf("%sStarting service %s for the customer #%d%s\n", GREEN, name, queue.peek().getId(), WHITE);

        reserved = true;
        long serviceTime = (long) generator.sample();
        this.serviceTimeSum += serviceTime;
        eventList.add(new Event(eventTypeScheduled, Clock.getInstance().getTime() + serviceTime));
    }

    public boolean isReserved() {
        return reserved;
    }

    public boolean isOnQueue() {
        return queue.size() > 0;
    }
    public int getQueueSize() {
        return queue.size();
    }

    public String getName() {
        return name;
    }

    public int getCustomerServiced() {
        return customerServiced;
    }

    public double getMeanServiceTime() {
        if(customerServiced == 0) return -1;
        return serviceTimeSum / customerServiced;
    }

//    public double getTotalQueueTime() {
//        double totalQueueTime = 0;
//        for (Passenger passenger : queue) {
//            totalQueueTime += Clock.getInstance().getClock() - passenger.getArrivalTime();
//        }
//        return totalQueueTime;
//    }
}