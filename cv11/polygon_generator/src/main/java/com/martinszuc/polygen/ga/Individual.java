package com.martinszuc.polygen.ga;

import java.awt.*;
import java.util.Random;

/**
 * Represents an individual in the population, consisting of multiple polygons.
 */
public class Individual {
    private final PolygonData[] polygons;
    private double fitness;
    private static final Random rand = new Random();

    /**
     * Initializes an Individual with a specified number of polygons.
     *
     * @param numPolygons Number of polygons in the individual.
     */
    public Individual(int numPolygons) {
        this.polygons = new PolygonData[numPolygons];
        for (int i = 0; i < numPolygons; i++) {
            polygons[i] = generateRandomPolygonData();
        }
    }

    /**
     * Private constructor for creating a deep copy of an Individual.
     *
     * @param polygons Array of PolygonData to copy.
     */
    private Individual(PolygonData[] polygons) {
        this.polygons = new PolygonData[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            PolygonData pd = polygons[i];
            this.polygons[i] = new PolygonData(
                    pd.getXPoints(),
                    pd.getYPoints(),
                    pd.getNumPoints(),
                    pd.getColor()
            );
        }
    }

    /**
     * Generates random PolygonData.
     *
     * @return A new PolygonData object with random data.
     */
    private PolygonData generateRandomPolygonData() {
        // Create a temporary Polygon to generate random data
        Polygon tempPolygon = new Polygon();
        return tempPolygon.getPolygonData();
    }

    /**
     * Gets the array of PolygonData.
     *
     * @return Array of PolygonData objects.
     */
    public PolygonData[] getPolygons() {
        return polygons;
    }

    /**
     * Gets the fitness of the individual.
     *
     * @return Fitness value.
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Sets the fitness of the individual.
     *
     * @param fitness Fitness value to set.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Mutates the individual by mutating its polygons.
     */
    public void mutate() {
        for (PolygonData polygon : polygons) {
            if (rand.nextDouble() < 0.1) { // 10% chance to mutate each polygon
                mutatePolygon(polygon);
            }
        }
    }

    /**
     * Mutates a single PolygonData object.
     *
     * @param polygon The PolygonData to mutate.
     */
    private void mutatePolygon(PolygonData polygon) {
        // Decide whether to mutate vertices or color
        if (rand.nextBoolean()) {
            // Mutate a random vertex
            int vertexIndex = rand.nextInt(polygon.getNumPoints());
            int[] xPoints = polygon.getXPoints().clone();
            int[] yPoints = polygon.getYPoints().clone();

            int dx = rand.nextInt(21) - 10; // Change between -10 and +10
            int dy = rand.nextInt(21) - 10;

            xPoints[vertexIndex] = clamp(xPoints[vertexIndex] + dx, 0, 400);
            yPoints[vertexIndex] = clamp(yPoints[vertexIndex] + dy, 0, 400);

            // Update polygon data
            polygons[findPolygonIndex(polygon)].setXPoints(xPoints);
            polygons[findPolygonIndex(polygon)].setYPoints(yPoints);
        } else {
            // Mutate color
            Color color = polygon.getColor();
            int r = clamp(color.getRed() + rand.nextInt(21) - 10, 0, 255);
            int g = clamp(color.getGreen() + rand.nextInt(21) - 10, 0, 255);
            int b = clamp(color.getBlue() + rand.nextInt(21) - 10, 0, 255);
            int a = clamp(color.getAlpha() + rand.nextInt(21) - 10, 0, 255);

            // Update polygon data
            polygons[findPolygonIndex(polygon)].setColor(new Color(r, g, b, a));
        }
    }

    /**
     * Finds the index of a given PolygonData in the polygons array.
     *
     * @param polygon The PolygonData to find.
     * @return Index of the PolygonData, or -1 if not found.
     */
    private int findPolygonIndex(PolygonData polygon) {
        for (int i = 0; i < polygons.length; i++) {
            if (polygons[i] == polygon) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Clamps a value between min and max.
     *
     * @param value The value to clamp.
     * @param min   Minimum allowable value.
     * @param max   Maximum allowable value.
     * @return Clamped value.
     */
    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Creates a deep copy of the individual.
     *
     * @return A new Individual object with copied polygons.
     */
    public Individual copy() {
        return new Individual(this.polygons);
    }
}
