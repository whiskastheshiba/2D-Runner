package GameObjects.PowerUps;

import Enviroment.Texture;
import Game.Game;
import GameObjects.GameObject;
import GameObjects.CoreGameObjects.ID;
import Handler.Handler;

import java.awt.*;


/**
 * Class for the heal power up. When collected, it increases the amount of players health points
 */
public class HealPowerUp extends GameObject {
    private Texture tex = Game.getInstance();
    private Handler handler;

    public HealPowerUp(int x, int y, int width, int height, ID id, Handler handler) {
        super(x,y,width,height,id);
        this.handler = handler;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tex.powerUpImages[0], x, y, null);
    }
}
