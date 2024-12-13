package View.Visualisation;

import Controller.SimulationController;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Abstract class representing a visualization in the simulation.
 * Provides common properties and methods for visualizations.
 */
public abstract class AbstractVisualization implements InterfaceVisualization {
    /**
     * The background color for the visualization.
     */
    protected static final Color backgroundColor = SimulationController.getBackgroundColor();

    /**
     * The x-coordinate of the visualization.
     */
    protected int x;

    /**
     * The y-coordinate of the visualization.
     */
    protected int y;

    /**
     * The graphics context used for drawing the visualization.
     */
    protected GraphicsContext graphicsContext;

    /**
     * The name of the visualization.
     */
    protected String name = "Loading Name from Simulation";

    /**
     * Constructs an AbstractVisualization with the specified coordinates and graphics context.
     *
     * @param x the x-coordinate of the visualization
     * @param y the y-coordinate of the visualization
     * @param graphicsContext the graphics context used for drawing the visualization
     */
    public AbstractVisualization(int x, int y, GraphicsContext graphicsContext) {
        this.x = x;
        this.y = y;
        this.graphicsContext = graphicsContext;
    }

    /**
     * Sets the name of the visualization.
     *
     * @param name the new name of the visualization
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the x-coordinate of the visualization.
     *
     * @return the x-coordinate of the visualization
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the visualization.
     *
     * @return the y-coordinate of the visualization
     */
    public int getY() {
        return y;
    }
}