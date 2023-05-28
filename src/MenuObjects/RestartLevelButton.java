package MenuObjects;

import Enviroment.Texture;
import Game.*;

import java.awt.*;

public class RestartLevelButton extends MenuObject{
    private Texture tex = Game.getInstance();
    public RestartLevelButton(int x, int y, MenuObjectID id, int width, int height) {
        super(x, y, id, width, height);
    }

    @Override
    public void onClick(Game game) {
        if(game.music.getClip() == null){
            game.music.playGameMusic();
        }
        game.play("/Levels/" + game.levelname + ".png");
        game.isPauseMenuActive = false;
        game.playerDiedMenuGenerated = false;
        //game.gameState = GameState.PLAYING;
    }

    @Override
    public void tick() {

    }
    @Override
    public void render(Graphics g) {
        g.drawImage(tex.buttonImages[4], x, y, null);
    }
}
