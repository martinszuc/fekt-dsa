package com.martinszuc.polygen;

import com.martinszuc.polygen.ga.GeneticAlgorithm;
import com.martinszuc.polygen.ga.Individual;
import com.martinszuc.polygen.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Main class to run the genetic art application.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            // Hardcoded paths and formats
            String targetImagePath = "src/main/resources/input/target_image.jpg"; // Change to your actual path
            String outputFormat = "png"; // Set to "png" or "jpg"

            // Load the target image
            BufferedImage targetImage = ImageUtils.loadImage(targetImagePath);
            if (targetImage == null) {
                logger.severe("Failed to load the target image.");
                return;
            }

            // Genetic algorithm parameters
            int populationSize = 100;   // Number of individuals in each generation
            int numPolygons = 50;       // Number of polygons per individual
            double mutationRate = 0.1;  // Mutation probability

            // Determine the number of available processors
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            logger.info("Available processors: " + availableProcessors);

            // Initialize the genetic algorithm with thread pool size equal to available processors
            GeneticAlgorithm ga = new GeneticAlgorithm(
                    targetImage,
                    populationSize,
                    numPolygons,
                    mutationRate,
                    availableProcessors,
                    outputFormat
            );

            // Add shutdown hook to save the best individual upon termination
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("\nShutting down...");
                ga.stop();
                ga.shutdownExecutors();

                Individual best = ga.getBestIndividual();
                if (best != null) {
                    BufferedImage finalImage = ImageUtils.renderImage(best, targetImage.getWidth(), targetImage.getHeight());
                    String finalFilename = "output/final_result." + outputFormat;
                    ImageUtils.saveImage(finalImage, finalFilename, outputFormat);
                    logger.info("Final image saved to " + finalFilename);
                } else {
                    logger.severe("No best individual found.");
                }
            }));

            // Start the evolution process in a separate thread
            Thread evolutionThread = new Thread(() -> {
                logger.info("Evolution started. Press Ctrl+C to stop and save the best result.");
                ga.evolve(1000000); // Set a very high number to run indefinitely
            });
            evolutionThread.start();

            // Keep the main thread alive until the user interrupts
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press Enter to stop the evolution and save the best result.");
            scanner.nextLine(); // Wait for user input (e.g., pressing Enter)
            ga.stop();

            // Wait for the evolution thread to finish
            evolutionThread.join();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during execution.", e);
        }
    }
}
