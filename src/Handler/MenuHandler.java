package Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import Game.*;
import MenuObjects.*;
import MenuObjects.MultiplayerButton;
import MenuObjects.Label;
import Enviroment.*;

/**
 * The class which helps with keeping track of all menu objects. Meu objects are the buttons, labels and text input fields
 */
public class MenuHandler {
    public LinkedList<MenuObject> object = new LinkedList<MenuObject>();

    public Game game;

    /**
     * Sets the game class instance which can be used to acces the game's properties
     * @param game
     */
    public MenuHandler(Game game) {
        this.game = game;
    }

    public String time;

    public int score = 0;

    public int crystalsCollected = 0;

    /**
     * Generates the menu for entering the username.
     * @param g graphics
     * @throws SQLException
     */
    public void generateUserNamePrompt(Graphics g) throws SQLException {
        object.clear();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        int textWidth = g.getFontMetrics().stringWidth("Enter your username: ");
        object.add(new Label(Game.WIDTH / 2 - textWidth, Game.HEIGHT / 2 - 100, MenuObjectID.Label, 0 ,0, "Enter your username: " , 30, Color.MAGENTA));

        object.add(new TextInput(Game.WIDTH / 2 + 10, Game.HEIGHT / 2 - 100, MenuObjectID.TextInput, 0 ,0, game));
        object.add(new SetUserNameButton((Game.WIDTH / 2) - 50,  Game.HEIGHT / 2, MenuObjectID.userNameSetButton   ,100, 50));
    }

    /**
     * Generates the level selection menu and multiplayer buttons
     * @param g graphics
     * @throws SQLException
     */
    public void generateStartMenu(Graphics g) throws SQLException {
        object.clear();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Game.WIDTH, Game.HEIGHT );
        g.setColor(Color.RED);
        for (int i = 0; i < Game.levelsAmount; i++) {
            object.add( new LevelSelectButton(Game.WIDTH / 2 - 50, (i * 30) + (i+1) * 50, MenuObjectID.levelButton, 100, 50, "/Levels/level" + (i+1) + ".png", game));
            object.get(i).tick();
        }

        object.add(new QuitButton(64, 30,MenuObjectID.quitButton, 100, 50));

