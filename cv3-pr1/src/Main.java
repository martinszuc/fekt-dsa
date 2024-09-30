import Shapes.*;
import Shapes.Rectangle;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        drawMickey();
        // https://app.diagrams.net/?src=about#G19V0jhYa1b0Bca-p8OXSYskj7sPZPJJIu#%7B%22pageId%22%3A%22D9wTtl9f2n_JX1vXOhLo%22%7D
    }

    public static void drawMickey() {
        Canvas canvas = new Canvas(800, 800);

        // Head
        canvas.addObject(new Circle(400, 400, 150, Color.BLACK, 2.0f));

        // Ears
        canvas.addObject(new Circle(250, 250, 75, Color.BLACK, 2.0f));
        canvas.addObject(new Circle(550, 250, 75, Color.BLACK, 2.0f));


        CompositeShape faceFeatures = new CompositeShape(0, 0, Color.BLACK, 2.0f);

        // Eyes
        faceFeatures.add(new Circle(350, 350, 20, Color.WHITE, 2.0f)); // Left eye
        faceFeatures.add(new Circle(450, 350, 20, Color.WHITE, 2.0f)); // Right eye

        // Nose
        faceFeatures.add(new Square(400, 420, 20, Color.BLACK, 2.0f));

        // Mouth
        faceFeatures.add(new Rectangle(400, 480, 100, 20, Color.BLACK, 2.0f));


        faceFeatures.setColorForAll(Color.RED);
        faceFeatures.setStrokeWidthForAll(5.0f);
        canvas.addObject(faceFeatures);

        // Save the image
        canvas.createImage("mickeyMouse.png");
    }
}
