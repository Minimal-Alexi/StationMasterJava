package Model.simulation.model;

import Model.distributions.Negexp;
import Model.distributions.Normal;

import Model.simulation.framework.ArrivalProcess;
import Model.simulation.framework.Clock;
import Model.simulation.framework.Engine;
import Model.simulation.framework.Event;
import Model.simulation.filewriter.CSVWriter;

import java.util.ArrayList;

public class MyEngine extends Engine {
    private static final int timer = 2000;
    private int speed = 1000;
    private final TrainStation[] trainStations;
    private final ArrivalProcess[] arrivalProcesses;
    private final ServicePoint[] servicePoints;
    private final CSVWriter csvWriter;

    public MyEngine(long seed) {
        super();
        servicePoints = new ServicePoint[2];
        trainStations = new TrainStation[3];
        arrivalProcesses = new ArrivalProcess[5];
        servicePoints[0] = new ServicePoint("Ticket Check 1", new Normal(10, 5,seed), eventList, EventType.B3_TICKET_CHECK_FINISH);
        servicePoints[1] = new ServicePoint("Ticket Check 2", new Normal(10, 5,seed), eventList, EventType.B4_TICKET_CHECK_FINISH);
        trainStations[0] = new TrainStation("Train Station 1", new Normal(120, 60,seed), new Normal(70, 30,seed), eventList, EventType.B5_TRAIN1_DEPARTURE);
        trainStations[1] = new TrainStation("Train Station 2", new Normal(120, 60,seed), new Normal(80, 50,seed), eventList, EventType.B6_TRAIN2_DEPARTURE);
        trainStations[2] = new TrainStation("Metro Station", new Normal(60, 20,seed), new Normal(100, 25,seed), eventList, EventType.B7_TRAIN3_DEPARTURE);
        arrivalProcesses[0] = new ArrivalProcess(new Negexp(10), eventList, EventType.B1_PASSENGER_ARRIVAL);
        arrivalProcesses[1] = new ArrivalProcess(new Negexp(10), eventList, EventType.B2_PASSENGER_ARRIVAL);
        arrivalProcesses[2] = new ArrivalProcess(new Normal(1800, 120,seed), eventList, EventType.B8_TRAIN1_ARRIVAL);
        arrivalProcesses[3] = new ArrivalProcess(new Normal(1500, 600,seed), eventList, EventType.B9_TRAIN2_ARRIVAL);
        arrivalProcesses[4] = new ArrivalProcess(new Normal(540, 90,seed), eventList, EventType.B10_TRAIN3_ARRIVAL);
        csvWriter = new CSVWriter(seed);
    }

    protected void initialize() {
        for (ArrivalProcess arrivalProcess : arrivalProcesses) {
            arrivalProcess.generateNextEvent();
        }
    }

    protected void listenerTimeUpdate(long currentTime) {
        if(connector != null) {
            connector.onTimeUpdate(currentTime);
        }
    }

    protected void listenerUpdate(){
        if(connector != null){
            synchronized (this) {
                try{
                    connector.onUpdate(servicePoints,trainStations);
                    wait(timer - speed);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
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
            case B2_PASSENGER_ARRIVAL:
                servicePoints[1].addToQueue(new Passenger());
                arrivalProcesses[1].generateNextEvent();
                break;
            case B3_TICKET_CHECK_FINISH:
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
            case B4_TICKET_CHECK_FINISH:
                passenger = servicePoints[1].removeFromQueue();
                if (passenger.getType() == PassengerType.Train) {
                    trainStations[1].addToQueue(passenger);
                } else {
                    trainStations[2].addToQueue(passenger);
                }
                break;
            case B5_TRAIN1_DEPARTURE:
                trainStation = trainStations[0];
                arrivalProcesses[2].generateNextEvent();
                passengers = trainStation.loadTrain();
                passengerListReport(passengers);
                break;
            case B6_TRAIN2_DEPARTURE:
                trainStation = trainStations[1];
                arrivalProcesses[3].generateNextEvent();
                passengers = trainStation.loadTrain();
                passengerListReport(passengers);
                break;
            case B7_TRAIN3_DEPARTURE:
                trainStation = trainStations[2];
                arrivalProcesses[4].generateNextEvent();
                passengers = trainStation.loadTrain();
                passengerListReport(passengers);
                break;
            case B8_TRAIN1_ARRIVAL:
                trainStation = trainStations[0];
                trainStation.beginService();
                break;
            case B9_TRAIN2_ARRIVAL:
                trainStation = trainStations[1];
                trainStation.beginService();
                break;
            case B10_TRAIN3_ARRIVAL:
                trainStation = trainStations[2];
                trainStation.beginService();
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
        for (ServicePoint sp : servicePoints) {
            System.out.printf("Total passengers at %s: %d\nTotal passengers serviced: %d\nAverage service time: %.2f\n\n", sp.getName(), sp.getQueueSize(), sp.getCustomerServiced(), sp.getMeanServiceTime());
            csvWriter.writeResultTicketCheck(sp.getName(), sp.getQueueSize(), sp.getCustomerServiced(), sp.getMeanServiceTime());
        }
        for (TrainStation st : trainStations) {
            System.out.printf("Total passengers at %s: %d\nTotal passengers serviced: %d\nAverage station load time: %.2f\n"
                    + "%d trains stopped at the station, with an average capacity of: %.2f \nAverage train travel time is: %.2f with an average loaded capacity of: %.2f \n\n",
                    st.getName(), st.getQueueSize(), st.getCustomerServiced(), st.getMeanServiceTime(), st.getTotalTrains(), st.getMeanTrainCapacity(),
                    st.getMeanTravelTime(),st.getMeanLoadedCapacity());
            csvWriter.writeResultTrainStation(st.getName(), st.getQueueSize(), st.getCustomerServiced(), st.getMeanServiceTime(), st.getTotalTrains(),
                    st.getMeanTrainCapacity(), st.getMeanTravelTime(), st.getMeanLoadedCapacity());
        }
    }

    private void passengerListReport(ArrayList<Passenger> passengers) {
        if (passengers != null && !passengers.isEmpty()) {
            for (Passenger passenger : passengers) {
                passenger.setDepartureTime();
            }
        }
    }

    public ServicePoint[] getServicePoints() {
        return servicePoints;
    }

    public TrainStation[] getTrainStations() {
        return trainStations;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
