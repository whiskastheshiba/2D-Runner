package GameObjects.PowerUps;

import Enviroment.Texture;
import Game.Game;
import GameObjects.CoreGameObjects.ID;
import GameObjects.GameObject;

import java.awt.*;

/**
 * Class for the max health power up. When picked up, the player increases the amount of health points he can have
 */
public class MaxHpPowerUp extends GameObject {

    private Texture tex = Game.getInstance();

    public MaxHpPowerUp(int x, int y, int width, int height, ID id) {
        super(x, y, width, height, id);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tex.powerUpImages[1],x,y,null);
    }
}
