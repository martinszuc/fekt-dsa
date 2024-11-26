package com.martinszuc.polygen.ga;

import com.martinszuc.polygen.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Manages the Genetic Algorithm for evolving individuals to approximate a target image.
 */
public class GeneticAlgorithm {
    private final BufferedImage targetImage;
    private final int populationSize;
    private final int numPolygons;
    private final double mutationRate;
    private List<Individual> population;
    private final AtomicReference<Individual> bestIndividual = new AtomicReference<>(null);
    private final Random rand = new Random();
    private final AtomicBoolean running = new AtomicBoolean(true);

    // ExecutorService for parallel processing
    private final ExecutorService executor;

    /**
     * Constructor to initialize the genetic algorithm parameters.
     *
     * @param targetImage    The image to approximate.
     * @param populationSize Number of individuals in the population.
     * @param numPolygons    Number of polygons per individual.
     * @param mutationRate   Mutation probability.
     * @param threadPoolSize Number of threads to use for parallel processing.
     */
    public GeneticAlgorithm(BufferedImage targetImage, int populationSize, int numPolygons, double mutationRate, int threadPoolSize) {
        this.targetImage = targetImage;
        this.populationSize = populationSize;
        this.numPolygons = numPolygons;
        this.mutationRate = mutationRate;
        this.population = Collections.synchronizedList(new ArrayList<>());
        initializePopulation();
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
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
     * Evaluates the fitness of all individuals in the population using parallel processing.
     */
    private static final double FITNESS_IMPROVEMENT_THRESHOLD = 1e-6; // Adjust as needed
    private double lastBestFitness = Double.MIN_VALUE; // Initialize to the smallest possible value

    private void evaluateFitness() {
        List<Callable<Void>> tasks = new ArrayList<>();

        for (Individual individual : population) {
            tasks.add(() -> {
                BufferedImage generatedImage = ImageUtils.renderImage(individual, 400, 400);
                double mse = ImageUtils.calculateMSE(targetImage, generatedImage);
                double fitness = 1.0 / (mse + 1e-10); // Inverse MSE for fitness
                individual.setFitness(fitness);

                // Update the best individual atomically
                bestIndividual.updateAndGet(currentBest -> {
                    if (currentBest == null || individual.getFitness() > currentBest.getFitness()) {
                        // Check for significant improvement
                        if (currentBest == null || (fitness - lastBestFitness) > FITNESS_IMPROVEMENT_THRESHOLD) {
                            lastBestFitness = fitness;
                            // Save intermediate image
                            ImageUtils.saveImage(generatedImage, "output/intermediate.png");
                            System.out.println("New best fitness: " + fitness);
                            return individual.copy();
                        }
                    }
                    return currentBest;
                });
                return null;
            });
        }

        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Fitness evaluation was interrupted.");
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

            List<Individual> newPopulation = Collections.synchronizedList(new ArrayList<>());

            List<Callable<Void>> crossoverTasks = new ArrayList<>();

            while (newPopulation.size() < populationSize) {
                // Selection
                Individual parent1 = selectParent();
                Individual parent2 = selectParent();

                // Crossover
                List<Individual> offspring = crossover(parent1, parent2);

                // Mutation and add to new population
                for (Individual child : offspring) {
                    crossoverTasks.add(() -> {
                        if (rand.nextDouble() < mutationRate) {
                            child.mutate();
                        }
                        synchronized (newPopulation) {
                            newPopulation.add(child);
                        }
                        return null;
                    });
                    if (newPopulation.size() >= populationSize) break;
                }
            }

            try {
                executor.invokeAll(crossoverTasks);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Crossover and mutation were interrupted.");
            }

            // Replace old population with new population
            population = newPopulation;
        }

        // Shutdown the executor service gracefully
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Executor did not terminate.");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
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
        return bestIndividual.get();
    }
}
