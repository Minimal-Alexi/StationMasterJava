package View.Visualisation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class representing the visualization of a service point in the simulation.
 * Extends the AbstractVisualization class and provides specific implementation for service point visualization.
 */
public class ServicePointVisualization extends AbstractVisualization {
    private static final int xSize = 70, ySize = 70;
    private final int centerCoordinateX, centerCoordinateY;
    private static final Color color = Color.GOLD;
    private boolean reserved;
    private PassengerVisualization passengerVisualization;

    /**
     * Constructs a ServicePointVisualization with the specified coordinates and graphics context.
     *
     * @param x the x-coordinate of the service point visualization
     * @param y the y-coordinate of the service point visualization
     * @param graphicsContext the graphics context used for drawing the visualization
     */
    public ServicePointVisualization(int x, int y, GraphicsContext graphicsContext) {
        super(x, y, graphicsContext);
        centerCoordinateX = x + xSize / 2;
        centerCoordinateY = y + ySize / 2;
        reserved = false;
    }

    /**
     * Draws the service point visualization on the canvas.
     */
    @Override
    public void drawVisualization() {
        super.graphicsContext.setFill(color);
        super.graphicsContext.fillOval(x, y, xSize, ySize);
        super.graphicsContext.setFill(Color.BLACK);
        super.graphicsContext.fillText(name, x - 50, y + ySize + 25);
        passengerDraw();
    }

    /**
     * Clears the service point visualization from the canvas.
     */
    public void clearVisualization() {
        super.graphicsContext.setFill(super.backgroundColor);
        super.graphicsContext.fillOval(x, y, xSize, ySize);
    }

    /**
     * Returns the x-coordinate of the center of the service point visualization.
     *
     * @return the x-coordinate of the center of the service point visualization
     */
    public int getCenterCoordinateX() {
        return centerCoordinateX;
    }

    /**
     * Returns the y-coordinate of the center of the service point visualization.
     *
     * @return the y-coordinate of the center of the service point visualization
     */
    public int getCenterCoordinateY() {
        return centerCoordinateY;
    }

    /**
     * Returns whether the service point is reserved.
     *
     * @return true if the service point is reserved, false otherwise
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Sets the reservation status of the service point.
     *
     * @param reserved the new reservation status of the service point
     */
    public void setIsReserved(boolean reserved) {
        this.reserved = reserved;
    }

    /**
     * Draws the passenger visualization if the service point is reserved.
     */
    private void passengerDraw() {
        if (isReserved()) {
            if (passengerVisualization == null) {
                passengerVisualization = new PassengerVisualization(
                        x + (xSize - PassengerVisualization.getxSize()) / 2,
                        y + (ySize - PassengerVisualization.getySize()) / 2,
                        graphicsContext
                );
            }
            passengerVisualization.drawVisualization();
        } else {
            passengerVisualization = null;
        }
    }
}