package game.objects;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * Cannon
 */
public class Cannon extends DynamicBody {
    private static final Shape cannonShape = new PolygonShape(-2.0f, -2.12f, 2.16f, -2.15f, 1.85f, 1.82f, -2.1f, 1.78f
    );
    private int health;
    private static final BodyImage image = new BodyImage("data/military.png", 6f);
    private static SoundClip destroy;


    public Cannon(World w) {
        super(w, cannonShape);
        addImage(image);
        health = 100;
    }

    public int getHealth() {
        return health;
    }

    public void decrementHealth() {
        health -= 25;
    }


    static {
        try {
            destroy = new SoundClip("data/destroyCannon.wav");
            System.out.println("Loading cannon sound");
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
