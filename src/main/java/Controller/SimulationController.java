package Controller;

import Model.simulation.model.MyEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.application.Platform;

public class SimulationController extends Controller {
    @FXML
    private Label speedLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label ticketCheck1Label;

    @FXML
    private Label ticketCheck2Label;

    @FXML
    private Label metroStationLabel;

    @FXML
    private Label trainStation1Label;

    @FXML
    private Label trainStation2Label;

    public void initialize() {
        // Start the engine thread
        Thread engineThread = new Thread(() -> {
            try {
                // Perform long-running operations
                long[] simulationData = super.application.getSimulationData();
                MyEngine myEngine = new MyEngine();
                myEngine.setSimulationTime(simulationData[0]);
                myEngine.run();

                // Once the operation is complete, update the UI on the JavaFX Application Thread
                Platform.runLater(() -> super.application.showResultView());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        engineThread.setDaemon(true);
        engineThread.start();
    }

    public void updateSpeed(double speed) {
        speedLabel.setText("Speed: " + speed);
    }

    public void updateTime(double time) {
        timeLabel.setText("Time: " + time);
    }

    public void updateTicketCheck1(int customersServed) {
        ticketCheck1Label.setText(String.valueOf(customersServed));
    }

    public void updateTicketCheck2(int customersServed) {
        ticketCheck2Label.setText(String.valueOf(customersServed));
    }

    public void updateMetroStation(int customersServed) {
        metroStationLabel.setText(String.valueOf(customersServed));
    }

    public void updateTrainStation1(int customersServed) {
        trainStation1Label.setText(String.valueOf(customersServed));
    }

    public void updateTrainStation2(int customersServed) {
        trainStation2Label.setText(String.valueOf(customersServed));
    }
}