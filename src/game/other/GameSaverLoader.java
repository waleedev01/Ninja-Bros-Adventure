package game.other;

import city.cs.engine.*;
import game.collisions.*;
import game.levels.*;
import game.main.Game;
import game.objects.*;
import game.objects.platforms.LargePlatform;
import org.jbox2d.common.Vec2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class used fot saving and loading the game
 */
public class GameSaverLoader {

    public static void save(GameLevel level, String fileName) throws IOException {
        boolean append = false;
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, append);//open the file in write mode
            writer.write(level.getLevelName() + "\n");//save the curren level name
            //save static bodies
            for (StaticBody body :
                    level.getStaticBodies()) {
                if (body instanceof Volcano) {
                    writer.write("Volcano," + body.getPosition().x + "," + body.getPosition().y + "\n");
                }
                if (body instanceof LargePlatform) {
                    writer.write("Platform," + body.getPosition().x + "," + body.getPosition().y + "\n");
                }
            }
            //save dynamic bodies
            for (DynamicBody body :
                    level.getDynamicBodies()) {
                if (body instanceof Ninja) {
                    writer.write("Ninja," + body.getPosition().x + "," + body.getPosition().y + ","
                            + ((Ninja) body).getHealth() + "," + ((Ninja) body).getWealth() + "\n");
                } else if (body instanceof Coin) {
                    writer.write("Coin," + body.getPosition().x + "," + body.getPosition().y + "\n");
                } else if (body instanceof Shooter) {
                    writer.write("Shooter," + body.getPosition().x + "," + body.getPosition().y + "," + ((Shooter) body).getHealth() + "\n");
                } else if (body instanceof Ball) {
                    writer.write("Ball," + body.getPosition().x + "," + body.getPosition().y + "," + ((Ball) body).getHealth() + "," + ((Ball) body).getDirection() + "\n");
                } else if (body instanceof Cannon) {
                    writer.write("Cannon," + body.getPosition().x + "," + body.getPosition().y + "," + ((Cannon) body).getHealth() + "\n");
                } else if (body instanceof Lava) {
                    writer.write("Lava," + body.getPosition().x + "," + body.getPosition().y + "," + body.getLinearVelocity().x + "," + body.getLinearVelocity().y + "\n");
                } else if (body instanceof Enemy) {
                    writer.write("Enemy," + body.getPosition().x + "," + body.getPosition().y + "," + ((Enemy) body).getHealth() + "\n");
                } else if (body instanceof IceBall) {
                    writer.write("Iceball," + body.getPosition().x + "," + body.getPosition().y + "," + body.getLinearVelocity().x + "," + body.getLinearVelocity().y + "\n");
                }


            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static GameLevel load(Game game, String fileName) throws IOException {
        BodyImage coinImage =
                new BodyImage("data/coin.png", 2f);
        Shape coinShape = new CircleShape(1f);
        BodyImage smallCoinImage =
                new BodyImage("data/coin.png", 1f);
        Shape smallCoinShape = new CircleShape(0.5f);
        Ninja ninja;

        FileReader fr = null;
        BufferedReader reader = null;

        try {
            System.out.println("Reading " + fileName + " ...");
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            GameLevel level = null;

            //check the level and update the currentLevel variable and create a new level
            if (line.equals("Level1")) {
                level = new Level1(game);
                Game.setCurrentLevel(1);
            }
            if (line.equals("Level2")) {
                level = new Level2(game);
                Game.setCurrentLevel(2);
            }
            if (line.equals("Level3")) {
                level = new Level3(game);
                Game.setCurrentLevel(3);
            }
            if (line.equals("Level4")) {
                level = new Level4(game);
                Game.setCurrentLevel(4);
            }

            line = reader.readLine();
            while (line != null) {//create objects that were saved
                String[] tokens = line.split(",");
                if (tokens[0].equals("Ninja")) {
                    ninja = new Ninja(level);
                    ninja.changeImage(1);
                    float x = Float.parseFloat(tokens[1]);
                    float y = Float.parseFloat(tokens[2]);
                    ninja.setPosition(new Vec2(x, y));
                    int health = Integer.parseInt(tokens[3]);
                    ninja.setHealth(health);
                    int wealth = Integer.parseInt(tokens[4]);
                    ninja.setWealth(wealth);
                    level.setNinja(ninja);
                    NinjaCollisionListener ninjaCollision = new NinjaCollisionListener(level, game, ninja);
                    ninja.addCollisionListener(ninjaCollision);

                }

                if (Game.getCurrentLevel() == 1) {
                    if (tokens[0].equals("Coin")) {
                        Coin coin = new Coin(level, coinShape, coinImage);
                        coin.setGravityScale(0);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        coin.setPosition(new Vec2(x, y));
                    }
                }
                if (Game.getCurrentLevel() == 2) {
                    if (tokens[0].equals("Shooter")) {
                        Shooter shooter = new Shooter(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        shooter.setPosition(new Vec2(x, y));
                        int health = Integer.parseInt(tokens[3]);
                        shooter.setHealth(health);
                        level.setShooter(shooter);
                    }
                    if (tokens[0].equals("Ball")) {
                        Ball ball = new Ball(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        ball.setPosition(new Vec2(x, y));
                        int health = Integer.parseInt(tokens[3]);
                        int dir = Integer.parseInt(tokens[4]);
                        ball.setHealth(health);
                        if (dir == 1)
                            ball.setLinearVelocity(new Vec2(10, 0));
                        else
                            ball.setLinearVelocity(new Vec2(-10, 0));
                        MoveBall collision = new MoveBall(ball, level, 2);
                        ball.addCollisionListener(collision);
                    }
                    if (tokens[0].equals("Cannon")) {
                        Cannon cannon = new Cannon(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        cannon.setPosition(new Vec2(x, y));
                        int health = Integer.parseInt(tokens[3]);
                        cannon.setHealth(health);
                    }
                    if (tokens[0].equals("Coin")) {
                        Coin coin = new Coin(level, smallCoinShape, smallCoinImage);
                        coin.setGravityScale(0);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        coin.setPosition(new Vec2(x, y));
                    }
                }
                if (Game.getCurrentLevel() == 3) {
                    if (tokens[0].equals("Shooter")) {
                        Shooter shooter = new Shooter(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        shooter.setPosition(new Vec2(x, y));
                        int health = Integer.parseInt(tokens[3]);
                        shooter.setHealth(health);
                        level.setShooter(shooter);
                        shooter.setImage();
                    }
                    if (tokens[0].equals("Lava")) {
                        Lava lava = new Lava(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        lava.setPosition(new Vec2(x, y));
                        float x1 = Float.parseFloat(tokens[3]);
                        float y1 = Float.parseFloat(tokens[4]);
                        lava.setLinearVelocity(new Vec2(x1, y1));
                        SpawnLava spawn = new SpawnLava(level, lava);
                        lava.addCollisionListener(spawn);
                    }
                    if (tokens[0].equals("Volcano")) {
                        Volcano volcano = new Volcano(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        volcano.setPosition(new Vec2(x, y));
                    }
                    if (tokens[0].equals("Enemy")) {
                        Enemy enemy = new Enemy(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        enemy.setPosition(new Vec2(x, y));
                        int health = Integer.parseInt(tokens[3]);
                        enemy.setHealth(health);
                        EnemyAttack attack = new EnemyAttack(enemy, level);
                        enemy.addCollisionListener(attack);
                        enemy.setAlive(true);
                        level.setEnemy(enemy);
                    }
                    if (tokens[0].equals("Iceball")) {
                        IceBall iceball = new IceBall(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        iceball.setPosition(new Vec2(x, y));
                        float x1 = Float.parseFloat(tokens[3]);
                        float y1 = Float.parseFloat(tokens[4]);
                        iceball.setLinearVelocity(new Vec2(x1, y1));
                        SpawnIceBall spawn = new SpawnIceBall(iceball, level, Game.getCurrentLevel());
                        iceball.addCollisionListener(spawn);
                    }
                    if (tokens[0].equals("Coin")) {
                        Coin coin = new Coin(level, coinShape, coinImage);
                        coin.setGravityScale(0);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        coin.setPosition(new Vec2(x, y));
                    }

                }
                if (Game.getCurrentLevel() == 4) {
                    if (tokens[0].equals("Shooter")) {
                        Shooter shooter = new Shooter(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        shooter.setPosition(new Vec2(x, y));
                        int health = Integer.parseInt(tokens[3]);
                        shooter.setHealth(health);
                        level.setShooter(shooter);
                        shooter.setImage();
                    }
                    if (tokens[0].equals("Lava")) {
                        Lava lava = new Lava(level);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        lava.setPosition(new Vec2(x, y));
                    }
                    if (tokens[0].equals("Platform")) {
                        Shape smallPlatform = new BoxShape(2, 0.4f);
                        LargePlatform platform = new LargePlatform(level, smallPlatform);
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        platform.setPosition(new Vec2(x, y));
                    }
                }
                line = reader.readLine();

            }

            return level;

        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }
}
