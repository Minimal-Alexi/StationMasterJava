package simulation.framework;

import simulation.model.EventType;

public class Event implements Comparable<Event> {
    private EventType type;
    private long time;

    public Event(EventType type, long time) {
        this.type = type;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return time + " [" + type + "]";
    }

    @Override
    public int compareTo(Event e) {
        if (time < e.time)
            return -1;
        else if (time > e.time)
            return 1;
        return 0;
    }
}