package Model.simulation.framework;

import Controller.Listener.EngineListener;

/**
 * Abstract class representing the engine for running simulations.
 * This class handles the main simulation loop and phases (A, B, and C).
 */
public abstract class Engine {
    private static final String RED = "\033[0;31m";
    private static final String WHITE = "\033[0;37m";
    private long simulationTime = 0;
    protected EventList eventList;
    protected EngineListener connector;

    /**
     * Constructor for the Engine class.
     * Initializes the event list.
     */
    public Engine() {
        eventList = new EventList();
    }

    /**
     * Sets the engine listener.
     *
     * @param connector the engine listener
     */
    public void setEngineListener(EngineListener connector) {
        this.connector = connector;
    }

    /**
     * Sets the simulation time.
     *
     * @param simulationTime the simulation time
     */
    public void setSimulationTime(long simulationTime) {
        this.simulationTime = simulationTime;
    }

    /**
     * Runs the simulation.
     * This method initializes the simulation and runs through the A, B, and C phases until the simulation time is reached.
     */
    public void run() {
        initialize();

        while (simulate()) {
            System.out.printf("\n%sA-phase:%s time is %d%n\n", RED, WHITE, currentTime());
            Clock.getInstance().setTime(currentTime());
            listenerTimeUpdate(currentTime());

            System.out.printf("%sB-phase:%s ", RED, WHITE);
            runBEvents();
            listenerUpdate();

            System.out.printf("%sC-phase:%s ", RED, WHITE);
            tryCEvents();
            listenerUpdate();
        }

        results();
    }

    /**
     * Checks if the simulation should continue running.
     *
     * @return true if the current time is less than the simulation time, false otherwise
     */
    private boolean simulate() {
        return Clock.getInstance().getTime() < simulationTime;
    }

    /**
     * Gets the current time from the event list.
     *
     * @return the current time
     */
    private long currentTime() {
        return eventList.getNextEventTime();
    }

    /**
     * Runs the B-phase events.
     * This method processes all events scheduled for the current time.
     */
    private void runBEvents() {
        while (eventList.getNextEventTime() == Clock.getInstance().getTime()) {
            runEvent(eventList.remove());
        }
    }

    /**
     * Abstract method to update the listener with the current time.
     *
     * @param currentTime the current time
     */
    protected abstract void listenerTimeUpdate(long currentTime);

    /**
     * Abstract method to update the listener.
     */
    protected abstract void listenerUpdate();

    /**
     * Abstract method to initialize the simulation.
     */
    protected abstract void initialize();

    /**
     * Abstract method to run an event.
     *
     * @param e the event to run
     */
    protected abstract void runEvent(Event e);

    /**
     * Abstract method to try running C-phase events.
     */
    protected abstract void tryCEvents();

    /**
     * Abstract method to handle the results of the simulation.
     */
    protected abstract void results();
}