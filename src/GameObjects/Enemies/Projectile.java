package GameObjects.Enemies;
import Enviroment.Texture;
import Game.Game;
import GameObjects.GameObject;
import GameObjects.CoreGameObjects.ID;
import Handler.Handler;
import java.awt.*;

/**
 * Class for the shooting enemies projectile
 */
public class Projectile extends GameObject {

    /**
     * Which side is the bullet flying to
     */
    private int side;
    protected Handler handler;
    private Texture tex = Game.getInstance();

    /**
     * Sets the x and y coordinates, width and height of the projectile, id, game object handler, damage and side (-1 if left, 1 if right)
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of projectile
     * @param height height of projectile
     * @param id id - projectile
     * @param handler game object handler
     * @param damage damage
     * @param side which side it is flying to
     */
    public Projectile(int x, int y, int width, int height, ID id, Handler handler, int damage, int side) {
        super(x, y, width, height, id);
        this.side = side;
        this.damage = damage;
        this.handler = handler;

    }


    /**
     * Updates the x coordinates of projectile without rendering the changes
     */
    @Override
    public void tick(){
        setX(getX() + 5 * side);
        collision();
    }

    /**
     * Renders the projectile
     * @param g graphics
     */
    @Override
    public void render(Graphics g){
        g.drawImage(tex.projectile, x, y, 32, 32,null);
    }


    /**
     * Checks the projectiles collisions with other game objects and removes it from the game if it collides
     */
    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject temp = handler.object.get(i);
            if(temp.getId() == ID.Platform && temp.getBounds().intersects(this.getBounds())){
                handler.removeObject(this);
            }
        }
    }

}
