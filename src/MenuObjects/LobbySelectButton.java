package MenuObjects;

import Game.*;
import Enviroment.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class LobbySelectButton extends MenuObject{

    private int lobbyID;
    private String player;
    private String level;

    public LobbySelectButton(int x, int y, MenuObjectID id, int width, int height, int lobbyID) throws SQLException {
        super(x, y, id, width, height);
        this.lobbyID = lobbyID;
        String[] lobbyInfo = mySqlDatabase.getLobbyInfo(lobbyID).split("\\s+");
        player = lobbyInfo[1];
        level = lobbyInfo[2];
    }

    @Override
    public void onClick(Game game) throws SQLException, IOException, InterruptedException {
        game.client.joinLobby(lobbyID);

        Thread.sleep(1000);
        game.levelSelectMenuGenerated = false;
        game.lobbyInfoGenerated = false;
        game.gameState = GameState.LOBBY;
        game.lobbyID = lobbyID;
        System.out.println(lobbyID);

    }

    @Override
    public void tick() throws SQLException {

    }

    @Override
    public void render(Graphics g) throws SQLException {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, width, height);
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        int textWidth = g.getFontMetrics().stringWidth("JOIN");
        g.drawString("JOIN", x + (width - textWidth) /2, y + 35);
        g.setColor(Color.WHITE);
        g.drawString("Player: " + player, x + 100 + 10, y + 35);
        textWidth = g.getFontMetrics().stringWidth("Level: " + level);
        g.drawString("Level: " + level, x - textWidth - 10, y + 35);
    }
}
