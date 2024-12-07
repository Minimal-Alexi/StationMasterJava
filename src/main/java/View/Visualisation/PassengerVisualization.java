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


public class PassengerVisualization extends AbstractVisualization{
    private static int currentSpeed = 1000,maxTime = 2000;
    private static final int xSize = 50, ySize = 50;
    private static final Bernoulli genderDecider = new Bernoulli(0.5);
    private final Image image;
    public PassengerVisualization(int x, int y, GraphicsContext graphicsContext) {
        super(x, y, graphicsContext);
        URL imageLink; // 0 - Man | 1 - Woman
        if(genderDecider.sample() == 0){
            imageLink = getClass().getResource("/images/man.png");
        }
        else{
            imageLink = getClass().getResource("/images/woman.png");
        }
        image = new Image(imageLink.toString());
    }

    @Override
    public void drawVisualization() {
        super.graphicsContext.drawImage(image, super.x,super.y,xSize,ySize);
    }
    public void clearVisualization() {
        super.graphicsContext.clearRect(super.x,super.y,xSize,ySize);
    }
    public void animateImage(int xDest, int yDest){
        IntegerProperty xProperty = new SimpleIntegerProperty(),yProperty = new SimpleIntegerProperty();
        xProperty.setValue(x);
        yProperty.setValue(y);

        Timeline timeline = new Timeline();

        KeyValue xValue = new KeyValue(xProperty,xDest);
        KeyValue yValue = new KeyValue(yProperty,yDest);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(maxTime - currentSpeed),xValue,yValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            drawVisualization();
        });
        timeline.play();
    }
    public static int getxSize(){
        return xSize;
    }
    public static int getySize(){
        return ySize;
    }
    public static void setCurrentSpeed(int speed){
        currentSpeed = speed;
    }
}
