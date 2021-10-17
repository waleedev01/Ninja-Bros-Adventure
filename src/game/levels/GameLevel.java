package game.levels;

import city.cs.engine.*;
import game.collisions.EnemyAttack;
import game.main.Game;
import game.objects.*;

import game.objects.platforms.Ground;
import game.objects.platforms.LeftWall;
import game.objects.platforms.RightWall;
import org.jbox2d.common.Vec2;

import java.util.EnumMap;
/**
 * GameLevel class
 */
public abstract class GameLevel extends World {
    private Ninja ninja;
    private Door door;
    private Shooter shooter;
    private Ground ground;
    private Enemy enemy;

    //creating static objects
    public GameLevel(Game game) {
        //make ground
        Shape groundShape = new BoxShape(20, 0.5f);
        ground = new Ground(this, groundShape);
        ground.setPosition(new Vec2(0f, -17.2f));
        //make bodies

        BodyImage image = new BodyImage("data/transparent.png", 1f);
        //make the shape for the wall
        Shape wallShape = new BoxShape(0.1f, 20);
        //make the wall  with transparent image
        LeftWall wall1 = new LeftWall(this, wallShape);
        wall1.setPosition(new Vec2(-20f, -7.8f));
        wall1.addImage(image);
        //make another wall
        RightWall wall2 = new RightWall(this, wallShape);
        wall2.setPosition(new Vec2(20f, -7.8f));
        wall2.addImage(image);
        door = new Door(this);
    }

    //creating non persistent objects
    public void populate(Game game) {
        enemy = new Enemy(this);
        enemy.setPosition(new Vec2(50, 50));
        ninja = new Ninja(this);
        ninja.changeImage(1);
        shooter = new Shooter(this);
    }

    public Door getDoor() {
        return door;
    }

    //method called in the level3 to create the enemy
    public void createEnemy() {
        enemy.setPosition(new Vec2(7, -11));
        EnemyAttack attack = new EnemyAttack(enemy, this);
        enemy.addCollisionListener(attack);
        enemy.setAlive(true);
    }

    public Ground getGround() {
        return ground;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public abstract String getLevelName();

    public void setNinja(Ninja ninja) {
        this.ninja = ninja;
    }

    public Ninja getNinja() {
        return ninja;
    }

    public void setShooter(Shooter shooter) {
        this.shooter = shooter;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public abstract boolean isComplete();

}
