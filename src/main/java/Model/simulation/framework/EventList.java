package Model.simulation.framework;

import Model.simulation.filewriter.CSVWriter;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * A class representing a list of events in the simulation.
 * This class uses a priority queue to manage events and writes events to a CSV file.
 */
public class EventList {
    private PriorityQueue<Event> evenList;
    private CSVWriter csvWriter;

    /**
     * Constructs an EventList.
     * Initializes the priority queue and the CSV writer.
     */
    public EventList() {
        evenList = new PriorityQueue<>();
        csvWriter = new CSVWriter();
    }

    /**
     * Adds an event to the event list and writes it to the CSV file.
     *
     * @param e the event to add
     */
    public void add(Event e) {
        System.out.printf(" Adding to the event list %s %d%n\n", e.getType(), e.getTime());
        evenList.add(e);
        csvWriter.writeEvent(e);
    }

    /**
     * Removes and returns the next event from the event list.
     * If the event list is empty, returns null.
     *
     * @return the next event, or null if the event list is empty
     */
    public Event remove() {
        if (evenList.isEmpty())
            return null;

        System.out.printf(" Removing from the event list %s %d%n", evenList.peek().getType(), evenList.peek().getTime());
        return evenList.remove();
    }

    /**
     * Returns the time of the next event in the event list.
     * If the event list is empty, returns 0.
     *
     * @return the time of the next event, or 0 if the event list is empty
     */
    public long getNextEventTime() {
        if (evenList.isEmpty())
            return 0;
        return evenList.peek().getTime();
    }

    /**
     * Prints all events in the event list.
     * The events are sorted by time before being printed.
     */
    public void print() {
        Object[] tmp = evenList.toArray();
        Arrays.sort(tmp);
        for (Object e : tmp)
            System.out.println(e);
    }
}