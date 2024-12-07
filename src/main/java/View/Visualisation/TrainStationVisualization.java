package View.Visualisation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TrainStationVisualization extends AbstractVisualization{
    private static final Image Railway = new Image("images/railroad.png");
    private static final Image Train = new Image("images/train.png");
    private static final Color color = Color.GOLD;
    private static final int xSizeBuilding = 200 , ySizeBuilding = 200, xSizeRailroad = 100, ySizeRailroad = 200, distanceBuildingRailroad = 25
            , xSize = xSizeBuilding + distanceBuildingRailroad + xSizeRailroad, ySize = 200;
    private boolean trainArrived;
    public TrainStationVisualization(int x, int y, GraphicsContext graphicsContext){
        super(x, y, graphicsContext);
        this.trainArrived = false;
    }
    public void setTrainArrived(boolean trainArrived){
        this.trainArrived = trainArrived;
    }
    @Override
    public void drawVisualization(){
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x,y,xSizeBuilding,ySizeBuilding);
        Image imageToDraw;
        if(trainArrived){
            imageToDraw = Train;
        }
        else{
            imageToDraw = Railway;
        }
        graphicsContext.drawImage(imageToDraw,x + xSizeBuilding + distanceBuildingRailroad,y,xSizeRailroad,ySizeRailroad);
        super.graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(name,x,y + ySizeBuilding + 25);
    }

    @Override
    public void clearVisualization() {
        graphicsContext.clearRect(x,y,xSize * 2 + 25,ySize * 2);
    }
    public boolean isTrainArrived(){
        return trainArrived;
    }
}
