package MenuObjects;
import Game.*;
import GameObjects.CoreGameObjects.ID;
import Enviroment.mySqlDatabase;

import java.awt.*;
import java.sql.SQLException;

public class LevelSelectButton extends MenuObject{

    private String levelName;
    private Game game;

    private String bestScorePlayer;

    private String bestScoreEver;

    public LevelSelectButton(int x, int y, MenuObjectID id, int width, int height, String levelName, Game game) {
        super(x, y, id, width, height);
        this.levelName = levelName;
        this.game = game;
    }

    @Override
    public void tick() throws SQLException {
        bestScoreEver = "Best score of all time: " + mySqlDatabase.getHighestScoreOfAllTime(levelName.substring(8, 14));
        bestScorePlayer = "Your best score: " + mySqlDatabase.getUsersHighestScoreOfAllTime(levelName.substring(8, 14), Integer.toString(game.userId));
    }

    @Override
    public void onClick(Game game) throws SQLException, InterruptedException {
        if(game.isInMultiplayer){
            if(!game.isLevelSelectButtonPressed){
                game.isLevelSelectButtonPressed = true;
                game.client.createLobby(levelName.substring(8, 14));

                Thread.sleep(1000);

                game.lobbyID = mySqlDatabase.getSpecificLobbyID(game.userName);
                System.out.println(game.lobbyID);
                game.lobbyInfoGenerated = false;
                game.gameState = GameState.LOBBY;
            }
        }else{
            game.play(levelName);
        }
    }

    public void render(Graphics g) throws SQLException {
        g.setColor(Color.CYAN);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        int textWidth = g.getFontMetrics().stringWidth(levelName.substring(8, 14));
        g.fillRect(x, y,width, height);
        g.setColor(Color.MAGENTA);
        g.drawString(levelName.substring(8, 14), x + (width - textWidth) /2 , y + height / 2+ 10);

        textWidth = g.getFontMetrics().stringWidth(bestScorePlayer);
        g.drawString(bestScorePlayer, x - textWidth - 20, y + height/ 2 + 10);

        g.drawString(bestScoreEver, x + width +  20, y + height/ 2 + 10);
    }
}
