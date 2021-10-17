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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * read and write the total wealth and health
 */
public class TotalScore {
    static FileWriter writer;
    public static void save(String fileName, int wealth, int health,boolean append) throws IOException {

        writer = null;

        try {
            writer = new FileWriter(fileName, append);
            writer.write("wealth," + wealth + "\n");
            writer.write("health," + health + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    public static void load(Game game, String fileName) throws IOException {

        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                if (tokens[0].equals("health")) {
                    int health = Integer.parseInt(tokens[1]);
                    game.setTotalHealth(health);
                }

                if (tokens[0].equals("wealth")) {
                    int wealth = Integer.parseInt(tokens[1]);
                    game.setTotalWealth(wealth);

                }
                line = reader.readLine();
            }


        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        //erase the content of the file
        try {
            new FileWriter("data/playerStats.txt", false).close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
