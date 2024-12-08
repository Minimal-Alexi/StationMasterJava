package eventlist.src.simu.framework;

import java.util.Arrays;
import java.util.PriorityQueue;

public class EventList {
    private PriorityQueue<Event> evenList;

    public EventList() {
        evenList = new PriorityQueue<>();
    }

    public void add(Event e) {
        System.out.printf(" Adding to the event list %s %.2f\n", e.getType(), e.getTime());
        evenList.add(e);
    }

    public Event remove() {
        if (evenList.isEmpty())
            return null;

        System.out.printf(" Removing from the event list %s %.2f", evenList.peek().getType(), evenList.peek().getTime());
        return evenList.remove();
    }

    public double getNextEventTime() {
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
