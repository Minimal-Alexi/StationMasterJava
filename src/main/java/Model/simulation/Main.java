package Model.simulation;

import Model.simulation.framework.Engine;
import Model.simulation.model.MyEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("Simulator\n");
        Engine engine = new MyEngine(System.currentTimeMillis() % Integer.MAX_VALUE);

        engine.setSimulationTime(25);
        engine.run();
    }
}
