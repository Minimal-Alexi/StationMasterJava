package simulation.framework;

public class Clock {
    private static Clock instance;
    private int time;
    private Clock(){
        this.time = 0;
    }

    public static synchronized Clock getInstance(){
        if(instance == null){
            instance = new Clock();
        }
        return instance;
    }
}
