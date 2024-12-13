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

/**
 * Class representing the visualization of a train station in the simulation.
 * Extends the AbstractVisualization class and provides specific implementation for train station visualization.
 */
public class TrainStationVisualization extends AbstractVisualization {
    public static int maxTime = 2000, currentSpeed = 1000;
    private static final Image Railway = new Image("images/railroad.png");
    private static final Image Train = new Image("images/train.png");
    private static final Color color = Color.GOLD;
    private static final int xSizeBuilding = 200, ySizeBuilding = 200, xSizeRailroad = 100, ySizeRailroad = 200, distanceBuildingRailroad = 25,
            xSize = xSizeBuilding + distanceBuildingRailroad + xSizeRailroad, ySize = 200,
            gridSquaresSize = PassengerVisualization.getxSize(), nrRows = xSizeBuilding / gridSquaresSize,
            nrColumns = ySizeBuilding / gridSquaresSize, maxPassenger = nrRows * nrColumns;
    private boolean trainArrived;
    public int passengerCount;
    private PassengerVisualization[][] passengerVisualizations;

    /**
     * Constructs a TrainStationVisualization with the specified coordinates and graphics context.
     *
     * @param x the x-coordinate of the train station visualization
     * @param y the y-coordinate of the train station visualization
     * @param graphicsContext the graphics context used for drawing the visualization
     */
    public TrainStationVisualization(int x, int y, GraphicsContext graphicsContext) {
        super(x, y, graphicsContext);
        this.trainArrived = false;
        this.passengerVisualizations = new PassengerVisualization[nrColumns][nrRows];
        for (int i = 0; i < nrColumns; i++) {
            for (int j = 0; j < nrRows; j++) {
                passengerVisualizations[i][j] = null;
            }
        }
    }

    /**
     * Sets the train arrival status.
     *
     * @param trainArrived the new train arrival status
     */
    public void setTrainArrived(boolean trainArrived) {
        this.trainArrived = trainArrived;
    }

    /**
     * Sets the passenger count.
     *
     * @param passengerCount the new passenger count
     */
    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    /**
     * Draws the train station visualization on the canvas.
     */
    @Override
    public void drawVisualization() {
        drawStation();
        drawPassengers();
    }

    /**
     * Clears the train station visualization from the canvas.
     */
    @Override
    public void clearVisualization() {
        graphicsContext.clearRect(x, y, xSize * 2 + 25, ySize * 2);
    }

    /**
     * Loads passengers onto the train.
     */
    public void loadPassengers() {
        Timeline timeline = new Timeline();
        int centerRailwayX = x + xSizeBuilding + distanceBuildingRailroad + xSizeRailroad / 2,
                centerRailwayY = y + ySizeBuilding / 2;
        for (int i = 0; i < nrRows; i++) {
            for (int j = 0; j < nrColumns; j++) {
                if (passengerVisualizations[i][j] != null) {
                    PassengerVisualization passenger = passengerVisualizations[i][j];
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

    /**
     * Returns whether the train has arrived.
     *
     * @return true if the train has arrived, false otherwise
     */
    public boolean isTrainArrived() {
        return trainArrived;
    }

    /**
     * Sets the current speed of the train.
     *
     * @param currentSpeed the new current speed of the train
     */
    public static void setCurrentSpeed(int currentSpeed) {
        TrainStationVisualization.currentSpeed = currentSpeed;
    }

    /**
     * Draws the train station on the canvas.
     */
    private void drawStation() {
        graphicsContext.setFill(SimulationController.getBackgroundColor());
        graphicsContext.fillRect(x, y, xSize, ySize);
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, xSizeBuilding, ySizeBuilding);
        Image imageToDraw;
        if (trainArrived) {
            imageToDraw = Train;
        } else {
            imageToDraw = Railway;
        }
        graphicsContext.drawImage(imageToDraw, x + xSizeBuilding + distanceBuildingRailroad, y, xSizeRailroad, ySizeRailroad);
        super.graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(name, x, y + ySizeBuilding + 25);
    }

    /**
     * Draws the passengers on the canvas.
     */
    private void drawPassengers() {
        int passengerViewCount = passengerCount;
        for (int i = 0; i < nrRows; i++) {
            for (int j = 0; j < nrColumns; j++) {
                if (passengerViewCount > 0) {
                    if (passengerVisualizations[i][j] == null) {
                        passengerVisualizations[i][j] = new PassengerVisualization(x + i * gridSquaresSize, y + j * gridSquaresSize, graphicsContext);
                        passengerVisualizations[i][j].drawVisualization();
                    } else {
                        passengerVisualizations[i][j].drawVisualization();
                    }
                }
                passengerViewCount--;
            }
        }
    }
}