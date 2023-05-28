package MenuObjects;

import Game.Game;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Base class for all of the other menu objects such as butons, labels and input fields.
 */
public abstract class MenuObject {
    protected int x, y;
    protected MenuObjectID id;
    protected int width, height;

    public MenuObject(int x, int y, MenuObjectID id, int width, int height) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height;
    }

    /**
     * Method to execute commands when a specific menu object is clicked. This method is abstract
     * @param game
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract void onClick(Game game) throws SQLException, IOException, InterruptedException;

    /**
     * Method to update a menu objects properties without rendering them
     * @throws SQLException
     */
    public abstract void tick() throws SQLException;

    /**
     * Renders a menu object depending on the implementation in each object
     * @param g
     * @throws SQLException
     */
    public abstract void render(Graphics g) throws SQLException;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MenuObjectID getId() {
        return id;
    }

    public void addToUserName(String letter){

    }
}
