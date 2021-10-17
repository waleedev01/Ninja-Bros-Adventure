package game.objects;

import city.cs.engine.*;
import city.cs.engine.Shape;
/**
 * Shooter
 */
public class Shooter extends Walker {
    private int health = 100;
    private boolean alive = true;
    private static Shape shooterShape = new PolygonShape(-0.443f, -1.109f, -1.455f, -0.063f, 0.0f, 1.161f, 0.952f, 0.381f, 0.775f, -1.121f);
    private static BodyImage image = new BodyImage("data/shooter.png", 2.5f);

    public Shooter(World world) {
        super(world, shooterShape);
        addImage(image);
    }


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    public int getHealth() {
        return health;
    }

    public void decrementHealth(int health) {
        this.health -= health;
    }


    public void setImage() {
        removeAllImages();
        BodyImage image = new BodyImage("data/shooterRight.png", 2.5f);
        addImage(image);
    }

    public void setHealth(int health) {
        this.health = health;
    }
}