package game.levels;

import city.cs.engine.*;
import city.cs.engine.Shape;


import game.collisions.MoveBall;
import game.collisions.NinjaCollisionListener;
import game.collisions.SpawnIceBall;
import game.main.Game;
import game.objects.*;
import game.objects.platforms.LargePlatform;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Level2 class
 */
public class Level2 extends GameLevel implements ActionListener {
    private Timer timer;
    private Timer timer2;

    public Level2(Game game) {
        super(game);
        //get bodies
        getDoor().setPosition(new Vec2(-18f, 13.9f));
        //create the shape for the large platforms

        Shape largePlatformShape = new BoxShape(17f, 0.7f);
        int yPos = 0;//variable used to change the yPos of the platforms
        for (int i = 1; i <= 5; i++) {
            StaticBody level = new LargePlatform(this, largePlatformShape);
            level.setPosition(new Vec2(-4, -12.2f + yPos));
            if (i % 2 == 0) {
                level.setPosition(new Vec2(4, -12.2f + yPos));
            }
            yPos += 6;
        }

        //timer to spawn a new ball every 10 seconds
        timer2 = new Timer(5000, this);
        timer2.setInitialDelay(10000);
        timer2.start();
        timer2.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (Game.getCurrentLevel() == 2)
                    if (Ball.getNumberOfInstances() < 10) {
                        Ball newBall = new Ball(game.getLevel());
                        newBall.setLinearVelocity(new Vec2(10, 0));
                        newBall.setPosition(new Vec2(-11, 13.6f));
                        //add collision to the ball
                        MoveBall collision = new MoveBall(newBall, game.getLevel(), 2);
                        newBall.addCollisionListener(collision);
                    }
            }
        });

        //timer to shot every 1.5 seconds
        timer = new Timer(1500, this);
        timer.setInitialDelay(100);
        timer.start();
    }

    //implementation of the timer
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (getShooter().isAlive()) {
                IceBall iceBall = new IceBall(this);
                iceBall.setPosition(this.getShooter().getPosition().add(new Vec2(-1, -0.1f)));//iceball be set near the shooter
                //transform them to world coordinates
                //position the books
                Vec2 enemy = this.getShooter().getPosition();//the shooter is the enemy
                Vec2 player = this.getNinja().getPosition();//the ninja is the player
                Vec2 v = player.sub(enemy);
                v.normalize();
                iceBall.setLinearVelocity(v.mul(10.0f));//iceball will move
                iceBall.setGravityScale(0);//iceball will not fall
                SpawnIceBall collision = new SpawnIceBall(iceBall, this, 2);//collision is added to the iceball
                iceBall.addCollisionListener(collision);
                if (this.getNinja().isLose()) {//remove collision of ninja if he is dead
                    this.getNinja().removeAllCollisionListeners();
                    this.getShooter().removeAllCollisionListeners();
                }
            }
        } catch (NullPointerException e) {

        }

    }

    //making non persistent objects
    @Override
    public void populate(Game game) {
        super.populate(game);
        //making objects
        getNinja().setPosition(new Vec2(-17, -17));
        getShooter().setPosition(new Vec2(10, -17));
        getNinja().changeImage(1);

        //add collision listener
        NinjaCollisionListener ninjaCollision = new NinjaCollisionListener(this, game, getNinja());
        getNinja().addCollisionListener(ninjaCollision);

        //make cannon
        Cannon cannon = new Cannon(this);
        cannon.setPosition(new Vec2(-12, 14.6f));

        //make ball
        Ball ball = new Ball(this);
        ball.setLinearVelocity(new Vec2(10, 0));
        ball.setPosition(cannon.getPosition().add(new Vec2(1, -1)));
        //add collision to ball
        MoveBall collision = new MoveBall(ball, this, 2);
        ball.addCollisionListener(collision);

        //make coins
        Coin[] coins = new Coin[9];
        Shape coinShape = new CircleShape(0.5f);
        BodyImage image =
                new BodyImage("data/coin.png", 1f);
        //set the gravity of the 3 coins to 0 so they don't fall.
        int xPos = 0;//change coins xPos
        int yPos = 0;//change coins yPos
        //nested loop to create Coin and set their position
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                coins[j] = new Coin(this, coinShape, image);
                coins[j].setGravityScale(0);
                coins[j].setPosition(new Vec2(xPos - 10, -8 + yPos));
                xPos += 10;
            }
            xPos = 0;
            yPos += 6;
        }
    }


    @Override
    public boolean isComplete() {
        if (getNinja().isLevelCompleted()) {
            return true;
        } else return false;
    }

    public String getLevelName() {
        return "Level2";
    }

}
