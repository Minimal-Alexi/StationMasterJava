package Model.simulation.model;

import Model.distributions.Negexp;
import Model.distributions.Normal;

import Model.simulation.framework.ArrivalProcess;
import Model.simulation.framework.Clock;
import Model.simulation.framework.Engine;
import Model.simulation.framework.Event;
import Model.simulation.filewriter.CSVWriter;

import java.util.ArrayList;

/**
 * MyEngine class extends the Engine class and implements the simulation logic.
 */
public class MyEngine extends Engine {
    private static final int timer = 2000;
    private static final long[] defaultValues = new long[15];
    static {
        // Travel time default values
        defaultValues[0] = 1800;
        defaultValues[1] = 1500;
        defaultValues[2] = 540;

        // Loading time default values of the trains
        defaultValues[3] = 120;
        defaultValues[4] = 120;
        defaultValues[5] = 60;

        // Default train capacity values
        defaultValues[6] = 70;
        defaultValues[7] = 30;
        defaultValues[8] = 80;
        defaultValues[9] = 50;
        defaultValues[10] = 100;
        defaultValues[11] = 25;

        // Passenger arrival default values
        defaultValues[12] = 10;
        defaultValues[13] = 10;

        // Passenger train-to-metro ratio
        defaultValues[14] = 30;
    }

    private int speed = 1000;
    private final TrainStation[] trainStations;
    private final ArrivalProcess[] arrivalProcesses;
    private final ServicePoint[] servicePoints;
    private final CSVWriter csvWriter;

    /**
     * Default constructor for MyEngine.
     * Initializes the service points, train stations, and arrival processes with default values.
     */
    public MyEngine(){
        super();
        servicePoints = new ServicePoint[2];
        trainStations = new TrainStation[3];
        arrivalProcesses = new ArrivalProcess[5];
        servicePoints[0] = new ServicePoint("Ticket Check 1", new Normal(10, 5), eventList, EventType.B3_TICKET_CHECK_FINISH);
        servicePoints[1] = new ServicePoint("Ticket Check 2", new Normal(10, 5), eventList, EventType.B4_TICKET_CHECK_FINISH);
        trainStations[0] = new TrainStation("Train Station 1", new Normal(120, 60), new Normal(70, 30), eventList, EventType.B5_TRAIN1_DEPARTURE);
        trainStations[1] = new TrainStation("Train Station 2", new Normal(120, 60), new Normal(80, 50), eventList, EventType.B6_TRAIN2_DEPARTURE);
        trainStations[2] = new TrainStation("Metro Station", new Normal(60, 20), new Normal(100, 25), eventList, EventType.B7_TRAIN3_DEPARTURE);
        arrivalProcesses[0] = new ArrivalProcess(new Negexp(10), eventList, EventType.B1_PASSENGER_ARRIVAL);
        arrivalProcesses[1] = new ArrivalProcess(new Negexp(10), eventList, EventType.B2_PASSENGER_ARRIVAL);
        arrivalProcesses[2] = new ArrivalProcess(new Normal(1800, 120), eventList, EventType.B8_TRAIN1_ARRIVAL);
        arrivalProcesses[3] = new ArrivalProcess(new Normal(1500, 600), eventList, EventType.B9_TRAIN2_ARRIVAL);
        arrivalProcesses[4] = new ArrivalProcess(new Normal(540, 90), eventList, EventType.B10_TRAIN3_ARRIVAL);
        Passenger.setTrainMetroRatio((double) defaultValues[12] / 100);
        Clock.getInstance().setTime(0);
        csvWriter = new CSVWriter();
    }

    /**
     * Constructor for MyEngine with simulation settings.
     * Initializes the service points, train stations, and arrival processes with specified values.
     *
     * @param simulationSettings the simulation settings
     */
    public MyEngine(long[] simulationSettings) {
        super();
        long seed = simulationSettings[1];
        for(int i = 2; i < simulationSettings.length; i++) {
            if(simulationSettings[i] == 0) simulationSettings[i] = defaultValues[i-2];
            System.out.println(simulationSettings[i]);
        }
        servicePoints = new ServicePoint[2];
        trainStations = new TrainStation[3];
        arrivalProcesses = new ArrivalProcess[5];
        servicePoints[0] = new ServicePoint("Ticket Check 1", new Normal(10, 5, seed), eventList, EventType.B3_TICKET_CHECK_FINISH);
        servicePoints[1] = new ServicePoint("Ticket Check 2", new Normal(10, 5, seed), eventList, EventType.B4_TICKET_CHECK_FINISH);
        trainStations[0] = new TrainStation("Train Station 1", new Normal(simulationSettings[5], 60, seed), new Normal(simulationSettings[8], simulationSettings[9], seed), eventList, EventType.B5_TRAIN1_DEPARTURE);
        trainStations[1] = new TrainStation("Train Station 2", new Normal(simulationSettings[6], 60, seed), new Normal(simulationSettings[10], simulationSettings[11], seed), eventList, EventType.B6_TRAIN2_DEPARTURE);
        trainStations[2] = new TrainStation("Metro Station", new Normal(simulationSettings[7], 20, seed), new Normal(simulationSettings[12], simulationSettings[13], seed), eventList, EventType.B7_TRAIN3_DEPARTURE);
        arrivalProcesses[0] = new ArrivalProcess(new Negexp(simulationSettings[14]), eventList, EventType.B1_PASSENGER_ARRIVAL);
        arrivalProcesses[1] = new ArrivalProcess(new Negexp(simulationSettings[15]), eventList, EventType.B2_PASSENGER_ARRIVAL);
        arrivalProcesses[2] = new ArrivalProcess(new Normal(simulationSettings[2], 120, seed), eventList, EventType.B8_TRAIN1_ARRIVAL);
        arrivalProcesses[3] = new ArrivalProcess(new Normal(simulationSettings[3], 600, seed), eventList, EventType.B9_TRAIN2_ARRIVAL);
        arrivalProcesses[4] = new ArrivalProcess(new Normal(simulationSettings[4], 90, seed), eventList, EventType.B10_TRAIN3_ARRIVAL);
        Passenger.setTrainMetroRatio((double) simulationSettings[16] / 100);
        Clock.getInstance().setTime(0);
        csvWriter = new CSVWriter(seed);
    }

