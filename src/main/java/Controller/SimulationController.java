package Controller;

import Controller.Listener.EngineListener;
import Model.simulation.model.MyEngine;
import Model.simulation.model.ServicePoint;
import Model.simulation.model.TrainStation;
import View.Visualisation.*;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * The type Simulation controller.
 */
public class SimulationController extends Controller {
    @FXML
    Slider speedSlider;
    @FXML
    Canvas simulationCanvas;
    @FXML
    Label timeLabel;
    @FXML
    Label speedLabel;

    static boolean isTrainLoading;
    private int speed;
    static long[] simulationData;
    private static String[] simulationResults;
    private static final Color backgroundColor = Color.CADETBLUE;
    private static final String timeFormat = "%03d Days - %02d Hours - %02d Minutes - %02d Seconds", standardName = "%s (%d)", stationNameTrain = "%s (%d / %d)",
    speedFormat = "Speed: %.2f x";
    private EngineListener engineListener;
    private MyEngine myEngine;
    private ServicePoint[] servicePoints;
    private TrainStation[] trainStations;
    private ServicePointVisualization[] servicePointVisualization;
    private TrainStationVisualization[] trainStationVisualization;
    private GraphicsContext simulationCtx;

    /**
     * Initialize.
     */
    public void initialize() {
        // Creating listener
        engineListener = new EngineListener(this);
        // Creating engine
        // simulationData[1] == Seed, simulationData[0] = Time
        myEngine = new MyEngine(simulationData);
        if(simulationData[0] == 0) simulationData[0] = 10000L;
        myEngine.setSimulationTime(simulationData[0]);
        myEngine.setSpeed(speed);
        myEngine.setEngineListener(engineListener);

        // Initializing Javafx Elements
        canvasInitializer();
        speedSliderInitialization();
        // Loading names for servicePoints
        visualizationNameInitializer(servicePointVisualization, myEngine.getServicePoints());
        visualizationNameInitializer(trainStationVisualization, myEngine.getTrainStations());

        // Creating engineThread.
        Thread engineThread = engineThreadCreator();

        // Surprise :)
        engineThread.start();
    }

