package GameObjects.Enemies;
import Enviroment.Texture;
import Game.Game;
import GameObjects.CoreGameObjects.ID;
import Handler.Handler;

import java.awt.*;

public class ShootingEnemy extends BasicEnemy{

    protected int side;
    protected int  projectileDamage;
    public int reloadTime = 240;
    public int reload;
    private Texture tex = Game.getInstance();

    /**
     * Sets x and y coordinates, width and height, moving range, damage, projectile damage and side, which the enemy is facing
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of enemy
     * @param height height of enemy
     * @param id id - shootingEnemy
     * @param handler game object handler
     * @param movingrange moving range - 0
     * @param damage damage
     * @param projectileDamage projectile damage
     * @param side side which this enemy is facing
     */

    public ShootingEnemy(int x, int y, int width, int height, ID id, Handler handler, int movingrange, int damage, int projectileDamage, int side) {
        super(x, y, width, height, id, handler, movingrange, damage);
        this.side = side;
        this.projectileDamage = projectileDamage;
    }


    /**
     * Updates the properties of this enemy without rendering the changes
     */
    @Override
    public void tick(){
        x += velX;
        y += velY;
        falling = true;
        if(jumping || falling){

            if(velY < 10){
                velY += gravity;
            }
        }
        collision();
        shoot();
    }

    /**
     * Renders the enemy
     * @param g graphics
     */
    @Override
    public void render(Graphics g){
        if(side > 0){
            g.drawImage(tex.shootingEnemyImages[0], x, y, 64, 128, null);
        }else{
            g.drawImage(tex.shootingEnemyImages[1], x, y, 64, 128, null);
        }
    }

    /**
     * Shoots the projectile from the shooting enemy
     */
    public void shoot(){
        if(reload <= 0){
            if(side >0){
                Projectile projectile = new Projectile(x + width,y + 40,32,16,ID.Projectile,handler,projectileDamage,side);
                reload = reloadTime;
                handler.addObject(projectile);
                Game.sound.playShot();
            }
            else {
                Projectile projectile = new Projectile(x -32,y + 40,32,16,ID.Projectile,handler,projectileDamage,side);
                reload = reloadTime;
                handler.addObject(projectile);
            }
        }
        else {
            reload -= 1;
        }
    }

}
