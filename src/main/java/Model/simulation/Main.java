package Model.simulation;

import Model.simulation.framework.Engine;
import Model.simulation.model.MyEngine;

/**
 * The Main class is the entry point for the simulation application.
 * It initializes the simulation engine and runs the simulation.
 */
public class Main {
    /**
     * The main method that starts the simulation.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Simulator\n");
        Engine engine = new MyEngine();

        engine.setSimulationTime(10000);
        engine.run();
    }
}