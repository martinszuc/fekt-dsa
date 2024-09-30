package Shapes;

import Base.GraphicObject;

import java.awt.*;

public class Square extends GraphicObject {
    private int sideLength;

    public Square(int x, int y, int sideLength, Color color, float strokeWidth) {
        super(x, y, color, strokeWidth);
        this.sideLength = sideLength;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(strokeWidth));
        g.drawRect(x - sideLength / 2, y - sideLength / 2, sideLength, sideLength);
    }
}