package Shapes;

import Base.GraphicObject;

import java.awt.*;

public class Rectangle extends GraphicObject {
    private int width, height;

    public Rectangle(int x, int y, int width, int height, Color color, float strokeWidth) {
        super(x, y, color, strokeWidth);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(strokeWidth));
        g.drawRect(x - width / 2, y - height / 2, width, height);
    }
}
