package simulation.framework;

public class Clock {
    private static Clock instance;
    private long time;
    private Clock(){
        this.time = 0;
    }

    public static Clock getInstance(){
        if(instance == null){
            instance = new Clock();
        }
        return instance;
    }
    public long getTime(){
        return this.time;
    }
    public void setTime(int time){
        this.time = time;
    }
}
