package com.martinszuc.polygen.utils;

import com.martinszuc.polygen.ga.Individual;
import com.martinszuc.polygen.ga.PolygonData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utility class for image loading, rendering, and fitness calculations.
 */
public class ImageUtils {

    /**
     * Loads an image from the specified path.
     *
     * @param path The file path to the image.
     * @return The loaded BufferedImage.
     * @throws IOException If the image cannot be read.
     */
    public static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    /**
     * Saves a rendered image to the specified path.
     *
     * @param image  The BufferedImage to save.
     * @param path   The file path where the image will be saved.
     * @param format Desired image format (e.g., "png", "jpg").
     */
    public static void saveImage(BufferedImage image, String path, String format) {
        try {
            File outputFile = new File(path);
            outputFile.getParentFile().mkdirs(); // Ensure directories exist
            ImageIO.write(image, format, outputFile);
            System.out.println("Image saved to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders an individual to a BufferedImage.
     *
     * @param individual The individual to render.
     * @param width      The width of the image.
     * @param height     The height of the image.
     * @return The rendered BufferedImage.
     */
    public static BufferedImage renderImage(Individual individual, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        try {
            // Clear the canvas with white background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // Enable anti-aliasing for better quality
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Render each polygon
            for (PolygonData polygon : individual.getPolygons()) {
                g.setColor(polygon.getColor());

                g.fillPolygon(polygon.getXPoints(), polygon.getYPoints(), polygon.getNumPoints());
            }
        } finally {
            g.dispose();
        }

        return image;
    }

    /**
     * Calculates the Mean Squared Error (MSE) between two images.
     *
     * @param img1 The first image.
     * @param img2 The second image.
     * @return The MSE value.
     */
    public static double calculateMSE(BufferedImage img1, BufferedImage img2) {
        double mse = 0.0;
        int width = img1.getWidth();
        int height = img1.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = rgb1 & 0xFF;

                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = rgb2 & 0xFF;

                mse += Math.pow(r1 - r2, 2);
                mse += Math.pow(g1 - g2, 2);
                mse += Math.pow(b1 - b2, 2);
            }
        }

        return mse / (width * height * 3); // Average over all pixels and channels
    }
}
