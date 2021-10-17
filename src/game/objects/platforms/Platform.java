package game.objects.platforms;

import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 * Class used to make platforms
 */
public class Platform extends StaticBody {
    public Platform(World w, Shape s) {
        super(w, s);
    }
}
