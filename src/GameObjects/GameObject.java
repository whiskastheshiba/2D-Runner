package GameObjects;
import GameObjects.CoreGameObjects.ID;

import java.awt.*;

/**
 * Base class for all game objects
 */
public abstract class GameObject {
    /**
     * x and y coordinates for the game object
     */
    protected int x, y;

    /**
     * ID of the game object (from GameObjectID enumeration)
     */
    protected ID id;
    /**
     * Velocity x and y for the game object
     */
    protected float velX, velY;

    /**
     * Width and height of the game object
     */
    protected int width, height;

    /**
     * Boolean which determines if the game object is falling
     */
    protected boolean falling = true;

    /**
     * Boolean which determines if the game object is jumping
     */
    protected boolean jumping = false;

    /**
     * Damage the game object deals to the player
     */
    protected int damage;

    /**
     * Variable which is used to determine if the enemy has already exploded
     */
    protected int exploded = 0;

    /**
     * Speed for the player if he collects the speed power up
     */
    protected int speed;

    /**
     * Jump height for the player if he collects the jump power up
     */
    protected int jumpHeight;

    /**
     * Boolean which determines if the game object is moving
     */
    public boolean moving = false;

    /**
     * Current health points of the game object
     */
    protected int currenthp;

    /**
     * Sets x and y coordinates for the game oibject
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of the game object
     * @param height height of the game object
     * @param id id of the game object
     */

    public GameObject(int x, int y, int width, int height, ID id){
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height;
    }

    /**
     * Updates the game objects properties without updating it
     */
    public abstract void tick();

    /**
     * Renders the game object
     * @param g graphics
     */
    public abstract void render(Graphics g);


    /**
     * Gets the bounds (outer edges) for the game object
     * @return Rectangle (hit box)
     */
    public Rectangle getBounds(){
        return new Rectangle(x , y ,width, height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void setId(ID id) {
        this.id = id;
    }


    public ID getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getExploded() {
        return exploded;
    }

    public void setExploded(int exploded) {
        this.exploded = exploded;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public int getCurrenthp() {
        return currenthp;
    }

    public void setCurrenthp(int currenthp) {
        this.currenthp = currenthp;
    }
}
