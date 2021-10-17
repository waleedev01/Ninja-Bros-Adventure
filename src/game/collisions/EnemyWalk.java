package game.collisions;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import city.cs.engine.Walker;
import game.levels.GameLevel;
import game.objects.Coin;
import game.objects.Enemy;
import game.objects.platforms.Ground;
import org.jbox2d.common.Vec2;

import java.util.Random;
/**
 * Class used to move the Enemy using a collision with the ground
 */
public class EnemyWalk implements CollisionListener {

    private Ground ground;
    private GameLevel gameLevel;

    public EnemyWalk(Ground ground, GameLevel gameLevel){
        this.ground = ground;
        this.gameLevel = gameLevel;
    }


    @Override
    public void collide(CollisionEvent e) {
        if(e.getOtherBody() instanceof Enemy){
            float random1 = 5 + new Random().nextInt(4);//a random number will allow a random jump
            ((Enemy) e.getOtherBody()).setLinearVelocity(new Vec2(-3,random1));
        }

    }
}
