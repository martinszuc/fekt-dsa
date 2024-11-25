package com.martinszuc.polygen.ga;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a polygon with specific color and shape properties.
 */
public class Polygon {
    private static final int MAX_VERTICES = 6; // Maximum number of vertices per polygon
    private static final int CANVAS_WIDTH = 400; // Canvas width in pixels
    private static final int CANVAS_HEIGHT = 400; // Canvas height in pixels

    private final List<Point> vertices;
    private Color color;
    private final Random rand = new Random();

    /**
     * Initializes a Polygon with random vertices and color.
     */
    public Polygon() {
        this.vertices = new ArrayList<>();
        initializeRandom();
    }

    /**
     * Initializes the polygon with random vertices and color.
     */
    private void initializeRandom() {
        int numVertices = rand.nextInt(MAX_VERTICES - 2) + 3; // Between 3 and MAX_VERTICES
        for (int i = 0; i < numVertices; i++) {
            int x = rand.nextInt(CANVAS_WIDTH);
            int y = rand.nextInt(CANVAS_HEIGHT);
            vertices.add(new Point(x, y));
        }
        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), rand.nextInt(128) + 128); // Semi-transparent
    }

    /**
     * Mutates the polygon by altering one vertex or its color.
     */
    public void mutate() {
        if (rand.nextBoolean()) {
            // Mutate a vertex
            int vertexIndex = rand.nextInt(vertices.size());
            Point p = vertices.get(vertexIndex);
            int dx = rand.nextInt(21) - 10; // Change between -10 and +10
            int dy = rand.nextInt(21) - 10;
            p.x = clamp(p.x + dx, CANVAS_WIDTH);
            p.y = clamp(p.y + dy, CANVAS_HEIGHT);
        } else {
            // Mutate color
            int r = clamp(color.getRed() + rand.nextInt(21) - 10, 255);
            int g = clamp(color.getGreen() + rand.nextInt(21) - 10, 255);
            int b = clamp(color.getBlue() + rand.nextInt(21) - 10, 255);
            int a = clamp(color.getAlpha() + rand.nextInt(21) - 10, 255);
            color = new Color(r, g, b, a);
        }
    }

    /**
     * Clamps a value between 0 and the specified maximum.
     *
     * @param value The value to clamp.
     * @param max   The maximum allowable value.
     * @return The clamped value.
     */
    private int clamp(int value, int max) {
        return Math.max(0, Math.min(value, max));
    }

    /**
     * Gets the list of vertices.
     *
     * @return List of Points representing the vertices.
     */
    public List<Point> getVertices() {
        return vertices;
    }

    /**
     * Gets the color of the polygon.
     *
     * @return Color object representing the polygon's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the polygon.
     *
     * @param color New color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Creates a deep copy of the polygon.
     *
     * @return A new Polygon object with copied properties.
     */
    public Polygon copy() {
        Polygon copy = new Polygon();
        copy.getVertices().clear(); // Clear default vertices
        for (Point p : this.vertices) {
            copy.getVertices().add(new Point(p.x, p.y));
        }
        copy.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha()));
        return copy;
    }
}
