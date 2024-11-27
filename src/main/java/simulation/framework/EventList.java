package simulation.framework;

import java.util.Arrays;
import java.util.PriorityQueue;

public class EventList {
    private PriorityQueue<Event> eventList;

    public EventList() {
        eventList = new PriorityQueue<>();
    }

    public void add(Event e) {
        System.out.printf(" Adding to the event list %s %.2f\n", e.getType(), e.getTime());
        eventList.add(e);
    }

    public Event remove() {
        if (eventList.isEmpty())
            return null;

        System.out.printf(" Removing from the event list %s %.2f", eventList.peek().getType(), eventList.peek().getTime());
        return eventList.remove();
    }

    public long getNextEventTime() {
        if (eventList.isEmpty())
            return 0;
        return eventList.peek().getTime();
    }

    public void print() {
        Object[] tmp = eventList.toArray();
        Arrays.sort(tmp);
        for (Object e : tmp)
            System.out.println(e);
    }
}