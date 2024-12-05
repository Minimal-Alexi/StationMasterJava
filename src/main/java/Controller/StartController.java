package Controller;

import View.StationApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class StartController {
    private StationApplication application;

    @FXML
    TextField dayField, hourField, minuteField, secondField,seedField;
    @FXML
    Button startButton;
    public void initialize() {
        integerFormatter(dayField,0);
        integerFormatter(hourField,24);
        integerFormatter(minuteField,60);
        integerFormatter(secondField,60);
        integerFormatter(seedField,0);
        startButton.setOnAction(event -> {
            long[] simulationData = new long[2];
            simulationData[0] = getTotalTime();
            simulationData[1] = getSeed();
            System.out.printf("Runtime: %d \nSeed: %d\n\n",simulationData[0],simulationData[1]);
            application.setSimulationData(simulationData);
            application.showSimulationView();
        });
    }
    public void setApplication(StationApplication application) {
        this.application = application;
    }
    private void integerFormatter(TextField textField, int max) {
        if(max > 0)
        {
            textField.setTextFormatter(new TextFormatter<Integer>(change -> {
                String text = change.getControlNewText();
                if (text.matches("\\d*")) {
                    if(!text.isEmpty() && Integer.parseInt(text) > max) {
                        return null;
                    }
                    return change;
                }
                return null;
            }));
        }
        else{
            textField.setTextFormatter(new TextFormatter<Integer>(change -> {
                String text = change.getControlNewText();
                if (text.matches("\\d*")) {
                    return change;
                }
                return null;
            }));
        }
    }
    private long getTotalTime(){
        long total = 0;
        if(!dayField.getText().isEmpty()){
            total += (long) Integer.parseInt(dayField.getText()) * 24 * 60 * 60;

        }
        if(!hourField.getText().isEmpty()){
            total += (long) Integer.parseInt(hourField.getText()) * 60 * 60;
        }
        if(!minuteField.getText().isEmpty()){
            total += Integer.parseInt(minuteField.getText()) * 60L;
        }
        if(!secondField.getText().isEmpty()){
            total += Long.parseLong(secondField.getText());
        }
        if(total == 0) return 1000L;
        return total;
    }
    private long getSeed(){
        if(seedField.getText().isEmpty()) return System.currentTimeMillis();
        return Long.parseLong(seedField.getText());
    }
}
