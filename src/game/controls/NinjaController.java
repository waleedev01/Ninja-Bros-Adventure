package game.controls;

import game.levels.GameLevel;
import game.main.Game;
import game.objects.Ninja;
import game.other.GameSaverLoader;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Class used to move the ninja
 */
public class NinjaController implements KeyListener {

    private static final float WALKING_SPEED = 5;
    private static final float JUMPING_SPEED = 13;
    private Ninja ninja;
    public Game game;

    public NinjaController(Game game, Ninja ninja) {
        this.ninja = ninja;
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //ninja will move only only if: his health is > 0; and if he didn't win or lose.
        if (ninja.getHealth() > 0 && (!ninja.isWin() && !ninja.isLose())) {
            if (code == KeyEvent.VK_A) {//if A is pressed, ninja will move to left
                ninja.startWalking(-WALKING_SPEED);
                ninja.changeImage(3);
            } else if (code == KeyEvent.VK_D) {//if D is pressed, ninja will move to right
                ninja.startWalking(WALKING_SPEED);
                ninja.changeImage(2);
            } else if (code == KeyEvent.VK_W) {//if W is pressed, ninja will jump
                Vec2 v = ninja.getLinearVelocity();
                if (Math.abs(v.y) < 0.01f) {//it will jump only if he's not already jumping
                    if (Game.getCurrentLevel() == 1)//in level1 the jump will be lower
                        ninja.jump(JUMPING_SPEED - 2);
                    else
                        ninja.jump(JUMPING_SPEED);
                }
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        //if A is released than ninja will stop Walking
        if (code == KeyEvent.VK_A) {
            ninja.stopWalking();
            ninja.setLinearVelocity(new Vec2(0, 0));
            ninja.changeImage(1);
        } //if D is released than ninja will stop Walking
        else if (code == KeyEvent.VK_D) {
            ninja.stopWalking();
            ninja.setLinearVelocity(new Vec2(0, 0));
            ninja.changeImage(1);
        } //if W is released than ninja will stop Jumping
        else if (code == KeyEvent.VK_W) {
            ninja.stopWalking();
            ninja.setLinearVelocity(new Vec2(0, -6));//ninja will fall to the ground a little faster
            ninja.changeImage(1);
        }


    }

    //update player
    public void updateNinja(Ninja ninja) {
        this.ninja = ninja;
    }


}
