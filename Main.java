import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    
    public static void main(String[] args) {
        String inputPath = "Convert-Images"; // Path to folder containing images to convert
        String outputPath = "Convert-Images-BW"; // Path to folder to save converted images
        convertImagesToBW(inputPath, outputPath); // Convert images in folder to black and white
        String inputFilePath = "Convert-Image/input.jpg"; // Path to image to convert
        String outputFilePath = "Convert-Image/output.jpg"; // Path to save converted image
        convertImageToBW(inputFilePath, outputFilePath); // Convert single image to black and white
    }
    
    // Convert all images in a folder to black and white
    public static void convertImagesToBW(String inputPath, String outputPath) {
        File inputFolder = new File(inputPath);
        File[] files = inputFolder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String outputFilePath = outputPath + "/" + file.getName();
                convertImageToBW(file.getPath(), outputFilePath);
            }
        }
    }
    
    // Convert a single image to black and white
    public static void convertImageToBW(String inputFilePath, String outputFilePath) {
        try {
            BufferedImage image = ImageIO.read(new File(inputFilePath));
            BufferedImage bwImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bwImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(bwImage, "jpg", new File(outputFilePath));
            System.out.println("Image converted to black and white: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error converting image to black and white: " + inputFilePath);
            e.printStackTrace();
        }
    }
}
