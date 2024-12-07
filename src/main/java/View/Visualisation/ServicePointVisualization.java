package View.Visualisation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ServicePointVisualization extends AbstractVisualization {
    private static final int xSize = 70, ySize = 70;
    private final int centerCoordinateX,centerCoordinateY;
    private static final Color color = Color.GOLD;
    private boolean reserved;
    private PassengerVisualization passengerVisualization;
    public ServicePointVisualization(int x, int y, GraphicsContext graphicsContext) {
        super(x, y, graphicsContext);
        centerCoordinateX = x + xSize / 2;
        centerCoordinateY = y + ySize / 2;
        reserved = false;
    }
    @Override
    public void drawVisualization() {
        super.graphicsContext.setFill(color);
        super.graphicsContext.fillOval(x,y,xSize,ySize);
        super.graphicsContext.setFill(Color.BLACK);
        super.graphicsContext.fillText(name,x - 50,y + ySize + 25);
        passengerDraw();

    }
    public void clearVisualization() {
        super.graphicsContext.setFill(super.backgroundColor);
        super.graphicsContext.fillOval(x,y,xSize,ySize);
    }
    public int getCenterCoordinateX() {
        return centerCoordinateX;
    }
    public int getCenterCoordinateY() {
        return centerCoordinateY;
    }
    public boolean isReserved() {
        return reserved;
    }
    public void setIsReserved(boolean reserved) {
        this.reserved = reserved;
    }
    private void passengerDraw(){
        if(isReserved()){
            if(passengerVisualization == null){
                passengerVisualization = new PassengerVisualization(x + (xSize - PassengerVisualization.getxSize()) / 2,y + (ySize - PassengerVisualization.getySize()) / 2,graphicsContext);
            }
            passengerVisualization.drawVisualization();
        }
        else{
            passengerVisualization = null;
        }
    }
}
