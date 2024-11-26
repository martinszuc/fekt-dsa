package com.martinszuc.polygen.ga;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a polygon with vertices and color.
 */
public class Polygon {
    private List<Point> vertices;
    private Color color;
    private static final Random rand = new Random();

    /**
     * Initializes a polygon with random vertices and color.
     */
    public Polygon() {
        vertices = new ArrayList<>();
        // Initialize with a random number of vertices between 3 and 6
        int numVertices = rand.nextInt(4) + 3;
        for (int i = 0; i < numVertices; i++) {
            vertices.add(new Point(rand.nextInt(400), rand.nextInt(400)));
        }
        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    /**
     * Gets the list of vertices.
     *
     * @return List of Point objects.
     */
    public List<Point> getVertices() {
        return vertices;
    }

    /**
     * Gets the color of the polygon.
     *
     * @return Color object.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the polygon.
     *
     * @param color Color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Mutates the polygon by randomly changing its vertices or color.
     */
    public void mutate() {
        // Randomly decide to mutate vertices or color
        if (rand.nextBoolean()) {
            // Mutate a random vertex
            int vertexIndex = rand.nextInt(vertices.size());
            Point vertex = vertices.get(vertexIndex);
            int dx = rand.nextInt(21) - 10; // Change between -10 and +10
            int dy = rand.nextInt(21) - 10;
            vertex.setLocation(clamp(vertex.x + dx, 0, 400), clamp(vertex.y + dy, 0, 400));
        } else {
            // Mutate color
            int r = clamp(color.getRed() + rand.nextInt(21) - 10, 0, 255);
            int g = clamp(color.getGreen() + rand.nextInt(21) - 10, 0, 255);
            int b = clamp(color.getBlue() + rand.nextInt(21) - 10, 0, 255);
            int a = clamp(color.getAlpha() + rand.nextInt(21) - 10, 0, 255);
            color = new Color(r, g, b, a);
        }
    }

    /**
     * Creates a deep copy of the polygon.
     *
     * @return A new Polygon object with copied vertices and color.
     */
    public Polygon copy() {
        Polygon copy = new Polygon();
        copy.getVertices().clear();
        for (Point p : this.vertices) {
            copy.getVertices().add(new Point(p.x, p.y));
        }
        copy.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha()));
        return copy;
    }

    /**
     * Utility method to clamp a value between min and max.
     *
     * @param value The value to clamp.
     * @param min   Minimum allowable value.
     * @param max   Maximum allowable value.
     * @return Clamped value.
     */
    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
