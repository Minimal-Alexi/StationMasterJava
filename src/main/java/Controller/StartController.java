package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class StartController {
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

        });
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

}
