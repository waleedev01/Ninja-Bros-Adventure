package game.collisions;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.objects.Ball;
import game.objects.platforms.BounceBackPlatforms;
import game.objects.platforms.BounceFrontPlatforms;
import org.jbox2d.common.Vec2;

/**
 * Class used to detect if the ball touches the small Platforms
 */
public class BounceBack implements CollisionListener {

    private Ball ball;
    public BounceBack(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof BounceBackPlatforms) {
            ball.setLinearVelocity(new Vec2(-15, 0));
        } else if (e.getOtherBody() instanceof BounceFrontPlatforms) {
            ball.setLinearVelocity(new Vec2(15, 0));
        }

    }
}

