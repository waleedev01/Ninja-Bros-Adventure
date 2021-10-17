package game.collisions;

import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.objects.*;
import game.objects.platforms.*;
import org.jbox2d.common.Vec2;

/**
 * Class used to detect if the FireBall touches any of the instances in the if statement.
 */

public class FireBallDestroy implements CollisionListener {

    private FireBall ball;

    public FireBallDestroy(FireBall b) {
        this.ball = b;
    }


    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof RightWall
                || e.getOtherBody() instanceof LeftWall
                || e.getOtherBody() instanceof Door
                || e.getOtherBody() instanceof Ground
        ) {
            ball.destroy();
        } else if (e.getOtherBody() instanceof Ball) {
            ball.destroy();//functionality used in level 2, when the fireball hit two times the ball, the ball will be destroyed
            ((Ball) e.getOtherBody()).decrementHealth();
            if (((Ball) e.getOtherBody()).getHealth() <= 0) {
                e.getOtherBody().destroy();
            }
        } else if (e.getOtherBody() instanceof IceBall) {
            e.getOtherBody().destroy();//in case of a collision between fireball and iceball, both will be destroyed
            ((IceBall) e.getOtherBody()).level2();
            ball.destroy();
        } //if a fireball hits a shooter, shooter health will be decreased
        else if (e.getOtherBody() instanceof Shooter) {
            ((Shooter) e.getOtherBody()).decrementHealth(20);
            e.getReportingBody().destroy();
            System.out.println("shooter health" + ((Shooter) e.getOtherBody()).getHealth());
            if (((Shooter) e.getOtherBody()).getHealth() < 1) {//check if shooter health is less than 1
                e.getOtherBody().removeAllCollisionListeners();
                e.getOtherBody().removeAllImages();
                BodyImage image = new BodyImage("data/shooterDead.png", 2.5f);
                ((Shooter) e.getOtherBody()).setAlive(false);
                e.getOtherBody().addImage(image);
                e.getOtherBody().destroy();
            }

        } else if (e.getOtherBody() instanceof Coin) {//this collision is used for not moving the coin in case of collision
            ball.destroy();
            e.getOtherBody().move(new Vec2(0, 0));
            ((Coin) e.getOtherBody()).setLinearVelocity(new Vec2(0, 0));//coin will not move
            ((Coin) e.getOtherBody()).setGravityScale(0);//coin will not fall
        } else if (e.getOtherBody() instanceof Cannon) {//functionality used in level 3 to destroy the cannon
            ((Cannon) e.getOtherBody()).decrementHealth();
            ball.destroy();
            if (((Cannon) e.getOtherBody()).getHealth() < 1)
                e.getOtherBody().destroy();
        } else if (e.getOtherBody() instanceof Lava || e.getOtherBody() instanceof FireBall) {//functionality used in level 3 and for to destroy both lava and fireball in case of contact
            ball.destroy();
            e.getOtherBody().destroy();
        } else if (e.getOtherBody() instanceof LargePlatform || e.getOtherBody() instanceof Volcano) {//functionality used in level 3 to destroy the fireball
            ball.destroy();
        }
        //used to decrement the enemy health in level 3
        else if(e.getOtherBody() instanceof Enemy){
            ((Enemy) e.getOtherBody()).decrementHealth();;
            ball.destroy();
            System.out.println("enemy health:"+((Enemy) e.getOtherBody()).getHealth());
            if (((Enemy) e.getOtherBody()).getHealth() <= 0) {//check if enemy health is less than 1
                e.getOtherBody().destroy();
            }
        }




    }

}

