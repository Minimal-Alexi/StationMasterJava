package Controller;

import Model.simulation.model.MyEngine;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.control.Slider;

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

    public void initialize() {
        //Get Engine
        Thread engineThread = engineThreadCreator();
    }
    private Thread engineThreadCreator(){
        Thread engineThread = new Thread(() -> {
            try {
                // Perform long-running operations
                long[] simulationData = super.application.getSimulationData();
                MyEngine myEngine = new MyEngine(simulationData[1]);
                myEngine.setSimulationTime(simulationData[0]);
                myEngine.run();

                // Once the operation is complete, update the UI on the JavaFX Application Thread
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    // Update the UI after the delay
                    Platform.runLater(() -> application.showResultView());
                    scheduler.shutdown();
                }, 5, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        engineThread.setDaemon(true);
        return engineThread;
    }
}