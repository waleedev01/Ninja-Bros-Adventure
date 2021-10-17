package game.GUI;

import game.levels.*;
import game.main.Game;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Control panel for saving and loading
 */
public class ControlPanel2 {
    private JPanel mainPanel;
    private JButton saveFile;
    private JButton chooseFileToLoadButton;

    private GameLevel gameLevel;
    private Game game;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String path;
    String fileName = new SimpleDateFormat("HH.mm.ss'.txt'").format(new Date());
    public ControlPanel2(GameLevel gameLevel, Game game) {
        this.gameLevel = gameLevel;
        this.game = game;
        saveFile.addActionListener(e -> game.save("data/"+fileName));//save file
        System.out.println("data/"+fileName);
        //code from: https://stackoverflow.com/questions/40255039/how-to-choose-file-in-java/40255184
        chooseFileToLoadButton.addActionListener(e -> {//load the game from a txt file in the data folder
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXT files", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
                path = chooser.getSelectedFile().getName();
                path = "data/" + path;
                game.load(path);
            }
        });


    }
}
