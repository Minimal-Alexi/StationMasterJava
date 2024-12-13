package Controller.Listener;

import Model.simulation.model.ServicePoint;
import Model.simulation.model.TrainStation;

/**
 * The interface Engine listener interface.
 */
public interface EngineListenerInterface {
    /**
     * On time update.
     *
     * @param time the time
     */
    public void onTimeUpdate(long time);

    /**
     * On update.
     *
     * @param servicePoints the service points
     * @param trainStations the train stations
     */
    public void onUpdate(ServicePoint[] servicePoints, TrainStation[] trainStations);
}
