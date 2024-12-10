package Controller.Listener;

import Controller.SimulationController;
import Model.simulation.model.ServicePoint;
import Model.simulation.model.TrainStation;

public class EngineListener implements EngineListenerInterface {
    private final SimulationController simulationController;
    public EngineListener(SimulationController controller) {
        simulationController = controller;
    }
    public void onTimeUpdate(long time){
        simulationController.onTimeUpdate(time);
    }
    public void onUpdate(ServicePoint[] servicePoints, TrainStation[] trainStations){
        simulationController.setServicePoints(servicePoints);
        simulationController.setTrainStations(trainStations);
        simulationController.onUpdate();
    }
}
