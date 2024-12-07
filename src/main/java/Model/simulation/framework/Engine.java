package Model.simulation.framework;


import Controller.Listener.EngineListener;

public abstract class Engine {
    private static final String RED = "\033[0;31m";
    private static final String WHITE = "\033[0;37m";
    private long simulationTime = 0;
    protected EventList eventList;
    protected EngineListener connector;

    public Engine() {
        eventList = new EventList();
    }
    public void setEngineListener(EngineListener connector) {
        this.connector = connector;
    }
    public void setSimulationTime(long simulationTime) {
        this.simulationTime = simulationTime;
    }

    public void run() {
        initialize();

        while (simulate()) {
            System.out.printf("\n%sA-phase:%s time is %d%n\n", RED, WHITE, currentTime());
            Clock.getInstance().setTime(currentTime());

            System.out.printf("%sB-phase:%s ", RED, WHITE);
            runBEvents();
            listenerUpdate();

            System.out.printf("%sC-phase:%s ", RED, WHITE);
            tryCEvents();
            listenerUpdate();
        }

        results();
    }

    private boolean simulate() {
        return Clock.getInstance().getTime() < simulationTime;
    }

    private long currentTime() {
        return eventList.getNextEventTime();
    }

    private void runBEvents() {
        while (eventList.getNextEventTime() == Clock.getInstance().getTime()) {
            runEvent(eventList.remove());
        }
    }

    protected abstract void listenerUpdate();
    protected abstract void initialize();
    protected abstract void runEvent(Event e);
    protected abstract void tryCEvents();
    protected abstract void results();
}