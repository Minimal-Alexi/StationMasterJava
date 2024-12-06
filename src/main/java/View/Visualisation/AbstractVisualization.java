package View.Visualisation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractVisualization implements InterfaceVisualization{
    private int x,y;
    private GraphicsContext graphicsContext;

    public AbstractVisualization(int x, int y, GraphicsContext graphicsContext) {
        this.x = x;
        this.y = y;
        this.graphicsContext = graphicsContext;
    }
}
