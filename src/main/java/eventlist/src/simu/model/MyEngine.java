package eventlist.src.simu.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eventlist.src.simu.framework.*;


/* Simulate a queueing system with one service point and one queue.
 * The service time is normally distributed with mean 10.
 * The interarrival time is exponentially distributed with mean 15.
 * The simulation runs until the number of arrivals exceeds 1000.
 * The program prints the average waiting time.
 */
public class MyEngine extends Engine {
    private ArrivalProcess arrivalProcess;
    private ServicePoint[] servicePoint;

   public MyEngine() {
        super();
        servicePoint = new ServicePoint[2];     // just an array of one element
        servicePoint[0] = new ServicePoint("S1", new Normal(10, 6), eventList, EventType.DEP1);
        servicePoint[1] = new ServicePoint("S2", new Normal(10, 10), eventList, EventType.DEP2);
        arrivalProcess = new ArrivalProcess(new Negexp(10), eventList, EventType.ARR);
    }

    protected void initialize() {
        arrivalProcess.generateNextEvent();
    }

    protected void runEvent(Event e) {
       Customer a;

       switch ((EventType) e.getType()) {
            case ARR:
                servicePoint[0].addToQueue(new Customer());
                arrivalProcess.generateNextEvent();
                break;

           case DEP1:
                a = servicePoint[0].removeFromQueue();
                servicePoint[1].addToQueue(a);
                break;

            case DEP2:
                a = servicePoint[1].removeFromQueue();
                a.setRemovalTime(Clock.getInstance().getClock());
                a.reportResults();
                break;
        }
    }

    protected void tryCEvents() {
        for (ServicePoint sp : servicePoint) {
            if (!sp.isReserved() && sp.isOnQueue()) {
                sp.beginService();
            }
        }
    }

    protected void results() {
        System.out.printf("\nSimulation ended at %.2f\n", Clock.getInstance().getClock());
        System.out.println("Total customers serviced: " + servicePoint[1].getCustomerServiced());
        //System.out.printf("Average service time: %.2f\n", servicePoint[0].getMeanServiceTime());
    }
}
