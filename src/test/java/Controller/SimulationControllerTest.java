package Controller;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class SimulationControllerTest {

    private SimulationController simulationController;

    @BeforeAll
    public static void initToolkit() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        simulationController = new SimulationController();
        simulationController.speedSlider = new Slider();
        simulationController.simulationCanvas = new Canvas();
        simulationController.timeLabel = new Label();
        simulationController.speedLabel = new Label();
        SimulationController.setSimulationData(new long[]{10000L, 123456789L});
    }


    @Test
    public void testSetTimeLabel() {
        simulationController.setTimeLabel(93785);
        assertEquals("001 Days - 02 Hours - 03 Minutes - 05 Seconds", simulationController.timeLabel.getText());
    }

    @Test
    public void testOnTimeUpdate() {
        simulationController.onTimeUpdate(93785);
        Platform.runLater(() -> assertEquals("001 Days - 02 Hours - 03 Minutes - 05 Seconds", simulationController.timeLabel.getText()));
    }

    @Test
    public void testSetSimulationData() {
        long[] data = {20000L, 987654321L};
        SimulationController.setSimulationData(data);
        assertArrayEquals(data, SimulationController.simulationData);
    }

    @Test
    public void testSetIsTrainLoading() {
        SimulationController.setIsTrainLoading(true);
        assertTrue(SimulationController.isTrainLoading);
    }
}