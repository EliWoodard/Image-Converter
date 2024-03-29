import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String inputPath = "input";
        String outputPath = "output";
        String overlayPath = "overlay";
        String outputFormat = "png"; //Change for each file format
        boolean showProgressBar = true;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to make images brighter/dimmer, convert to black and white, resize, or overlay? (black & white(1), brightness(2), resize(3), overlay(4))");
        String userChoice = scanner.nextLine().toLowerCase();

        if (userChoice.equals("1")) {
            convertImagesToBW(inputPath, outputPath, outputFormat, showProgressBar);
        } else if (userChoice.equals("2")) {
            System.out.println("Enter the percentage for brightness adjustment:");
            int brightnessPercentage = scanner.nextInt();
            adjustImageBrightness(inputPath, outputPath, outputFormat, brightnessPercentage, showProgressBar);
        } else if (userChoice.equals("3")) {
            System.out.println("Enter the new width for resizing:");
            int newWidth = scanner.nextInt();
            System.out.println("Enter the new height for resizing:");
            int newHeight = scanner.nextInt();
            resizeImages(inputPath, outputPath, outputFormat, newWidth, newHeight, showProgressBar);
        } else if (userChoice.equals("4")) {
            File overlayFolder = new File(overlayPath);
            File[] overlayFiles = overlayFolder.listFiles();
            if (overlayFiles != null && overlayFiles.length > 0) {
                File overlayImageFile = overlayFiles[0]; // Select the first overlay image
                overlayImages(inputPath, outputPath, overlayPath + "/" + overlayImageFile.getName(), outputFormat, showProgressBar);
            } else {
                System.out.println("No overlay image found in the 'overlay' folder.");
            }
        } else {
            System.out.println("Invalid choice. Please choose either 'b/w', 'brighter', or 'resize'.");
        }

        scanner.close();
    }

    public static void convertImagesToBW(String inputPath, String outputPath, String outputFormat, boolean showProgressBar) {
        File inputFolder = new File(inputPath);
        File[] files = inputFolder.listFiles();
        int numFiles = files.length;
        int numConverted = 0;
        JProgressBar progressBar = null;
        JFrame frame = null;
        if (showProgressBar) {
            progressBar = new JProgressBar(0, numFiles);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            frame = new JFrame("Converting Images");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(progressBar);
            frame.pack();
            frame.setVisible(true);
        }
        for (File file : files) {
            if (file.isFile()) {
                String outputFilePath = outputPath + "/" + getFileNameWithoutExtension(file.getName()) + "." + outputFormat;
                convertImageToBW(file.getPath(), outputFilePath, outputFormat);
                numConverted++;
                if (showProgressBar && progressBar != null) {
                    progressBar.setValue(numConverted);
                }
            }
        }
        if (showProgressBar && frame != null) {
            JOptionPane.showMessageDialog(frame, "Conversion complete!");
            frame.dispose();
        }
    }

    public static void adjustImageBrightness(String inputPath, String outputPath, String outputFormat, int brightnessPercentage, boolean showProgressBar) {
        File inputFolder = new File(inputPath);
        File[] files = inputFolder.listFiles();
        int numFiles = files.length;
        int numConverted = 0;
        JProgressBar progressBar = null;
        JFrame frame = null;
        if (showProgressBar) {
            progressBar = new JProgressBar(0, numFiles);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            frame = new JFrame("Adjusting Image Brightness");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(progressBar);
            frame.pack();
            frame.setVisible(true);
        }
        for (File file : files) {
            if (file.isFile()) {
                String outputFilePath = outputPath + "/" + getFileNameWithoutExtension(file.getName()) + "." + outputFormat;
                adjustBrightness(file.getPath(), outputFilePath, outputFormat, brightnessPercentage);
                numConverted++;
                if (showProgressBar && progressBar != null) {
                    progressBar.setValue(numConverted);
                }
            }
        }
        if (showProgressBar && frame != null) {
            JOptionPane.showMessageDialog(null, "Brightness adjustment complete!");
            frame.dispose();
        }
    }

    public static void resizeImages(String inputPath, String outputPath, String outputFormat, int newWidth, int newHeight, boolean showProgressBar) {
        File inputFolder = new File(inputPath);
        File[] files = inputFolder.listFiles();
        int numFiles = files.length;
        int numResized = 0;
        JProgressBar progressBar = null;
        JFrame frame = null;
        if (showProgressBar) {
            progressBar = new JProgressBar(0, numFiles);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            frame = new JFrame("Resizing Images");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(progressBar);
            frame.pack();
            frame.setVisible(true);
        }
        for (File file : files) {
            if (file.isFile()) {
                String outputFilePath = outputPath + "/" + getFileNameWithoutExtension(file.getName()) + "." + outputFormat;
                resizeImage(file.getPath(), outputFilePath, outputFormat, newWidth, newHeight);
                numResized++;
                if (showProgressBar && progressBar != null) {
                    progressBar.setValue(numResized);
                }
            }
        }
        if (showProgressBar && frame != null) {
            JOptionPane.showMessageDialog(null, "Image resizing complete!");
            frame.dispose();
        }
    }

    public static void resizeImage(String inputFilePath, String outputFilePath, String outputFormat, int newWidth, int newHeight) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(inputFilePath));
            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
            Graphics2D g = outputImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
            g.dispose();
            ImageIO.write(outputImage, outputFormat, new File(outputFilePath));
            System.out.println("Image resized: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error resizing image: " + inputFilePath);
            e.printStackTrace();
        }
    }

    public static void adjustBrightness(String inputFilePath, String outputFilePath, String outputFormat, int brightnessPercentage) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(inputFilePath));
            float scaleFactor = brightnessPercentage / 100f;

            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());

            for (int y = 0; y < inputImage.getHeight(); y++) {
                for (int x = 0; x < inputImage.getWidth(); x++) {
                    Color c = new Color(inputImage.getRGB(x, y));

                    int r = (int) Math.max(Math.min(scaleFactor * c.getRed(), 255), 0);
                    int g = (int) Math.max(Math.min(scaleFactor * c.getGreen(), 255), 0);
                    int b = (int) Math.max(Math.min(scaleFactor * c.getBlue(), 255), 0);

                    Color newColor = new Color(r, g, b);
                    outputImage.setRGB(x, y, newColor.getRGB());
                }
            }

            ImageIO.write(outputImage, outputFormat, new File(outputFilePath));
            System.out.println("Image brightness adjusted: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error adjusting image brightness: " + inputFilePath);
            e.printStackTrace();
        }
    }

    public static String getFileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(0, dotIndex);
        } else {
            return fileName;
        }
    }

    public static void convertImageToBW(String inputFilePath, String outputFilePath, String outputFormat) {
        try {
            BufferedImage original = ImageIO.read(new File(inputFilePath));
            BufferedImage bw = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g = bw.createGraphics();
            g.drawImage(original, 0, 0, null);
            g.dispose();
            ImageIO.write(bw, outputFormat, new File(outputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void overlayImages(String inputPath, String outputPath, String overlayPath, String outputFormat, boolean showProgressBar) {
        File inputFolder = new File(inputPath);
        File[] files = inputFolder.listFiles();
        BufferedImage overlayImage;
        try {
            overlayImage = ImageIO.read(new File(overlayPath));
        } catch (IOException e) {
            System.out.println("Error loading overlay image.");
            return;
        }
        int numFiles = files.length;
        int numOverlaid = 0;
        JProgressBar progressBar = null;
        JFrame frame = null;
        if (showProgressBar) {
            progressBar = new JProgressBar(0, numFiles);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            frame = new JFrame("Overlaying Images");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(progressBar);
            frame.pack();
            frame.setVisible(true);
        }
        for (File file : files) {
            if (file.isFile()) {
                String outputFilePath = outputPath + "/" + getFileNameWithoutExtension(file.getName()) + "." + outputFormat;
                overlayImage(file.getPath(), outputFilePath, overlayImage, outputFormat);
                numOverlaid++;
                if (showProgressBar && progressBar != null) {
                    progressBar.setValue(numOverlaid);
                }
            }
        }
        if (showProgressBar && frame != null) {
            JOptionPane.showMessageDialog(null, "Image overlay complete!");
            frame.dispose();
        }
    }

    public static void overlayImage(String inputFilePath, String outputFilePath, BufferedImage overlayImage, String outputFormat) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(inputFilePath));
            if (originalImage.getWidth() != overlayImage.getWidth() || originalImage.getHeight() != overlayImage.getHeight()) {
                overlayImage = resizeImage(overlayImage, originalImage.getWidth(), originalImage.getHeight());
            }
            BufferedImage combined = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = combined.getGraphics();
            g.drawImage(originalImage, 0, 0, null);
            g.drawImage(overlayImage, 0, 0, null);
            ImageIO.write(combined, outputFormat, new File(outputFilePath));
        } catch (IOException e) {
            System.out.println("Error overlaying image: " + inputFilePath);
            e.printStackTrace();
        }
    }

    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
