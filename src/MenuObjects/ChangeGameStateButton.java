package MenuObjects;

import Game.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Button which takes the player back to the main menu
 */
public class ChangeGameStateButton extends MenuObject{


    public ChangeGameStateButton(int x, int y, MenuObjectID id, int width, int height) {
        super(x, y, id, width, height);
    }

    @Override
    public void onClick(Game game) throws SQLException, IOException {
        if(game.isInMultiplayer && game.gameState == GameState.PAUSED){
            game.client.leaveGame();
            //game.handler.object.clear();
        }

        if(game.isInMultiplayer){
            game.client.shutdown();
        }
        game.isInMultiplayer = false;
        game.isMenuGenerated = false;
        game.gameState = GameState.MENU;
    }

    @Override
    public void tick() throws SQLException {

    }

    @Override
    public void render(Graphics g) throws SQLException {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        int textWidth = g.getFontMetrics().stringWidth("MENU");
        g.drawString("MENU", x + (width - textWidth) /2, y + 35);
    }
}
