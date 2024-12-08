package Model.simulation.framework;

import Model.simulation.filewriter.CSVWriter;

import java.util.Arrays;
import java.util.PriorityQueue;

public class EventList {
    private PriorityQueue<Event> evenList;
    private CSVWriter csvWriter;

    public EventList() {
        evenList = new PriorityQueue<>();

        csvWriter = new CSVWriter();
    }

    public void add(Event e) {
        System.out.printf(" Adding to the event list %s %d%n\n", e.getType(), e.getTime());
        evenList.add(e);
        csvWriter.writeEvent(e);
    }

    public Event remove() {
        if (evenList.isEmpty())
            return null;

        System.out.printf(" Removing from the event list %s %d%n", evenList.peek().getType(), evenList.peek().getTime());
        return evenList.remove();
    }

    public long getNextEventTime() {
        if (evenList.isEmpty())
            return 0;
        return evenList.peek().getTime();
    }

    public void print() {
        Object[] tmp = evenList.toArray();
        Arrays.sort(tmp);
        for (Object e : tmp)
            System.out.println(e);
    }
}