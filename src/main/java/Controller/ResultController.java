package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController extends Controller {

    @FXML
    private Label totalPassengersServedAtTicket1;
    @FXML
    private Label averageServiceTimeAtTicket1;
    @FXML
    private Label totalPassengersServedAtTicket2;
    @FXML
    private Label averageServiceTimeAtTicket2;
    @FXML
    private Label totalPassengersServedAtStation1;
    @FXML
    private Label averageLoadedCapacityAtStation1;
    @FXML
    private Label averageTrainTravelTimeStation1;
    @FXML
    private Label totalTrainAtStation1;
    @FXML
    private Label totalPassengersServedAtStation2;
    @FXML
    private Label averageLoadedCapacityAtStation2;
    @FXML
    private Label averageTrainTravelTimeStation2;
    @FXML
    private Label totalTrainAtStation2;
    @FXML
    private Label totalPassengersServedAtMetroStation;
    @FXML
    private Label averageLoadedCapacityAtMetroStation;
    @FXML
    private Label averageTrainTravelTimeMetroStation;
    @FXML
    private Label totalTrainAtMetroStation;
    @FXML
    private Button rerunButton;
    @FXML
    private Button quitButton;

    private static String[] results;

    public void initialize() {
        updateLabels();
        // Set the rerun button action
        rerunButton.setOnAction(event -> application.showStartView());
        // Set the quit button action
        quitButton.setOnAction(event -> {
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        });
    }

    public static void setResults(String[] results) {
        ResultController.results = results;
    }

    private void updateLabels() {
        totalPassengersServedAtTicket1.setText("Total passengers served: " + results[0]);
        averageServiceTimeAtTicket1.setText("Average service time: " + results[1]);
        totalPassengersServedAtTicket2.setText("Total passengers served: " + results[2]);
        averageServiceTimeAtTicket2.setText("Average service time: " + results[3]);
        totalPassengersServedAtStation1.setText("Total passengers served: " + results[4]);
        averageLoadedCapacityAtStation1.setText("Average load time: " + results[5]);
        averageTrainTravelTimeStation1.setText("Average train travel time: " + results[6]);
        totalTrainAtStation1.setText("Total train stopped: " + results[7]);
        totalPassengersServedAtStation2.setText("Total passengers served: " + results[8]);
        averageLoadedCapacityAtStation2.setText("Average load time: " + results[9]);
        averageTrainTravelTimeStation2.setText("Average train travel time: " + results[10]);
        totalTrainAtStation2.setText("Total train stopped: " + results[11]);
        totalPassengersServedAtMetroStation.setText("Total passengers served: " + results[12]);
        averageLoadedCapacityAtMetroStation.setText("Average load time: " + results[13]);
        averageTrainTravelTimeMetroStation.setText("Average train travel time " + results[14]);
        totalTrainAtMetroStation.setText("Total train stopped: " + results[15]);
    }
}