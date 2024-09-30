package Shapes;

import Base.GraphicObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CompositeShape extends GraphicObject {
    private List<GraphicObject> objects = new ArrayList<>();

    public CompositeShape(int x, int y, Color color, float strokeWidth) {
        super(x, y, color, strokeWidth);
    }

    public void add(GraphicObject obj) {
        objects.add(obj);
    }

    public void setColorForAll(Color newColor) {
        for (GraphicObject obj : objects) {
            obj.setColor(newColor);
        }
    }

    public void setStrokeWidthForAll(float newStrokeWidth) {
        for (GraphicObject obj : objects) {
            obj.setStrokeWidth(newStrokeWidth);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for (GraphicObject obj : objects) {
            obj.draw(g);
        }
    }
}
