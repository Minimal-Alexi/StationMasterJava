package Controller.Listener;

import Controller.SimulationController;
import Model.simulation.model.MyEngine;
import Model.simulation.model.ServicePoint;
import Model.simulation.model.TrainStation;

public class EngineConnector implements EngineConnectorInterface {
    private final MyEngine myEngine;
    private final SimulationController simulationController;
    public EngineConnector(MyEngine engine, SimulationController controller) {
        myEngine = engine;
        simulationController = controller;
    }
    public void onSpeedUpdate(int speed) {

    }
    public long onTimeUpdate(long time){
        return time;
    }
    public void onUpdate(ServicePoint[] servicePoints, TrainStation[] trainStations){
        simulationController.setServicePoints(servicePoints);
        simulationController.setTrainStations(trainStations);
    }
}
