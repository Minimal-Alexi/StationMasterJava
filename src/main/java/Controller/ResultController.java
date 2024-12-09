package Controller;

import Model.simulation.model.MyEngine;
import View.StationApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController extends Controller {

    @FXML
    private Label totalPassengersStation1;
    @FXML
    private Label totalServicedStation1;
    @FXML
    private Label avgLoadTimeStation1;
    @FXML
    private Label totalPassengersStation2;
    @FXML
    private Label totalServicedStation2;
    @FXML
    private Label avgLoadTimeStation2;
    @FXML
    private Label totalPassengersMetro;
    @FXML
    private Label totalServicedMetro;
    @FXML
    private Label avgLoadTimeMetro;
    @FXML
    private Button rerunButton;
    @FXML
    private Button quitButton;

    private static String[] results;

    public void initialize() {
        // Retrieve the simulation engine instance

        // Get the results from the engine
//        String[] results = engine.getResultsAsString();
        // Update the labels with the results
//        totalPassengersStation1.setText(results[0]);
//        totalServicedStation1.setText(results[1]);
//        avgLoadTimeStation1.setText(results[2]);
//        totalPassengersStation2.setText(results[3]);
//        totalServicedStation2.setText(results[4]);
//        avgLoadTimeStation2.setText(results[5]);
//        totalPassengersMetro.setText(results[6]);
//        totalServicedMetro.setText(results[7]);
//        avgLoadTimeMetro.setText(results[8]);
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
        totalPassengersStation1.setText(results[0]);
        totalServicedStation1.setText(results[1]);
        avgLoadTimeStation1.setText(results[2]);
        totalPassengersStation2.setText(results[3]);
        totalServicedStation2.setText(results[4]);
        avgLoadTimeStation2.setText(results[5]);
        totalPassengersMetro.setText(results[6]);
        totalServicedMetro.setText(results[7]);
        avgLoadTimeMetro.setText(results[8]);
    }
}