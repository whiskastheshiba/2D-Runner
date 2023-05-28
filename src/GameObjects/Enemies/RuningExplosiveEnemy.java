package GameObjects.Enemies;

import GameObjects.CoreGameObjects.ID;
import Handler.Handler;

public class RuningExplosiveEnemy extends ExplosiveEnemy{

    protected int acceleration;
    /**
     * sets x and y coordinates, width and height of this enemy, game object handler instance, moving range, damage, explosion range and acceleration for when the player enters this enemies range
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of enemy
     * @param height height of enemy
     * @param id id of enemy - explosive enemy
     * @param handler game object handler
     * @param movingrange moving range of enemy
     * @param damage damage on explosion
     * @param explosionRange range of the explosion
     * @param acceleration acceleration for when the player enters its range
     */
    public RuningExplosiveEnemy(int x, int y, int width, int height, ID id, Handler handler, int movingrange, int damage, int explosionRange, int acceleration) {
        super(x, y, width, height, id, handler, movingrange, damage, explosionRange);
        this.acceleration = acceleration;
    }

    /**
     * Updates the velocity x depending on if the player has enetered the range of the enemy
     */
    @Override
    public void moving(){
        if(velX == 0){
            setVelX(-1);
        }

        if(player != null  &&player.getX() - x > 0 && isActivated ){
            velX = acceleration;
        }
        else if(player != null && isActivated ){
            velX = -acceleration;
        }
        else if(Math.abs(x -startX) >= movingrange ){
            setVelX(-1 * velX);
        }
    }
}
