package eventlist.src;

import eventlist.src.simu.framework.Engine;
import eventlist.src.simu.model.MyEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("Simulator\n");

        Engine engine = new MyEngine();
        engine.setSimulationTime(1000);
        engine.run();
    }
}
