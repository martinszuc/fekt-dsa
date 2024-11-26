package com.martinszuc.polygen.ga;

import java.awt.Color;

/**
 * Data holder for polygon coordinates and color.
 */
public class PolygonData {
    private int[] xPoints;
    private int[] yPoints;
    private final int numPoints;
    private Color color;

    /**
     * Constructor to initialize polygon data.
     *
     * @param xPoints   Array of x-coordinates.
     * @param yPoints   Array of y-coordinates.
     * @param numPoints Number of points in the polygon.
     * @param color     Color of the polygon.
     */
    public PolygonData(int[] xPoints, int[] yPoints, int numPoints, Color color) {
        // Defensive copying to prevent external modifications
        this.xPoints = xPoints.clone();
        this.yPoints = yPoints.clone();
        this.numPoints = numPoints;
        this.color = color;
    }

    /**
     * Gets the array of x-coordinates.
     *
     * @return Array of x-coordinates.
     */
    public int[] getXPoints() {
        return xPoints.clone(); // Return a clone to preserve immutability
    }

    /**
     * Sets the array of x-coordinates.
     *
     * @param xPoints New array of x-coordinates.
     */
    public void setXPoints(int[] xPoints) {
        this.xPoints = xPoints.clone();
    }

    /**
     * Gets the array of y-coordinates.
     *
     * @return Array of y-coordinates.
     */
    public int[] getYPoints() {
        return yPoints.clone(); // Return a clone to preserve immutability
    }

    /**
     * Sets the array of y-coordinates.
     *
     * @param yPoints New array of y-coordinates.
     */
    public void setYPoints(int[] yPoints) {
        this.yPoints = yPoints.clone();
    }

    /**
     * Gets the number of points in the polygon.
     *
     * @return Number of points.
     */
    public int getNumPoints() {
        return numPoints;
    }

    /**
     * Gets the color of the polygon.
     *
     * @return AWT Color object.
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
     * Creates a deep copy of this PolygonData.
     *
     * @return A new PolygonData object with copied data.
     */
    public PolygonData copy() {
        return new PolygonData(this.xPoints, this.yPoints, this.numPoints, this.color);
    }
}
