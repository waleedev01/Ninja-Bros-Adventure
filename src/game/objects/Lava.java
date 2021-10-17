package game.objects;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * Lava
 */
public class Lava extends DynamicBody {
    private static int numberOfLava = 0;
    private static final Shape lavaShape = new PolygonShape(-0.182f, -0.334f, 0.194f, -0.31f, 0.256f, -0.088f, 0.016f, 0.388f, -0.226f, -0.062f);
    private static final BodyImage image = new BodyImage("data/lava.png", 1f);
    private static SoundClip destroy;


    public Lava(World world) {
        super(world, lavaShape);
        addImage(image);
        numberOfLava++;
    }

    public static int getNumberOfLava() {
        return numberOfLava;
    }

    static {
        try {
            destroy = new SoundClip("data/destroy.wav");
            System.out.println("Loading lava sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void destroy() {
        destroy.play();
        super.destroy();
    }

}
