package Game;

import Enviroment.Window;
import Enviroment.*;
import GameObjects.CoreGameObjects.*;
import GameObjects.Enemies.BasicEnemy;
import GameObjects.Enemies.ExplosiveEnemy;
import GameObjects.Enemies.RuningExplosiveEnemy;
import GameObjects.Enemies.ShootingEnemy;
import GameObjects.GameObject;
import GameObjects.PowerUps.*;
import Handler.*;
import Multiplayer.Client;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import org.apache.log4j.*;

/**
 * The main game class. This class has the game loop, the logic for generating levels from images and to handle all present game and menu objects and render them.
 */

public class Game extends Canvas implements Runnable {


    /**
     * Represents Width and height of the game window.
     */

    public static int WIDTH, HEIGHT;

    /**
     * Represents Width and height of level.
     */
    public static int levelWidth, levelHeight;


    /**
     * Represents the logger which writes game logs to file.
     */
    public static Logger logger = Logger.getLogger(Game.class);

    /**
     * Represents the thread on which the game is running.
     */
    private Thread thread;

    /**
     * Boolean which describes whether the game is running
     */
    private boolean running = false;

    private Graphics g;
    /**
     * Represents the Game State of current game. The game state can be 'paused', 'playing', 'menu' and so on.
     */
    public GameState gameState;

    /**
     * Represents the picture, from which the level is loaded and generated.
     */
    public BufferedImage level;

    /**
     * Represents the game background picture.
     */
    public BufferedImage background;

    /**
     * Represents the instance of Texture class in which all textures and sprites are available for use.
     */
    static Texture tex;
    public static Sound music = new Sound();
    public static Sound sound = new Sound();
    public static Sound runSound = new Sound();


    /**
     * Amount of levels in the game
     */
    public static int levelsAmount = 9;

    /**
     * Path to level image
     */
    public String levelname;


    /**
     * Players starting x coordinate when first loading a level. Used to position the player at the beginning of the level in 1v1 if he dies
     */
    private int startXpostionPlayer;

    /**
     * Players starting y coordinate when first loading a level. Used to position the player at the beginning of the level in 1v1 if he dies
     */
    private int startYpositionPlayer;

    /**
     * Represents the camera of the game. Makes sure that the player is always visible when playing/
     */
    Camera camera = new Camera(0,0);


    /**
     * Represents the instance of the class which is responsible for handling all changes in different game objects when playing the game.
     */
    public Handler handler;

    /**
     * Represents the instance of the class which is responsible for handling all changes in different menu objects when not playing.
     */
    public MenuHandler menuHandler;

    /**
     * Represents the username of current player.
     */
    public String userName;
    /**
     * Represents the user id of the current player.
     */
    public int userId;
    /**
     * Represents a boolean value which determines whether the single player level select menu should be generated, or just rendered again.
     */
    public boolean isMenuGenerated;
    /**
     * Represents a boolean value which determines whether the menu for entering the players username should be generated, or just rendered again.
     */
    public boolean isUserNameSet;
    /**
     * Represents a boolean value which determines whether the specified menu should be generated, or just rendered again.
     */
    public boolean isPauseMenuActive;
    /**
     * Represents a boolean value which determines whether the pause menu should be generated, or just rendered again.
     */
    public boolean isLevelFinishedMenuActive;

    /**
     * Represents a boolean value which determines whether the player died menu should be generated, or just rendered again.
     */

    public boolean playerDiedMenuGenerated;
    /**
     * Represents a boolean value which determines whether the multiplayer lobby select menu should be generated, or just rendered again.
     */

    public boolean isMultiiplayerMenuGenerated;

    /**
     * Represents a boolean value which determines whether the menu for selecting the level which the user wants to play in 1v1 mode should be generated, or just rendered again.
     */

    public boolean levelSelectMenuGenerated = false;

    /**
     * Represents a boolean value which determines weather or not the lobby info menu should be generated, or just rendered again.
     */

    public boolean lobbyInfoGenerated;


    /**
     * Represents a boolean value which determines weather or not the multiplayer level finished waiting menu should be generated, or just rendered again.
     */
    public boolean waitingMenuGenerated;
    /**
     * Represents a boolean value which determines weather or not the multiplayer level won waiting menu should be generated, or just rendered again.
     */
    public boolean wonMenuGenerated;
    /**
     * Represents a boolean value which determines weather or not the multiplayer level lost waiting menu should be generated, or just rendered again.
     */
    public boolean lostMenuGenerated;

