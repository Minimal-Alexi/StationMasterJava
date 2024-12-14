package Model.simulation.model;

import Model.distributions.ContinuousGenerator;
import Model.simulation.framework.Clock;
import Model.simulation.framework.Event;
import Model.simulation.framework.EventList;

import java.util.LinkedList;

/**
 * Represents a service point in the simulation where passengers are serviced.
 */
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

    /**
     * Constructs a ServicePoint with the specified name, generator, event list, and event type.
     *
     * @param name the name of the service point
     * @param g the continuous generator for service times
     * @param tl the event list
     * @param type the event type scheduled after service
     */
    public ServicePoint(String name, ContinuousGenerator g, EventList tl, EventType type) {
        this.name = name;
        this.generator = g;
        this.eventList = tl;
        this.eventTypeScheduled = type;
        this.serviceTimeSum = 0;
        this.customerServiced = 0;
        queue = new LinkedList<>();
    }

    /**
     * Adds a passenger to the queue.
     *
     * @param a the passenger to add
     */
    public void addToQueue(Passenger a) {
        queue.addFirst(a);
    }

    /**
     * Removes and returns a passenger from the queue.
     * Increments the number of customers serviced and sets reserved to false.
     *
     * @return the removed passenger, or null if the queue is empty
     */
    public Passenger removeFromQueue() {
        if (queue.size() > 0) {
            Passenger a = queue.removeLast();
            customerServiced++;
            reserved = false;
            return a;
        } else
            return null;
    }

    /**
     * Begins servicing the next passenger in the queue.
     * Schedules the next event based on the service time.
     */
    public void beginService() {
        System.out.printf("%sStarting service %s for the customer #%d%s\n", GREEN, name, queue.peek().getId(), WHITE);

        reserved = true;
        long serviceTime = (long) generator.sample();
        this.serviceTimeSum += serviceTime;
        eventList.add(new Event(eventTypeScheduled, Clock.getInstance().getTime() + serviceTime));
    }

    /**
     * Returns whether the service point is reserved.
     *
     * @return true if the service point is reserved, false otherwise
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Returns whether there are passengers in the queue.
     *
     * @return true if there are passengers in the queue, false otherwise
     */
    public boolean isOnQueue() {
        return queue.size() > 0;
    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue
     */
    public int getQueueSize() {
        return queue.size();
    }

    /**
     * Returns the name of the service point.
     *
     * @return the name of the service point
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of customers serviced.
     *
     * @return the number of customers serviced
     */
    public int getCustomerServiced() {
        return customerServiced;
    }

    /**
     * Returns the mean service time.
     *
     * @return the mean service time, or -1 if no customers have been serviced
     */
    public double getMeanServiceTime() {
        if (customerServiced == 0) return -1;
        return serviceTimeSum / customerServiced;
    }
}