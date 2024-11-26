package com.martinszuc.polygen;

import com.martinszuc.polygen.ga.GeneticAlgorithm;
import com.martinszuc.polygen.ga.Individual;
import com.martinszuc.polygen.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * Main class to run the genetic art application.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Load the target image
            BufferedImage targetImage = ImageUtils.loadImage("src/main/resources/input/target_image.png");
            if (targetImage == null) {
                System.err.println("Failed to load the target image.");
                return;
            }

            // Genetic algorithm parameters
            int populationSize = 100;   // Number of individuals in each generation
            int numPolygons = 50;       // Number of polygons per individual
            double mutationRate = 0.1;  // Mutation probability

            // Determine the number of available processors
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            System.out.println("Available processors: " + availableProcessors);

            // Initialize the genetic algorithm with thread pool size equal to available processors
            GeneticAlgorithm ga = new GeneticAlgorithm(targetImage, populationSize, numPolygons, mutationRate, availableProcessors);

            // Add shutdown hook to save the best individual upon termination
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nShutting down...");
                ga.stop();

                Individual best = ga.getBestIndividual();
                if (best != null) {
                    BufferedImage finalImage = ImageUtils.renderImage(best, 400, 400);
                    ImageUtils.saveImage(finalImage, "output/final_result.png");
                    System.out.println("Final image saved to output/final_result.png");
                } else {
                    System.err.println("No best individual found.");
                }
            }));

            // Start the evolution process in a separate thread
            Thread evolutionThread = new Thread(() -> {
                System.out.println("Evolution started. Press Ctrl+C to stop and save the best result.");
                ga.evolve(1000000); // Set a very high number to run indefinitely
            });
            evolutionThread.start();

            // Keep the main thread alive until the user interrupts
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); // Wait for user input (e.g., pressing Enter)
            ga.stop();

            // Wait for the evolution thread to finish
            evolutionThread.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
