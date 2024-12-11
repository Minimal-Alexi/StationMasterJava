package View;

import Controller.ResultController;
import Controller.SimulationController;
import Controller.StartController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class StationApplication extends Application {
    private static Stage primaryStage;
    private static MediaPlayer mediaPlayer;
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

            stopSfxPlayer();
            Scene scene = new Scene(startLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            alertSystem(e);
        }
    }
    public void showSimulationView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Simulation.fxml"));
            Parent simulationLayout = loader.load();

            SimulationController controller = loader.getController();
            controller.setApplication(this);
            Platform.runLater(() -> {
                sfxPlayer("/sfx/run_for_your_life.mp3");
            });

            Scene scene = new Scene(simulationLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            alertSystem(e);
        }
    }
    public void showResultView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Result.fxml"));
            Parent startLayout = loader.load();

            ResultController controller = loader.getController();
            controller.setApplication(this);

            stopSfxPlayer();
            Scene scene = new Scene(startLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            alertSystem(e);
        }
    }
    public void alertSystem(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Something went wrong.\n" + e.getMessage());
        e.printStackTrace();
        alert.showAndWait();
    }
    private void sfxPlayer(String path){
        try {
            File file = new File(getClass().getResource(path).toURI());
            Media media = new Media(file.toURI().toString());

            mediaPlayer = new MediaPlayer(media);
            System.out.println("File URI: " + media.getSource());
            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
                System.out.println("Total duration: " + mediaPlayer.getTotalDuration());
                if (path.equals("/sfx/run_for_your_life.mp3")) {
                    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                }
            });
            mediaPlayer.setOnError(() -> {
                System.err.println("Error with media player: " + mediaPlayer.getError());
            });
            mediaPlayer.setOnEndOfMedia(() -> {System.out.println("Finished playing");});
        } catch (Exception e) {
            alertSystem(e);
        }
    }
    private void stopSfxPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
