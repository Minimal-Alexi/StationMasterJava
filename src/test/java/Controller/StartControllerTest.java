package Controller;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StartControllerTest {

    private StartController startController;

    @BeforeAll
    public static void initToolkit() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        startController = new StartController();
        startController.dayField = new TextField();
        startController.hourField = new TextField();
        startController.minuteField = new TextField();
        startController.secondField = new TextField();
        startController.seedField = new TextField();
        startController.trainDayField1 = new TextField();
        startController.trainHourField1 = new TextField();
        startController.trainMinuteField1 = new TextField();
        startController.trainSecondField1 = new TextField();
        startController.trainDayField2 = new TextField();
        startController.trainHourField2 = new TextField();
        startController.trainMinuteField2 = new TextField();
        startController.trainSecondField2 = new TextField();
        startController.trainDayField3 = new TextField();
        startController.trainHourField3 = new TextField();
        startController.trainMinuteField3 = new TextField();
        startController.trainSecondField3 = new TextField();
        startController.minuteLoaderTimeField1 = new TextField();
        startController.secondLoaderTimeField1 = new TextField();
        startController.minuteLoaderTimeField2 = new TextField();
        startController.secondLoaderTimeField2 = new TextField();
        startController.minuteLoaderTimeField3 = new TextField();
        startController.secondLoaderTimeField3 = new TextField();
        startController.averageTrainCapacityField1 = new TextField();
        startController.variabilityTrainCapacityField1 = new TextField();
        startController.averageTrainCapacityField2 = new TextField();
        startController.variabilityTrainCapacityField2 = new TextField();
        startController.averageTrainCapacityField3 = new TextField();
        startController.variabilityTrainCapacityField3 = new TextField();
        startController.generationMean1 = new TextField();
        startController.generationMean2 = new TextField();
        startController.trainToMetroRatioSlider = new Slider();
        startController.trainToMetroRatioInfo = new Label();
        startController.startButton = new Button();
    }

    @Test
    public void testInitialize() {
        startController.initialize();
        assertNotNull(startController.dayField.getTextFormatter());
        assertNotNull(startController.hourField.getTextFormatter());
        assertNotNull(startController.minuteField.getTextFormatter());
        assertNotNull(startController.secondField.getTextFormatter());
        assertNotNull(startController.seedField.getTextFormatter());
    }

    @Test
    public void testGetTotalTime() {
        startController.dayField.setText("1");
        startController.hourField.setText("1");
        startController.minuteField.setText("1");
        startController.secondField.setText("1");
        long totalTime = startController.getTotalTime(startController.dayField, startController.hourField, startController.minuteField, startController.secondField);
        assertEquals(90061, totalTime);
    }

    @Test
    public void testGetSeed() {
        startController.seedField.setText("123456789");
        long seed = startController.getSeed();
        assertEquals(123456789, seed);
    }

    @Test
    public void testGetSeedWithEmptyField() {
        startController.seedField.setText("");
        long seed = startController.getSeed();
        assertTrue(seed > 0 && seed < Integer.MAX_VALUE);
    }
}