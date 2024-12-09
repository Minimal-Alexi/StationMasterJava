package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class StartController extends Controller {

    @FXML
    TextField dayField, hourField, minuteField, secondField,seedField;
    @FXML
    TextField trainDayField1, trainHourField1, trainMinuteField1, trainSecondField1,
              trainDayField2, trainHourField2, trainMinuteField2, trainSecondField2,
              trainDayField3, trainHourField3, trainMinuteField3, trainSecondField3;
    @FXML
    TextField minuteLoaderTimeField1, secondLoaderTimeField1,
              minuteLoaderTimeField2, secondLoaderTimeField2,
              minuteLoaderTimeField3, secondLoaderTimeField3;
    @FXML
    TextField averageTrainCapacityField1,variabilityTrainCapacityField1,
              averageTrainCapacityField2,variabilityTrainCapacityField2,
              averageTrainCapacityField3,variabilityTrainCapacityField3;
    @FXML
    TextField generationMean1,generationMean2;
    @FXML
    Slider trainToMetroRatioSlider;
    @FXML
    Label trainToMetroRatioInfo;
    @FXML
    Button startButton;
    public void initialize() {
        //Formatting General Simulation Settings
        integerFormatter(dayField,0);
        integerFormatter(hourField,24);
        integerFormatter(minuteField,60);
        integerFormatter(secondField,60);
        integerFormatter(seedField,0);

        //Formatting Train Station Settings
        trainSettingInitializer();

        //Formatting Passenger Settings
        passengerSettingInitializer();

        startButton.setOnAction(event -> {
            long[] simulationData = new long[2];
            simulationData[0] = getTotalTime();
            simulationData[1] = getSeed();
            SimulationController.setSimulationData(simulationData);
            application.showSimulationView();
        });
    }
    private void trainSettingInitializer(){
        trainSettingTimeFormatter(trainDayField1, trainHourField1, trainMinuteField1, trainSecondField1);
        trainLoaderTimeFormatter(minuteLoaderTimeField1, secondLoaderTimeField1);
        trainCapacityFormatter(averageTrainCapacityField1,variabilityTrainCapacityField1);
        trainSettingTimeFormatter(trainDayField2, trainHourField2, trainMinuteField2, trainSecondField2);
        trainLoaderTimeFormatter(minuteLoaderTimeField2, secondLoaderTimeField2);
        trainCapacityFormatter(averageTrainCapacityField2,variabilityTrainCapacityField2);
        trainSettingTimeFormatter(trainDayField3, trainHourField3, trainMinuteField3, trainSecondField3);
        trainLoaderTimeFormatter(minuteLoaderTimeField3, secondLoaderTimeField3);
        trainCapacityFormatter(averageTrainCapacityField3,variabilityTrainCapacityField3);
    }
    private void trainSettingTimeFormatter(TextField trainDayField, TextField trainHourField, TextField trainMinuteField, TextField trainSecondField) {
        integerFormatter(trainDayField,0);
        integerFormatter(trainHourField,24);
        integerFormatter(trainMinuteField,60);
        integerFormatter(trainSecondField,60);
    }
    private void trainLoaderTimeFormatter(TextField minuteLoaderTimeField1, TextField secondLoaderTimeField1) {
        integerFormatter(minuteLoaderTimeField1,60);
        integerFormatter(secondLoaderTimeField1,60);
    }
    private void trainCapacityFormatter(TextField averageTrainCapacityField1, TextField variabilityTrainCapacityField1) {
        integerFormatter(averageTrainCapacityField1,500);
        integerFormatter(variabilityTrainCapacityField1,500);
    }
    private void passengerSettingInitializer(){
        integerFormatter(generationMean1,60);
        integerFormatter(generationMean2,60);
        trainToMetroRatioSlider.setValue(30);
        trainToMetroRatioSlider.setMax(100);
        trainToMetroRatioSlider.setMin(1);
        trainToMetroRatioInfo.setText(String.format("%d %%",30));
        trainToMetroRatioSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int percentage = (int) newValue.doubleValue();
            trainToMetroRatioInfo.setText(String.format("%d %%",percentage));
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
