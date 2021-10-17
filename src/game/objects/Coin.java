package game.objects;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
/**
 * Coin
 */
public class Coin extends DynamicBody {
    private static SoundClip coinPickupSound;

    public Coin(World world, Shape coinShape, BodyImage image) {
        super(world, coinShape);
        addImage(image);
    }


    static {
        try {
            coinPickupSound = new SoundClip("data/coinPickup.wav");
            System.out.println("Loading coin sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void destroy() {
        coinPickupSound.play();
        super.destroy();
    }
}
