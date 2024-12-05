package Controller;

import View.StationApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class StartController {
    StationApplication application;

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
                if (text.matches("\\\\d*")) {
                    if(Integer.parseInt(text) > max) {
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
                if (text.matches("\\\\d*")) {
                    return change;
                }
                return null;
            }));
        }
    }
    private long getTotalTime(){
        long total = 0;
        total += (long) Integer.parseInt(dayField.getText()) * 24 * 60 * 60;
        total += (long) Integer.parseInt(hourField.getText()) * 60 * 60;
        total += Integer.parseInt(minuteField.getText()) * 60L;
        total += Integer.parseInt(secondField.getText());
        return total;
    }
}
