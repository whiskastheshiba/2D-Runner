package Enviroment;

import java.awt.image.BufferedImage;


/**
 * Class used for getting all animations and textures used in the game.
 */
public class Texture {
    SpriteSheet playerSheet;

    SpriteSheet player2Sheet;
    SpriteSheet platformSheet;

    SpriteSheet crystalSheet;
    SpriteSheet powerUpSheet;
    SpriteSheet explosiveEnemySheet;
    SpriteSheet basicEnemySheet;
    SpriteSheet shootingEnemySheet;
    SpriteSheet buttonSheet;
    private BufferedImage platform = null;
    private BufferedImage player = null;

    private BufferedImage player2 = null;

    private BufferedImage crystal = null;

    private BufferedImage powerUp = null;

    private BufferedImage shootingEnemy = null;
    private BufferedImage basicEnemy = null;
    private BufferedImage explosiveEnemy = null;
    public BufferedImage projectile = null;

    public BufferedImage finishLine = null;

    public BufferedImage buttons = null;

    public BufferedImage[] platformImages = new BufferedImage[1];
    public BufferedImage[] playerImages = new BufferedImage[34];
    public BufferedImage[] player2Images = new BufferedImage[13];

    public BufferedImage[] crystalImages = new BufferedImage[4];
    public BufferedImage[] powerUpImages = new BufferedImage[4];

    public BufferedImage[] shootingEnemyImages = new BufferedImage[2];
    public BufferedImage[] basicEnemyImages = new BufferedImage[8];
    public BufferedImage[] explosiveEnemyImages = new BufferedImage[6];

    public BufferedImage[] buttonImages = new BufferedImage[5];

    public Texture(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            platform = loader.loadImage("/textures/BasicGameTextures/platform.png");
            player = loader.loadImage("/textures//BasicGameTextures/player1.png");
            player2 = loader.loadImage("/textures//BasicGameTextures/player2.png");
            crystal = loader.loadImage("/textures/PowerUpCollectibles/crystal.png");
            powerUp = loader.loadImage("/textures/PowerUpCollectibles/powerUps.png");
            shootingEnemy = loader.loadImage("/textures/Enemy/shootingEnemy.png");
            basicEnemy = loader.loadImage("/textures/Enemy/basicEnemy.png");
            projectile = loader.loadImage("/textures/Enemy/projectile.png");
            explosiveEnemy = loader.loadImage("/textures/Enemy/explosiveEnemy.png");
            finishLine = loader.loadImage("/textures//BasicGameTextures/finishLine.png");
            buttons = loader.loadImage("/textures/Buttons/buttons.png");
        }catch(Exception e){
            e.printStackTrace();
        }

        playerSheet = new SpriteSheet(player);
        player2Sheet = new SpriteSheet(player2);
        platformSheet = new SpriteSheet(platform);
        crystalSheet = new SpriteSheet(crystal);
        powerUpSheet = new SpriteSheet(powerUp);
        shootingEnemySheet = new SpriteSheet(shootingEnemy);
        basicEnemySheet = new SpriteSheet(basicEnemy);
        explosiveEnemySheet = new SpriteSheet(explosiveEnemy);
        buttonSheet = new SpriteSheet(buttons);

