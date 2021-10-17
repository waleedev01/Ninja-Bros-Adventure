package game.objects;

import city.cs.engine.*;
/**
 * Door
 */
public class Door extends StaticBody {
    private static final Shape gemShape = new PolygonShape(-0.81f,-1.45f, 0.91f,-1.43f, 0.92f,0.94f, 0.15f,1.47f, -0.82f,1.07f
    );

    private static final BodyImage image =
            new BodyImage("data/door.png", 3f);

    public Door(World world) {
        super(world, gemShape);
        addImage(image);
    }
}
