package game.collisions;

import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.levels.GameLevel;
import game.objects.*;
import game.objects.platforms.*;
import org.jbox2d.common.Vec2;

import java.util.Random;

/**
 * Class used to spawn or destroy lava when collisions happens
 */
public class SpawnLava implements CollisionListener {
    private Lava ball;
    private GameLevel gameLevel;

    public SpawnLava(GameLevel gameLevel, Lava ball) {
        this.gameLevel = gameLevel;
        this.ball = ball;
    }


    @Override
    public void collide(CollisionEvent e) {

        if (e.getOtherBody() instanceof Ground || e.getOtherBody() instanceof Ninja || e.getOtherBody() instanceof BallSpawnPlatform || e.getOtherBody() instanceof Volcano ||
                e.getOtherBody() instanceof LargePlatform || e.getOtherBody() instanceof LeftWall || e.getOtherBody() instanceof RightWall) {
            if (Lava.getNumberOfLava() < 70) {//lava will be spawn only if the number of instances are less than 70
                //generate random values from 0-24
                ball.destroy();
                createLava();
            } else {
                if (e.getOtherBody() instanceof Volcano) {//when there will be more than 70 instances volcano will be destroyed and a big enemy will be spawn
                    e.getOtherBody().destroy();
                    e.getReportingBody().destroy();
                    gameLevel.createEnemy();

                    ball.destroy();
                    ((Volcano) e.getOtherBody()).eruption();
                }
                ball.destroy();
            }
        } else if (e.getOtherBody() instanceof Shooter) {//if shooter will be hit by a lava both will be destroyed
            ((Shooter) e.getOtherBody()).decrementHealth(100);
            e.getReportingBody().destroy();
            if (((Shooter) e.getOtherBody()).getHealth() < 1) {//check if shooter is dead
                e.getOtherBody().removeAllCollisionListeners();
                e.getOtherBody().removeAllImages();
                BodyImage image = new BodyImage("data/shooterDead.png", 2.5f);
                ((Shooter) e.getOtherBody()).setAlive(false);
                e.getOtherBody().addImage(image);
                e.getOtherBody().destroy();
            }

        }

    }


    /*
    method used to spawn new lava in random positions
     */
    void createLava() {

        float random1 = -15 + new Random().nextInt(25);
        float random2 = new Random().nextInt(31);
        Lava balls = new Lava(gameLevel);
        balls.setPosition(new Vec2(8, -4));
        balls.setLinearVelocity(new Vec2(random1, random2));
        SpawnLava spawn = new SpawnLava(gameLevel, balls);
        balls.addCollisionListener(spawn);
    }


}
