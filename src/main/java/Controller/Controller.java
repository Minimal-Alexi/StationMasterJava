package Controller;

import View.StationApplication;

public abstract class Controller {
    protected StationApplication application;
    public Controller(){

    }
    public void setApplication(StationApplication application) {
        this.application = application;
    }
}