    /**
     * Represents a value which contains info about the score when a level has been finished.
     */
    public String infoAboutScore;

    /**
     * Client class instance used in the 1v1 game mode.
     */
    public  Client client;

    /**
     * Boolean value which determines the behavior of levelSelectButton.
     */
    public boolean isInMultiplayer = false;

    /**
     * Boolean value which determines whether the level select button has been pressed
     */
    public boolean isLevelSelectButtonPressed = false;

    /**
     * Boolean value which determines if the background has been loaded
     */
    private boolean isBackgroundLoaded;


    /**
     * Lobby id for the lobby which the user is currently in
     */
    public int lobbyID;

    /**
     * Game constructor sets the WIDTH and HEIGHT of the game to the size of the screen.
     * Creates both handlers which are going to be used while the game is running.
     * Defines KeyListener and Mouse Listener for listening for the user input.
     */

    public Game(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screenSize.width;
        HEIGHT = screenSize.height;

        userName = "";

        handler = new Handler();
        menuHandler = new MenuHandler(this);
        new Window(WIDTH, HEIGHT, "First game", this);
        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(new MouseInput(this, g));
        tex = new Texture();

        gameState = GameState.USERNAME_PROMPT;

        music.playMenuMusic();



        isMenuGenerated = false;

    }

    /**
     * gets the desired level image and loads the level.
     * sets the game state to playing
     * sets all the level finished menu generated and pause menu generated variables to false.
     *
     * @param path specifies the path to the desired level image
     */

    public void play(String path){
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage(path);
        handler.object.clear();
        loadLevelImage(level);
        levelWidth = loader.getLevelWidth(path);
        levelHeight = loader.getLevelHeight(path);
        background = loader.loadImage("/Textures/BasicGameTextures/background.png");
        gameState = GameState.PLAYING;
        if(music.getClip() != null){
            music.stop();
        }
        music.playGameMusic();
        levelname = path.substring(8,14);
        menuHandler.object.clear();
        logger.info(userName + " joined the level");
        logger.info(levelname + " has been loaded");
        isPauseMenuActive = false;
        isLevelFinishedMenuActive = false;
        waitingMenuGenerated = false;
        wonMenuGenerated = false;
        lostMenuGenerated = false;
    }


    /**
     * Empties both the game object and menu object handlers
     */
    public void emptyHandler(){
        handler.object.clear();
        menuHandler.object.clear();
    }


