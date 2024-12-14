package Controller.Listener;

import Controller.SimulationController;
import Model.simulation.model.ServicePoint;
import Model.simulation.model.TrainStation;

/**
 * The EngineListener class implements the EngineListenerInterface and handles
 * updates from the simulation engine.
 */
public class EngineListener implements EngineListenerInterface {
    private final SimulationController simulationController;

    /**
     * Constructs an EngineListener with the specified SimulationController.
     *
     * @param controller the SimulationController to be used by this listener
     */
    public EngineListener(SimulationController controller) {
        simulationController = controller;
    }

    /**
     * Called when the simulation time is updated.
     *
     * @param time the current simulation time
     */
    public void onTimeUpdate(long time) {
        simulationController.onTimeUpdate(time);
    }

    /**
     * Called when the simulation updates the service points and train stations.
     *
     * @param servicePoints an array of updated service points
     * @param trainStations an array of updated train stations
     */
    public void onUpdate(ServicePoint[] servicePoints, TrainStation[] trainStations) {
        simulationController.setServicePoints(servicePoints);
        simulationController.setTrainStations(trainStations);
        simulationController.onUpdate();
    }
}