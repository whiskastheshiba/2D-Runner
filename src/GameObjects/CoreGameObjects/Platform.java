package GameObjects.CoreGameObjects;
import Enviroment.Texture;
import Game.Game;
import GameObjects.GameObject;
import Handler.Handler;
import java.awt.*;
/**
 * Platform game object and logic for updating and rendering it.
 */
public class Platform extends GameObject {

    private Handler handler;
    private Texture tex = Game.getInstance();

    public Platform(int x, int y,int width, int height, ID id, Handler handler){
        super(x, y,width, height, id);
        this.handler = handler;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

        g.drawImage(tex.platformImages[0], x, y, null);
    }
}
