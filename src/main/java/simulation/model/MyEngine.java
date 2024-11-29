package simulation.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import simulation.framework.ArrivalProcess;
import simulation.framework.Clock;
import simulation.framework.Engine;
import simulation.framework.Event;

public class MyEngine extends Engine {
    private ArrivalProcess arrivalProcess;
    private ServicePoint[] servicePoints;

    public MyEngine() {
        super();
        servicePoints = new ServicePoint[4];
        servicePoints[0] = new ServicePoint("Ticket Check",new Normal(30,20),eventList,EventType.B2_TICKET_CHECK_FINISH);
        servicePoints[1] = new ServicePoint("Train Station 1",new Normal(240,600),eventList,EventType.B3_TRAIN1_DEPARTURE);
        servicePoints[2] = new ServicePoint("Train Station 2",new Normal(420,60),eventList,EventType.B4_TRAIN2_DEPARTURE);
        servicePoints[3] = new ServicePoint("Metro Station",new Normal(120,90),eventList,EventType.B5_TRAIN3_DEPARTURE);
        arrivalProcess = new ArrivalProcess(new Negexp(20),eventList,EventType.B1_PASSENGER_ARRIVAL);
    }
    protected void initialize(){
        arrivalProcess.generateNextEvent();
    }
    protected void runEvent(Event event){
        Passenger passenger;

        switch((EventType) event.getType()){
            case B1_PASSENGER_ARRIVAL:
                servicePoints[0].addToQueue(new Passenger());
                arrivalProcess.generateNextEvent();
                break;

            case B2_TICKET_CHECK_FINISH:
                passenger = servicePoints[0].removeFromQueue();
                if(passenger.getType() == PassengerType.Train){
                    if(servicePoints[1].getQueueSize() > servicePoints[2].getQueueSize()){
                        servicePoints[2].addToQueue(passenger);
                    }
                    else {
                        servicePoints[1].addToQueue(passenger);
                    }
                }
                else
                {
                    servicePoints[3].addToQueue(passenger);
                }
                break;
        }
    }

    protected void tryCEvents() {
        for (ServicePoint sp : servicePoints) {
            if (!sp.isReserved() && sp.isOnQueue()) {
                sp.beginService();
            }
        }
    }

    protected void results() {
        System.out.printf("\nSimulation ended at %d%n", Clock.getInstance().getTime());
        for(ServicePoint sp : servicePoints){
            System.out.printf("Total passengers at %s: %d\n", sp.getName(),sp.getQueueSize());
        }
    }
}
