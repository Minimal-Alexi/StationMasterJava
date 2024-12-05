package View;

import Controller.StartController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;

public class StationApplication extends Application {
    private static Stage primaryStage;
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("StationMaster");
        showStartView();
    }
    public void showStartView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Start.fxml"));
            Parent startLayout = loader.load();

            StartController controller = loader.getController();
            controller.setApplication(this);

            Scene scene = new Scene(startLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something went wrong.\n" + e.getMessage());
            alert.showAndWait();
        }
    }
    public void showSimulationView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Simulation.fxml"));
            Parent simulationLayout = loader.load();

            StartController controller = loader.getController();
            controller.setApplication(this);

            Scene scene = new Scene(simulationLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something went wrong.\n" + e.getMessage());
            alert.showAndWait();
        }
    }
    public void showResultView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Result.fxml"));
            Parent startLayout = loader.load();

            StartController controller = loader.getController();
            controller.setApplication(this);

            Scene scene = new Scene(startLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something went wrong.\n" + e.getMessage());
            System.err.println(e.getMessage());
            alert.showAndWait();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