    /**
     * Initializes the speed slider with a minimum value of 100, a maximum value of 1999, and a default value of 1000.
     * Sets up a listener to update the speed value and corresponding label whenever the slider value changes.
     * Also updates the speed of the engine and the current speed of the train station visualization.
     */
    private void speedSliderInitialization(){
        speedSlider.setMin(100);
        speedSlider.setMax(1999);
        speedSlider.setValue(1000);
        speed = 1000;
        speedLabel.setText(String.format(speedFormat, (float) speed / 1000));
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            speed = newValue.intValue();
            speedLabel.setText(String.format(speedFormat, (float) speed / 1000));
            myEngine.setSpeed(speed);
            TrainStationVisualization.setCurrentSpeed(speed);
        });
    }

    /**
     * Initializes the canvas by setting the background color, drawing the curve connecting the service points and train stations,
     * and initializing the service point and train station visualizations.
     */
    private void canvasInitializer() {
        //simulationCtx init
        simulationCtx = simulationCanvas.getGraphicsContext2D();
        simulationCtx.setFont(javafx.scene.text.Font.font("Arial",14));
        simulationCtx.setFill(backgroundColor);
        simulationCtx.fillRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Service point + train station init
        servicePointVisualization = new ServicePointVisualization[2];
        servicePointVisualization[0] = new ServicePointVisualization(100,350,simulationCtx);
        servicePointVisualization[1] = new ServicePointVisualization(100,600,simulationCtx);

        trainStationVisualization = new TrainStationVisualization[3];
        trainStationVisualization[0] = new TrainStationVisualization(350,50,simulationCtx);
        trainStationVisualization[1] = new TrainStationVisualization(350,350,simulationCtx);
        trainStationVisualization[2] = new TrainStationVisualization(350,650,simulationCtx);

        // Initial display of station + service points
        curveDrawer();
        visualizationDrawer(servicePointVisualization);
        visualizationDrawer(trainStationVisualization);

        // Empty display of UI
        setTimeLabel(0);
        isTrainLoading = false;
        // For testing different sprites. Adjust accordingly.
        //testDisplayPassengers();
        //testDisplayServicePoints();
        //testTrainStationDisplay();
    }

    /**
     * Creates a new thread that runs the engine and updates the simulation results.
     * The thread is set as a daemon thread to ensure that it is terminated when the main thread is terminated.
     * The thread also updates the simulation results in the ResultController and shows the result view.
     * If an exception occurs, an alert is shown to the user.
     * @return the thread that runs the engine and updates the simulation results
     */
    private Thread engineThreadCreator(){
        Thread engineThread = new Thread(() -> {
            try {
                myEngine.run();
                simulationResults = myEngine.getResultsAsString();
                Platform.runLater(()->{
                    ResultController.setResults(simulationResults);
                    application.showResultView();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    application.alertSystem(e);
                });
            }
        });
        engineThread.setDaemon(true);
        return engineThread;
    }

    /**
     * Refreshes the map state by updating the visualization of the service points and train stations.
     * If the train is loading, the method returns without updating the map state.
     * The background color is set to the canvas, and the canvas is cleared.
     * The curve connecting the service points and train stations is drawn.
     * The visualization of the service points and train stations are updated.
     */
    private void mapStateRefresher(){
        visualizationStateUpdater(servicePoints,trainStations);
        if(isTrainLoading){
            return;
        }
        simulationCtx.setFill(backgroundColor);
        simulationCtx.fillRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());
        curveDrawer();
        visualizationDrawer(servicePointVisualization);
        visualizationDrawer(trainStationVisualization);
    }

    /**
     * Draws the visualization of the service points and train stations.
     * @param visualization the visualization to be drawn
     */
    private void visualizationDrawer(AbstractVisualization[] visualization){
        for(int i=0; i<visualization.length; ++i){
            visualization[i].drawVisualization();
        }
    }

    /**
     * Updates the visualization state by updating the service points and train stations.
     * The display name of the service points and train stations are updated.
     * If the train station is reserved, the display name is updated to include the current capacity of the train station.
     * If the train station is reserved and the train has arrived, the train station is updated to load passengers.
     * The visualization of the service points and train stations are updated.
     * @param servicePoints the service points to be updated
     * @param trainStations the train stations to be updated
     */
    private void visualizationStateUpdater(ServicePoint[] servicePoints, TrainStation[] trainStations){
        for(int i=0; i<servicePoints.length; ++i){
            String displayName = String.format(standardName,servicePoints[i].getName(),servicePoints[i].getQueueSize());
            servicePointVisualization[i].setName(displayName);
            if(servicePoints[i].isReserved() != servicePointVisualization[i].isReserved()){
                servicePointVisualization[i].setIsReserved(servicePoints[i].isReserved());
            }
        }
        for(int i=0; i<trainStations.length; ++i){
            String displayName;
            if(trainStations[i].isReserved() != trainStationVisualization[i].isTrainArrived()){
                if(trainStationVisualization[i].isTrainArrived()){
                    trainStationVisualization[i].loadPassengers();
                    isTrainLoading = true;
                }
                trainStationVisualization[i].setTrainArrived(trainStations[i].isReserved());
            }
            if(trainStationVisualization[i].isTrainArrived()){
                displayName = String.format(stationNameTrain,trainStations[i].getName(),trainStations[i].getQueueSize(),trainStations[i].getCurrentCapacity());
            }
            else{
                displayName = String.format(standardName,trainStations[i].getName(),trainStations[i].getQueueSize());
            }
            trainStationVisualization[i].setName(displayName);
            trainStationVisualization[i].setPassengerCount(trainStations[i].getQueueSize());
        }
    }

    /**
     * Initializes the display name of the service points and train stations.
     * If the train station is reserved, the display name is updated to include the current capacity of the train station.
     * @param visualization the visualization to be initialized
     * @param servicePoints the service points to be initialized
     */
    private void visualizationNameInitializer(AbstractVisualization[] visualization, ServicePoint[] servicePoints){
        for(int i = 0; i < visualization.length; ++i){
            String serviceName = servicePoints[i].getName(), displayName;
            if(servicePoints[i] instanceof TrainStation && servicePoints[i].isReserved()){
                TrainStation trainStation = (TrainStation) servicePoints[i];
                displayName = String.format(stationNameTrain,serviceName,trainStation.getQueueSize(),trainStation.getCurrentCapacity());
            }
            else{
                displayName = String.format(standardName,serviceName,servicePoints[i].getQueueSize());
            }
            visualization[i].setName(displayName);
        }
    }

    /**
     * Draws the curve connecting the service points and train stations.
     */
    private void curveDrawer(){
        simulationCtx.setStroke(Color.BLACK);
        simulationCtx.setLineWidth(2);
        int[][] startPoints = new int[2][2];
        for(int i = 0 ; i < 2; ++i){
            startPoints[i][0] = servicePointVisualization[i].getCenterCoordinateX() + 35;
            startPoints[i][1] = servicePointVisualization[i].getCenterCoordinateY();
        }
        int[][] endPoints = new int[3][2];
        endPoints[0][0] = 350;
        endPoints[1][0] = 350;
        endPoints[2][0] = 350;
        endPoints[0][1] = 150;
        endPoints[1][1] = 450;
        endPoints[2][1] = 750;
        for(int i=0; i<2; ++i){
            for(int j=0; j<3; ++j){
                if(i != 1 || j != 0){
                    int startX = startPoints[i][0],startY = startPoints[i][1],endX = endPoints[j][0],endY = endPoints[j][1];
                    int middleX = (startX + endX) / 2,middleY = (startY + endY) / 2;
                    if(endY > startY){
                        middleY -= 75;
                    }
                    else if(endY < startY){
                        middleY += 75;
                    }
                    simulationCtx.beginPath();
                    simulationCtx.moveTo(startX,startY);
                    simulationCtx.bezierCurveTo(startX,startY,middleX,middleY,endX,endY);
                    simulationCtx.stroke();
                }
            }
        }
    }

    /**
     * Sets the time label to the given time.
     * The time is formatted in the format "Days - Hours - Minutes - Seconds".
     * @param time the time to be set
     */
    void setTimeLabel(long time){
        long days = time / 86400, hours = (time % 86400) / 3600, minutes = (time % 3600) / 60, seconds = time % 60;
        timeLabel.setText(String.format(timeFormat,days,hours,minutes,seconds));
    }

    /**
     * Sets simulation data.
     *
     * @param simulationData the simulation data
     */
