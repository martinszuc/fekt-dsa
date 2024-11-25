package com.martinszuc.polygen.utils;

import com.martinszuc.polygen.ga.Individual;
import com.martinszuc.polygen.ga.Polygon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
     * @param image The BufferedImage to save.
     * @param path  The file path where the image will be saved.
     */
    public static void saveImage(BufferedImage image, String path) {
        try {
            File outputFile = new File(path);
            outputFile.getParentFile().mkdirs(); // Ensure directories exist
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders an individual to a BufferedImage.
     *
     * @param individual The individual to render.
     * @param width      The width of the resulting image.
     * @param height     The height of the resulting image.
     * @return The rendered BufferedImage.
     */
    public static BufferedImage renderImage(Individual individual, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Clear the canvas with white background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Render each polygon
        for (Polygon polygon : individual.getPolygons()) {
            List<Point> vertices = polygon.getVertices();
            int n = vertices.size();
            int[] xPoints = new int[n];
            int[] yPoints = new int[n];
            for (int i = 0; i < n; i++) {
                xPoints[i] = vertices.get(i).x;
                yPoints[i] = vertices.get(i).y;
            }
            g.setColor(polygon.getColor());
            g.fillPolygon(xPoints, yPoints, n);
        }

        g.dispose();
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