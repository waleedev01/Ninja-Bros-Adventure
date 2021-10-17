package game.main;

import city.cs.engine.DebugViewer;
import city.cs.engine.SoundClip;
import game.GUI.ControlPanel2;
import game.GUI.HighScore;
import game.levels.*;
import game.GUI.ControlPanel;
import game.other.*;
import game.controls.MouseHandler;
import game.controls.NinjaController;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * A world with some bodies.
 */
public class Game {

    /**
     * The World in which the bodies move and interact.
     */
    private GameLevel level;
    /**
     * A graphical display of the world (a specialised JPanel).
     */
    private GameView view;
    /**
     * controller which allows the player to move
     */
    private NinjaController controller;
    /**
     * variable that allow to use mouse
     */
    private MouseHandler mouse;
    /**
     * Jframe.
     */
    private final JFrame frame;
    /**
     * Total Health of the ninja
     */
    private int totalHealth = 0;
    /**
     * Total wealth of the ninja
     */
    private int totalWealth = 0;
    /**
     * Current level in the world
     */
    private static int currentLevel = 1;
    /**
     * GUI control panel
     */
    private ControlPanel controlPanel;
    /**
     * GUI control panel
     */
    private ControlPanel2 controlPanel2;
    /**
     * Background music
     */
    private SoundClip gameMusic;
    /**
     * Debug view variable
     */
    private JFrame debugView;

