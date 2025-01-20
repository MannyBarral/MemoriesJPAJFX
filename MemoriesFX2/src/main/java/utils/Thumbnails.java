package utils;

import domain.Picture;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.ApplicationSettings;

/**
 *
 * @author tl
 */
public class Thumbnails {

    private String appPath;

    /**
     * This method makes a thumbnail version of the picture pic. The method
     * should be called for all pictures as thay are imported. It creates a PNG
     * file that contains a small version of the original picture. The file is
     * written in the same folder as the picture.
     *
     * @param pic
     */
    public static void makeThumbnail(Picture pic) {
        File imageFile = new File(
                pic.getFilePath());
        // The code needs to be adapted here. The getAppPath method is a method
        // that returns the path of the image in the managedImagesFolder. It
        // should be a string like : YYYY/MM/DD/
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imageFile);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int thumbnailHeight = ApplicationSettings.ThumbnailHeight;
            int thumbnailWidth = (int) Math.round(
                    (1.0 * width) / height * thumbnailHeight);
            BufferedImage thumbnailImage
                    = resizeImage(bufferedImage,
                                  (int) thumbnailWidth,
                                  (int) thumbnailHeight);
            // Create a PNG image file
            String thumbnailFilename = thumbnailFilename(pic);
            ensureFolderExists(thumbnailFilename);
            File pngFile = new File(thumbnailFilename);
            // Write the buffered image to the PNG file
            try {
                ImageIO.write(thumbnailImage, "png", pngFile);
                System.out.println("Thumbnail created: " + pngFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Failed to create thumbnail: " + pngFile.getAbsolutePath());
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("imagefile:  " + imageFile);
            e.printStackTrace();
        }
    }

    /**
     * This method is used in the presentation layer to obtain the thumbnail
     * file. (see the setImage method in PictureView).
     *
     * @param pic
     * @return
     */
    public static String thumbnailFilename(Picture pic) {
        String filename = pic.getFilePath();
        filename = filename.substring(0, filename.lastIndexOf('.')) + ".png";
        return filename;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage,
                                             int newWidth, int newHeight) {
        // Create a new BufferedImage with the specified width and height
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight,
                                                       originalImage.getType());
        // Create a Graphics2D object from the new image
        Graphics2D g2d = resizedImage.createGraphics();
        // Set rendering hints to improve the quality of the resizing (optional)
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                             RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                             RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        // Create an AffineTransform to scale the image
        AffineTransform transform = AffineTransform.getScaleInstance(
                (double) newWidth / originalImage.getWidth(),
                (double) newHeight / originalImage.getHeight());
        // Apply the transformation to the image
        AffineTransformOp op = new AffineTransformOp(transform,
                                                     AffineTransformOp.TYPE_BILINEAR);
        op.filter(originalImage, resizedImage);
        // Dispose the Graphics2D object
        g2d.dispose();
        return resizedImage;
    }

    private static void ensureFolderExists(String path) {
        String directory = path.substring(
                ApplicationSettings.managedImagesFolder.length(),
                path.lastIndexOf('/'));
        String[] folders = directory.split("[/]");
        createSubdirectories(new File(ApplicationSettings.managedImagesFolder),
                             folders);
    }

    private static void createSubdirectories(File parentDirectory,
                                             String[] subdirectories) {
        for (String subdirectory : subdirectories) {
            File newDirectory = new File(parentDirectory, subdirectory);
            boolean created = newDirectory.mkdir();
            parentDirectory = new File(
                    parentDirectory.toString() + "/" + subdirectory);
        }
    }

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

}
