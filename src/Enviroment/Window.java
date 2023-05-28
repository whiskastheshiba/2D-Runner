package Enviroment;

import Game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Window {

    /**
     * Creates and sets up the window for the game.
     * @param width width of window
     * @param height height of window
     * @param title title of the window
     * @param game Game instance to generate in the window
     */
    public Window(int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    mySqlDatabase.setUserOffline(game.userName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                mySqlDatabase.closeConn();
            }
        });
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

}

