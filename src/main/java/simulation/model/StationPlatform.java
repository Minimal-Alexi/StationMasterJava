package simulation.model;

import java.util.Random;
import simulation.framework.Clock;

import java.util.ArrayList;
public class StationPlatform extends ServicePoint{

    private int trainCapacity,minimumTrainCapacity,maximumTrainCapacity;

    public StationPlatform(String serviceName, int minimumResponseTime, int maximumResponseTime,int minimumTrainCapacity, int maximumTrainCapacity) {
        super(serviceName,new ArrayList<ServicePoint>(),minimumResponseTime,maximumResponseTime);
        this.minimumTrainCapacity = minimumTrainCapacity;
        this.maximumTrainCapacity = maximumTrainCapacity;
    }
    public void trainDeparture(Clock clock){
        Random rand = new Random();
        this.trainCapacity = rand.nextInt(this.minimumTrainCapacity,this.maximumTrainCapacity);
        int currentTrainCapacity = this.trainCapacity;
        while(!super.isQueueEmpty() && currentTrainCapacity > 0){
            super.servePassenger(clock);
            currentTrainCapacity--;
        }
    }
}

