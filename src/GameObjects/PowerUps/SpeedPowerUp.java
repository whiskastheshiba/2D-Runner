package GameObjects.PowerUps;

import Enviroment.Texture;
import Game.Game;
import GameObjects.CoreGameObjects.ID;
import GameObjects.GameObject;

import java.awt.*;

/**
 * The class for the speed power up. When picked up, the player runs quicker for a short duration
 */
public class SpeedPowerUp extends GameObject {

    private Texture tex = Game.getInstance();
    public SpeedPowerUp(int x, int y, int width, int height, ID id) {
        super(x, y, width, height, id);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tex.powerUpImages[2],x,y,null );
    }
}
