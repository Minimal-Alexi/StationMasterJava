package Controller;

import View.StationApplication;

/**
 * The type Controller.
 */
public abstract class Controller {
    /**
     * The Application.
     */
    protected StationApplication application;

    /**
     * Instantiates a new Controller.
     */
    public Controller(){

    }

    /**
     * Sets application.
     *
     * @param application the application
     */
    public void setApplication(StationApplication application) {
        this.application = application;
    }
}
