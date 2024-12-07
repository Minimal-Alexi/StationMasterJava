package View.Visualisation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ServicePointVisualization extends AbstractVisualization {
    public static final int xSize = 70, ySize = 70;
    public int  centerCoordinateX,centerCoordinateY;
    private static final Color color = Color.GOLD;
    private String name = "Loading Name from Simulation";
    public ServicePointVisualization(int x, int y, GraphicsContext graphicsContext) {
        super(x, y, graphicsContext);
        centerCoordinateX = x + xSize / 2;
        centerCoordinateY = y + ySize / 2;
    }
    @Override
    public void drawVisualization() {
        super.graphicsContext.setFill(color);
        super.graphicsContext.fillOval(x,y,xSize,ySize);
        super.graphicsContext.setFill(Color.BLACK);
        super.graphicsContext.fillText(name,x - 50,y + ySize + 25);
    }
    public void clearVisualization() {
        super.graphicsContext.setFill(super.backgroundColor);
        super.graphicsContext.fillOval(x,y,xSize,ySize);
    }
}
