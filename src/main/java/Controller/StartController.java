package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;


/**
 * The type Start controller.
 */
public class StartController extends Controller {

    /**
     * The Day field.
     */
    @FXML
    TextField dayField,
    /**
     * The Hour field.
     */
    hourField,
    /**
     * The Minute field.
     */
    minuteField,
    /**
     * The Second field.
     */
    secondField,
    /**
     * The Seed field.
     */
    seedField;
    /**
     * The Train day field 1.
     */
    @FXML
    TextField trainDayField1,
    /**
     * The Train hour field 1.
     */
    trainHourField1,
    /**
     * The Train minute field 1.
     */
    trainMinuteField1,
    /**
     * The Train second field 1.
     */
    trainSecondField1,
    /**
     * The Train day field 2.
     */
    trainDayField2,
    /**
     * The Train hour field 2.
     */
    trainHourField2,
    /**
     * The Train minute field 2.
     */
    trainMinuteField2,
    /**
     * The Train second field 2.
     */
    trainSecondField2,
    /**
     * The Train day field 3.
     */
    trainDayField3,
    /**
     * The Train hour field 3.
     */
    trainHourField3,
    /**
     * The Train minute field 3.
     */
    trainMinuteField3,
    /**
     * The Train second field 3.
     */
    trainSecondField3;
    /**
     * The Minute loader time field 1.
     */
    @FXML
    TextField minuteLoaderTimeField1,
    /**
     * The Second loader time field 1.
     */
    secondLoaderTimeField1,
    /**
     * The Minute loader time field 2.
     */
    minuteLoaderTimeField2,
    /**
     * The Second loader time field 2.
     */
    secondLoaderTimeField2,
    /**
     * The Minute loader time field 3.
     */
    minuteLoaderTimeField3,
    /**
     * The Second loader time field 3.
     */
    secondLoaderTimeField3;
    /**
     * The Average train capacity field 1.
     */
    @FXML
    TextField averageTrainCapacityField1,
    /**
     * The Variability train capacity field 1.
     */
    variabilityTrainCapacityField1,
    /**
     * The Average train capacity field 2.
     */
    averageTrainCapacityField2,
    /**
     * The Variability train capacity field 2.
     */
    variabilityTrainCapacityField2,
    /**
     * The Average train capacity field 3.
     */
    averageTrainCapacityField3,
    /**
     * The Variability train capacity field 3.
     */
    variabilityTrainCapacityField3;
    /**
     * The Generation mean 1.
     */
    @FXML
    TextField generationMean1,
    /**
     * The Generation mean 2.
     */
    generationMean2;
    /**
     * The Train to metro ratio slider.
     */
    @FXML
    Slider trainToMetroRatioSlider;
    /**
     * The Train to metro ratio info.
     */
    @FXML
    Label trainToMetroRatioInfo;
    /**
     * The Start button.
     */
    @FXML
    Button startButton;

