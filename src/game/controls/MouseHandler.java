package game.controls;

import game.collisions.FireBallDestroy;
import game.main.Game;
import game.objects.Ninja;
import game.other.GameView;
import game.levels.GameLevel;
import game.objects.FireBall;
import game.objects.Shooter;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Class used to shoot when mouse is pressed
 */
public class MouseHandler extends MouseAdapter {

    private GameView view;
    private Ninja ninja;

    public MouseHandler(GameView view, Ninja ninja) {
        this.view = view;
        this.ninja = ninja;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!ninja.isLose()) {
            if (Game.getCurrentLevel() > 1) {//mouseHandler will not be used in level1
                //create FireBalls and add them to world
                FireBall ball = new FireBall(view.getWorld());
                ninja.incrementTotalShoot();//increment the total number of shoots in the Ninja Class
                ninja.changeImage(6);
                //get the mouse coordinates
                //make a collision listener on the fireball
                FireBallDestroy collision = new FireBallDestroy(ball);//collision added to the fireball
                ball.addCollisionListener(collision);

                Point mousePoint = e.getPoint();//mouse point coordinate
                //transform them to world coordinates
                Vec2 worldPoint = view.viewToWorld(mousePoint);
                //check if the worldPoint is left or to the right of the ninja
                if (worldPoint.x < ninja.getPosition().x)
                    ball.setPosition(ninja.getPosition().add(new Vec2(-1f, 0.5f)));
                if (worldPoint.x > ninja.getPosition().x)
                    ball.setPosition(ninja.getPosition().add(new Vec2(1f, 0.5f)));

                Vec2 enemy = ninja.getPosition();
                Vec2 v = worldPoint.sub(enemy);
                v.normalize();
                ball.setLinearVelocity(v.mul(15.0f));//ball will shoot at this speed
                ball.setGravityScale(0);//no gravity
                if (worldPoint.x < ninja.getPosition().x) {
                    ninja.changeImage(7);//change the image if ninja shoots at left
                }
                    ninja.shoot();//sound will be played
            }
        }
    }

    //update ninja
    public void updateNinja(Ninja ninja) {
        this.ninja = ninja;
    }

}
