package game.collisions;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.levels.GameLevel;
import game.main.Game;
import game.objects.*;
import org.jbox2d.common.Vec2;
/**
 * Class used in level3 to attack the NINJA
 */
public class EnemyAttack implements CollisionListener {
    private Enemy enemy;
    private GameLevel gameLevel;

    public EnemyAttack(Enemy enemy, GameLevel gameLevel) {
        this.enemy = enemy;
        this.gameLevel = gameLevel;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Ninja) {//if enemy collides with ninja, ninja health will be decremented
            ((Ninja) e.getOtherBody()).decrementHealth(50);
            ((Ninja) e.getOtherBody()).setLinearVelocity(new Vec2(-15, 0));
            if (((Ninja) e.getOtherBody()).getHealth() < 1) {//check if ninja health is less than 1
                ((Ninja) e.getOtherBody()).changeImage(1);
                ((Ninja) e.getOtherBody()).setLose(true);//if ninja health is less than 1, setLose will be set to true.
                e.getOtherBody().destroy();
                ((Ninja) e.getOtherBody()).gameOver();
                enemy.removeAllCollisionListeners();
            }
        }
        if (e.getOtherBody() instanceof Lava || e.getOtherBody() instanceof IceBall) {//destroy the ball that hits the Enemy
            e.getOtherBody().destroy();
        }
    }
}
