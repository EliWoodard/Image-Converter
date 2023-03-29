import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The Image Converter program converts images to black and white, either for a
 * single image or all images in a specified folder. Progress can be displayed
 * using a progress bar. The program can also delete all files in the input and
 * output folders using the -delete command line argument.
 * 
 * @version 1.0
 * @author Eli Woodard
 */

public class Main {

    /**
     * The main method of the Image Converter program. Converts all images in a
     * specified input folder to black and white,
     * and saves them in a specified output folder. Progress can be displayed using
     * a progress bar. The program can also delete
     * all files in the input and output folders using the -delete command line
     * argument.
     *
     * @param args Command line arguments. The program accepts the -delete argument
     *             to delete all files in the input and output
     *             folders.
     */
    public static void main(String[] args) {
        String inputPath = "Convert-Images"; // Path to folder containing images to convert
        String outputPath = "Convert-Images-BW"; // Path to folder to save converted images
        String outputFormat = "jpg"; // Default output format
        boolean showProgressBar = true; // Set to true to show progress bar
        if (args.length > 0 && args[0].equals("-delete")) { // check if the argument is -delete
            deleteDirectory(new File(inputPath));
            deleteDirectory(new File(outputPath));
            System.out.println("All files deleted.");
        } else {
            convertImagesToBW(inputPath, outputPath, outputFormat, showProgressBar); // Convert images in folder to
                                                                                     // black and white
            // String inputFilePath = "Convert-Image/input.jpg"; // Path to image to convert
            // String outputFilePath = "Convert-Image/output.jpg"; // Path to save converted
            // image
            // convertImageToBW(inputFilePath, outputFilePath, outputFormat); // Convert
            // single image to black and white
        }
    }

    /**
     * Convert all images in a folder to black and white.
     *
     * @param inputPath       the path to the input folder containing images to
     *                        convert
     * @param outputPath      the path to the output folder to save converted images
     * @param outputFormat    the output format of the converted images
     * @param showProgressBar whether to display a progress bar during conversion
     */
    public static void convertImagesToBW(String inputPath, String outputPath, String outputFormat,
            boolean showProgressBar) {
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
                String outputFilePath = outputPath + "/" + getFileNameWithoutExtension(file.getName()) + "."
                        + outputFormat;
                convertImageToBW(file.getPath(), outputFilePath, outputFormat);
                numConverted++;
                if (showProgressBar && progressBar != null) {
                    progressBar.setValue(numConverted);
                }
            }
        }
        if (showProgressBar && frame != null) {
            JOptionPane.showMessageDialog(null, "Conversion complete!");
            frame.dispose();
        }
    }

    /**
     * Convert a single image to black and white.
     *
     * @param inputFilePath  the path to the input image to convert
     * @param outputFilePath the path to the output image to save
     * @param outputFormat   the output format of the converted image
     */
    public static void convertImageToBW(String inputFilePath, String outputFilePath, String outputFormat) {
        try {
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                System.out.println("Input file does not exist: " + inputFilePath);
                return;
            }
            BufferedImage image = ImageIO.read(inputFile);
            if (image == null) {
                System.out.println("Unsupported image format: " + inputFilePath);
                return;
            }
            BufferedImage bwImage = new BufferedImage(image.getWidth(), image.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bwImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(bwImage, outputFormat, new File(outputFilePath));
            System.out.println("Image converted to black and white: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error converting image to black and white: " + inputFilePath);
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the file without its extension.
     * 
     * @param fileName the name of the file
     * @return the name of the file without its extension
     */
    public static String getFileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(0, dotIndex);
        }
    }

    /**
     * Deletes all files in a directory.
     *
     * @param directory the directory to delete
     */
    public static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }
}
