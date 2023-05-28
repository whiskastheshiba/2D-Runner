package GameObjects.CoreGameObjects;
import Enviroment.*;
import GameObjects.GameObject;
import Handler.*;
import java.awt.*;
import java.sql.SQLException;

import Game.*;
import GameObjects.*;


/**
 * Player class. The player can be controlled by the user
 */
public class Player extends GameObject {
    /**
     * Handler class instance used for iterating through its object list and checking if the player is colliding with different game objects
     */
    private Handler handler;

    /**
     * Gravity variable, which specifiers how quick / slow the player should fall and how high he can jump.
     */
    private float gravity = 0.5f;

    /**
     * Maximum health of player
     */
    public int maxhp= 100;

    /**
     * used to measure the time before the player can get attacked by an enemy again.
     */
    public int attacked = 0;

    /**
     *
     */
    private boolean leftbound = true;
    private GameObject intersactedEnemy;

    private int crystalsCollected = 0;
    private int speedTimer = -1;
    private int jumpTimer = -1;
    public boolean isPlayingRun = false;

    protected Game game;

    private Texture tex = Game.getInstance();

    private Animation playerWalkRight;
    private Animation playerWalkLeft;

    private Animation standingStillDamage;
    private Animation runningRightDamage;
    private Animation runningLeftDamage;

    private Animation jumpingRightDamage;

    private Animation jumpingLeftDamage;

    private long startTime, elapsedTime;


    /**
     * Sets the x and y coordinates, game object handler and game instance
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of player
     * @param height height of player
     * @param id id - player
     * @param handler game object handler
     * @param game instance of game class
     */
    public Player(int x, int y, int width, int height, ID id, Handler handler, Game game){
        super(x, y,width, height, id);
        this.handler = handler;
        speed = 5;
        jumpHeight = 15;
        playerWalkLeft = new Animation(4, tex.playerImages[7], tex.playerImages[8], tex.playerImages[9], tex.playerImages[10], tex.playerImages[11], tex.playerImages[12]);
        playerWalkRight = new Animation(4, tex.playerImages[1], tex.playerImages[2], tex.playerImages[3], tex.playerImages[4], tex.playerImages[5], tex.playerImages[6]);
        standingStillDamage = new Animation(4, tex.playerImages[15], tex.playerImages[0]);
        runningRightDamage = new Animation(4, tex.playerImages[1], tex.playerImages[17], tex.playerImages[3], tex.playerImages[19], tex.playerImages[5], tex.playerImages[21]);
        runningLeftDamage = new Animation(4, tex.playerImages[7], tex.playerImages[23], tex.playerImages[9], tex.playerImages[25], tex.playerImages[11], tex.playerImages[27]);
        jumpingRightDamage = new Animation(4, tex.playerImages[28], tex.playerImages[13]);
        jumpingLeftDamage = new Animation(4, tex.playerImages[29], tex.playerImages[14]);
        startTime = System.currentTimeMillis();
        this.currenthp = maxhp;
        this.game = game;
    }

    /**
     * Updates the players properties.
     *
     */
    @Override
    public void tick() {
        x += velX;
        y += velY;
        falling = true;
        if(speedTimer < 0){
            speed = 5;
        }
        else if(speedTimer > -2){
            speedTimer -= 1;
        }
        if(jumpTimer < 0){
            jumpHeight = 15;
        }
        else if(jumpTimer > -2){
            jumpTimer -= 1;
        }
        if(jumping || falling){

            if(velY < 10){
                velY += gravity;
            }
        }
        if (attacked > 0){
            attacked  = attacked-1;
        }
        if(exploded > 0){
            explosion();
        }
        collision();

        if(attacked > 0){
            if(jumping || falling){
                if(velX >= 0){
                    jumpingRightDamage.runAnimation();
                }else{
                    jumpingLeftDamage.runAnimation();
                }
            }else{
                if(velX == 0){
                    standingStillDamage.runAnimation();
                }else if(velX > 0){
                    runningRightDamage.runAnimation();
                }else{
                    runningLeftDamage.runAnimation();
                }
            }
        }else{
            if(jumping || falling){

            }else{
                if(velX != 0){
                    if(velX < 0){
                        playerWalkLeft.runAnimation();
                    }else if(velX > 0){
                        playerWalkRight.runAnimation();
                    }
                }
            }
        }



        if(moving && !isPlayingRun && !isFalling() && !isJumping()){
            Game.runSound.playMove();
            isPlayingRun = true;
        }
        if((!moving  || isFalling() || isJumping())&& isPlayingRun){
            Game.runSound.stop();
            isPlayingRun = false;
        }

        if(currenthp <= 0){
            Game.sound.playDie();
            game.gameState = GameState.PLAYER_DIED;
            Game.logger.info("Player died");
        }

        if(game.isInMultiplayer){
            game.client.sendCoordinates(x,y,(int)velX, (int)velY);
        }

    }

