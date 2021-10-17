package game.objects;

import city.cs.engine.*;
import city.cs.engine.Shape;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * Ninja player
 */
public class Ninja extends Walker {
    /**
     * health of ninja
     */
    private int health;
    /**
     * wealth of ninja
     */
    private int wealth;
    /**
     * check if ninja lose
     */
    private boolean lose = false;
    /**
     * check if ninja win
     */
    private boolean win = false;
    /**
     * Ninja image
     */
    private static BodyImage image;
    /**
     * check if ninja has completed the level
     */
    private boolean levelCompleted = false;
    /**
     * total shoots
     */
    private int totalShoot;
    /**
     * shape of ninja
     */
    private static Shape ninjaShape = new PolygonShape(-0.503f, -1.25f, 0.512f, -1.23f, 0.817f, -0.03f, 0.742f, 1.205f, -0.623f, 1.24f, -0.718f, 0.34f);
    /**
     * sound played when ninja completes a level
     */
    private static SoundClip levelComplete;
    /**
     * sound played when ninja win
     */
    private static SoundClip won;
    /**
     * sound played when ninja lose
     */
    private static SoundClip gameOver;
    /**
     * sound played when ninja shoots
     */
    private static SoundClip shoot;

    /**
     * Constructor used for initializing ninja.

     * @param  world world with bodies
     * @return
     */
    public Ninja(World world) {
        super(world, ninjaShape);
        health = 100;
        wealth = 0;
        totalShoot = 0;
    }
    /**
     * Method used to get the total shoot

     *
     * @param
     * @return Return the total shoot
     */
    public int getTotalShoot() {
        return totalShoot;
    }
    /**
     * Method used to increment total shoot

     *
     * @param
     * @return
     */
    public void incrementTotalShoot() {
        totalShoot++;
    }

    /**
     * Method that return if the level is completed

     *
     * @param
     * @return return the value of levelCompleted
     */
    public boolean isLevelCompleted() {
        return levelCompleted;
    }
    /**
     * Method used to set levelCompleted

     *
     * @param levelCompleted boolean that sets true or false
     * @return
     */
    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
    }

    /**
     * Method used to get the health

     *
     * @param
     * @return Return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method used to decrement the health

     *
     * @param health value that will be subtracted
     * @return
     */
    public void decrementHealth(int health) {
        this.health -= health;
    }

    /**
     * Method used to get the health

     *
     * @param
     * @return Return the wealth
     */
    public int getWealth() {
        return wealth;
    }
    /**
     * Method used to increment the health

     *
     * @param
     * @return
     */
    public void incrementWealth() {
        wealth++;
    }

    /**
     * Method used to change image
     * <p>
     * This method is used in the controller class to change the image
     * @param imageNumber image will change in basis of this value
     * @return
     */
    //method used for changing the image of the ninja
    public void changeImage(int imageNumber) {
        removeAllImages();
        switch (imageNumber) {
            case 1:
                if (getHealth() > 1) {
                    image = new BodyImage("data/ninjaIdle.png", 2.5f);
                } else {
                    image = new BodyImage("data/ninjaDead.png", 2.5f);
                }
                addImage(image);
                break;
            case 2:
                image = new BodyImage("data/ninjaRunRight.png", 2.5f);
                addImage(image);
                break;
            case 3:
                image = new BodyImage("data/ninjaRunLeft.png", 2.5f);
                addImage(image);
                break;
            case 4:
                image = new BodyImage("data/ninjaTransparent.png", 2.5f);
                addImage(image);
                break;
            case 5:
                image = new BodyImage("data/ninjaDead.png", 2.5f);
                addImage(image);
                break;
            case 6:
                image = new BodyImage("data/ninjaAttack.png", 2.5f);
                addImage(image);
                break;
            case 7:
                image = new BodyImage("data/ninjaAttackLeft.png", 2.5f);
                addImage(image);
                break;

        }

    }

    /**
     * Method used to check if ninja win

     *
     * @param
     * @return return true or false
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Method used to set the win

     *
     * @param win true or false
     * @return
     */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     * Method used to check if ninja lose

     *
     * @param
     * @return return true or false
     */
    public boolean isLose() {
        return lose;
    }
    /**
     * Method used to set the lose

     *
     * @param lose true or false
     * @return
     */
    public void setLose(boolean lose) {
        this.lose = lose;
    }


    static {
        try {
            levelComplete = new SoundClip("data/levelComplete.wav");
            System.out.println("Loading level complete sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }


    static {
        try {
            shoot = new SoundClip("data/shoot.wav");
            System.out.println("Loading shoot sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    /**
     * Method that plays a sound when ninja shoots

     */
    public void shoot() {
        shoot.play();
    }


    static {
        try {
            gameOver = new SoundClip("data/gameOver.wav");
            System.out.println("Loading lose sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }


    static {
        try {
            won = new SoundClip("data/win.wav");
            System.out.println("Loading win sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    /**
     * Method that plays a sound when ninja lose

     */
    public void gameOver() {
        gameOver.play();
    }
    /**
     * Method that plays a sound when ninja win

     */
    public void win() {
        won.play();
    }
    /**
     * Method used to set the health

     *
     * @param health value that stored
     * @return
     */
    public void setHealth(int health) {
        this.health = health;
    }
    /**
     * Method used to decrement the wealth

     *
     * @param wealth value that will be stored
     * @return
     */
    public void setWealth(int wealth) {
        this.wealth = wealth;
    }
}