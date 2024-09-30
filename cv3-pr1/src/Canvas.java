import Base.GraphicObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private int width, height;
    private List<GraphicObject> objects = new ArrayList<>();

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addObject(GraphicObject obj) {
        objects.add(obj);
    }

    public void createImage(String fileName) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        for (GraphicObject obj : objects) {
            obj.draw(g);
        }

        try {
            File folder = new File("cv3-pr1/images");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folder, fileName);
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.dispose();
    }
}