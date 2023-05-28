package Enviroment;

import java.awt.image.BufferedImage;

/**
 * Class used to get sprite images
 */
public class SpriteSheet {

    private BufferedImage image;

    /**
     * Sets the image to the desired image
     * @param image
     */
    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    /**
     * Grabs a sub image from the desired sprite sheet depending on rows and columns.
     * @param col column of desired image in the sprite sheet
     * @param row row of desired image in the sprite sheet
     * @param width width of sprite image
     * @param height height of sprite image
     * @return sprite image of desired width and height
     */
    public BufferedImage grabImage(int col, int row, int width, int height){
        BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);
        return img;
    }
}
