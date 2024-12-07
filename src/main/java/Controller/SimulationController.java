package Controller;

import Model.simulation.model.MyEngine;
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
    private Label timeLabel;

    private static long[] simulationData;
    private static String[] simulationResults;
    private static final Color backgroundColor = Color.CADETBLUE;
    private static final String timeFormat = "%d Days - %d Hours - %d Minutes - %d Seconds";
    private MyEngine myEngine;

    private GraphicsContext simulationCtx;

    public void initialize() {
        //Create Engine
        canvasInitializer();
        Thread engineThread = engineThreadCreator();
    }
    private void canvasInitializer() {
        simulationCtx = simulationCanvas.getGraphicsContext2D();
        simulationCtx.setFill(backgroundColor);
        simulationCtx.fillRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());
    }
    private Thread engineThreadCreator(){
        Thread engineThread = new Thread(() -> {
            try {
                // Perform long-running operations
                myEngine = new MyEngine(simulationData[1]);
                myEngine.setSimulationTime(simulationData[0]);
                myEngine.run();

                simulationResults = myEngine.getResultsAsString();

                // Once the operation is complete, update the UI on the JavaFX Application Thread
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    // Update the UI after the delay
                    Platform.runLater(() -> application.showResultView(simulationResults));
                    scheduler.shutdown();
                }, 5, TimeUnit.SECONDS);
            } catch (Exception e) {
                application.alertSystem(e);
            }
        });
        engineThread.setDaemon(true);
        return engineThread;
    }
    public static void setSimulationData(long[] simulationData) {
        SimulationController.simulationData = simulationData;
    }
    public static Color getBackgroundColor() {
        return backgroundColor;
    }
}