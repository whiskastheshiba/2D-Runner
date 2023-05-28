package Multiplayer;

import java.util.logging.Level;

/**
 * Store information about lobby client is in
 */
public class Lobby {
    /**
     * Player 1(lobby owner) username
     */
    public String player1;
    /**
     * Player 2 username
     */
    public String player2;
    /**
     * Level name
     */
    public String level;
    /**
     * Player 1 score
     */
    public int player1Score = 0;
    /**
     * Player 2 score
     */
    public int player2Score = 0;

    /**
     * Determines if player 1 reached finish line
     */
    public boolean player1Finished;
    /**
     * Determines if player 2 reached finish line
     */
    public boolean player2Finished;
    /**
     * Determines if game is finished
     */
    public boolean finished;
    /**
     * Determines the state of the lobby
     */
    public boolean running = false;

    /**
     * Determines if player has left the game
     */
    public boolean playerLeft;

}
