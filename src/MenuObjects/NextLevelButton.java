package MenuObjects;

import Game.Game;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class NextLevelButton extends MenuObject{
    private int currentLevel;

    public NextLevelButton(int x, int y, MenuObjectID id, int width, int height, int currentLevel) {
        super(x, y, id, width, height);
        this.currentLevel = currentLevel;
    }

    @Override
    public void onClick(Game game) throws SQLException, IOException, InterruptedException {
        game.play("/Levels/level" + ((currentLevel % game.levelsAmount) + 1 )+ ".png");
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
        int textWidth = g.getFontMetrics().stringWidth("Next Level");
        g.drawString("Next level", x + (width - textWidth) /2, y + 32);
    }
}
