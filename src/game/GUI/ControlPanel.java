package game.GUI;

import city.cs.engine.SoundClip;
import game.levels.GameLevel;
import game.levels.Level1;
import game.main.Game;
import game.other.GameView;

import javax.swing.*;
/**
 * Control panel to manage the game
 */
public class ControlPanel {
    private GameLevel gameLevel;
    private Game game;
    private SoundClip soundClip;
    private JButton playButton;
    private JButton muteButton;
    private JButton pauseButton;
    private JPanel mainPanel;
    private JButton unmuteButton;
    private JButton exitButton;
    private JButton nextLevelButton;
    private JButton saveButton;
    private JButton loadButton;


    public ControlPanel(GameLevel gameLevel, SoundClip soundClip, Game game) {
        this.gameLevel = gameLevel;
        this.soundClip = soundClip;
        this.game = game;
        pauseButton.addActionListener(e -> this.gameLevel.stop());//game is paused
        playButton.addActionListener(e -> this.gameLevel.start());//game is played
        muteButton.addActionListener(e -> this.soundClip.pause());//soundclip is paused
        unmuteButton.addActionListener(e -> this.soundClip.resume());//soundclip is resumed
        exitButton.addActionListener(e -> System.exit(0));//game is closed
        nextLevelButton.addActionListener(e -> game.goToNextLevel());//game goes to next level
        saveButton.addActionListener(e -> game.save("data/save.txt"));//save game
        loadButton.addActionListener(e -> game.load("data/save.txt"));//load game
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void updateMusic(SoundClip soundClip) {
        this.soundClip = soundClip;
    }

    public void updateLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }


}
