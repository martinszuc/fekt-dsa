package Shapes;

import Base.GraphicObject;

import java.awt.*;

public class Circle extends GraphicObject {
    private int radius;

    public Circle(int x, int y, int radius, Color color, float strokeWidth) {
        super(x, y, color, strokeWidth);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(strokeWidth));
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}
