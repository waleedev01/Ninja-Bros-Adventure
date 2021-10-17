package game.objects;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * Volcano
 */
public class Volcano extends StaticBody {

    private static final Shape volcanoShape = new PolygonShape(-3.69f, -5.57f, 3.99f, -5.74f, 5.31f, 5.81f, -4.51f, 5.69f);
    private static final BodyImage image = new BodyImage("data/volcano.png", 12f);
    private static SoundClip eruption;

    public Volcano(World w) {
        super(w, volcanoShape);
        addImage(image);
    }


    static {
        try {
            eruption = new SoundClip("data/volcano.wav");
            System.out.println("Loading volcano sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void eruption() {
        eruption.play();
    }
}
