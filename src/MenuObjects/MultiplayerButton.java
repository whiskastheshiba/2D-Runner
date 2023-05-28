package MenuObjects;

import Game.Game;
import Game.GameState;
import Multiplayer.Client;

import java.awt.*;
import java.sql.SQLException;

public class MultiplayerButton extends MenuObject{
    public MultiplayerButton(int x, int y, MenuObjectID id, int width, int height) {
        super(x, y, id, width, height);
    }

    @Override
    public void onClick(Game game) {
        game.isInMultiplayer = true;
        game.isMenuGenerated = false;
        game.isMultiiplayerMenuGenerated = false;
        game.gameState = GameState.MULTIPLAYER_MENU;
        game.client = new Client(game.userName);
        game.client.run();
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
        int textWidth = g.getFontMetrics().stringWidth("1 v 1");
        g.drawString("1 v 1", x + (width - textWidth) /2, y + 35);
    }
}