    /**
     * Initializes the arrival processes by generating the next event for each process.
     */
    protected void initialize() {
        for (ArrivalProcess arrivalProcess : arrivalProcesses) {
            arrivalProcess.generateNextEvent();
        }
    }

    /**
     * Updates the listener with the current time.
     *
     * @param currentTime the current time
     */
    protected void listenerTimeUpdate(long currentTime) {
        if(connector != null) {
            connector.onTimeUpdate(currentTime);
        }
    }

    /**
     * Updates the listener and waits for the specified time.
     */
    protected void listenerUpdate(){
        if(connector != null){
            synchronized (this) {
                try{
                    connector.onUpdate(servicePoints, trainStations);
                    wait(timer - speed);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Runs the specified event.
     *
     * @param event the event to run
     */
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

    /**
     * Tries to run C-phase events for each service point.
     */
    protected void tryCEvents() {
        for (ServicePoint sp : servicePoints) {
            if (!sp.isReserved() && sp.isOnQueue()) {
                sp.beginService();
            }
        }
    }

    /**
     * Prints the results of the simulation and writes them to the CSV file.
     */
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
                    st.getMeanTravelTime(), st.getMeanLoadedCapacity());
            csvWriter.writeResultTrainStation(st.getName(), st.getQueueSize(), st.getCustomerServiced(), st.getMeanServiceTime(), st.getTotalTrains(),
                    st.getMeanTrainCapacity(), st.getMeanTravelTime(), st.getMeanLoadedCapacity());
        }
    }

    /**
     * Returns the results of the simulation as a string array.
     *
     * @return the results as a string array
     */
    public String[] getResultsAsString() {
        String[] results = new String[16];
        results[0] = String.valueOf(servicePoints[0].getCustomerServiced());
        results[1] = String.format("%.2f", servicePoints[0].getMeanServiceTime());
        results[2] = String.valueOf(servicePoints[1].getCustomerServiced());
        results[3] = String.format("%.2f", servicePoints[1].getMeanServiceTime());
        results[4] = String.valueOf(trainStations[0].getCustomerServiced());
        results[5] = String.format("%.2f", trainStations[0].getMeanLoadedCapacity());
        results[6] = String.format("%.2f", trainStations[0].getMeanTravelTime());
        results[7] = String.valueOf(trainStations[0].getTotalTrains());
        results[8] = String.valueOf(trainStations[1].getCustomerServiced());
        results[9] = String.format("%.2f", trainStations[1].getMeanLoadedCapacity());
        results[10] = String.format("%.2f", trainStations[1].getMeanTravelTime());
        results[11] = String.valueOf(trainStations[1].getTotalTrains());
        results[12] = String.valueOf(trainStations[2].getCustomerServiced());
        results[13] = String.format("%.2f", trainStations[2].getMeanLoadedCapacity());
        results[14] = String.format("%.2f", trainStations[2].getMeanTravelTime());
        results[15] = String.valueOf(trainStations[2].getTotalTrains());
        return results;
    }

    /**
     * Reports the departure time for each passenger in the list.
     *
     * @param passengers the list of passengers
     */
    private void passengerListReport(ArrayList<Passenger> passengers) {
        if (passengers != null && !passengers.isEmpty()) {
            for (Passenger passenger : passengers) {
                passenger.setDepartureTime();
            }
        }
    }

    /**
     * Returns the service points.
     *
     * @return the service points
     */
    public ServicePoint[] getServicePoints() {
        return servicePoints;
    }

    /**
     * Returns the train stations.
     *
     * @return the train stations
     */
    public TrainStation[] getTrainStations() {
        return trainStations;
    }

    /**
     * Sets the speed of the simulation.
     *
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}