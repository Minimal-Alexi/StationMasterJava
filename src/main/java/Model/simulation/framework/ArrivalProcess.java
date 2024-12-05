package Model.simulation.framework;

import Model.distributions.ContinuousGenerator;
import Model.distributions.Negexp;
import Model.simulation.model.EventType;

public class ArrivalProcess {
    private ContinuousGenerator generator;
    private EventList eventList;
    private EventType eventType;
    public ArrivalProcess(ContinuousGenerator generator, EventList eventList, EventType eventType) {
        this.generator = generator;
        this.eventList = eventList;
        this.eventType = eventType;
    }
    public long generateNextEvent(){
        long eventTime = Clock.getInstance().getTime() + (long) generator.sample();
        Event event = new Event(eventType, eventTime);
        eventList.add(event);
        return eventTime;
    }
    public static void main(String[] args) {
        EventList eventList = new EventList();
        ArrivalProcess arrivalProcess = new ArrivalProcess(new Negexp(10), eventList, EventType.B1_PASSENGER_ARRIVAL);

        for (int i = 0; i < 10; i++) {
            Clock.getInstance().setTime(arrivalProcess.generateNextEvent());
        }
        eventList.print();
    }
}
