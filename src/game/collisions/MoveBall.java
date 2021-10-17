package game.collisions;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.levels.GameLevel;
import game.objects.*;
import game.objects.platforms.LargePlatform;
import game.objects.platforms.LeftWall;
import game.objects.platforms.RightWall;
import org.jbox2d.common.Vec2;

/**
 * Class used to detect if the ball touches the wall
 */
public class MoveBall implements CollisionListener {
    private int count = 0;

    private Ball ball;
    private GameLevel gameLevel;
    private int currentLevel;
    private Ball newBall;

    public MoveBall(Ball ball, GameLevel gameLevel, int currentLevel) {
        this.ball = ball;
        this.gameLevel = gameLevel;
        this.currentLevel = currentLevel;
    }

    @Override
    public void collide(CollisionEvent e) {
        if(!gameLevel.getNinja().isLose()) {//this collision will work only if ninja is alive
            if (e.getOtherBody() instanceof RightWall) {//if ball hits the right wall its set linear velocity will be set
                ball.setLinearVelocity(new Vec2(-12, -2));
                ball.setDirection(-1);
                ball.play();

            } else if (e.getOtherBody() instanceof LeftWall) {///if ball hits the right wall its set linear velocity will be set
                ball.setLinearVelocity(new Vec2(10, -2));
                ball.setDirection(1);
                ball.play();

            }
            else if (e.getOtherBody() instanceof Ball) {//if a bass will collide with another ball, the ball will be destroyed
               e.getOtherBody().destroy();
               ball.setLinearVelocity(new Vec2(10,0));

            }
        }
    }


    //new ball is spawned then the ball touches the left wall
    void createBall() {

    }
}