    /**
     * Initializes the StartController.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the formatting for various input fields and initializes the start button action.
     */
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
            long[] simulationData = new long[17];
            // Adding general settings
            simulationData[0] = getTotalTime(dayField,hourField,minuteField,secondField);
            simulationData[1] = getSeed();
            // Travel time of the trains
            simulationData[2] = getTotalTime(trainDayField1,trainHourField1,trainMinuteField1,trainSecondField1);
            simulationData[3] = getTotalTime(trainDayField2,trainHourField2,trainMinuteField2,trainSecondField2);
            simulationData[4] = getTotalTime(trainDayField3,trainHourField3,trainMinuteField3,trainSecondField3);
            // Loading time of the trains
            simulationData[5] = getTotalTime(null,null,minuteLoaderTimeField1,secondLoaderTimeField1);
            simulationData[6] = getTotalTime(null,null,minuteLoaderTimeField2,secondLoaderTimeField2);
            simulationData[7] = getTotalTime(null,null,minuteLoaderTimeField3,secondLoaderTimeField3);
            // Capacity of the trains
            simulationData[8] = parseLongWithDefault(averageTrainCapacityField1.getText());
            simulationData[9] = parseLongWithDefault(variabilityTrainCapacityField1.getText());
            simulationData[10] = parseLongWithDefault(averageTrainCapacityField2.getText());
            simulationData[11] = parseLongWithDefault(variabilityTrainCapacityField2.getText());
            simulationData[12] = parseLongWithDefault(averageTrainCapacityField3.getText());
            simulationData[13] = parseLongWithDefault(variabilityTrainCapacityField3.getText());
            //Passenger generation
            simulationData[14] = parseLongWithDefault(generationMean1.getText());
            simulationData[15] = parseLongWithDefault(generationMean2.getText());
            //Passenger metro-to-train ratio.
            simulationData[16] = (long) trainToMetroRatioSlider.getValue();
            SimulationController.setSimulationData(simulationData);
            application.showSimulationView();
        });
    }

    /**
     * Initializes the train settings.
     * This method sets up the time, loader time, and capacity formatters for three trains.
     */
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

    /**
     * Formats the time fields for a train.
     *
     * @param trainDayField   the train day field
     * @param trainHourField  the train hour field
     * @param trainMinuteField the train minute field
     * @param trainSecondField the train second field
     */
    private void trainSettingTimeFormatter(TextField trainDayField, TextField trainHourField, TextField trainMinuteField, TextField trainSecondField) {
        integerFormatter(trainDayField,0);
        integerFormatter(trainHourField,24);
        integerFormatter(trainMinuteField,60);
        integerFormatter(trainSecondField,60);
    }

    /**
     * Formats the loader time fields for a train.
     * This method sets up the integer formatter for the minute and second loader time fields.
     *
     * @param minuteLoaderTimeField1 the minute loader time field
     * @param secondLoaderTimeField1 the second loader time field
     */
    private void trainLoaderTimeFormatter(TextField minuteLoaderTimeField1, TextField secondLoaderTimeField1) {
        integerFormatter(minuteLoaderTimeField1,60);
        integerFormatter(secondLoaderTimeField1,60);
    }

    /**
     * Formats the capacity fields for a train.
     * This method sets up the integer formatter for the average and variability train capacity fields.
     *
     * @param averageTrainCapacityField1 the average train capacity field
     * @param variabilityTrainCapacityField1 the variability train capacity field
     */
    private void trainCapacityFormatter(TextField averageTrainCapacityField1, TextField variabilityTrainCapacityField1) {
        integerFormatter(averageTrainCapacityField1,500);
        integerFormatter(variabilityTrainCapacityField1,500);
    }

    /**
     * Initializes the passenger settings.
     * This method sets up the generation mean formatters and the train to metro ratio slider.
     */
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

    /**
     * Formats an integer field.
     * This method sets up the integer formatter for a text field.
     *
     * @param textField the text field
     * @param max       the maximum value
     */
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

    /**
     * Gets the total time.
     * This method calculates the total time in seconds from the day, hour, minute, and second fields.
     *
     * @param dayField    the day field
     * @param hourField   the hour field
     * @param minuteField the minute field
     * @param secondField the second field
     * @return the total time
     */
    private long getTotalTime(TextField dayField, TextField hourField, TextField minuteField, TextField secondField) {
        long total = 0;
        if(dayField != null && !dayField.getText().isEmpty()){
            total += (long) Integer.parseInt(dayField.getText()) * 24 * 60 * 60;

        }
        if(dayField != null && !hourField.getText().isEmpty()){
            total += (long) Integer.parseInt(hourField.getText()) * 60 * 60;
        }
        if(!minuteField.getText().isEmpty()){
            total += Integer.parseInt(minuteField.getText()) * 60L;
        }
        if(!secondField.getText().isEmpty()){
            total += Long.parseLong(secondField.getText());
        }
        return total;
    }

    /**
     * Gets the seed.
     * This method gets the seed from the seed field.
     * If the seed field is empty, the current time in milliseconds is used as the seed.
     *
     * @return the seed
     */
    private long getSeed(){
        // I'm so fucking smart. The Eduni seed is actually an int (32 bits) even though it takes a long.
        // This means we need to take the remainder that fits inside the Integer.
        if(seedField.getText().isEmpty()) return System.currentTimeMillis() % Integer.MAX_VALUE;
        long seed = Long.parseLong(seedField.getText());
        return seed % Integer.MAX_VALUE;
    }

    /**
     * Parses a long with default.
     * This method parses a long from a string.
     * If the string is empty, 0 is returned.
     *
     * @param text the text
     * @return the long
     */
    private long parseLongWithDefault(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        } else {
            return Long.parseLong(text);
        }
    }
}
