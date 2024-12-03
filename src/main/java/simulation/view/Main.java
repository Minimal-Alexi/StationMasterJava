package simulation.view;

import simulation.framework.Engine;
import simulation.model.MyEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("Simulator\n");
        Engine engine = new MyEngine();

        engine.setSimulationTime(10000);
        engine.run();
    }
}