        object.add(new MultiplayerButton(174, 30,MenuObjectID.button, 100, 50));
    }

    /**
     * Generates the pause menu
     * @param g graphics
     * @throws SQLException
     */
    public void generatePauseMenu(Graphics g, boolean isMultiplayer) throws SQLException {
        object.clear();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        int textWidth = g.getFontMetrics().stringWidth("Game Paused!");

        object.add(new Label((Game.WIDTH / 2) - (textWidth / 2), 50, MenuObjectID.Label, 0 ,0, "Game Paused!" , 30, Color.MAGENTA));
        object.add(new ContinuePlayingButton(Game.WIDTH / 2 - 50, Game.HEIGHT / 3 - 50,MenuObjectID.continuePlayingButton, 100, 50));
        if(isMultiplayer){
            object.add(new ChangeGameStateButton(Game.WIDTH / 2 - 50, Game.HEIGHT /3 + 50, MenuObjectID.changeGameStateButton, 100, 50));
        }else{
            object.add(new RestartLevelButton(Game.WIDTH / 2 - 50, Game.HEIGHT / 3 + 50,MenuObjectID.restartLevelButton, 100, 50));
            object.add(new ChangeGameStateButton(Game.WIDTH / 2 - 50, Game.HEIGHT /3 + 150, MenuObjectID.changeGameStateButton, 100, 50));
        }
    }

    /**
     * Generates the menu for when a player finishes a level
     * @param g
     */
    public void generateLevelFinishedMenu(Graphics g){
        object.clear();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        int textWidth = g.getFontMetrics().stringWidth("Level Completed");

        object.add(new Label((Game.WIDTH / 2) - (textWidth / 2), 50, MenuObjectID.Label, 0,0, "Level Completed!",30, Color.YELLOW));

        textWidth = g.getFontMetrics().stringWidth(game.infoAboutScore);
        object.add(new Label(Game.WIDTH / 2 - (textWidth / 2), 100, MenuObjectID.Label, 0,0, game.infoAboutScore,30, Color.CYAN));

        object.add(new ChangeGameStateButton(Game.WIDTH / 2 - 160, Game.HEIGHT - Game.HEIGHT / 2, MenuObjectID.changeGameStateButton, 100, 50));

        object.add(new RestartLevelButton(Game.WIDTH / 2 - 50, Game.HEIGHT - Game.HEIGHT / 2,MenuObjectID.restartLevelButton, 100, 50));

        object.add(new NextLevelButton(Game.WIDTH / 2 + 60, Game.HEIGHT - Game.HEIGHT / 2,MenuObjectID.nextLevelButton, 100, 50, Integer.parseInt(game.levelname.substring(5, 6))));
    }


    /**
     * Generates the waiting menu which is displayed when one player has already finished the level but the other has not
     * @param g
     */
    public void generateWaitingMenu(Graphics g){
        object.clear();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        int textWidth = g.getFontMetrics().stringWidth("Waiting for the other player to finish");
        object.add(new Label(Game.WIDTH / 2 - (textWidth / 2), Game.HEIGHT / 2, MenuObjectID.Label, 0,0, "Waiting for the other player to finish",30, Color.CYAN));
    }

    /**
     * Generates the menu which is displayed when the player wins the 1v1 game
     * @param g
     */
    public void generateWonMenu(Graphics g){
        object.clear();
        g.setColor(Color.GREEN);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        int textWidth = g.getFontMetrics().stringWidth("You Won");
        object.add(new Label(Game.WIDTH / 2 - (textWidth / 2), Game.HEIGHT / 2, MenuObjectID.Label, 0,0, "You Won",30, Color.GREEN));
        object.add(new QuitButton(64, 30,MenuObjectID.quitButton, 100, 50));
        object.add(new ChangeGameStateButton(174, 30, MenuObjectID.changeGameStateButton, 100, 50));
    }

    /**
     * Generates the menu which is displayed when the player loses the 1v1 game
     * @param g
     */
    public void generateLostMenu(Graphics g){
        object.clear();
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        int textWidth = g.getFontMetrics().stringWidth("You Lost");
        object.add(new Label(Game.WIDTH / 2 - (textWidth / 2), Game.HEIGHT / 2, MenuObjectID.Label, 0,0, "You Lost",30, Color.RED));
        object.add(new QuitButton(64, 30,MenuObjectID.quitButton, 100, 50));
        object.add(new ChangeGameStateButton(174, 30, MenuObjectID.changeGameStateButton, 100, 50));
    }

    /**
     * Generates the menu for when a player dies
     * @param g graphics
     */
    public void generatePlayerDiedMenu(Graphics g){
        object.clear();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        int textWidth = g.getFontMetrics().stringWidth("You died!");

        object.add(new Label((Game.WIDTH / 2) - (textWidth / 2), 50, MenuObjectID.Label, 0,0, "You died!",30, Color.YELLOW));

        object.add(new ChangeGameStateButton(Game.WIDTH / 2 -105, Game.HEIGHT - Game.HEIGHT / 2, MenuObjectID.changeGameStateButton, 100, 50));

        object.add(new RestartLevelButton(Game.WIDTH / 2 + 5, Game.HEIGHT - Game.HEIGHT / 2,MenuObjectID.restartLevelButton, 100, 50));
    }


    /**
     * Generates the multiplayer menu. Displays buttons to join all currently available lobbies.
     * @param g graphics
     * @throws SQLException
     */
    public void generateMultiplayerMenu(Graphics g) throws SQLException {
        object.clear();

        object.add(new ChangeGameStateButton(64, 30, MenuObjectID.changeGameStateButton, 100, 50));

        int[] lobbyIds = mySqlDatabase.getLobbyIds();


        for(int i = 0; i < lobbyIds.length; i++){
            if(lobbyIds[i] != 0){
                object.add(new LobbySelectButton(Game.WIDTH / 2 - 50, (i * 30) + (i+1) * 50, MenuObjectID.lobbySelectButton , 100, 50, lobbyIds[i]));
            }
        }

        object.add(new CreateLobbyButton(Game.WIDTH / 2 + Game.WIDTH/4,Game.HEIGHT/2 + Game.HEIGHT/4,MenuObjectID.CreateLobbyButton,100,50));
    }

    /**
     * Displays the menu for creating a new lobby and choosing a level which the user wants to play in the 1v1 gamemode
     * @param g
     * @throws SQLException
     */
    public void generateLobbyMenu(Graphics g) throws SQLException {
        object.clear();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Game.WIDTH, Game.HEIGHT );
        g.setColor(Color.RED);

        for (int i = 0; i < Game.levelsAmount; i++) {
            object.add( new LevelSelectButton(Game.WIDTH / 2 - 100, (i * 64) + (i+1) * 30, MenuObjectID.levelButton, 100, 50, "/Levels/level" + (i+1) + ".png", game));
            object.get(i).tick();
        }
        object.add(new ReturnToMultiplayerMenu(64, 30, MenuObjectID.returnToMultiplayerMenuButton, 100, 50));
    }

    /**
     * Displays all info about the lobby a user has joined
     * @param g
     * @param lobbyID
     * @throws SQLException
     */
    public void generateLobbyInfo(Graphics g, int lobbyID) throws SQLException {
        object.clear();
        String[] splitArray = mySqlDatabase.getLobbyInfo(lobbyID).split("\\s+");

        object.add(new Label((Game.WIDTH / 2), 50, MenuObjectID.Label, 0,0, "Lobby ID:" + splitArray[0],30, Color.YELLOW));
        object.add(new Label((Game.WIDTH / 2), 150, MenuObjectID.Label, 0,0, "Player 1: " +splitArray[1],30, Color.YELLOW));
        object.add(new Label((Game.WIDTH / 2), 250, MenuObjectID.Label, 0,0, "Player 2: " + splitArray[3],30, Color.YELLOW));
        object.add(new Label((Game.WIDTH / 2), 350, MenuObjectID.Label, 0,0, "Level: " + splitArray[2],30, Color.YELLOW));
        object.add(new ReturnToMultiplayerMenu(64, 30, MenuObjectID.returnToMultiplayerMenuButton, 100, 50));
        object.add(new StartGameButton(Game.WIDTH / 2 + Game.WIDTH/4,Game.HEIGHT/2 + Game.HEIGHT/4,MenuObjectID.startGameButton,100,50));
        game.levelname = "/Levels/" + splitArray[2] + ".png";
    }


    /**
     * Renders all the menu objects in the list of menu objects
     * @param g
     * @throws SQLException
     */
    public void render(Graphics g) throws SQLException {
        for(int i = 0; i < object.size(); i++){
            object.get(i).render(g);
        }
    }

    /**
     * Updates properties for all menu objects in the list without rendering the changes
     * @throws SQLException
     */

    public void tick() throws SQLException {
        for(int i = 0; i < object.size(); i++){
            object.get(i).tick();
        }
    }

    /**
     * Runs through all the menu objects currently present and excecutes the correct method depending on where the user clicked
     * @param g graphics
     * @param e mouse event
     * @param game game class instance
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
   public void executeClick(Graphics g, MouseEvent e, Game game) throws SQLException, IOException, InterruptedException {
       for(int i = 0; i < object.size(); i++){
           MenuObject tempObject = object.get(i);
           if(e.getX() >= tempObject.getX() && e.getX() <= tempObject.getX() + tempObject.getWidth() && e.getY() >= tempObject.getY() && e.getY() <= tempObject.getY() + tempObject.getHeight()){
               tempObject.onClick(game);
           }
       }
   }
}
