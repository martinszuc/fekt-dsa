package com.martinszuc.polygen.ga;

import java.awt.Color;
import java.util.Random;

/**
 * Represents a polygon with vertices and color.
 */
public class Polygon {
    private final int[] xPoints;
    private final int[] yPoints;
    private final int numPoints;
    private int r, g, b, a;
    private static final Random rand = new Random();

    /**
     * Initializes a polygon with random vertices and color.
     *
     * @param imageWidth  Width of the image.
     * @param imageHeight Height of the image.
     */
    public Polygon(int imageWidth, int imageHeight) {
        // Initialize with a random number of vertices between 3 and 6
        this.numPoints = rand.nextInt(4) + 3;
        this.xPoints = new int[numPoints];
        this.yPoints = new int[numPoints];
        initializeRandom(imageWidth, imageHeight);
    }

    /**
     * Initializes the polygon with specified vertices and color.
     * Used for creating copies.
     *
     * @param xPoints   Array of x-coordinates.
     * @param yPoints   Array of y-coordinates.
     * @param numPoints Number of points.
     * @param r         Red component.
     * @param g         Green component.
     * @param b         Blue component.
     * @param a         Alpha component.
     */
    public Polygon(int[] xPoints, int[] yPoints, int numPoints, int r, int g, int b, int a) {
        this.numPoints = numPoints;
        this.xPoints = new int[numPoints];
        this.yPoints = new int[numPoints];
        System.arraycopy(xPoints, 0, this.xPoints, 0, numPoints);
        System.arraycopy(yPoints, 0, this.yPoints, 0, numPoints);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Initializes the polygon with random vertices and color.
     *
     * @param imageWidth  Width of the image.
     * @param imageHeight Height of the image.
     */
    private void initializeRandom(int imageWidth, int imageHeight) {
        for (int i = 0; i < numPoints; i++) {
            xPoints[i] = rand.nextInt(imageWidth);
            yPoints[i] = rand.nextInt(imageHeight);
        }
        r = rand.nextInt(256);
        g = rand.nextInt(256);
        b = rand.nextInt(256);
        a = rand.nextInt(256);
    }

    /**
     * Gets the PolygonData object containing all necessary data.
     *
     * @return PolygonData object.
     */
    public PolygonData getPolygonData() {
        // Create copies of xPoints and yPoints to prevent external modification
        int[] xCopy = new int[numPoints];
        int[] yCopy = new int[numPoints];
        System.arraycopy(this.xPoints, 0, xCopy, 0, numPoints);
        System.arraycopy(this.yPoints, 0, yCopy, 0, numPoints);
        Color color = new Color(r, g, b, a);
        return new PolygonData(xCopy, yCopy, numPoints, color);
    }

    /**
     * Gets the color of the polygon.
     *
     * @return AWT Color object.
     */
    public Color getColor() {
        return new Color(r, g, b, a);
    }

    /**
     * Mutates the polygon by randomly changing its vertices or color.
     *
     * @param imageWidth  Width of the image.
     * @param imageHeight Height of the image.
     */
    public void mutate(int imageWidth, int imageHeight) {
        if (rand.nextBoolean()) {
            // Mutate a random vertex
            int vertexIndex = rand.nextInt(numPoints);
            int dx = rand.nextInt(21) - 10; // Change between -10 and +10
            int dy = rand.nextInt(21) - 10;
            xPoints[vertexIndex] = clamp(xPoints[vertexIndex] + dx, 0, imageWidth);
            yPoints[vertexIndex] = clamp(yPoints[vertexIndex] + dy, 0, imageHeight);
        } else {
            // Mutate color
            r = clamp(r + rand.nextInt(21) - 10, 0, 255);
            g = clamp(g + rand.nextInt(21) - 10, 0, 255);
            b = clamp(b + rand.nextInt(21) - 10, 0, 255);
            a = clamp(a + rand.nextInt(21) - 10, 0, 255);
        }
    }

    /**
     * Creates a deep copy of the polygon.
     *
     * @return A new Polygon object with copied vertices and color.
     */
    public Polygon copy() {
        int[] xCopy = new int[numPoints];
        int[] yCopy = new int[numPoints];
        System.arraycopy(this.xPoints, 0, xCopy, 0, numPoints);
        System.arraycopy(this.yPoints, 0, yCopy, 0, numPoints);
        return new Polygon(xCopy, yCopy, numPoints, r, g, b, a);
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