    /**
     * Initialise a new Game.
     */
    public Game() {

        // initialize level to Level1
        level = new Level1(this);
        level.populate(this);
        // make a view
        view = new GameView(level, 800, 700, level.getNinja(), currentLevel, level.getShooter(), level.getEnemy());
        view.setZoom(20);
        //level.addStepListener(new Tracker(view, level.getNinja()));
        // add some mouse actions
        // add this to the view, so coordinates are relative to the view
        mouse = new MouseHandler(view, level.getNinja());
        view.addMouseListener(mouse);

        //add controller
        controller = new NinjaController(this, level.getNinja());
        view.addKeyListener(controller);
        view.addMouseListener(new GiveFocus(view));
        //add background track
        try {
            gameMusic = new SoundClip("data/level1Background.wav");   // Open an audio input stream
            gameMusic.loop();  // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

        // add the view to a frame (Java top level window)
        frame = new JFrame("Game");
        frame.add(view);
        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        controlPanel = new ControlPanel(level, gameMusic, this);

        frame.add(controlPanel.getMainPanel(), BorderLayout.WEST);

        frame.add(view, BorderLayout.CENTER);

        controlPanel2 = new ControlPanel2(level, this);
        frame.add(controlPanel2.getMainPanel(), BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);

        JLabel versionLabel = new JLabel();
        versionLabel.setText("Ninja Bros");
        frame.add(versionLabel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);


        // uncomment this to make a debugging view
        //debugView = new DebugViewer(level, 800, 700);

        // start our game world simulation!
        level.start();

    }
    /**
     * Method that creates a new level
     * <p>
     * This method is used when the user choose to load a saved level.
     *
     * @param  level level that will be created.
     * @return
     */
    public void setLevel(GameLevel level) {
        gameMusic.stop();
        this.level.stop();
        this.level = level;
        view.setWorld(this.level);
        try {
            gameMusic = new SoundClip("data/level" + Game.getCurrentLevel() + "Background.wav");   // Open an audio input stream
            gameMusic.loop();  // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
        //update all the objects
        controller.updateNinja(this.level.getNinja());
        view.updateNinja(this.level.getNinja());
        view.updateShooter(this.level.getShooter());
        mouse.updateNinja(this.level.getNinja());
        view.updateEnemy(this.level.getEnemy());

        this.level.start();

    }
    /**
     * Method that creates a new level
     * <p>
     * This method is invoked when the ninja finishes a level, so the player will progress to the next level
     *
     * @param
     * @return
     */
    public void goToNextLevel() {
        level.stop();//level stop
        gameMusic.stop();//music stop
        if (level instanceof Level1) {
            try {
                TotalScore.save("data/playerStats.txt", this.getLevel().getNinja().getWealth(), this.getLevel().getNinja().getHealth(), true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            currentLevel = 2;
            //add new track
            try {
                gameMusic = new SoundClip("data/level2Background.wav");   // Open an audio input stream
                gameMusic.loop();  // Set it to continous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            // initialize level to Level2
            level = new Level2(this);
            level.populate(this);

            //change the view to look into new level
            view.setWorld(level);
            //update the ninja and mouse Controller
            mouse.updateNinja(level.getNinja());
            //update level and music
            controlPanel.updateLevel(level);
            controlPanel.updateMusic(gameMusic);
            //change the controller to control the
            //student in the new world
            controller.updateNinja(level.getNinja());
            view.updateNinja(level.getNinja());
            view.updateShooter(level.getShooter());
            //debugView = new DebugViewer(level, 800, 700);
            //start the simulation in the new level
            level.start();
        } else if (level instanceof Level2) {
            try {
                TotalScore.save("data/playerStats.txt", this.getLevel().getNinja().getWealth(), this.getLevel().getNinja().getHealth(), true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            currentLevel = 3;

            try {
                gameMusic = new SoundClip("data/level3Background.wav");   // Open an audio input stream
                gameMusic.loop();  // Set it to continous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            // initialize level to Level3
            level = new Level3(this);
            level.populate(this);
            //change the view to look into new level
            view.setWorld(level);
            //update the ninja and mouse Controller
            mouse.updateNinja(level.getNinja());
            //update level and music
            controlPanel.updateLevel(level);
            controlPanel.updateMusic(gameMusic);
            //change the controller to control the
            //student in the new world
            controller.updateNinja(level.getNinja());
            view.updateNinja(level.getNinja());
            view.updateShooter(level.getShooter());
            //debugView = new DebugViewer(level, 800, 700);

            //start the simulation in the new level
            level.start();
            view.updateEnemy(level.getEnemy());
        } else if (level instanceof Level3) {
            currentLevel = 4;
            try {
                TotalScore.save("data/playerStats.txt", this.getLevel().getNinja().getWealth(), this.getLevel().getNinja().getHealth(), true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                gameMusic = new SoundClip("data/level4Background.wav");   // Open an audio input stream
                gameMusic.loop();  // Set it to continuous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            // initialize level to Level4
            level = new Level4(this);
            level.populate(this);
            //change the view to look into new level
            view.setWorld(level);
            //update the ninja and mouse Controller
            mouse.updateNinja(level.getNinja());
            //update level and music
            controlPanel.updateLevel(level);
            controlPanel.updateMusic(gameMusic);
            //change the controller to control the
            //student in the new world
            controller.updateNinja(level.getNinja());
            view.updateNinja(level.getNinja());
            view.updateShooter(level.getShooter());
            //debugView = new DebugViewer(level, 800, 700);

            //start the simulation in the new level
            level.start();
        } else if (level instanceof Level4) {
            List<Integer> scores = new ArrayList<>();
            try {
                TotalScore.save("data/playerStats.txt", this.getLevel().getNinja().getWealth(), this.getLevel().getNinja().getHealth(), true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            try {
                TotalScore.load(this, "data/playerStats.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


            System.out.println("Total Health: " + getTotalHealth());
            System.out.println("Total Wealth: " + getTotalWealth());

            JDialog scoreDialog = new JDialog(frame, true);
            HighScore highScore = new HighScore(this, scores);
            scoreDialog.getContentPane().add(highScore.getPnlScore());
            scoreDialog.pack();
            scoreDialog.setVisible(true);


        }


    }


    /**
     * Method that runs the game
     * <p>
     *
     * @param
     * @return
     */
    public static void main(String[] args) {
        new Game();
    }

    /**
     * Method that return the current level name
     * <p>
     * This method is used to load the correct level
     *
     * @param
     * @return It return the current level
     */
    public GameLevel getLevel() {
        return level;
    }
    /**
     * Method that return the number of the current level
     * <p>
     * It's useful to trace the current level
     *
     * @param
     * @return Return an integer which is the current level
     */
    public static int getCurrentLevel() {
        return currentLevel;
    }
    /**
     * Method that sets number of the current level
     * <p>
     * It's useful to update the current level
     *
     * @param currentLevel current level number
     * @return
     */
    public static void setCurrentLevel(int currentLevel) {
        Game.currentLevel = currentLevel;
    }
    /**
     * Method used to save the game

     *
     * @param path path of the file where the game will be saved
     * @return
     */
    public void save(String path) {
        try {
            GameSaverLoader.save(this.getLevel(), path);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    /**
     * Method used to load the game

     *
     * @param path path of the file where the game is saved
     * @return
     */
    public void load(String path) {
        try {
            GameLevel level = GameSaverLoader.load(this, path);
            this.setLevel(level);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    /**
     * Method used to get the total health

     *
     * @param
     * @return Return the total health
     */
    public int getTotalHealth() {
        return totalHealth;
    }
    /**
     * Method used to set the total health

     *
     * @param totalHealth it updates the total health
     * @return
     */
    public void setTotalHealth(int totalHealth) {
        this.totalHealth += totalHealth;
    }
    /**
     * Method used to get the total wealth

     *
     * @param
     * @return Return the total wealth
     */
    public int getTotalWealth() {
        return totalWealth;
    }
    /**
     * Method used to set the total wealth

     *
     * @param totalWealth it updates the total wealth
     * @return
     */
    public void setTotalWealth(int totalWealth) {
        this.totalWealth += totalWealth;
    }
}
