package MenuObjects;

import Game.Game;

import java.awt.*;
import java.sql.SQLException;

public class TextInput extends MenuObject{

    private Game game;

    public TextInput(int x, int y, MenuObjectID id, int width, int height, Game game) {
        super(x, y, id, width, height);
        this.game = game;
    }

    @Override
    public void onClick(Game game) {

    }

    @Override
    public void tick() throws SQLException {

    }

    @Override
    public void render(Graphics g) throws SQLException {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.CYAN);
        g.drawString(game.userName, x, y);
    }
}