    /**
     * Generates the level by generating different game objects depending on pixels RGB values in the level image.
     * @param image
     */
    private void loadLevelImage(BufferedImage image){
        int w = image.getWidth();
        int h = image.getWidth();

        Player player;

        for(int x = 0; x < h; x++){
            for(int y = 0; y < w; y++){
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue =(pixel) & 0xff;


                if(red == 255 && green == 255 && blue == 255){
                    handler.addObject(new Platform(x*64,y*64, 64, 64, ID.Platform, handler));
                }

                if(red == 0 && green == 0 && blue == 255){
                    player = new Player(x*64, y*64, 64, 128, ID.Player, handler, this);
                    startXpostionPlayer = x*64;
                    startYpositionPlayer = y * 64;
                    handler.addObject(player);
                    Hpbar hpbar = new Hpbar(0,0,0,32, ID.Hpbar,player);

                    handler.addObject(hpbar);
                    if(isInMultiplayer){
                        handler.addObject(new SecondPlayer(x*64, y*64, 64, 128, ID.SecondPlayer, handler, this));
                    }
                }

                if(red == 255 && green ==0 && blue == 0){
                    handler.addObject(new BasicEnemy(x*64, y*64, 64, 128, ID.BasicEnemy, handler, 100, 10));
                }

                if(red == 0 && green == 255 && blue == 0){
                    handler.addObject(new ShootingEnemy(x*64, y*64, 64, 128, ID.BasicEnemy, handler, 170, 10, 20, 1));
                }

                if(red == 0 && green == 128 && blue == 0){
                    handler.addObject(new ShootingEnemy(x*64, y*64, 64, 128, ID.BasicEnemy, handler, 170, 10, 20, -1));
                }

                if(red == 255 && green == 255 && blue == 128){
                    handler.addObject(new ExplosiveEnemy(x*64, y*64, 64, 64, ID.ExplosiveEnemy, handler, 50, 50, 150));
                }

                if(red == 128 && green == 255 && blue == 255){
                    handler.addObject(new RuningExplosiveEnemy(x*64, y*64, 64, 64, ID.ExplosiveEnemy, handler, 20, 50, 150 ,5));
                }

                if(red == 255 && green == 128 && blue == 255){
                    handler.addObject(new FinishLine(x*64, y*64, 64, 128, ID.FinishLine, handler));
                }

                if(red == 255 && green == 255 && blue == 64){
                    handler.addObject(new Crystal(x*64, y*64, 64, 64, ID.Crystal, handler));
                }

                if(red == 255 && green == 216 && blue == 0){
                    handler.addObject(new HealPowerUp(x*64, y * 64, 64, 64, ID.HealPowerUp, handler));
                }
                if(red == 128 && green == 128 && blue == 128){
                    handler.addObject((new MaxHpPowerUp(x*64, y * 64,64,64,ID.MaxHpPowerUp)));
                }

                if(red == 0 && green == 155 && blue == 255){
                    handler.addObject(new SpeedPowerUp(x*64, y * 64,64,64,ID.SpeedPowerUp));
                }

                if(red == 88 && green == 140 && blue == 58){
                    handler.addObject(new JumpPowerUp(x*64, y * 64,64,64,ID.JumpPowerUp));
                }
            }
        }
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Main game loop.
     * Calls the render and tick methods 60 times a second.
     */
    public void run(){
        this.requestFocus();
        long lastTime = System.nanoTime();   //Start time in nanoseconds
        double amountOfTicks = 60.0;        // Amount of ticks per second
        double ns = 1000000000 / amountOfTicks;  // Amount of nanoseconds in one tick
        double delta = 0;
        long timer = System.currentTimeMillis();   //Used to display fps which refreshes once every second
        int frames = 0;                             //Used to display fps which refreshes once every second
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                try {
                    render();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                delta--;
            }
        }
        stop();
    }

    /**
     * Updates the game or menu object fields without rendering them when called.
     */
    private void tick(){

        if(gameState == GameState.PLAYING){
            handler.tick();

            for(int i = 0; i < handler.object.size(); i++){
                if(handler.object.get(i).getId() == ID.Player){
                    camera.tick(handler.object.get(i), levelWidth, levelHeight);
                }

                if(handler.object.get(i).getId() == ID.SecondPlayer && client.secondPlayerFinished){
                    handler.removeObject((handler.object.get(i)));
                }
            }
        }else if(gameState == GameState.MENU){
            if(music.getClip() == null){
                music.playMenuMusic();
            }
        }else if(client != null && client.isGameStarted){
            client.isGameStarted = false;
            play(levelname);
        }
    }

