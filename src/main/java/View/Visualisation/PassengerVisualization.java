package View.Visualisation;

import Model.distributions.Bernoulli;

import java.net.URL;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * Class representing the visualization of a passenger in the simulation.
 * Extends the AbstractVisualization class and provides specific implementation for passenger visualization.
 */
public class PassengerVisualization extends AbstractVisualization {
    private static final int xSize = 50, ySize = 50;
    private static final Bernoulli genderDecider = new Bernoulli(0.5);
    private final Image image;

    /**
     * Constructs a PassengerVisualization with the specified coordinates and graphics context.
     *
     * @param x the x-coordinate of the passenger visualization
     * @param y the y-coordinate of the passenger visualization
     * @param graphicsContext the graphics context used for drawing the visualization
     */
    public PassengerVisualization(int x, int y, GraphicsContext graphicsContext) {
        super(x, y, graphicsContext);
        URL imageLink; // 0 - Man | 1 - Woman
        if (genderDecider.sample() == 0) {
            imageLink = getClass().getResource("/images/man.png");
        } else {
            imageLink = getClass().getResource("/images/woman.png");
        }
        image = new Image(imageLink.toString());
    }

    /**
     * Draws the passenger visualization on the canvas.
     */
    @Override
    public void drawVisualization() {
        super.graphicsContext.drawImage(image, super.x, super.y, xSize, ySize);
    }

    /**
     * Clears the passenger visualization from the canvas.
     */
    public void clearVisualization() {
        super.graphicsContext.clearRect(super.x, super.y, xSize, ySize);
    }

    /**
     * Sets the position of the passenger visualization.
     *
     * @param x the new x-coordinate of the passenger visualization
     * @param y the new y-coordinate of the passenger visualization
     */
    public void setPosition(int x, int y) {
        super.x = x;
        super.y = y;
    }

    /**
     * Returns the width of the passenger visualization.
     *
     * @return the width of the passenger visualization
     */
    public static int getxSize() {
        return xSize;
    }

    /**
     * Returns the height of the passenger visualization.
     *
     * @return the height of the passenger visualization
     */
    public static int getySize() {
        return ySize;
    }
}