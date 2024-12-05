package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimulationController {

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