package game.collisions;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import game.levels.GameLevel;
import game.main.Game;
import game.objects.*;
import game.objects.platforms.Ground;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Class used to detect all the ninja collisions that can affect the game
 */

public class NinjaCollisionListener implements CollisionListener {
    /**
     * Ninja object
     */
    private Ninja ninja;
    /**
     * Level
     */
    private GameLevel level;
    /**
     * Game
     */
    private Game game;

    /**
     * Constructor used for initializing the collision.

     * @param  level,game,ninja
     * @return
     */
    public NinjaCollisionListener(GameLevel level, Game game, Ninja ninja) {
        this.ninja = ninja;
        this.level = level;
        this.game = game;
    }

    /**
     * Method that control the collisions

     * @param  e object used to call the collide methods
     * @return
     */
    @Override
    public void collide(CollisionEvent e) {
        //if ninja touches a coin, the coin will be destroyed and ninja wealth will be increased by 1
        if (e.getOtherBody() instanceof Coin) {
            ninja.incrementWealth();
            e.getOtherBody().destroy();
            System.out.println("Coins " + ninja.getWealth());
        } //if a ninja collides with ball or a lava  his health will be decreased by 10 points.
        else if (e.getOtherBody() instanceof Ball || e.getOtherBody() instanceof Lava && (!ninja.isLose() && (!ninja.isWin()))) {
            ninja.decrementHealth(10);
            System.out.println("Health " + ninja.getHealth());
            if (e.getOtherBody() instanceof Ball) {
                ((Ball) e.getOtherBody()).play();//sound played
            }
            ninja.changeImage(4);
            if (e.getOtherBody() instanceof Lava) {
                e.getOtherBody().destroy();//sound played
            }

            if (ninja.getHealth() < 1) {//check if ninja health is less than 1
                ninja.gameOver();
                ninja.removeAllCollisionListeners();
                ninja.changeImage(1);
                ninja.setLose(true);//if ninja health is less than 1, setLose will be set to true.
            }
            if (e.getOtherBody() instanceof Ball) {//in case of a collision with the ball, the ball will not stop but it will move to the side
                if (ninja.getPosition().x < e.getOtherBody().getPosition().x)
                    ((Ball) e.getOtherBody()).setLinearVelocity(new Vec2(15, 0));
                else
                    ((Ball) e.getOtherBody()).setLinearVelocity(new Vec2(-15, 0));

            }
        }
        //if ninja reaches the Win platform the variable setWin will be set to true.
        else if (e.getOtherBody() instanceof Door) {//condition used for going to next level
            if (Game.getCurrentLevel() == 4) {//check if all levels are completed
                ninja.setWin(true);
                ninja.win();
            }
            ninja.setLevelCompleted(true);
            game.goToNextLevel();


        } else if (e.getOtherBody() instanceof IceBall) {//if a ninja collides with iceball his health will be decreased by 10 points.
            if (Game.getCurrentLevel() != 3) {
                ninja.decrementHealth(10);
                ((IceBall) e.getOtherBody()).play();//sound played
                System.out.println("Health " + ninja.getHealth());
                ninja.changeImage(4);
                if (ninja.getHealth() < 1) {
                    ninja.gameOver();
                    ninja.removeAllCollisionListeners();
                    ninja.changeImage(1);
                    ninja.setLose(true);//if ninja health is less than 1, setLose will be set to true.
                }
            }
            if (Game.getCurrentLevel() == 2 || Game.getCurrentLevel() == 4) {//while in level 2 and 4 iceball will be also destroyed
                ninja.decrementHealth(10);
                System.out.println("Health " + ninja.getHealth());
                ninja.changeImage(4);
                e.getOtherBody().destroy();
                if (ninja.getHealth() < 1) {
                    ninja.gameOver();
                    ninja.removeAllCollisionListeners();
                    ninja.changeImage(1);
                    ninja.setLose(true);//if ninja health is less than 1, setLose will be set to true.
                }
            }
            if (Game.getCurrentLevel() == 3) {//in level 3 iceball will not decrease the ninja health
                e.getOtherBody().destroy();
            }


        }

    }
}

