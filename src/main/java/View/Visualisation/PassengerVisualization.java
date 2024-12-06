package View.Visualisation;

import Model.distributions.Bernoulli;

import java.net.URL;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class PassengerVisualization extends AbstractVisualization{
    public static final int xSize = 50, ySize = 50;
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
}
