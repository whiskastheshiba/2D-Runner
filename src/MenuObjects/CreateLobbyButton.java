package MenuObjects;

import Game.Game;
import Game.GameState;

import java.awt.*;
import java.sql.SQLException;

public class CreateLobbyButton extends MenuObject{
    public CreateLobbyButton(int x, int y, MenuObjectID id, int width, int height) {
        super(x, y, id, width, height);
    }

    @Override
    public void onClick(Game game) {
        game.gameState = GameState.MULTIPLAYER_LEVEL_SELECT;
        game.isMultiiplayerMenuGenerated = false;
        game.levelSelectMenuGenerated = false;
    }

    @Override
    public void tick() throws SQLException {

    }

    @Override
    public void render(Graphics g) throws SQLException {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        int textWidth = g.getFontMetrics().stringWidth("CREATE");
        g.drawString("CREATE", x + (width - textWidth) /2, y + 32);
    }
}
