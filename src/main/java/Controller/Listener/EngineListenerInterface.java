package Controller.Listener;

import Model.simulation.model.ServicePoint;
import Model.simulation.model.TrainStation;

public interface EngineListenerInterface {
    public long onTimeUpdate(long time);
    public void onUpdate(ServicePoint[] servicePoints, TrainStation[] trainStations);
    public void onSpeedUpdate(int speed);
}
