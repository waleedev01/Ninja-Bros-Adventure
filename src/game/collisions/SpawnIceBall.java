package game.collisions;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.levels.GameLevel;
import game.objects.*;
import game.objects.platforms.*;
import org.jbox2d.common.Vec2;

/**
 * Class used to spawn or destroy ice ball when collisions happens
 */
public class SpawnIceBall implements CollisionListener {
    private int count = 0;

    private IceBall ball;
    private GameLevel gameLevel;
    private int currentLevel;

    public SpawnIceBall(IceBall ball, GameLevel gameLevel, int currentLevel) {
        this.ball = ball;
        this.gameLevel = gameLevel;
        this.currentLevel = currentLevel;
    }

    int xPos = 0;

    @Override
    public void collide(CollisionEvent e) {//if an iceball touches the BallSpawnPlatform or Ninja a new ice ball will be spawned
        if (currentLevel == 1 && !gameLevel.getNinja().isLose())
            if (e.getOtherBody() instanceof BallSpawnPlatform || e.getOtherBody() instanceof Ninja) {
                ball.destroy();
                IceBall iceBall = new IceBall(gameLevel);
                iceBall.setPosition(new Vec2(ball.getPosition().add(new Vec2(0, 6.2f))));
                xPos += 4;
                SpawnIceBall spawn = new SpawnIceBall(iceBall, gameLevel, 1);
                iceBall.addCollisionListener(spawn);
            }

        if (currentLevel == 2 || currentLevel == 4) {
            if (e.getOtherBody() instanceof LeftWall || e.getOtherBody() instanceof RightWall || e.getOtherBody() instanceof Ground || e.getOtherBody() instanceof IceBall) {
                ball.destroy();
            }
        }

        if (currentLevel == 3 || currentLevel==4) {
            if (e.getOtherBody() instanceof LeftWall || e.getOtherBody() instanceof RightWall ||
                    e.getOtherBody() instanceof Lava || e.getOtherBody() instanceof LargePlatform || e.getOtherBody() instanceof Ground ||
                    e.getOtherBody() instanceof Volcano || e.getOtherBody() instanceof Ninja
            || e.getOtherBody() instanceof Door) {
                ball.destroy();

                if (e.getOtherBody() instanceof Lava) {
                    e.getOtherBody().destroy();
                }
            }
        }


    }
}




