package GameObjects.PowerUps;

import Enviroment.Animation;
import Enviroment.Texture;
import Game.Game;
import GameObjects.GameObject;
import GameObjects.CoreGameObjects.ID;
import Handler.Handler;
import java.awt.*;

/**
 * Class for the collectible crystal. When crystal is collected, the player recieves more points.
 */
public class Crystal extends GameObject {
    private Handler handler;

    private Texture tex = Game.getInstance();

    private Animation starAnimation;

    /**
     * Sets the x and y coordinates, width and height, id and game object handler for this collectible
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of this collectible
     * @param height height of this collectible
     * @param id id - collectibleCrystal
     * @param handler game object handler
     */
    public Crystal(int x, int y, int width, int height, ID id, Handler handler) {
        super(x, y, width, height, id);
        this.handler = handler;
        starAnimation = new Animation(5, tex.crystalImages[0], tex.crystalImages[1], tex.crystalImages[2], tex.crystalImages[3]);
    }

    /**
     * Runs the crystal animation
     */
    @Override
    public void tick() {
        starAnimation.runAnimation();
    }


    /**
     * Renders the crystal animation
     * @param g graphics
     */
    @Override
    public void render(Graphics g) {
        starAnimation.drawAnimation(g, x, y);
    }
}
