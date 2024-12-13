package Model.simulation.framework;

/**
 * The type Clock.
 */
public class Clock {
    private static Clock instance;
    private long time;
    private Clock(){
        this.time = 0;
    }

    /**
     * Get instance clock.
     *
     * @return the clock
     */
    public static Clock getInstance(){
        if(instance == null){
            instance = new Clock();
        }
        return instance;
    }

    /**
     * Get time long.
     *
     * @return the long
     */
    public long getTime(){
        return this.time;
    }

    /**
     * Set time.
     *
     * @param time the time
     */
    public void setTime(long time){
        this.time = time;
    }
}
