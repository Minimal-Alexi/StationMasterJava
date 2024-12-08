package eventlist.src.simu.framework;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eventlist.src.simu.model.EventType;


/* to add arrival events to the event list */
public class ArrivalProcess {
    private ContinuousGenerator generator;
    private EventList eventList;
    private IEventType type;

    public ArrivalProcess(ContinuousGenerator g, EventList tl, IEventType type) {
        this.generator = g;
        this.eventList = tl;
        this.type = type;
    }

    public double generateNextEvent() {
        double eventTime = Clock.getInstance().getClock() + generator.sample();
        Event t = new Event(type, eventTime);
        eventList.add(t);

        return eventTime;
    }

    public static void main(String[] args) {
        EventList eventList = new EventList();
        ArrivalProcess arrivalProcess = new ArrivalProcess(new Negexp(10), eventList, EventType.ARR);

        for (int i = 0; i < 10; i++) {
            Clock.getInstance().setClock(arrivalProcess.generateNextEvent());
        }
        eventList.print();
    }
}
