package MenuObjects;

import Enviroment.mySqlDatabase;
import Game.*;

import java.awt.*;
import java.sql.SQLException;

public class ReturnToMultiplayerMenu extends MenuObject{

    public ReturnToMultiplayerMenu(int x, int y, MenuObjectID id, int width, int height) {
        super(x, y, id, width, height);
    }

    @Override
    public void onClick(Game game) throws SQLException {
        game.client.leaveLobby();

        game.lobbyInfoGenerated = false;
        game.isMultiiplayerMenuGenerated = false;
        game.levelSelectMenuGenerated = false;
        game.gameState = GameState.MULTIPLAYER_MENU;
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
        int textWidth = g.getFontMetrics().stringWidth("BACK");
        g.drawString("BACK", x + (width - textWidth) /2, y + 35);
    }
}
