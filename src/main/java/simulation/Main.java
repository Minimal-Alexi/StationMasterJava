package simulation;

import simulation.framework.Clock;
import simulation.model.ServicePoint;
import simulation.model.Passenger;

import java.util.Random;
import java.util.ArrayList;

public class Main {
    //set the two the same I'm lazy, the code will break if you don't.
    private static int TOTAL_PASSENGERS = 10,PASSENGERS = 10;
    private static int MIN_PASSENGER_GENERATION = 1, MAX_PASSENGER_GENERATION = 10;
    private static int ID = 0;
    private static int averageStationTime = 0;
    private static Random rand = new Random();
    public static void main(String[] args) {
        // Initialization
        Clock clock = Clock.getInstance();
        ServicePoint ticketCheck = new ServicePoint("Ticket Booth",new ArrayList<ServicePoint>(),1,5);
        int nextPassenger = rand.nextInt(MIN_PASSENGER_GENERATION,MAX_PASSENGER_GENERATION);
        while(PASSENGERS != 0 || !ticketCheck.isQueueEmpty()){
            if(nextPassenger == 0 && PASSENGERS > 0){
                Passenger passenger = new Passenger(ID++,clock.getTime());
                ticketCheck.addPassenger(passenger,clock);
                ticketCheck.generateResponseTime();
                nextPassenger = rand.nextInt(MIN_PASSENGER_GENERATION,MAX_PASSENGER_GENERATION);
                PASSENGERS--;
            }
            if(ticketCheck.getResponseTime() == 0 && !ticketCheck.isQueueEmpty()){
                Passenger passenger = ticketCheck.servePassenger(clock);
                averageStationTime += passenger.getStationTime();
            }
            nextPassenger--;
            clock.setTime(clock.getTime() + 1);
            ticketCheck.setResponseTime(ticketCheck.getResponseTime() - 1);
        }

        System.out.println("Passengers spent on average about: " + (float) averageStationTime / TOTAL_PASSENGERS + " minutes at the station.");
        System.out.println("Passengers spent on average about: " + ticketCheck.getAverageResponseTime() + " minutes at the ticket check.");
    }
}