    /**
     * Renders the desired objects depending on which game state the game is currently in and whether the desired menu has already been generated.
     * If it has, then this function only renders the already generated objects.
     * @throws SQLException
     */
    private void render() throws SQLException {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {

            this.createBufferStrategy(3);
            return;
        }

        if(!isBackgroundLoaded){
            BufferedImageLoader loader = new BufferedImageLoader();
            background = loader.loadImage("/Textures/BasicGameTextures/background.png");
            isBackgroundLoaded = true;
        }

        g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        int imgX = (this.getWidth() - background.getWidth(null)) / 2;
        int imgY = (this.getHeight() - background.getHeight(null)) / 2;
        g.drawImage(background, imgX ,imgY, null);

        if(gameState == GameState.USERNAME_PROMPT){

            if(!isUserNameSet){
                menuHandler.generateUserNamePrompt(g);
                isUserNameSet = true;
            }
            menuHandler.render(g);

        }else if(gameState == GameState.MENU){
            if(!isMenuGenerated){
                menuHandler.generateStartMenu(g);

                isMenuGenerated = true;
            }

                menuHandler.render(g);
        }else if(gameState == GameState.PLAYING){
            int imageX = (this.getWidth() - background.getWidth(null)) / 2;
            int imageY = (this.getHeight() - background.getHeight(null)) / 2;
            g.drawImage(background, imageX ,imageY, null);
            g2d.translate(camera.getX(), camera.getY());
            if(isInMultiplayer){
                if(client.secondPlayerLeft){
                    gameState = GameState.LEVEL_FINISHED;
                }
            }
            handler.render(g);
            g.dispose();
            bs.show();
        }else if(gameState == GameState.PLAYER_DIED){
            if(isInMultiplayer){
                gameState = GameState.PLAYING;
                for(int i = 0; i < handler.object.size(); i++){
                    GameObject tmp = handler.object.get(i);
                    if(handler.object.get(i).getId() == ID.Player){
                        tmp.setX(startXpostionPlayer);
                        tmp.setY(startYpositionPlayer);
                        tmp.setCurrenthp(100);
                    }
                }
            }

            if(!playerDiedMenuGenerated){
                menuHandler.generatePlayerDiedMenu(g);
                playerDiedMenuGenerated = true;
            }
            menuHandler.render(g);
        }else if(gameState == GameState.PAUSED){

            if(!isPauseMenuActive){
                menuHandler.generatePauseMenu(g, isInMultiplayer);
                isPauseMenuActive = true;
            }
            menuHandler.render(g);
        }else if(gameState == GameState.LEVEL_FINISHED){
            if(isInMultiplayer){
                if(client.isWinner && client.gameFinished && !wonMenuGenerated){
                    menuHandler.generateWonMenu(g);
                    music.stop();
                    sound.playVictory();
                    wonMenuGenerated = true;
                    client.deleteLobby();
                }else if(client.isLooser && client.gameFinished && !lostMenuGenerated){
                    menuHandler.generateLostMenu(g);
                    music.stop();
                    sound.playDefeat();
                    lostMenuGenerated = true;
                }else if(!client.gameFinished && !waitingMenuGenerated){
                    menuHandler.generateWaitingMenu(g);
                    waitingMenuGenerated = true;
                }
            }
            if(!isLevelFinishedMenuActive && !isInMultiplayer){
                menuHandler.generateLevelFinishedMenu(g);
                isLevelFinishedMenuActive = true;
            }
            menuHandler.render(g);
        }else if(gameState == GameState.MULTIPLAYER_MENU){
            if(!isMultiiplayerMenuGenerated){
                menuHandler.generateMultiplayerMenu(g);
                isMultiiplayerMenuGenerated = true;
            }
            menuHandler.render(g);
        }else if(gameState == GameState.MULTIPLAYER_LEVEL_SELECT){
            if(!levelSelectMenuGenerated){
                menuHandler.generateLobbyMenu(g);
                levelSelectMenuGenerated = true;
            }

            menuHandler.render(g);
        }else if(gameState == GameState.LOBBY){
            if(!lobbyInfoGenerated || client.isAction){
                menuHandler.generateLobbyInfo(g, lobbyID);
                client.isAction = false;
                lobbyInfoGenerated = true;
            }else if(client.isLobbyDeleted){
                client.isLobbyDeleted = false;
                isMultiiplayerMenuGenerated = false;
                gameState = GameState.MULTIPLAYER_MENU;
            }
            isLevelSelectButtonPressed = false;
            menuHandler.render(g);
        }

        g.dispose();
        bs.show();
    }


    /**
     * Used to set an int value to the specified minimum value if the desired value is less than min and the specified maximum value if the desired value is greater than the maximum allowed value.
     * If the desired value is in range (min < n < max) then it sets the value to the desired value.
     * @param val desired value
     * @param min minumum value
     * @param max maximum value
     * @return either the desired int value, or specified minimum or maximum value.
     */
    public static int clamp(int val, int min, int max){
        if(val >= max){
            return max;
        }else if(val <= min){
            return min;
        }else{
            return val;
        }
    }

    /**
     * Gets an instance of the texture class. Instance of texture class has all the animations and sprites for the game.
     * @return an instance of Texture class
     */

    /**
     * Returns an instance of Texture class used for the game textures
     * @return Texture
     */

    public static Texture getInstance(){
        return tex;
    }

    public static void main(String args[]){
        new Game();
    }
}
