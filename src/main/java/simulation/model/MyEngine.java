package simulation.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import simulation.framework.ArrivalProcess;
import simulation.framework.Clock;
import simulation.framework.SeedSingleton;
import simulation.framework.Engine;
import simulation.framework.Event;

import java.util.ArrayList;

public class MyEngine extends Engine {
    private final TrainStation[] trainStations;
    private final ArrivalProcess[] arrivalProcesses;
    private final ServicePoint[] servicePoints;

    public MyEngine() {
        super();
        servicePoints = new ServicePoint[1];
        trainStations = new TrainStation[3];
        arrivalProcesses = new ArrivalProcess[4];
        servicePoints[0] = new ServicePoint("Ticket Check", new Normal(20, 20), eventList, EventType.B2_TICKET_CHECK_FINISH);
        trainStations[0] = new TrainStation("Train Station 1", new Normal(60, 120),new Normal(20,30), eventList, EventType.B3_TRAIN1_DEPARTURE);
        trainStations[1] = new TrainStation("Train Station 2", new Normal(60, 120),new Normal(20,30), eventList, EventType.B4_TRAIN2_DEPARTURE);
        trainStations[2] = new TrainStation("Metro Station", new Normal(30, 45),new Normal(50,10), eventList, EventType.B5_TRAIN3_DEPARTURE);
        arrivalProcesses[0] = new ArrivalProcess(new Negexp(20), eventList, EventType.B1_PASSENGER_ARRIVAL);
        arrivalProcesses[1] = new ArrivalProcess(new Normal(240, 600), eventList, EventType.B6_TRAIN1_ARRIVAL);
        arrivalProcesses[2] = new ArrivalProcess(new Normal(420, 60), eventList, EventType.B7_TRAIN2_ARRIVAL);
        arrivalProcesses[3] = new ArrivalProcess(new Normal(120, 90), eventList, EventType.B8_TRAIN3_ARRIVAL);
    }

    protected void initialize() {
        for (ArrivalProcess arrivalProcess : arrivalProcesses) {
            arrivalProcess.generateNextEvent();
        }
    }

    protected void runEvent(Event event) {
        Passenger passenger;
        ArrayList<Passenger> passengers;
        TrainStation trainStation;

        switch ((EventType) event.getType()) {
            case B1_PASSENGER_ARRIVAL:
                servicePoints[0].addToQueue(new Passenger());
                arrivalProcesses[0].generateNextEvent();
                break;

            case B2_TICKET_CHECK_FINISH:
                passenger = servicePoints[0].removeFromQueue();
                if (passenger.getType() == PassengerType.Train) {
                    if (trainStations[0].getQueueSize() > trainStations[1].getQueueSize()) {
                        trainStations[1].addToQueue(passenger);
                    } else {
                        trainStations[0].addToQueue(passenger);
                    }
                } else {
                    trainStations[2].addToQueue(passenger);
                }
                break;
            case B3_TRAIN1_DEPARTURE:
                arrivalProcesses[1].generateNextEvent();
                trainStation = trainStations[0];
                passengers = trainStation.loadTrain();
                passengerListReport(passengers);
                break;
            case B4_TRAIN2_DEPARTURE:
                arrivalProcesses[2].generateNextEvent();
                trainStation = trainStations[1];
                passengers = trainStation.loadTrain();
                passengerListReport(passengers);
                break;
            case B5_TRAIN3_DEPARTURE:
                arrivalProcesses[3].generateNextEvent();
                trainStation = trainStations[2];
                passengers = trainStation.loadTrain();
                System.out.println(passengers);
                passengerListReport(passengers);
                break;
            case B6_TRAIN1_ARRIVAL:
                trainStation = trainStations[0];
                trainStation.beginService();
                break;
            case B7_TRAIN2_ARRIVAL:
                trainStation = trainStations[1];
                trainStation.beginService();
                break;
            case B8_TRAIN3_ARRIVAL:
                trainStation = trainStations[2];
                trainStation.beginService();
                break;
        }
    }

    protected void tryCEvents() {
        for (ServicePoint sp : servicePoints) {
            if (!sp.isReserved() && sp.isOnQueue() && sp.getClass() == ServicePoint.class) {
                sp.beginService();
            }
        }
    }

    protected void results() {
        System.out.printf("\nSimulation ended at %d%n", Clock.getInstance().getTime());
        for (ServicePoint sp : servicePoints) {
            System.out.printf("Total passengers at %s: %d\nTotal passengers serviced: %d\nAverage service time: %.2f\n\n", sp.getName(), sp.getQueueSize(), sp.getCustomerServiced(), sp.getMeanServiceTime());
        }
        for (TrainStation st : trainStations) {
            System.out.printf("Total passengers at %s: %d\nTotal passengers serviced: %d\nAverage station wait time: %.2f\n"
                    + "%d trains stopped at the station, with an average capacity of: %.2f \n\n", st.getName(), st.getQueueSize(),st.getCustomerServiced(), st.getMeanServiceTime(),st.getTotalTrains(),st.getMeanTrainCapacity());
        }
    }

    private void passengerListReport(ArrayList<Passenger> passengers){
        if(passengers != null && !passengers.isEmpty()) {
            for(Passenger passenger : passengers) {
                passenger.setDepartureTime();
            }
        }
    }
}
