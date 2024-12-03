import simulation.framework.Engine;
import simulation.model.MyEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("Simulator\n");
        Engine engine = new MyEngine();

        engine.setSimulationTime(100000);
        engine.run();
    }
}
