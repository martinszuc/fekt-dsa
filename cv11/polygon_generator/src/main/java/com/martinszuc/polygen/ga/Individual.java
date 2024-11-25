package com.martinszuc.polygen.ga;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an individual in the population, consisting of multiple polygons.
 */
public class Individual {
    private final List<Polygon> polygons;
    private double fitness;
    private static final Random rand = new Random();

    /**
     * Initializes an Individual with a specified number of polygons.
     *
     * @param numPolygons Number of polygons in the individual.
     */
    public Individual(int numPolygons) {
        polygons = new ArrayList<>();
        for (int i = 0; i < numPolygons; i++) {
            polygons.add(new Polygon());
        }
    }

    /**
     * Gets the list of polygons.
     *
     * @return List of Polygon objects.
     */
    public List<Polygon> getPolygons() {
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
        for (Polygon polygon : polygons) {
            if (rand.nextDouble() < 0.1) { // 10% chance to mutate each polygon
                polygon.mutate();
            }
        }
    }

    /**
     * Creates a deep copy of the individual.
     *
     * @return A new Individual object with copied polygons.
     */
    public Individual copy() {
        Individual copy = new Individual(0);
        for (Polygon polygon : this.polygons) {
            Polygon newPolygon = new Polygon();
            // Copy vertices
            List<Point> originalVertices = polygon.getVertices();
            for (Point p : originalVertices) {
                newPolygon.getVertices().add(new Point(p.x, p.y));
            }
            // Copy color
            newPolygon.setColor(new Color(polygon.getColor().getRed(),
                    polygon.getColor().getGreen(),
                    polygon.getColor().getBlue(),
                    polygon.getColor().getAlpha()));
            copy.getPolygons().add(newPolygon);
        }
        return copy;
    }
}