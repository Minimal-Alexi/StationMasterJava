package View.Visualisation;

import Controller.SimulationController;
import Model.simulation.model.Passenger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TrainStationVisualization extends AbstractVisualization{
    public static int maxTime = 2000,currentSpeed = 1000;
    private static final Image Railway = new Image("images/railroad.png");
    private static final Image Train = new Image("images/train.png");
    private static final Color color = Color.GOLD;
    private static final int xSizeBuilding = 200 , ySizeBuilding = 200, xSizeRailroad = 100, ySizeRailroad = 200, distanceBuildingRailroad = 25
            , xSize = xSizeBuilding + distanceBuildingRailroad + xSizeRailroad, ySize = 200
            ,gridSquaresSize = PassengerVisualization.getxSize(),nrRows = xSizeBuilding / gridSquaresSize
            ,nrColumns = ySizeBuilding / gridSquaresSize, maxPassenger = nrRows * nrColumns;
    private boolean trainArrived;
    public int passengerCount;
    private PassengerVisualization[][] passengerVisualizations;
    public TrainStationVisualization(int x, int y, GraphicsContext graphicsContext){
        super(x, y, graphicsContext);
        this.trainArrived = false;
        this.passengerVisualizations = new PassengerVisualization[nrColumns][nrRows];
        for(int i = 0; i < nrColumns; i++){
            for(int j = 0; j < nrRows; j++){
                passengerVisualizations[i][j] = null;
            }
        }
    }
    public void setTrainArrived(boolean trainArrived){
        this.trainArrived = trainArrived;
    }
    public void setPassengerCount(int passengerCount){this.passengerCount = passengerCount;}
    @Override
    public void drawVisualization(){
        drawStation();
        drawPassengers();
    }

    @Override
    public void clearVisualization() {
        graphicsContext.clearRect(x,y,xSize * 2 + 25,ySize * 2);
    }
    public void loadPassengers(){
        Timeline timeline = new Timeline();
        int centerRailwayX = x + xSizeBuilding + distanceBuildingRailroad + xSizeRailroad / 2,
        centerRailwayY = y + ySizeBuilding / 2;
        for(int i = 0; i < nrRows; i++){
            for(int j = 0; j < nrColumns; j++){
                if(passengerVisualizations[i][j]!= null){
                    PassengerVisualization passenger= passengerVisualizations[i][j];
                    IntegerProperty xProperty = new SimpleIntegerProperty(passengerVisualizations[i][j].getX());
                    IntegerProperty yProperty = new SimpleIntegerProperty(passengerVisualizations[i][j].getY());

                    KeyValue xValue = new KeyValue(xProperty, centerRailwayX);
                    KeyValue yValue = new KeyValue(yProperty, centerRailwayY);

                    KeyFrame keyFrame = new KeyFrame(
                            Duration.millis(maxTime - currentSpeed), // Duration of animation
                            e -> {
                                passenger.setPosition(centerRailwayX, centerRailwayY);
                                passenger.clearVisualization();
                                trainArrived = false;
                            },
                            xValue,
                            yValue
                    );
                    timeline.getKeyFrames().add(keyFrame);
                    xProperty.addListener((obs, oldVal, newVal) -> {
                        passenger.clearVisualization();
                        drawVisualization();
                        passenger.setPosition(newVal.intValue(), passenger.getY());
                        passenger.drawVisualization();
                        trainArrived = true;
                    });

                    yProperty.addListener((obs, oldVal, newVal) -> {
                        passenger.clearVisualization();
                        drawVisualization();
                        passenger.setPosition(passenger.getX(), newVal.intValue());
                        passenger.drawVisualization();
                        trainArrived = true;
                    });
                }
            }
        }
        timeline.setOnFinished(event -> {
            for (int i = 0; i < nrRows; i++) {
                for (int j = 0; j < nrColumns; j++) {
                    if (passengerVisualizations[i][j] != null) {
                        passengerVisualizations[i][j].clearVisualization();
                        passengerVisualizations[i][j] = null; // Set passenger to null to delete
                    }
                }
            }
        });
        timeline.play();
        Platform.runLater(() -> {
            drawPassengers();
            SimulationController.setIsTrainLoading(false);
        });
    }
    public boolean isTrainArrived(){
        return trainArrived;
    }
    public static void setCurrentSpeed(int currentSpeed){
        TrainStationVisualization.currentSpeed = currentSpeed;
    }
    private void drawStation(){
        graphicsContext.setFill(SimulationController.getBackgroundColor());
        graphicsContext.fillRect(x,y,xSize,ySize);
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x,y,xSizeBuilding,ySizeBuilding);
        Image imageToDraw;
        if(trainArrived){
            imageToDraw = Train;
        }
        else{
            imageToDraw = Railway;
        }
        graphicsContext.drawImage(imageToDraw,x + xSizeBuilding + distanceBuildingRailroad,y,xSizeRailroad,ySizeRailroad);
        super.graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(name,x,y + ySizeBuilding + 25);
    }
    private void drawPassengers(){
        int passengerViewCount = passengerCount;
        for(int i = 0; i < nrRows; i++){
            for(int j = 0; j < nrColumns; j++){
                if(passengerViewCount > 0){
                    if(passengerVisualizations[i][j] == null){
                        passengerVisualizations[i][j] = new PassengerVisualization(x + i * gridSquaresSize, y + j * gridSquaresSize, graphicsContext);
                        passengerVisualizations[i][j].drawVisualization();
                    }
                    else{
                        passengerVisualizations[i][j].drawVisualization();
                    }
                }
                passengerViewCount--;
            }
        }
    }
}
