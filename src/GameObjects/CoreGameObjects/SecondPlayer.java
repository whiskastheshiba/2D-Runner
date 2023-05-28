package GameObjects.CoreGameObjects;

import Enviroment.Animation;
import Enviroment.Texture;
import Game.Game;
import Handler.Handler;

import java.awt.*;


/**
 * The game object used in 1v1 game mode to display your opponent
 */
public class SecondPlayer extends Player{

    private Animation player2WalkRight;
    private Animation player2WalkLeft;

    /**
     * Sets the x and y coordinates, game object handler and game instance
     *
     * @param x       x coordinate
     * @param y       y coordinate
     * @param width   width of player
     * @param height  height of player
     * @param id      id - player
     * @param handler game object handler
     * @param game    instance of game class
     */

    /**
     * Gets the texture class instance which contains all textures and sprites for the game
     */
    private Texture tex = Game.getInstance();
    public SecondPlayer(int x, int y, int width, int height, ID id, Handler handler, Game game) {
        super(x, y, width, height, id, handler, game);

        player2WalkLeft = new Animation(4, tex.player2Images[7], tex.player2Images[8], tex.player2Images[9], tex.player2Images[10], tex.player2Images[11], tex.player2Images[12]);
        player2WalkRight = new Animation(4, tex.player2Images[1], tex.player2Images[2], tex.player2Images[3], tex.player2Images[4], tex.player2Images[5], tex.player2Images[6]);
    }

    /**
     * Updates the player2 class instance fields without rendering them
     */

    @Override
    public void tick(){
        x = game.client.secondPlayerX;
        y = game.client.secondPlayerY;
        velX = game.client.secondPlayerVelX;
        velY = game.client.secondPlayerVelY;
        if(velX != 0){
            if(velX < 0){
                player2WalkLeft.runAnimation();
            }else if(velX > 0){
                player2WalkRight.runAnimation();
            }
        }
    }

    /**
     * Renders the player2
     * @param g graphics
     */
    @Override
    public void render(Graphics g){

        //System.out.println(velX);
        if(velX != 0){
            if(velX < 0){
                player2WalkLeft.drawAnimation(g, x, y);

            }else if(velX > 0){
                player2WalkRight.drawAnimation(g, x, y);
            }
        }else{
            g.drawImage(tex.player2Images[0],x, y, null );
        }
    }
}
