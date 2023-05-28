package Enviroment;


import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

import static Game.Game.sound;

/**
 * Sound class with all the sounds which are played in the game
 */
public class Sound {
    Clip clip;
    private int movingTimer = -1;
    URL soundURL[] = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("/sounds/movement/jump.wav"); //7,12,17,22,31,34,50
        soundURL[1] = getClass().getResource("/sounds/movement/move.wav");//run
        soundURL[2] = getClass().getResource("/sounds/collection/Crystal.wav");// collectibles
        soundURL[3] = getClass().getResource("/sounds/collection/Heal.wav");
        soundURL[4] = getClass().getResource("/sounds/collection/HpBoost.wav");
        soundURL[5] = getClass().getResource("/sounds/collection/JumpBoost.wav");
        soundURL[6] = getClass().getResource("/sounds/collection/SpeedBoost.wav");//collectibles ends
        soundURL[7] = getClass().getResource("/sounds/enemies/Shoot.wav");//Enemies
        soundURL[8] = getClass().getResource("/sounds/enemies/Bomb_Explosion.wav");
        soundURL[9] = getClass().getResource("/sounds/enemies/Bomb_trigger.wav");
        soundURL[10] = getClass().getResource("/sounds/enemies/Enemy_dies.wav");
        soundURL[11] = getClass().getResource("/sounds/enemies/Damage.wav");//Enemies ends
        soundURL[12] = getClass().getResource("/sounds/gameEnd/Die.wav");//Game end starts
        soundURL[13] = getClass().getResource("/sounds/gameEnd/Victory.wav");
        soundURL[14] = getClass().getResource("/sounds/gameEnd/Loose.wav");


        soundURL[20] = getClass().getResource("/sounds/music/Whisperer.wav");//menu music
        soundURL[21] = getClass().getResource("/sounds/music/Warmth.wav");
        soundURL[22] = getClass().getResource("/sounds/music/Nostalgic.wav");//menu music endsd
        soundURL[23] = getClass().getResource("/sounds/music/Electric.wav");//Soundtrack
        soundURL[24] = getClass().getResource("/sounds/music/Spaceship.wav");
        soundURL[25] = getClass().getResource("/sounds/music/Game Loop.wav");
        soundURL[26] = getClass().getResource("/sounds/music/Sweet Treats.wav");//Soundtrack ends

    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (UnsupportedAudioFileException e){

        }catch (IOException e){

        }catch (LineUnavailableException e){

        }

    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
        clip = null;
    }

    public void playSound(int i){
        setFile(i);
        play();
    }
    public void playMenuMusic(){
        setFile(20 + (int)(Math.random() * (23-20)));
        play();
        loop();
    }
    public void playGameMusic(){
        setFile(23 + (int)(Math.random() * (27-23)));
        play();
        loop();
    }

    public void playMove() {
        playSound(1);
        loop();
    }
    public void playExplosion(){playSound(8); }
    public void playBombTrigger(){playSound(9);}
    public void playShot(){playSound(7);}
    public void playEnemyDies(){playSound(10);}
    public void playDamage(){playSound(11);}
    public void playCrystal(){
        playSound(2);
    }
    public void playHeal(){
        playSound(3);
    }
    public void playMaxHp(){
        playSound(4);
    }
    public void playJumpBoost(){
        playSound(5);
    }
    public void playSpeedBoost(){
        playSound(6);
    }
    public void playDie(){playSound(12);}
    public void playVictory(){playSound(13);}
    public void playDefeat(){playSound(14);}
    public Clip getClip() {
        return clip;
    }
}
