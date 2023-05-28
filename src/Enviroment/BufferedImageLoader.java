package Enviroment;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class for loading images
 */
public class BufferedImageLoader {
    private BufferedImage image;

    /**
     * Loads the specified image
     * @param path path to the image
     * @return BufferedImage object with the loaded image inside it
     */
    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    /**
     * Gets the width of the level. Calculated by counting the amount of platforms on the top line in a level image
     * @param path path to the level image
     * @return level width in pixels
     */
    public int getLevelWidth(String path){                     // Function to determine when the camera should stop
        try {
            image = ImageIO.read(getClass().getResource(path));
            int w = image.getWidth();
            int i =0;
            while(i < w){
                int pixel = image.getRGB(i, 0);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue =(pixel) & 0xff;
                if(red != 255 && green != 255 && blue != 255){
                    return -64 *i;
                }else{
                    i++;
                }
            }
            return w;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the height of the level. Calculated by counting the amount of platforms on the first right column.
     * @param path path to the level image
     * @return height of the level in pixels
     */
    public int getLevelHeight(String path){                     // Function to determine when the camera should stop
        try {
            image = ImageIO.read(getClass().getResource(path));
            int h = image.getHeight();
            int i =0;
            while(i < h){
                int pixel = image.getRGB(0, i);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue =(pixel) & 0xff;
                if(red != 255 && green != 255 && blue != 255){
                    return -64 *i;
                }else{
                    i++;
                }
            }
            return h;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
