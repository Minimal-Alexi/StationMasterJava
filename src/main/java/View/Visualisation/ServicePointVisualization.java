package View.Visualisation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ServicePointVisualization extends AbstractVisualization {
    public static final int xSize = 70, ySize = 70;
    private static final Color color = Color.GOLD;
    public ServicePointVisualization(int x, int y, GraphicsContext graphicsContext) {
        super(x, y, graphicsContext);
    }
    @Override
    public void drawVisualization() {
        super.graphicsContext.setFill(color);
        super.graphicsContext.fillOval(x,y,xSize,ySize);
    }
    public void clearVisualization() {
        super.graphicsContext.setFill(super.backgroundColor);
        super.graphicsContext.fillOval(x,y,xSize,ySize);
    }
}