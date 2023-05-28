package GameObjects.Enemies;
import Enviroment.Animation;
import Enviroment.Texture;
import Game.Game;
import GameObjects.GameObject;
import GameObjects.CoreGameObjects.ID;
import Handler.Handler;
import java.awt.*;


/**
 * Class for the main enemy which walks back and forth
 */
public class BasicEnemy extends GameObject {


    /**
     * Moving range for this enemy
     */
    protected int movingrange;
    protected int startX;
    protected int startY;
    protected float gravity = 0.5f;
    protected Handler handler;

    private Texture tex = Game.getInstance();

    private Animation WalkRight;
    private Animation WalkLeft;


    /**
     * Sets x and y coordinates, id, moving range and damage the player takes if he collides with this enemy
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of enemy
     * @param height height of enemy
     * @param id id of enemy - basicEnemy
     * @param handler game object handler
     * @param movingrange moving range
     * @param damage damage
     */
    public BasicEnemy(int x, int y, int width, int height, ID id, Handler handler, int movingrange, int damage) {
        super(x, y, width, height, id);
        this.movingrange = movingrange;
        startX = x;
        startY = y;
        this.handler = handler;
        this.damage = damage;
        WalkLeft = new Animation(4, tex.basicEnemyImages[0],tex.basicEnemyImages[1],tex.basicEnemyImages[2],tex.basicEnemyImages[3]);
        WalkRight = new Animation(4, tex.basicEnemyImages[4],tex.basicEnemyImages[5],tex.basicEnemyImages[6],tex.basicEnemyImages[7]);
    }


    /**
     * Updates the Basic Enemy properties without rendering them
     */
    @Override
    public void tick() {
        moving();
        x += velX;
        y += velY;
        falling = true;
        if(jumping || falling){

            if(velY < 10){
                velY += gravity;
            }
        }

        if(velX != 0){
            if(velX < 0){
                WalkLeft.runAnimation();
            }else if(velX > 0){
                WalkRight.runAnimation();
            }
        }
        collision();
    }

    /**
     * Renders the Basic Enemy
     * @param g graphics
     */
    @Override
    public void render(Graphics g) {

        if(velX != 0){
            if(velX < 0){
                WalkLeft.drawAnimation(g, x, y, 64, 128);
            }else if(velX > 0){
                WalkRight.drawAnimation(g, x, y, 64, 128);
            }
        }else{
            g.drawImage(tex.basicEnemyImages[0], x, y,64, 128, null);
        }
    }

    /**
     * Updates the enemies velocity along the x axis depending on its moving range
     */
    public void moving(){
        if(velX == 0){
            setVelX(-3);
        }

        if(Math.abs(x -startX) >= movingrange){
            setVelX(-1 * velX);
        }
    }

    /**
     * Checks and updates properties depending on what the enemy is colliding
     */
    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject temp = handler.object.get(i);
            if(temp.getId() == ID.Platform){

                if(bottomCollision(temp.getBounds())){
                    velY = 0;
                    falling = false;
                    jumping = false;
                    y = temp.getY() - height + 1;
                }

                if(this.getBoundsRight().intersects(temp.getBounds())){
                    x = temp.getX() - width;
                    setVelX(-1 * velX);
                }

                if(this.getBoundsLeft().intersects(temp.getBounds())){
                    x = temp.getX() + temp.getWidth();
                    setVelX(-1 * velX);
                }
            }
        }

    }


    /**
     * Returns whether the enemy intersects a platform which is below it.
     * @param platform which platform we want to check
     * @return
     */
    public boolean bottomCollision(Rectangle platform){
        return platform.intersects(getBoundsBottom());
    }

    /**
     * Returns the bottom hit box of enemy
     * @return rectangle - bottom hit box
     */
    public Rectangle getBoundsBottom(){return new Rectangle(x + (width / 2) - (width/2)/2, y + (height / 2),width / 2, height / 2);}

    /**
     * Returns the top hit box of enemy
     * @return rectangle - top hit box
     */
    public Rectangle getBoundsTop(){
        return new Rectangle(x + (width / 2) - (width/2)/2, y,width/ 2, height/2);
    }

    /**
     * Returns the left hit box of enemy
     * @return rectangle - left hit box
     */
    public Rectangle getBoundsLeft(){
        return new Rectangle(x , y + height/4,width/4, height/2);
    }

    /**
     * Returns the right hit box of enemy
     * @return rectangle - right hit box
     */
    public Rectangle getBoundsRight(){
        return new Rectangle(x + width - width/4, y + height/4,width/4, height/2);
    }

}
