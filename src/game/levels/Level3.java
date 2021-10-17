package game.levels;

import city.cs.engine.*;
import city.cs.engine.Shape;


import game.collisions.*;
import game.main.Game;
import game.objects.*;
import game.objects.platforms.LargePlatform;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
/**
 * Level3 Class
 */
public class Level3 extends GameLevel implements ActionListener {
    private Timer timer;

    public Level3(Game game) {
        super(game);
        //get bodies
        getDoor().setPosition(new Vec2(-18f, 12.4f));


        //create the shape for the small and large platforms
        Shape largePlatformShape = new BoxShape(17f, 0.7f);

        StaticBody level = new LargePlatform(this, largePlatformShape);
        level.setPosition(new Vec2(-8, 10.2f));


        //make small platforms
        Shape smallPlatform = new BoxShape(2, 0.4f);
        StaticBody shooterBase = new LargePlatform(this, smallPlatform);
        shooterBase.setPosition(new Vec2(-19, 2));


        int yPos = 0;//var used to change the y post of the smallPlatform
        for (int i = 0; i < 4; i++) {
            StaticBody small = new LargePlatform(this, smallPlatform);
            small.setPosition(new Vec2(18, -10.2f + yPos));
            if (i % 2 == 0) {
                small.setPosition(new Vec2(12, -10.2f + yPos));
            }
            yPos += 6;
        }
        //enemy collision added
        EnemyWalk action = new EnemyWalk(getGround(), this);
        getGround().addCollisionListener(action);

        //making timer to shoot every 0,5 seconds
        timer = new Timer(500, this);
        timer.setInitialDelay(100);
        timer.start();
    }

    //making non persistent objects
    public void populate(Game game) {
        super.populate(game);

        //making objects
        getNinja().setPosition(new Vec2(-17, -17));
        getShooter().setPosition(new Vec2(-18, 3));
        getShooter().setImage();
        getNinja().changeImage(1);

        //add collision listener to ninja
        NinjaCollisionListener gemPickup = new NinjaCollisionListener(this, game, getNinja());
        getNinja().addCollisionListener(gemPickup);

        //make volcano
        Volcano volcano = new Volcano(this);
        volcano.setPosition(new Vec2(8, -11));
        volcano.eruption();//play sound


        //make lavas
        Lava[] balls = new Lava[40];
        for (int i = 0; i < balls.length; i++) {
            //generate random values
            float random1 = -15 + new Random().nextInt(25);
            float random2 = 2 + new Random().nextInt(31);
            balls[i] = new Lava(this);
            balls[i].setPosition(new Vec2(volcano.getPosition().x, volcano.getPosition().y + 7));
            balls[i].setLinearVelocity(new Vec2(random1, random2));//set random linear velocity
            SpawnLava spawn = new SpawnLava(this, balls[i]);
            balls[i].addCollisionListener(spawn);
        }


        //make coins
        Coin[] coins = new Coin[3];
        BodyImage coinImage =
                new BodyImage("data/coin.png", 2f);
        Shape coinShape = new CircleShape(1f);
        //Create and set the gravity of the 3 coins to 0 so they don't fall.
        int xPos = 0;
        for (int i = 0; i < 3; i++) {
            coins[i] = new Coin(this, coinShape, coinImage);
            coins[i].setGravityScale(0);
            coins[i].setPosition(new Vec2(xPos - 10, 12.5f));
            xPos += 9;
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (getShooter().isAlive() && !getEnemy().isAlive()) {
                IceBall iceBall = new IceBall(this);
                iceBall.setPosition(this.getShooter().getPosition().add(new Vec2(+1, -0.1f)));//iceball be set near the shooter
                //transform them to world coordinates
                //position the books
                float random1 = -12 + new Random().nextInt(25);
                float random2 = -7 + new Random().nextInt(15);
                Vec2 enemy = this.getShooter().getPosition();//the shooter is the enemy
                Vec2 player = new Vec2(random1, random2);//the ninja is the player
                Vec2 v = player.sub(enemy);
                v.normalize();
                iceBall.setLinearVelocity(v.mul(10.0f));//iceball will move
                iceBall.setGravityScale(0);//iceball will not fall

                SpawnIceBall collision = new SpawnIceBall(iceBall, this, 3);//collision is added to the iceball
                iceBall.addCollisionListener(collision);
                if (this.getNinja().isLose()) {//remove collision of ninja if he is dead
                    this.getNinja().removeAllCollisionListeners();
                    this.getShooter().removeAllCollisionListeners();
                }
            }
        } catch (NullPointerException e) {

        }

    }

    @Override
    public boolean isComplete() {
        if (getNinja().isLevelCompleted()) {
            return true;
        } else return false;
    }

    public String getLevelName() {
        return "Level3";
    }


}
