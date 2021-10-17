package game.levels;

import city.cs.engine.*;
import game.collisions.NinjaCollisionListener;
import game.collisions.SpawnIceBall;
import game.collisions.SpawnLava;
import game.main.Game;
import game.objects.Coin;
import game.objects.IceBall;
import game.objects.Lava;
import game.objects.Shooter;
import game.objects.platforms.LargePlatform;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
/**
 * Level4 Class
 */
public class Level4 extends GameLevel implements ActionListener {
    private Timer timer;
    Lava[] lavas = new Lava[70];//make array of lavas

    public Level4(Game game) {
        super(game);
        //get bodies
        getGround();
        getDoor().setPosition(new Vec2(-12.8f, -15.5f));

        //make timer to shoot every second
        timer = new Timer(1000, this);
        timer.setInitialDelay(100);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (getShooter().isAlive()) {//will shoot till the shooter is alive
                IceBall iceBall = new IceBall(this);
                iceBall.setPosition(this.getShooter().getPosition().add(new Vec2(1, -0.1f)));//iceball be set near the shooter
                //transform them to world coordinates
                //position the books
                Vec2 enemy = this.getShooter().getPosition();//the shooter is the enemy
                Vec2 player = this.getNinja().getPosition();//the ninja is the player
                Vec2 v = player.sub(enemy);
                v.normalize();
                iceBall.setLinearVelocity(v.mul(10.0f));//iceball will move
                iceBall.setGravityScale(0);//iceball will not fall
                SpawnIceBall collision = new SpawnIceBall(iceBall, this, 4);//collision is added to the iceball
                iceBall.addCollisionListener(collision);
            }
            if (this.getNinja().isLose()) {//remove collision of ninja if he is dead
                this.getNinja().removeAllCollisionListeners();
                this.getShooter().removeAllCollisionListeners();
            }
        } catch (NullPointerException e) {

        }
    }

    //make non persistent objects
    public void populate(Game game) {
        super.populate(game);

        //make objects
        getNinja().setPosition(new Vec2(-17.5f, -14));
        getNinja().changeImage(1);
        getShooter().setPosition(new Vec2(-11.2f, -15));
        getShooter().setImage();

        //add collision listener to ninja
        NinjaCollisionListener ninjaCollision = new NinjaCollisionListener(this, game, getNinja());
        getNinja().addCollisionListener(ninjaCollision);

        //make shaper for platform
        Shape smallPlatform = new BoxShape(2, 0.4f);

        float xPos = 0;//var used to change the xPos of the platforms
        float yPos = 0;//var used to change the yPos of the platforms
        //loop used to make platforms and lavas
        for (int i = 0; i < 7; i++) {
            StaticBody platform = new LargePlatform(this, smallPlatform);
            platform.setPosition(new Vec2(-17 + xPos, -15 + yPos));
            if (i % 2 == 0) {
                lavas[i] = new Lava(this);
                lavas[i].setPosition(new Vec2(platform.getPosition().add(new Vec2(-3, -2))));
            }
            xPos += 2;
            yPos += 2;
        }

        xPos = 0;
        yPos = 0;
        //loop used to make platforms and lavas
        for (int i = 0; i < 7; i++) {
            StaticBody platform = new LargePlatform(this, smallPlatform);
            platform.setPosition(new Vec2(-17 + xPos, 15 + yPos));
            if (i % 2 == 0 && i <= 5) {
                lavas[i] = new Lava(this);
                lavas[i].setPosition(new Vec2(platform.getPosition().add(new Vec2(+4, -3.5f))));
            }
            xPos += 2;
            yPos -= 2;
        }

        xPos = 0;
        yPos = 0;
        //loop used to make platforms and lavas
        for (int i = 0; i < 7; i++) {
            StaticBody platform = new LargePlatform(this, smallPlatform);
            platform.setPosition(new Vec2(17 + xPos, 15 + yPos));
            if (i % 2 == 0 && i <= 5) {
                lavas[i] = new Lava(this);
                lavas[i].setPosition(new Vec2(platform.getPosition().add(new Vec2(-3, -2))));
            }
            xPos -= 2;
            yPos -= 2;
        }

        xPos = 0;
        yPos = 0;
        //loop used to make platforms and lavas
        for (int i = 0; i < 7; i++) {
            StaticBody platform = new LargePlatform(this, smallPlatform);
            platform.setPosition(new Vec2(17 + xPos, -15 + yPos));
            if (i % 2 == 0) {
                lavas[i] = new Lava(this);
                lavas[i].setPosition(new Vec2(platform.getPosition().add(new Vec2(+3, -2))));
            }
            xPos -= 2;
            yPos += 2;
        }
        xPos = 0;
        //loop used to make lavas on the ground
        for (int i = 0; i < 45; i++) {
            if (i < 7 || i > 11) {
                lavas[i] = new Lava(this);
                lavas[i].setPosition(new Vec2(-20 + xPos, -17));
            }
            xPos += 1;
        }
    }


    @Override
    public boolean isComplete() {
        return false;
    }

    public String getLevelName() {
        return "Level4";
    }
}
