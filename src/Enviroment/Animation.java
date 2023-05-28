package Enviroment;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * The class used for running and rendering animations by changing sprites.
 */
public class Animation {
    private int speed;
    private int frames;

    private int index = 0;
    private int count = 0;

    private BufferedImage[] images;
    private BufferedImage currentImg;

    /**
     * Creates the animation by compiling the necessary sprite in to an array
     * @param speed how often is the sprite changed
     * @param args array of animation sprites
     */
    public Animation(int speed, BufferedImage... args) {
        this.speed = speed;
        this.images = new BufferedImage[args.length];
        for(int i = 0; i < args.length; i++){
            images[i] = args[i];
        }
        frames = args.length;
    }

    /**
     * Checks if it is already time to change the sprite image to the next one.
     */
    public void runAnimation(){
        index++;
        if(index > speed){
            index = 0;
            nextFrame();
        }
    }

    /**
     * replaces the animation sprite to the next one. If it gets to the last sprite, it continues with the first one.
     */
    private void nextFrame(){
        for(int i = 0; i < frames; i++){
            if(count == i){
                currentImg = images[i];
            }
        }
        count++;

        if(count > frames){
            count = 0;
        }
    }

    /**
     * Renders the sprite image
     * @param g Graphics
     * @param x x coordinate of images top left corner
     * @param y y coordinate of images top left corner
     */
    public void drawAnimation(Graphics g, int x, int y){
        g.drawImage(currentImg, x, y, null);
    }
    /**
     * Renders the sprite image using scaling
     * @param g Graphics
     * @param x x coordinate of images top left corner
     * @param y y coordinate of images top left corner
     * @param scaleX  how wide should the image be
     * @param scaleY how high should the image be
     */
    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
        g.drawImage(currentImg, x, y, scaleX, scaleY,  null);
    }
}
