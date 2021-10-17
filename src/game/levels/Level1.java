package game.levels;

import city.cs.engine.*;
import city.cs.engine.Shape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import game.collisions.BounceBack;
import game.collisions.NinjaCollisionListener;
import game.collisions.SpawnIceBall;
import game.main.Game;
import game.objects.*;
import game.objects.platforms.BallSpawnPlatform;
import game.objects.platforms.BounceBackPlatforms;
import game.objects.platforms.BounceFrontPlatforms;
import game.objects.platforms.Platform;
import org.jbox2d.common.Vec2;

import javax.swing.*;

/**
 * Level1 class
 */
public class Level1 extends GameLevel {
    public Level1(Game game) {
        super(game);

        //create the shape for the small and large platforms
        Shape platformShape = new BoxShape(10f, 0.7f);
        Shape smallPlatformShape = new BoxShape(2, 0.7f);
        getDoor().setPosition(new Vec2(-18f, 15f));


        int yPos = 0;//variable used for moving the yPos of the platforms.
        //make 3 platforms of the same shape but with different angleDegrees and yPos
        for (int i = 0; i < 3; i++) {
            StaticBody platform = new BallSpawnPlatform(this, platformShape);
            platform.setPosition(new Vec2(-1, -10 + yPos));
            if (i == 0)//the middle platform will have a negative angle.
                platform.setAngleDegrees(-3);
            else if (i == 1)
                platform.setAngleDegrees(3);
            yPos += 10;

        }

        //make three small platforms.
        StaticBody smallPlatform1 = new Platform(this, smallPlatformShape);
        StaticBody smallPlatform2 = new Platform(this, smallPlatformShape);
        StaticBody smallPlatform3 = new Platform(this, smallPlatformShape);


        //setting the position for the 3 small platforms.
        smallPlatform1.setPosition(new Vec2(17, -12.8f));
        smallPlatform2.setPosition(new Vec2(-15.2f, -5f));
        smallPlatform3.setPosition(new Vec2(13.5f, 4.5f));

        //make the shape for the last platform
        Shape nextLevelBase = new BoxShape(3, 0.7f);

        //make the platform to advance to the next level
        StaticBody lastStep = new Platform(this, nextLevelBase);
        lastStep.setPosition(new Vec2(-18, 12.8f));

        //Creating a body image that will save a transparent image
        BodyImage image = new BodyImage("data/transparent.png", 1f);

        //making the ball and the collision listener
        Ball[] balls = new Ball[2];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(this);
            balls[i].setLinearVelocity(new Vec2(15, 0));
            BounceBack collision = new BounceBack(balls[i]);
            balls[i].addCollisionListener(collision);
        }
        balls[0].setPosition(new Vec2(-9, -8));
        balls[1].setPosition(new Vec2(7, 1));

        //making the iceballs and the collision listener
        IceBall[] iceBalls = new IceBall[5];
        int xPos = 0;//variable used to change the xPos
        for (int i = 0; i < iceBalls.length; i++) {
            iceBalls[i] = new IceBall(this);
            iceBalls[i].setPosition(new Vec2(-9 + xPos, 16.5f));
            xPos += 4;
            SpawnIceBall spawn = new SpawnIceBall(iceBalls[i], this, 1);
            iceBalls[i].addCollisionListener(spawn);
        }

        //make a shape for small platforms that will be used by the ball to bounce back
        Shape bounceShape = new BoxShape(0.2f, 0.2f);


        //make some small platforms that will be used by the ball collision listener to make the ball bounce back
        StaticBody bottomRightBoundary = new BounceBackPlatforms(this, bounceShape);
        bottomRightBoundary.setPosition(new Vec2(9f, -10f));
        bottomRightBoundary.addImage(image);

        StaticBody bottomLeftBoundary = new BounceFrontPlatforms(this, bounceShape);
        bottomLeftBoundary.setPosition(new Vec2(-11f, -8.9f));
        bottomLeftBoundary.addImage(image);


        StaticBody centreLeftBoundary = new BounceFrontPlatforms(this, bounceShape);
        centreLeftBoundary.setPosition(new Vec2(-11f, 0.1f));
        centreLeftBoundary.addImage(image);

        StaticBody centreRightBoundary = new BounceBackPlatforms(this, bounceShape);
        centreRightBoundary.setPosition(new Vec2(9.2f, 1.3f));
        centreRightBoundary.addImage(image);
    }

    @Override
    public boolean isComplete() {
        if (getNinja().isLevelCompleted()) {
            return true;
        } else return false;
    }

    //method used to return the current Level name
    @Override
    public String getLevelName() {
        return "Level1";
    }

    //creating non persistent objects
    @Override
    public void populate(Game game) {
        super.populate(game);
        //making bodies
        getNinja().setPosition(new Vec2(-17, -15.3f));
        getNinja().changeImage(1);
        getShooter().setPosition(new Vec2(-50, 2));

        //ninja collision listener
        NinjaCollisionListener ninjaCollision = new NinjaCollisionListener(this, game, getNinja());
        getNinja().addCollisionListener(ninjaCollision);

        //making coins
        Coin[] coins = new Coin[3];
        BodyImage coinImage =
                new BodyImage("data/coin.png", 2f);
        Shape coinShape = new CircleShape(1f);
        for (int i = 0; i < coins.length; i++) {
            coins[i] = new Coin(this, coinShape, coinImage);
            coins[i].setGravityScale(0);//gravity to 0 so they don't fall
        }
        coins[0].setPosition(new Vec2(12, -6));
        coins[1].setPosition(new Vec2(-14, 0));
        coins[2].setPosition(new Vec2(12, 9));


    }


}
