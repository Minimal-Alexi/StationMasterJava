package View.Visualisation;

import Controller.SimulationController;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractVisualization implements InterfaceVisualization{
    protected static final Color backgroundColor = SimulationController.getBackgroundColor();
    protected int x,y;
    protected GraphicsContext graphicsContext;

    public AbstractVisualization(int x, int y, GraphicsContext graphicsContext) {
        this.x = x;
        this.y = y;
        this.graphicsContext = graphicsContext;
    }
}
