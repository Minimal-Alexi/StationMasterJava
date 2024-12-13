package Controller;

import javafx.application.Platform;
import javafx.scene.control.Label;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ResultControllerTest {

    private ResultController controller;

    @BeforeAll
    static void setup() {
        // Initialize JavaFX Toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void init() throws Exception {
        // Manually instantiate and initialize the controller
        controller = new ResultController();

        // Use reflection or a mocking framework to inject mock Labels
        controller.totalPassengersServedAtTicket1 = new Label();
        controller.averageServiceTimeAtTicket1 = new Label();
        controller.totalPassengersServedAtTicket2 = new Label();
        controller.averageServiceTimeAtTicket2 = new Label();
        controller.totalPassengersServedAtStation1 = new Label();
        controller.averageLoadedCapacityAtStation1 = new Label();
        controller.averageTrainTravelTimeStation1 = new Label();
        controller.totalTrainAtStation1 = new Label();
        controller.totalPassengersServedAtStation2 = new Label();
        controller.averageLoadedCapacityAtStation2 = new Label();
        controller.averageTrainTravelTimeStation2 = new Label();
        controller.totalTrainAtStation2 = new Label();
        controller.totalPassengersServedAtMetroStation = new Label();
        controller.averageLoadedCapacityAtMetroStation = new Label();
        controller.averageTrainTravelTimeMetroStation = new Label();
        controller.totalTrainAtMetroStation = new Label();

        // Prepare test data
        String[] testResults = {
                "50", "10.5", "40", "12.3", "100", "85.0", "7.2", "20", "90", "82.5", "6.8", "18", "200", "75.3", "8.0", "25"
        };
        ResultController.setResults(testResults);
    }

    @Test
    void testSetResults() {
        // Verify that the results are set correctly
        String[] expected = {
                "50", "10.5", "40", "12.3", "100", "85.0", "7.2", "20", "90", "82.5", "6.8", "18", "200", "75.3", "8.0", "25"
        };
        assertArrayEquals(expected, ResultController.results, "Results array should match the expected values.");
    }

    @Test
    void testUpdateLabels() {
        // Run updateLabels on the JavaFX thread
        Platform.runLater(() -> {
            controller.updateLabels();

            // Validate the content of labels
            assertEquals("Total passengers served: 50", controller.totalPassengersServedAtTicket1.getText());
            assertEquals("Average service time: 10.5", controller.averageServiceTimeAtTicket1.getText());
            assertEquals("Total passengers served: 40", controller.totalPassengersServedAtTicket2.getText());
            assertEquals("Average service time: 12.3", controller.averageServiceTimeAtTicket2.getText());
            assertEquals("Total passengers served: 100", controller.totalPassengersServedAtStation1.getText());
            assertEquals("Average load time: 85.0", controller.averageLoadedCapacityAtStation1.getText());
            assertEquals("Average train travel time: 7.2", controller.averageTrainTravelTimeStation1.getText());
            assertEquals("Total train stopped: 20", controller.totalTrainAtStation1.getText());
            assertEquals("Total passengers served: 90", controller.totalPassengersServedAtStation2.getText());
            assertEquals("Average load time: 82.5", controller.averageLoadedCapacityAtStation2.getText());
            assertEquals("Average train travel time: 6.8", controller.averageTrainTravelTimeStation2.getText());
            assertEquals("Total train stopped: 18", controller.totalTrainAtStation2.getText());
            assertEquals("Total passengers served: 200", controller.totalPassengersServedAtMetroStation.getText());
            assertEquals("Average load time: 75.3", controller.averageLoadedCapacityAtMetroStation.getText());
            assertEquals("Average train travel time 8.0", controller.averageTrainTravelTimeMetroStation.getText());
            assertEquals("Total train stopped: 25", controller.totalTrainAtMetroStation.getText());
        });
    }
}
