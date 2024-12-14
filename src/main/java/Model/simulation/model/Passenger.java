package Model.simulation.model;

import Model.distributions.Bernoulli;
import Model.simulation.framework.Clock;

/**
 * Represents a passenger in the simulation.
 * Each passenger has a unique ID, type (Train or Metro), arrival time, and departure time.
 */
public class Passenger {
    private static int nr = 0;
    private static double TRAIN_METRO_RATIO = 0.3;
    private static Bernoulli bernoulli = new Bernoulli(TRAIN_METRO_RATIO);
    private final int id;
    private final PassengerType type;
    private long arrivalTime;
    private long departureTime;

    /**
     * Constructs a Passenger with a unique ID and sets the arrival time.
     * The type of the passenger (Train or Metro) is determined based on a Bernoulli distribution.
     */
    public Passenger() {
        this.id = nr++;
        this.arrivalTime = Clock.getInstance().getTime();
        if (bernoulli.sample() == 1) {
            this.type = PassengerType.Train;
        } else {
            this.type = PassengerType.Metro;
        }
    }

    /**
     * Sets the departure time of the passenger.
     */
    public void setDepartureTime() {
        this.departureTime = Clock.getInstance().getTime();
    }

    /**
     * Returns the ID of the passenger.
     *
     * @return the ID of the passenger
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the type of the passenger (Train or Metro).
     *
     * @return the type of the passenger
     */
    public PassengerType getType() {
        return this.type;
    }

    /**
     * Returns the time the passenger spent at the station.
     *
     * @return the time the passenger spent at the station
     */
    public long getStationTime() {
        return this.departureTime - this.arrivalTime;
    }

    /**
     * Sets the train-to-metro ratio for determining the type of passengers.
     *
     * @param trainMetroRatio the train-to-metro ratio
     */
    public static void setTrainMetroRatio(double trainMetroRatio) {
        Passenger.TRAIN_METRO_RATIO = trainMetroRatio;
    }

    /**
     * Main method for testing the Passenger class.
     * Generates 100 passengers and prints the number of Train and Metro passengers.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int metro = 0, train = 0;
        for (int i = 0; i < 100; ++i) {
            Passenger passenger = new Passenger();
            if (passenger.getType() == PassengerType.Train) {
                train++;
            } else if (passenger.getType() == PassengerType.Metro) {
                metro++;
            }
        }
        System.out.println(train);
        System.out.println(metro);
    }
}