/*
    private void testDisplayPassengers(){
        for(int i=0; i<10; ++i){
            PassengerVisualization passengerVisualization = new PassengerVisualization(i * PassengerVisualization.xSize,0,simulationCtx);
            passengerVisualization.drawVisualization();
        }
    }
    private void testDisplayServicePoints(){
        for(int i = 0; i < 4; ++i){
            ServicePointVisualization servicePointVisualization = new ServicePointVisualization(i * ServicePointVisualization.xSize,100,simulationCtx);
            servicePointVisualization.drawVisualization();
        }
    }
    private void testTrainStationDisplay(){
        for(int i = 0; i < 3; ++i){
            TrainStationVisualization trainStationVisualization = new TrainStationVisualization(i * TrainStationVisualization.xSize,200,simulationCtx);
            if(i%2 == 0){
                trainStationVisualization.setTrainArrived(true);
            }
            trainStationVisualization.drawVisualization();
        }
    }
    */

    /**
     * Gets simulation data.
     *
     * @return the simulation data
     */
    public static void setSimulationData(long[] simulationData) {
        SimulationController.simulationData = simulationData;
    }

    /**
     * Gets background color.
     *
     * @return the background color
     */
    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * On time update.
     *
     * @param time the time
     */
    public void onTimeUpdate(long time){
        Platform.runLater(()->{
            setTimeLabel(time);
        });
    }

    /**
     * On update.
     */
    public void onUpdate(){
        Platform.runLater(()->{
            mapStateRefresher();
        });
    }

    /**
     * Sets service points.
     *
     * @param servicePoints the service points
     */
    public void setServicePoints(ServicePoint[] servicePoints) {
        this.servicePoints = servicePoints;
    }

    /**
     * Sets train stations.
     *
     * @param trainStations the train stations
     */
    public void setTrainStations(TrainStation[] trainStations) {
        this.trainStations = trainStations;
    }

    /**
     * Sets is train loading.
     *
     * @param isTrainLoading the is train loading
     */
    public static void setIsTrainLoading(boolean isTrainLoading) {
        SimulationController.isTrainLoading = isTrainLoading;
    }
}