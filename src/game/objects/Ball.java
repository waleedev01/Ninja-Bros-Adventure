package game.objects;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * Ball
 */
public class Ball extends DynamicBody {

    private int health = 100;
    private static int numberOfInstances = 0;
    private static final Shape ballShape = new CircleShape(1f);
    private static SoundClip ninjaCollision;
    private static final BodyImage image = new BodyImage("data/ball.png", 2f);
    private static SoundClip spawn;
    private static SoundClip destroy;
    private int direction=1;


    public Ball(World world) {
        super(world, ballShape);
        addImage(image);
        numberOfInstances++;
    }
    //method used to get number of instances
    public static int getNumberOfInstances() {
        return numberOfInstances;
    }

    public int getHealth() {
        return health;
    }

    public void decrementHealth() {
        health -= 50;
    }


    static {
        try {
            ninjaCollision = new SoundClip("data/ballCollision.wav");
            System.out.println("Loading books sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void play() {
        ninjaCollision.play();
    }


    static {
        try {
            spawn = new SoundClip("data/spawnBall.wav");
            System.out.println("Loading ball sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void spawn() {
        spawn.play();
    }


    static {
        try {
            destroy = new SoundClip("data/destroy.wav");
            System.out.println("Loading ball sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void destroy() {
        destroy.play();
        super.destroy();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


}
