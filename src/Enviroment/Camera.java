package Enviroment;
import Game.Game;
import GameObjects.GameObject;

/**
 * Class used for transforming and generating the level around the player so the player is always visible.
 */
public class Camera {

    /**
     * Cameras x and y coordinates.
     */
    private float x, y;

    /**
     * Sets the cameras x and y coordinates.
     * @param x cameras x coordinate
     * @param y cameras y coordinate
     */
    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the cameras x and y values depending on the players x and y coordinates.
     * The values are clamped so the user cannot see outside the generated level..
     * @param player Which player is the camera focusing on
     * @param levelWidth Width of the level to know, when to stop the camera from moving.
     * @param levelHeight Height of the level to know, when to stop the camera from moving.
     */

    public void tick(GameObject player, int levelWidth, int levelHeight){
        x = Game.clamp(-player.getX() + Game.WIDTH/2 + player.getWidth(), levelWidth + Game.WIDTH - 20, 0);
        y = Game.clamp(-player.getY() + Game.HEIGHT/ 2, levelHeight + Game.HEIGHT - 40,0);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