    /**
     * Runs through the game object handler's list of all game objects and checks if the player has collided with any of them.
     * Updates the players properties depending on which other game objects the player has collided with
     */
    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject temp = handler.object.get(i);
            if(temp.getId() == ID.Platform){
                if(this.getBoundsTop().intersects(temp.getBounds())){
                    velY = 0;
                    y = temp.getY() + temp.getHeight();
                }

                if(bottomCollision(temp.getBounds())){
                    velY = 0;
                    falling = false;
                    jumping = false;
                    y = temp.getY() - height + 1;
                }

                if(this.getBoundsRight().intersects(temp.getBounds())){
                    x = temp.getX() - width;
                }

                if(this.getBoundsLeft().intersects(temp.getBounds())){
                    x = temp.getX() + temp.getWidth();
                }
            }
            else if(temp.getId() == ID.BasicEnemy ){
                if(this.bottomCollision(temp.getBounds()) && attacked <=0 && leftbound){
                    handler.removeObject(temp);
                    Game.sound.playEnemyDies();
                    temp = null;
                    Game.logger.info("Player killed a basic enemy");
                } else if(this.getBounds().intersects(temp.getBounds()) ){
                    if(attacked <=0){
                        currenthp -= temp.getDamage();
                        Game.sound.playDamage();
                        attacked = 80;
                    }
                    leftbound  = false;
                    intersactedEnemy = temp;
                    Game.logger.info("Player was attacked by a basic enemy");
                }else if (intersactedEnemy == temp){
                    leftbound = true;
                }

            }
            else if(temp.getId() == ID.Projectile){
                if(this.getBounds().intersects(temp.getBounds())){
                    if(attacked <=0){
                        currenthp -= temp.getDamage();
                        Game.sound.playDamage();
                        handler.removeObject(temp);
                        Game.logger.info("Player was shot by a projectile");
                        attacked = 80;
                    }
                }
            }
            else if (temp.getId() == ID.ExplosiveEnemy){
                if(temp.getExploded() > 0){
                    currenthp -= temp.getDamage();
                    Game.sound.playDamage();
                    Game.logger.info("Player was in the range of an explosive enemy");
                    attacked = 80;
                }
            }else if(temp.getId() == ID.FinishLine){
                if(getBoundsLeft().intersects(temp.getBounds())){
                    elapsedTime = System.currentTimeMillis() - startTime;
                    long seconds = elapsedTime / 1000;
                    long miliSeconds = elapsedTime % 1000;
                    int score = game.clamp((int) (999999999 / elapsedTime * 100) + crystalsCollected * 1000, 0, 999999999);
                    try {
                        mySqlDatabase.saveLevelResult(game.levelname, score, Integer.toString(game.userId));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if(game.isInMultiplayer){
                        game.client.finishedGame();
                        game.client.sendScore(Integer.toString(score));
                    }else {
                        Game.music.stop();
                        Game.sound.playVictory();
                    }

                    game.infoAboutScore = "Time: " + seconds + "." + miliSeconds + ", " + crystalsCollected + " / 3 crystals collected. Score is: " + score;
                    game.emptyHandler();
                    game.gameState = GameState.LEVEL_FINISHED;
                    game.isMenuGenerated = false;
                    Game.logger.info("Player finished the level" + game.infoAboutScore);
                }
            }else if(temp.getId() == ID.Crystal){
                if(getBounds().intersects(temp.getBounds())){
                    handler.removeObject(temp);
                    crystalsCollected++;
                    Game.sound.playCrystal();
                    Game.logger.info("Player collected a crystal");
                }
            }else if(temp.getId() == ID.HealPowerUp){
                if(getBounds().intersects(temp.getBounds())){
                    currenthp = Game.clamp(currenthp+25, 0, maxhp);
                    handler.removeObject(temp);
                    Game.sound.playHeal();
                    Game.logger.info("Player healed using the heal power up");
                }
            }else if(temp.getId() == ID.MaxHpPowerUp){
                if(getBounds().intersects(temp.getBounds())){
                    maxhp += 50;
                    currenthp = Game.clamp((int)(currenthp * ((float)maxhp/(float)(maxhp-50))),0,maxhp);
                    handler.removeObject(temp);
                    Game.sound.playMaxHp();
                    Game.logger.info("Player collected the max health power up");
                }
            }else  if(temp.getId() == ID.SpeedPowerUp){
                if(getBounds().intersects(temp.getBounds())){
                    speed *= 2;
                    speedTimer = 300;
                    handler.removeObject(temp);
                    Game.sound.playSpeedBoost();
                    Game.logger.info("Player collected the speed power up");
                }
            }else if(temp.getId() == ID.JumpPowerUp){
                if(getBounds().intersects(temp.getBounds())){
                    jumpHeight *= 2;
                    jumpTimer = 180;
                    handler.removeObject(temp);
                    Game.sound.playJumpBoost();
                    Game.logger.info("Player collected the jump power up");
                }
            }
        }
    }

    /**
     * Checks if the players bottom hit box has collided with a platform
     * @param platform platform
     * @return true if has collided, false if has not
     */
    public boolean bottomCollision(Rectangle platform){
        return platform.intersects(getBoundsBottom());
    }

    /**
     * Returns the player's bottom hit box
     * @return rectangle
     */
    public Rectangle getBoundsBottom(){
        return new Rectangle(x + (width / 2) - (width/2)/2, y + (height / 2),width / 2, height / 2);
    }
    /**
     * Returns the player's top hit box
     * @return rectangle
     */
    public Rectangle getBoundsTop(){
        return new Rectangle(x + (width / 2) - (width/2)/2, y,width/ 2, height/2);
    }
    /**
     * Returns the player's left hit box
     * @return rectangle
     */
    public Rectangle getBoundsLeft(){
        return new Rectangle(x , y + 5,10, height-10);
    }
    /**
     * Returns the player's right hit box
     * @return rectangle
     */
    public Rectangle getBoundsRight(){
        return new Rectangle(x + width - 10, y + 5,10, height-10);
    }

    /**
     * Renders the player depending on if he is moving, standing, jumping and so on
     * @param g graphics
     */
    @Override
    public void render(Graphics g) {

        if(attacked > 0){
            if(jumping || falling){
                if(velX >= 0){
                    jumpingRightDamage.drawAnimation(g, x, y);
                }else{
                    jumpingLeftDamage.drawAnimation(g, x, y);
                }
            }else{
                if(velX == 0){
                    standingStillDamage.drawAnimation(g, x , y);
                }else if(velX > 0){
                    runningRightDamage.drawAnimation(g, x, y);
                }else{
                    runningLeftDamage.drawAnimation(g, x, y);
                }
            }
        }else{
            if(jumping || falling){
                if(velX != 0) {
                    if (velX < 0) {
                        g.drawImage(tex.playerImages[14], x, y, null);
                    } else if (velX > 0) {
                        g.drawImage(tex.playerImages[13], x, y, null);
                    }
                }else{
                    g.drawImage(tex.playerImages[13], x, y, null);
                }
            }else{
                if(velX != 0){
                    if(velX < 0){
                        playerWalkLeft.drawAnimation(g, x, y);
                    }else if(velX > 0){
                        playerWalkRight.drawAnimation(g, x, y);
                    }
                }else{
                    g.drawImage(tex.playerImages[0],x, y, null );
                }
            }
        }
    }


    /**
     * Gets the current health of player
     * @return int health points
     */
    public int getCurrenthp() {
        return currenthp;
    }

    /**
     * Deals the player damage if he was in the range of an explosive enemy at the moment of it exploding
     */
    public void explosion(){
        currenthp = currenthp - exploded;
        exploded = 0;
    }
}
