package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The ResultController class is responsible for handling the results view in the application.
 * It updates the labels with the results and sets actions for the rerun and quit buttons.
 */
public class ResultController extends Controller {

    /**
     * Label to display the total number of passengers served at Ticket 1.
     */
    @FXML
    private Label totalPassengersServedAtTicket1;

    /**
     * Label to display the average service time at Ticket 1.
     */
    @FXML
    private Label averageServiceTimeAtTicket1;

    /**
     * Label to display the total number of passengers served at Ticket 2.
     */
    @FXML
    private Label totalPassengersServedAtTicket2;

    /**
     * Label to display the average service time at Ticket 2.
     */
    @FXML
    private Label averageServiceTimeAtTicket2;

    /**
     * Label to display the total number of passengers served at Station 1.
     */
    @FXML
    private Label totalPassengersServedAtStation1;

    /**
     * Label to display the average loaded capacity at Station 1.
     */
    @FXML
    private Label averageLoadedCapacityAtStation1;

    /**
     * Label to display the average train travel time at Station 1.
     */
    @FXML
    private Label averageTrainTravelTimeStation1;

    /**
     * Label to display the total number of trains at Station 1.
     */
    @FXML
    private Label totalTrainAtStation1;

    /**
     * Label to display the total number of passengers served at Station 2.
     */
    @FXML
    private Label totalPassengersServedAtStation2;

    /**
     * Label to display the average loaded capacity at Station 2.
     */
    @FXML
    private Label averageLoadedCapacityAtStation2;

    /**
     * Label to display the average train travel time at Station 2.
     */
    @FXML
    private Label averageTrainTravelTimeStation2;

    /**
     * Label to display the total number of trains at Station 2.
     */
    @FXML
    private Label totalTrainAtStation2;

    /**
     * Label to display the total number of passengers served at the Metro Station.
     */
    @FXML
    private Label totalPassengersServedAtMetroStation;

    /**
     * Label to display the average loaded capacity at the Metro Station.
     */
    @FXML
    private Label averageLoadedCapacityAtMetroStation;

    /**
     * Label to display the average train travel time at the Metro Station.
     */
    @FXML
    private Label averageTrainTravelTimeMetroStation;

    /**
     * Label to display the total number of trains at the Metro Station.
     */
    @FXML
    private Label totalTrainAtMetroStation;

    /**
     * Button to rerun the simulation.
     */
    @FXML
    private Button rerunButton;

    /**
     * Button to quit the application.
     */
    @FXML
    private Button quitButton;

    /**
     * Array to store the results to be displayed.
     */
    private static String[] results;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
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

    /**
     * Sets the results to be displayed by this controller.
     *
     * @param results an array of result strings
     */
    public static void setResults(String[] results) {
        ResultController.results = results;
    }

    /**
     * Updates the labels with the results.
     */
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