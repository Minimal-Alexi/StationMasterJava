package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class StartController extends Controller {

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
            SimulationController.setSimulationData(simulationData);
            application.showSimulationView();
        });
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
        if(total == 0) return 10000L;
        return total;
    }
    private long getSeed(){
        // I'm so fucking smart. The Eduni seed is actually an int (32 bits) even though it takes a long.
        // This means we need to take the remainder that fits inside the Integer.
        if(seedField.getText().isEmpty()) return System.currentTimeMillis() % Integer.MAX_VALUE;
        long seed = Long.parseLong(seedField.getText());
        return seed % Integer.MAX_VALUE;
    }
}
