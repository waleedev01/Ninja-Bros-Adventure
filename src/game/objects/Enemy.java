package game.objects;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * Enemy
 */
public class Enemy extends Walker {
    private static final Shape enemyShape = new PolygonShape(-2.12f, -4.38f, -1.33f, 3.35f, 0.27f, 4.1f, 2.26f, 2.37f, 2.84f, -4.3f
    );
    private boolean alive = false;
    private int health = 100;
    private static int numberOfEnemies = 0;
    private static final BodyImage image = new BodyImage("data/enemyIdle.png", 10f);
    private static SoundClip destroy;


    public Enemy(World world) {
        super(world, enemyShape);
        addImage(image);
        numberOfEnemies++;
    }


    public int getHealth() {
        return health;
    }

    public void decrementHealth() {
        health -= 20;
    }


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public static int getNumberOfEnemies() {
        return numberOfEnemies;
    }


    static {
        try {
            destroy = new SoundClip("data/destroyCannon.wav");
            System.out.println("Loading enemy sound");
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
}
