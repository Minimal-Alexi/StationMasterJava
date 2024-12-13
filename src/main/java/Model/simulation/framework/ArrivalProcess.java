package Model.simulation.framework;

import Model.distributions.ContinuousGenerator;
import Model.distributions.Negexp;
import Model.simulation.model.EventType;

/**
 * The type Arrival process.
 */
public class ArrivalProcess {
    private ContinuousGenerator generator;
    private EventList eventList;
    private EventType eventType;

    /**
     * Instantiates a new Arrival process.
     *
     * @param generator the generator
     * @param eventList the event list
     * @param eventType the event type
     */
    public ArrivalProcess(ContinuousGenerator generator, EventList eventList, EventType eventType) {
        this.generator = generator;
        this.eventList = eventList;
        this.eventType = eventType;
    }

    /**
     * Generate next event long.
     *
     * @return the long
     */
    public long generateNextEvent(){
        long eventTime = Clock.getInstance().getTime() + (long) generator.sample();
        Event event = new Event(eventType, eventTime);
        eventList.add(event);
        return eventTime;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        EventList eventList = new EventList();
        ArrivalProcess arrivalProcess = new ArrivalProcess(new Negexp(10), eventList, EventType.B1_PASSENGER_ARRIVAL);

        for (int i = 0; i < 10; i++) {
            Clock.getInstance().setTime(arrivalProcess.generateNextEvent());
        }
        eventList.print();
    }
}
