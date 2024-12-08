package eventlist.src.simu.framework;

public class Event implements Comparable<Event> {
    private IEventType type;
    private double time;

    public Event(IEventType type, double time) {
        this.type = type;
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public IEventType getType() {
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
