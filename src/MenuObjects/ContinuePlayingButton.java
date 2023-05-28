package MenuObjects;

import Enviroment.Texture;
import Game.*;

import java.awt.*;

public class ContinuePlayingButton extends MenuObject{
    private Texture tex = Game.getInstance();

    public ContinuePlayingButton(int x, int y, MenuObjectID id, int width, int height) {
        super(x, y, id, width, height);
    }

    @Override
    public void onClick(Game game) {
        if(game.music.getClip() == null){
            game.music.playGameMusic();
        }
        game.gameState = GameState.PLAYING;
        game.isPauseMenuActive = false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tex.buttonImages[3], x, y, null);
    }
}
