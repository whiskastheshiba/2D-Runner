package Enviroment;

import GameObjects.GameObject;
import GameObjects.CoreGameObjects.ID;
import Handler.*;
import Game.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Class for detecting keyboard input and executing actions depending on which key has been pressed or released.
 */
public class KeyInput extends KeyAdapter {

    public Handler handler;
    private Game game;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }

    /**
     * Checks which keys the user has pressed and edits the player's velocities.
     * @param e the event to be processed
     */
    public void keyPressed (KeyEvent e) {

        int key = e.getKeyCode();

        if(game.gameState == GameState.USERNAME_PROMPT){
            if(key == KeyEvent.VK_BACK_SPACE && game.userName.length() > 0){
                game.userName = game.userName.substring(0, game.userName.length() - 1);
            }else if(key != KeyEvent.VK_SHIFT && key != KeyEvent.VK_CAPS_LOCK && key != KeyEvent.VK_ESCAPE){
                if(game.userName.length() < 25){
                    game.userName = game.userName + e.getKeyChar();
                }
            }
        }else if(game.gameState == GameState.PLAYING){
            for(int i = 0;i < handler.object.size(); i++) {

                GameObject temp = handler.object.get(i);

                if(temp.getId() == ID.Player) {
                    if (key == KeyEvent.VK_D) {
                        temp.setVelX(temp.getSpeed());
                        if(!temp.moving){
                            temp.moving = true;
                        }
                    }

                    if (key == KeyEvent.VK_A) {
                        temp.setVelX(-1 * temp.getSpeed());
                        if(!temp.moving){
                            temp.moving = true;
                        }
                    }
                    if(key == KeyEvent.VK_SPACE && ! temp.isJumping() && ! temp.isFalling()){
                        temp.setVelY(-1* temp.getJumpHeight());
                        Game.sound.setFile(0);
                        Game.sound.play();
                        temp.setJumping(true);
                    }

                    if(key == KeyEvent.VK_ESCAPE){
                        game.gameState = GameState.PAUSED;
                    }
                }
            }
        }else if(game.gameState == GameState.PAUSED){
            if(key == KeyEvent.VK_ESCAPE){
                game.gameState = GameState.PLAYING;
                game.isPauseMenuActive = false;
            }
        }
    }
    /**
     * Checks which keys the user has released and edits the player's velocities.
     * @param e the event to be processed
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        for(int i = 0;i < handler.object.size(); i++) {

            GameObject temp = handler.object.get(i);

            if(temp.getId() == ID.Player){
                if(key == KeyEvent.VK_D){
                    temp.setVelX(0);
                    temp.moving = false;
                }

                if (key == KeyEvent.VK_A) {
                    temp.setVelX(0);
                    temp.moving = false;
                }
            }
        }
    }














}
