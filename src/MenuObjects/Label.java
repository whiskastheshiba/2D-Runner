package MenuObjects;

import Game.Game;

import java.awt.*;
import java.sql.SQLException;

public class Label extends MenuObject{
    private String text;

    private int fontSize;

    private Color color;


    public Label(int x, int y, MenuObjectID id, int width, int height, String text, int fontSize, Color color) {
        super(x, y, id, width, height);
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
    }

    @Override
    public void onClick(Game game) {
    }

    @Override
    public void tick() throws SQLException {
    }

    @Override
    public void render(Graphics g) throws SQLException {
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        //int textWidth = g.getFontMetrics().stringWidth(text);
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
