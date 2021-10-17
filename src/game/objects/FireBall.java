package game.objects;

import city.cs.engine.*;
/**
 * FireBall
 */
public class FireBall extends DynamicBody {

    private static final Shape ballShape = new CircleShape(0.25f);

    private static final BodyImage image =
            new BodyImage("data/fireBall.png", 0.5f);

    public FireBall(World world) {
        super(world, ballShape);
        addImage(image);
    }


}
