package com.martinszuc.polygen.ga;

import com.martinszuc.polygen.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages the Genetic Algorithm for evolving individuals to approximate a target image.
 */
public class GeneticAlgorithm {
    private final BufferedImage targetImage;
    private final int populationSize;
    private final int numPolygons;
    private final double mutationRate;
    private Individual[] population;
    private final AtomicReference<Individual> bestIndividual = new AtomicReference<>(null);
    private final Random rand = new Random();
    private final AtomicBoolean running = new AtomicBoolean(true);

    // ExecutorService for parallel processing
    private final ExecutorService executor;
    // ExecutorService for image saving
    private final ExecutorService imageSaverExecutor;

    private static final double FITNESS_IMPROVEMENT_THRESHOLD = 1e-4; // Adjusted threshold
    private double lastBestFitness = Double.MIN_VALUE; // Initialize to the smallest possible value

    private int generationCount = 0; // Track the generation number

    private static final Logger logger = Logger.getLogger(GeneticAlgorithm.class.getName());

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
        initializePopulation();
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        this.imageSaverExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * Initializes the population with random individuals.
     */
    private void initializePopulation() {
        this.population = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            population[i] = new Individual(numPolygons);
        }
    }

    /**
     * Evaluates the fitness of all individuals in the population using parallel processing.
     */
    private void evaluateFitness() {
        List<Callable<Void>> tasks = new ArrayList<>();

        for (Individual individual : population) {
            tasks.add(() -> {
                double fitness = calculateFitness(individual);
                individual.setFitness(fitness);

                // Update the best individual atomically
                bestIndividual.updateAndGet(currentBest -> {
                    if (currentBest == null || fitness > currentBest.getFitness()) {
                        // Check for significant improvement
                        if (currentBest == null || (fitness - lastBestFitness) > FITNESS_IMPROVEMENT_THRESHOLD) {
                            lastBestFitness = fitness;
                            saveIntermediateImage(individual, generationCount);
                            logger.info("New best fitness: " + fitness);
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
            logger.severe("Fitness evaluation was interrupted.");
        }
    }

    /**
     * Calculates the fitness of an individual based on the Mean Squared Error (MSE).
     *
     * @param individual The individual to evaluate.
     * @return The fitness value.
     */
    private double calculateFitness(Individual individual) {
        BufferedImage generatedImage = ImageUtils.renderImage(individual);
        double mse = ImageUtils.calculateMSE(targetImage, generatedImage);
        // Allow GC to reclaim the image
        generatedImage.flush();
        return 1.0 / (mse + 1e-10);
    }

    /**
     * Saves the intermediate image of the best individual.
     *
     * @param individual     The best individual.
     * @param generationCount The current generation count.
     */
    private void saveIntermediateImage(Individual individual, int generationCount) {
        imageSaverExecutor.submit(() -> {
            BufferedImage finalImage = ImageUtils.renderImage(individual);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String filename = String.format("output/intermediate_gen_%05d_%s.png", generationCount, timestamp);
            ImageUtils.saveImage(finalImage, filename);
            logger.info("Image saved to " + filename);
        });
    }

    /**
     * Selects an individual from the population using tournament selection.
     *
     * @return Selected Individual.
     */
    private Individual selectParent() {
        int tournamentSize = 5;
        Individual best = null;
        for (int i = 0; i < tournamentSize; i++) {
            Individual randomIndividual = population[rand.nextInt(populationSize)];
            if (best == null || randomIndividual.getFitness() > best.getFitness()) {
                best = randomIndividual;
            }
        }
        return best;
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
            offspring1.getPolygons()[i] = parent2.getPolygons()[i].copy();
            offspring2.getPolygons()[i] = parent1.getPolygons()[i].copy();
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
            generationCount = generation;
            logger.info("Generation: " + generation);
            evaluateFitness();

            // Sort population by fitness in descending order
            Arrays.sort(population, Comparator.comparingDouble(Individual::getFitness).reversed());

            // Retain top 10% as elites
            int eliteCount = (int) (populationSize * 0.1);
            Individual[] elites = Arrays.copyOfRange(population, 0, eliteCount);

            // Initialize new population with elites
            Individual[] newPopulation = new Individual[populationSize];
            System.arraycopy(elites, 0, newPopulation, 0, eliteCount);

            // Generate the rest of the new population
            int offspringIndex = eliteCount;
            while (offspringIndex < populationSize) {
                Individual parent1 = selectParent();
                Individual parent2 = selectParent();

                List<Individual> offspring = crossover(parent1, parent2);

                for (Individual child : offspring) {
                    // Mutation
                    if (rand.nextDouble() < mutationRate) {
                        child.mutate();
                    }

                    // Add to new population
                    if (offspringIndex < populationSize) {
                        newPopulation[offspringIndex++] = child;
                    }
                }
            }

            // Replace old population with new population
            population = newPopulation;

            // Log current best fitness
            Individual currentBest = population[0];
            logger.info("Current best fitness: " + currentBest.getFitness());
        }

        // Shutdown the executor services gracefully
        shutdownExecutors();
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

    /**
     * Shuts down the executor services gracefully.
     */
    public void shutdownExecutors() {
        executor.shutdown();
        imageSaverExecutor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    logger.severe("Executor did not terminate.");
            }
            if (!imageSaverExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                imageSaverExecutor.shutdownNow();
                if (!imageSaverExecutor.awaitTermination(60, TimeUnit.SECONDS))
                    logger.severe("Image Saver Executor did not terminate.");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            imageSaverExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