        getTextures();
    }

    public void getTextures(){
        platformImages[0] = platformSheet.grabImage(2, 1, 64, 64); // Platform texture

        crystalImages[0] = crystalSheet.grabImage(1,1,64,64);
        crystalImages[1] = crystalSheet.grabImage(2,1,64,64);
        crystalImages[2] = crystalSheet.grabImage(3,1,64,64);
        crystalImages[3] = crystalSheet.grabImage(4,1,64,64);

        playerImages[0] = playerSheet.grabImage(1, 3, 64, 128); //Still animation

        playerImages[1] = playerSheet.grabImage(1, 1, 64, 128); //Right animation
        playerImages[2] = playerSheet.grabImage(2, 1, 64, 128); //Right animation
        playerImages[3] = playerSheet.grabImage(3, 1, 64, 128); //Right animation
        playerImages[4] = playerSheet.grabImage(4, 1, 64, 128); //Right animation
        playerImages[5] = playerSheet.grabImage(5, 1, 64, 128); //Right animation
        playerImages[6] = playerSheet.grabImage(6, 1, 64, 128); //Right animation

        playerImages[7] = playerSheet.grabImage(1, 2, 64, 128); //Left animation
        playerImages[8] = playerSheet.grabImage(2, 2, 64, 128); //Left animation
        playerImages[9] = playerSheet.grabImage(3, 2, 64, 128); //Left animation
        playerImages[10] = playerSheet.grabImage(4, 2, 64, 128); //Left animation
        playerImages[11] = playerSheet.grabImage(5, 2, 64, 128); //Left animation
        playerImages[12] = playerSheet.grabImage(6, 2, 64, 128); //Left animation
        playerImages[13] = playerSheet.grabImage(1, 4, 64, 128); // jumping right
        playerImages[14] = playerSheet.grabImage(2, 4, 64, 128); // jumping left


        //Damaged player
        playerImages[15] = playerSheet.grabImage(1, 7, 64, 128); //Still animation

        playerImages[16] = playerSheet.grabImage(1, 5, 64, 128); //Right animation
        playerImages[17] = playerSheet.grabImage(2, 5, 64, 128); //Right animation
        playerImages[18] = playerSheet.grabImage(3, 5, 64, 128); //Right animation
        playerImages[19] = playerSheet.grabImage(4, 5, 64, 128); //Right animation
        playerImages[20] = playerSheet.grabImage(5, 5, 64, 128); //Right animation
        playerImages[21] = playerSheet.grabImage(6, 5, 64, 128); //Right animation

        playerImages[22] = playerSheet.grabImage(1, 6, 64, 128); //Left animation
        playerImages[23] = playerSheet.grabImage(2, 6, 64, 128); //Left animation
        playerImages[24] = playerSheet.grabImage(3, 6, 64, 128); //Left animation
        playerImages[25] = playerSheet.grabImage(4, 6, 64, 128); //Left animation
        playerImages[26] = playerSheet.grabImage(5, 6, 64, 128); //Left animation
        playerImages[27] = playerSheet.grabImage(6, 6, 64, 128); //Left animation
        playerImages[28] = playerSheet.grabImage(1, 8, 64, 128); // jumping right
        playerImages[29] = playerSheet.grabImage(2, 8, 64, 128); // jumping left

        player2Images[0] = player2Sheet.grabImage(1, 3, 64, 128); //Still animation

        player2Images[1] = player2Sheet.grabImage(1, 1, 64, 128); //Right animation
        player2Images[2] = player2Sheet.grabImage(2, 1, 64, 128); //Right animation
        player2Images[3] = player2Sheet.grabImage(3, 1, 64, 128); //Right animation
        player2Images[4] = player2Sheet.grabImage(4, 1, 64, 128); //Right animation
        player2Images[5] = player2Sheet.grabImage(5, 1, 64, 128); //Right animation
        player2Images[6] = player2Sheet.grabImage(6, 1, 64, 128); //Right animation

        player2Images[7] = player2Sheet.grabImage(1, 2, 64, 128); //Left animation
        player2Images[8] = player2Sheet.grabImage(2, 2, 64, 128); //Left animation
        player2Images[9] = player2Sheet.grabImage(3, 2, 64, 128); //Left animation
        player2Images[10] = player2Sheet.grabImage(4, 2, 64, 128); //Left animation
        player2Images[11] = player2Sheet.grabImage(5, 2, 64, 128); //Left animation
        player2Images[12] = player2Sheet.grabImage(6, 2, 64, 128); //Left animation

        powerUpImages[0] = powerUpSheet.grabImage(1,1,64,64);  // Heal
        powerUpImages[1] = powerUpSheet.grabImage(2,1,64,64);  // max health increase
        powerUpImages[2] = powerUpSheet.grabImage(3,1,64,64);  // speed
        powerUpImages[3] = powerUpSheet.grabImage(4,1,64,64);  // high jump

        shootingEnemyImages[0] = shootingEnemySheet.grabImage(1,1,64,64);  // shooting enemy that shoots right
        shootingEnemyImages[1] = shootingEnemySheet.grabImage(2,1,64,64);  // shooting enemy that shoots left

        basicEnemyImages[0] = basicEnemySheet.grabImage(1,1,64,64);  // basic enemy left run animation
        basicEnemyImages[1] = basicEnemySheet.grabImage(1,2,64,64);  // basic enemy left run animation
        basicEnemyImages[2] = basicEnemySheet.grabImage(1,3,64,64);  // basic enemy left run animation
        basicEnemyImages[3] = basicEnemySheet.grabImage(1,4,64,64); // basic enemy left run animation

        basicEnemyImages[4] = basicEnemySheet.grabImage(2,1,64,64);  // basic enemy right run animation
        basicEnemyImages[5] = basicEnemySheet.grabImage(2,2,64,64);  // basic enemy right run animation
        basicEnemyImages[6] = basicEnemySheet.grabImage(2,3,64,64);  // basic enemy right run animation
        basicEnemyImages[7] = basicEnemySheet.grabImage(2,4,64,64);  // basic enemy right run animation

        explosiveEnemyImages[0] = explosiveEnemySheet.grabImage(1,1,64,64);  // explosive enemy right run animation
        explosiveEnemyImages[1] = explosiveEnemySheet.grabImage(2,1,64,64);  // explosive enemy left run animation
        explosiveEnemyImages[2] = explosiveEnemySheet.grabImage(3,1,64,64);  // explode facing right
        explosiveEnemyImages[3] = explosiveEnemySheet.grabImage(4,1,64,64);  // explode facing left

        buttonImages[0] = buttonSheet.grabImage(1, 1, 100, 50); // NEXT
        buttonImages[1] = buttonSheet.grabImage(2, 1, 100, 50); // QUIT
        buttonImages[2] = buttonSheet.grabImage(3, 1, 100, 50); // DONE
        buttonImages[3] = buttonSheet.grabImage(4, 1, 100, 50); // CONTINUE
        buttonImages[4] = buttonSheet.grabImage(5, 1, 100, 50); // RESTART


    }
}
