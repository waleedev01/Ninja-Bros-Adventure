package game.GUI;

import game.main.Game;
import game.objects.Ninja;
import game.other.HighScoreReader;
import game.other.HighScoreWriter;
import game.other.TotalScore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class used to show the high score dialog
 */
public class HighScore {
    private JPanel pnlScore;
    private JPanel pnlControl;
    private JLabel lblPlayerName;
    private JTextField txtName;
    private JLabel lblScore;
    private JButton btnSave;
    private JButton btnCancel;
    private JList<String> listScores;
    private JScrollPane scrollScores;
    private JLabel lblHealth;

    private Game game;
    private HighScoreWriter highScoreWriter;
    private HighScoreReader highScoreReader;
    private final String fileName = "data/highScores.txt";



    public HighScore(Game game, List<Integer> stats) {
        this.game = game;
        lblScore.setText(game.getTotalWealth()+"");
        lblHealth.setText(game.getTotalHealth()+"");

        File scores = new File(fileName);
        try {
            scores.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        highScoreWriter = new HighScoreWriter(fileName);
        highScoreReader = new HighScoreReader(fileName);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    highScoreWriter.writeHighScore(txtName.getText(),game.getTotalWealth(),game.getTotalHealth());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });


        btnCancel.addActionListener(e -> System.exit(0));

        DefaultListModel<String> model = new DefaultListModel<>();

        try {
            model.addAll(highScoreReader.readScores());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        listScores.setModel(model);
    }

    public JPanel getPnlScore() {
        return pnlScore;
    }
}
