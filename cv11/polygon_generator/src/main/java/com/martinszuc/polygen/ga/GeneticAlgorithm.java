package com.martinszuc.polygen.ga;

import com.martinszuc.polygen.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Manages the Genetic Algorithm for evolving individuals to approximate a target image.
 */
public class GeneticAlgorithm {
    private final BufferedImage targetImage;
    private final int populationSize;
    private final int numPolygons;
    private final double mutationRate;
    private final List<Individual> population;
    private Individual bestIndividual;
    private final Random rand = new Random();
    private final AtomicBoolean running = new AtomicBoolean(true);

    /**
     * Constructor to initialize the genetic algorithm parameters.
     *
     * @param targetImage   The image to approximate.
     * @param populationSize Number of individuals in the population.
     * @param numPolygons   Number of polygons per individual.
     * @param mutationRate  Mutation probability.
     */
    public GeneticAlgorithm(BufferedImage targetImage, int populationSize, int numPolygons, double mutationRate) {
        this.targetImage = targetImage;
        this.populationSize = populationSize;
        this.numPolygons = numPolygons;
        this.mutationRate = mutationRate;
        this.population = new ArrayList<>();
        initializePopulation();
    }

    /**
     * Initializes the population with random individuals.
     */
    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            Individual individual = new Individual(numPolygons);
            population.add(individual);
        }
    }

    /**
     * Evaluates the fitness of all individuals in the population.
     */
    private void evaluateFitness() {
        for (Individual individual : population) {
            BufferedImage generatedImage = ImageUtils.renderImage(individual, 400, 400);
            double mse = ImageUtils.calculateMSE(targetImage, generatedImage);
            double fitness = 1.0 / (mse + 1e-10); // Inverse MSE for fitness
            individual.setFitness(fitness);

            if (bestIndividual == null || individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual.copy();
                // Save intermediate image
                ImageUtils.saveImage(generatedImage, "output/intermediate.png");
                System.out.println("New best fitness: " + fitness);
            }
        }
    }

    /**
     * Selects an individual from the population using tournament selection.
     *
     * @return Selected Individual.
     */
    private Individual selectParent() {
        int tournamentSize = 5;
        List<Individual> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            Individual randomIndividual = population.get(rand.nextInt(populationSize));
            tournament.add(randomIndividual);
        }
        return tournament.stream().max(Comparator.comparingDouble(Individual::getFitness)).orElse(null);
    }

    /**
     * Performs crossover between two parents to produce two offspring.
     *
     * @param parent1 First parent.
     * @param parent2 Second parent.
     * @return List containing two offspring.
     */
    private List<Individual> crossover(Individual parent1, Individual parent2) {
        Individual offspring1 = parent1.copy();
        Individual offspring2 = parent2.copy();

        // Single-point crossover based on polygons
        int crossoverPoint = rand.nextInt(numPolygons);
        for (int i = crossoverPoint; i < numPolygons; i++) {
            offspring1.getPolygons().set(i, parent2.getPolygons().get(i).copy());
            offspring2.getPolygons().set(i, parent1.getPolygons().get(i).copy());
        }

        return Arrays.asList(offspring1, offspring2);
    }

    /**
     * Evolves the population for a specified number of generations.
     *
     * @param maxGenerations Maximum number of generations to evolve.
     */
    public void evolve(int maxGenerations) {
        for (int generation = 1; generation <= maxGenerations && running.get(); generation++) {
            System.out.println("Generation: " + generation);
            evaluateFitness();

            List<Individual> newPopulation = new ArrayList<>();

            while (newPopulation.size() < populationSize) {
                // Selection
                Individual parent1 = selectParent();
                Individual parent2 = selectParent();

                // Crossover
                List<Individual> offspring = crossover(parent1, parent2);

                // Mutation
                for (Individual child : offspring) {
                    if (rand.nextDouble() < mutationRate) {
                        child.mutate();
                    }
                    newPopulation.add(child);
                    if (newPopulation.size() >= populationSize) break;
                }
            }

            // Replace old population with new population
            population.clear();
            population.addAll(newPopulation);
        }
    }

    /**
     * Stops the evolution process.
     */
    public void stop() {
        running.set(false);
    }

    /**
     * Gets the best individual found.
     *
     * @return Best Individual.
     */
    public Individual getBestIndividual() {
        return bestIndividual;
    }
}