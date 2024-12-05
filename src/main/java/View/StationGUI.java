package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StationGUI extends Application {

    private static StationGUI instance;
    private SimulationController simulationController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
        Parent root = loader.load();
        simulationController = loader.getController();

        primaryStage.setTitle("Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static StationGUI getInstance() {
        return instance;
    }

    public SimulationController getSimulationController() {
        return simulationController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}