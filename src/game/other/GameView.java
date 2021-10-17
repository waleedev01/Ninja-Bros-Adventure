package game.other;

import city.cs.engine.UserView;
import city.cs.engine.World;
import game.levels.GameLevel;
import game.main.Game;
import game.objects.Enemy;
import game.objects.Ninja;
import game.objects.Shooter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.io.IOException;

/**
 * Creating a view with a background.
 */
public class GameView extends UserView {
    /**
     * Array of background images
     */
    private Image[] backgrounds = new Image[5];//array of images
    /**
     * Player
     */
    private Ninja ninja;
    /**
     * Shooter
     */
    private Shooter shooter;
    /**
     * Enemy
     */
    private Enemy enemy;


    /**
     * Constructor used for initializing the view size and the image and some bodies.

     * @param  w,width,height,n,gl,s,e
     * @return
     */
    public GameView(World w, int width, int height, Ninja n, int gl, Shooter s, Enemy e) {
        super(w, width, height);
        backgrounds[1] = new ImageIcon("data/level1.jpg").getImage();
        backgrounds[2] = new ImageIcon("data/level2.png").getImage();
        backgrounds[3] = new ImageIcon("data/level3.png").getImage();
        backgrounds[4] = new ImageIcon("data/level4.png").getImage();
        ninja = n;
        shooter = s;
        enemy = e;
    }

    /**
     * Overridable method called to paint the background.

     * @param  g object used to call the paintBackground methods
     * @return
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        //background will change in every level
        g.drawImage(backgrounds[Game.getCurrentLevel()], 0, 0, this);
    }
    /**
     * Overridable method called to paint the Foreground.

     * @param  g object used to call the paintForeground methods
     * @return
     */
    protected void paintForeground(Graphics2D g) {

        Image win = new ImageIcon("data/win.png").getImage();
        Image gameOver = new ImageIcon("data/gameOver.gif").getImage();

        g.setColor(Color.white);//setting the text color to white
        if (Game.getCurrentLevel() < 4)
            g.drawString("Coins = " + ninja.getWealth(), 20, 20);
        g.drawString("Health = " + ninja.getHealth(), 110, 20);

        g.drawString("Level = " + Game.getCurrentLevel(), 210, 20);

        //if ninja will win or lose it will print an image or a gif.
        if (ninja.isWin()) {
            g.drawImage(win, 200, 250, this);
        } else if (ninja.isLose()) {
            g.drawImage(gameOver, 180, 250, this);
        }
        //enemy life bar
        if (Game.getCurrentLevel() == 2) {
            g.drawString("Enemy life", 603, 36);
            g.draw3DRect(670, 22, 100, 20, false);
            try {
                int percentage = ((100 * shooter.getHealth()) / 100);
                g.fillRect(670, 22, percentage, 20);
            } catch (NullPointerException e) {
            }

        }
        //enemy life bar
        if (Game.getCurrentLevel() == 3) {
            g.drawString("Enemy life", 603, 36);
            g.draw3DRect(670, 22, 100, 20, false);
            try {
                int percentage = ((100 * enemy.getHealth()) / 100);
                g.fillRect(670, 22, percentage, 20);
            } catch (NullPointerException e) {

            }
        }
        //enemy life bar
        if (Game.getCurrentLevel() == 4) {
            try {
                g.drawString("Enemy life", 603, 20);
                g.drawString("Total shoots = " + ninja.getTotalShoot(), 10, 20);
                g.draw3DRect(670, 10, 100, 20, false);
                int percentage = ((100 * shooter.getHealth()) / 100);
                g.fillRect(670, 10, percentage, 20);
            } catch (NullPointerException e) {

            }
        }
    }

    /**
     * method that updates the ninja

     * @param  ninja updated ninja is copied in the ninja variable
     * @return
     */
    public void updateNinja(Ninja ninja) {
        this.ninja = ninja;
    }
    /**
     * method that updates the shooter

     * @param  shooter updated shooter is copied in the shooter variable
     * @return
     */
    public void updateShooter(Shooter shooter) {
        this.shooter = shooter;
    }
    /**
     * method that updates the enemy

     * @param  enemy updated enemy is copied in the enemy variable
     * @return
     */
    public void updateEnemy(Enemy enemy) {
        this.enemy = enemy;
    }


}
