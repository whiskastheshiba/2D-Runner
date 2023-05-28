package GameObjects.CoreGameObjects;
import Game.Game;
import GameObjects.GameObject;

import java.awt.*;

/**
 * Health bar game object and logic for updating and rendering it.
 */
public class Hpbar extends GameObject {
    private float green = 255;
    public  int currenthp;
    public int maxhp;

    private Player player;

    public Hpbar(int x, int y, int width, int height, ID id, Player player){
        super(x,y,width,height,id);
        this.player =  player;
        this.maxhp = player.maxhp;
        currenthp = maxhp;
    }
    public void tick(){
        x = Game.clamp(player.getX() - Game.WIDTH/2 , 80, -1* Game.levelWidth - Game.WIDTH + player.getWidth());
        y =  Game.clamp(player.getY() - Game.HEIGHT/2 + 64, 80, -1* Game.levelHeight - Game.HEIGHT + 100);
       currenthp =  (int) Game.clamp(player.getCurrenthp() - 2, 0, maxhp);
       maxhp = player.maxhp;
       green = 255 * currenthp/player.maxhp;

    }

    public void render(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(x, y, maxhp * 2 -4, 32);
        g.setColor(new Color(160, (int)green, 0));
        g.fillRect(x, y, currenthp * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, maxhp * 2 - 4, 32);

    }


}
