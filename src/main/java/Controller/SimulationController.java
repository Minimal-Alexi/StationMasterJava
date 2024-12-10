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
import javafx.scene.paint.Color;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationController extends Controller {
    @FXML
    private Slider speedSlider;
    @FXML
    private Canvas simulationCanvas;
    @FXML
    private Label timeLabel,speedLabel;

    private static boolean isTrainLoading;
    private int speed;
    private static long[] simulationData;
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
        engineThread.start();
    }
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
    private void visualizationDrawer(AbstractVisualization[] visualization){
        for(int i=0; i<visualization.length; ++i){
            visualization[i].drawVisualization();
        }
    }
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
    private void setTimeLabel(long time){
        long days = time / 86400, hours = (time % 86400) / 3600, minutes = (time % 3600) / 60, seconds = time % 60;
        timeLabel.setText(String.format(timeFormat,days,hours,minutes,seconds));
    }
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
    public static void setSimulationData(long[] simulationData) {
        SimulationController.simulationData = simulationData;
    }
    public static Color getBackgroundColor() {
        return backgroundColor;
    }
    public void onTimeUpdate(long time){
        Platform.runLater(()->{
            setTimeLabel(time);
        });
    }
    public void onUpdate(){
        Platform.runLater(()->{
            mapStateRefresher();
        });
    }
    public void setServicePoints(ServicePoint[] servicePoints) {
        this.servicePoints = servicePoints;
    }
    public void setTrainStations(TrainStation[] trainStations) {
        this.trainStations = trainStations;
    }
    public static void setIsTrainLoading(boolean isTrainLoading) {
        SimulationController.isTrainLoading = isTrainLoading;
    }
}