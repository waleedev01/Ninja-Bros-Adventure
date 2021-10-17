package game.objects;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * IceBall
 */
public class IceBall extends DynamicBody {

    private static final Shape ballShape = new CircleShape(0.25f);
    private static final BodyImage image = new BodyImage("data/iceBall.png", 0.5f);
    private static SoundClip ninjaCollision;
    private static SoundClip destroy;


    public IceBall(World world) {
        super(world, ballShape);
        addImage(image);
    }

    static {
        try {
            ninjaCollision = new SoundClip("data/iceBallCollision1.wav");
            System.out.println("Loading iceball sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void play() {
        ninjaCollision.play();
    }


    static {
        try {
            destroy = new SoundClip("data/destroy.wav");
            System.out.println("Loading iceball2 sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void level2() {
        destroy.play();
    }

}
