package game.other;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Write the high-score data
 */
public class HighScoreWriter {

    private String fileName;

    public HighScoreWriter(String fileName) {
        this.fileName = fileName;
    }

    public void writeHighScore(String name, int score, int health) throws IOException {
        boolean append = true;
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, append);
            writer.write(name + "," + score + "," + health + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


}
