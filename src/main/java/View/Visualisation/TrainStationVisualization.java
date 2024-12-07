package View.Visualisation;

import Model.simulation.model.Passenger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TrainStationVisualization extends AbstractVisualization{
    private static final Image Railway = new Image("images/railroad.png");
    private static final Image Train = new Image("images/train.png");
    private static final Color color = Color.GOLD;
    private static final int xSizeBuilding = 200 , ySizeBuilding = 200, xSizeRailroad = 100, ySizeRailroad = 200, distanceBuildingRailroad = 25
            , xSize = xSizeBuilding + distanceBuildingRailroad + xSizeRailroad, ySize = 200
            ,gridSquaresSize = PassengerVisualization.getxSize(),nrRows = xSizeBuilding / gridSquaresSize
            ,nrColumns = ySizeBuilding / gridSquaresSize, maxPassenger = nrRows * nrColumns;
    private boolean trainArrived;
    public int passengerCount;
    private PassengerVisualization[][] passengerVisualizations;
    public TrainStationVisualization(int x, int y, GraphicsContext graphicsContext){
        super(x, y, graphicsContext);
        this.trainArrived = false;
        this.passengerVisualizations = new PassengerVisualization[nrColumns][nrRows];
        for(int i = 0; i < nrColumns; i++){
            for(int j = 0; j < nrRows; j++){
                passengerVisualizations[i][j] = null;
            }
        }
    }
    public void setTrainArrived(boolean trainArrived){
        this.trainArrived = trainArrived;
    }
    public void setPassengerCount(int passengerCount){this.passengerCount = passengerCount;}
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
        drawPassengers();
    }

    @Override
    public void clearVisualization() {
        graphicsContext.clearRect(x,y,xSize * 2 + 25,ySize * 2);
    }
    public void loadPassengers(){
        int centerRailwayX = x + xSizeBuilding + distanceBuildingRailroad + xSizeRailroad / 2,
        centerRailwayY = y + ySizeBuilding / 2;
        for(int i = 0; i < nrRows; i++){
            for(int j = 0; j < nrColumns; j++){
                if(passengerVisualizations[i][j]!= null){
                    passengerVisualizations[i][j].animateImage(centerRailwayX,centerRailwayY);
                    passengerVisualizations[i][j] = null;
                }
            }
        }
    }
    public boolean isTrainArrived(){
        return trainArrived;
    }
    private void drawPassengers(){
        int passengerViewCount = passengerCount;
        for(int i = 0; i < nrRows; i++){
            for(int j = 0; j < nrColumns; j++){
                if(passengerViewCount > 0){
                    if(passengerVisualizations[i][j] == null){
                        passengerVisualizations[i][j] = new PassengerVisualization(x + i * gridSquaresSize, y + j * gridSquaresSize, graphicsContext);
                        passengerVisualizations[i][j].drawVisualization();
                    }
                    else{
                        passengerVisualizations[i][j].drawVisualization();
                    }
                }
                passengerViewCount--;
            }
        }
    }
}
