package Model.simulation.framework;

import Model.simulation.model.EventType;

/**
 * Represents an event in the simulation.
 * This class implements the Comparable interface to allow events to be sorted by time.
 */
public class Event implements Comparable<Event> {
    private EventType type;
    private long time;

    /**
     * Constructs an Event with the specified type and time.
     *
     * @param type the type of the event
     * @param time the time of the event
     */
    public Event(EventType type, long time) {
        this.type = type;
        this.time = time;
    }

    /**
     * Returns the time of the event.
     *
     * @return the time of the event
     */
    public long getTime() {
        return time;
    }

    /**
     * Returns the type of the event.
     *
     * @return the type of the event
     */
    public EventType getType() {
        return type;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        return time + " [" + type + "]";
    }

    /**
     * Compares this event with the specified event for order.
     * Returns a negative integer, zero, or a positive integer as this event is less than, equal to, or greater than the specified event.
     *
     * @param e the event to be compared
     * @return a negative integer, zero, or a positive integer as this event is less than, equal to, or greater than the specified event
     */
    @Override
    public int compareTo(Event e) {
        if (time < e.time)
            return -1;
        else if (time > e.time)
            return 1;
        return 0;
    }
}