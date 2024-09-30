package Base;

import java.awt.*;

public abstract class GraphicObject {
    protected int x, y;
    protected Color color;
    protected float strokeWidth;

    public GraphicObject(int x, int y, Color color, float strokeWidth) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public abstract void draw(Graphics2D g);
}