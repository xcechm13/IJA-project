package Game;

import Game.Records.LoggerResult;
import Game.Views.*;
import Interfaces.ICommonMazeObjectView;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Class for LoggerPlayes
 */
public class LoggerPlayer implements Runnable{
    /**
     * layout
     */
    GridPane maze;
    /**
     * object views
     */
    List<ICommonMazeObjectView> views;
    Logger logger;
    /**
     * maze as walls and paths
     */
    LoggerResult result;
    /**
     * actual number of lives as view
     */
    HBox livesView;
    /**
     * actual number of steps as view
     */
    Label stepsView;
    /**
     * maze width
     */
    double width;
    /**
     * maze height
     */
    double height;
    /**
     * actual number of lives
     */
    int actLives;
    int rows;
    int cols;
    /**
     * denies bugging objects
     */
    boolean insideUpdateObjects;
    boolean playing = false;

    /**
     * Constructor
     * @param maze layout
     * @param logger logger
     * @param firstResult first log parsed
     * @param livesView lives view
     * @param stepsView steps view
     * @param width maze width
     * @param height maze height
     * @param rows number of rows in maze
     * @param cols number of cols in maze
     * @throws IOException file error
     */
    public LoggerPlayer(GridPane maze, Logger logger, LoggerResult firstResult, HBox livesView, Label stepsView, double width, double height, int rows, int cols) throws IOException {
        this.views = new ArrayList<>();
        this.maze = maze;
        this.logger = logger;
        this.result = firstResult;
        this.livesView = livesView;
        this.stepsView = stepsView;
        this.actLives = firstResult.lives();
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.height = height;
        UpdateLives();
    }

    /**
     * start thread
     */
    @Override
    public void run() {
        UpdateObjects();
    }

    /**
     * remove the previous views and "draw" the new
     */
    public void UpdateObjects()
    {
        if(insideUpdateObjects) return;
        insideUpdateObjects = true;

        if(result.lives() < actLives)
        {
            actLives --;
        }
        else if(result.lives() > actLives)
        {
            actLives ++;
        }

        UpdateLives();
        UpdateSteps();

        for(var view : views)
        {
            view.RemoveView();
            maze.getChildren().removeAll();
            try
            {
                sleep(10);
            } catch (InterruptedException e){}
        }

        views = new ArrayList<>();

        for(int r = 0; r < rows; r++)
        {
            for(int c = 0; c < cols; c++)
            {
                for (var object : result.maze()[r][c])
                {
                    if(object.contains("Target"))
                    {
                        views.add(new TargetView(maze, r, c, height, width));
                    }
                    else if(object.contains("Key"))
                    {
                        views.add(new KeyView(maze, r, c, height, width));
                    }
                    else if(object.contains("Home"))
                    {
                        views.add(new HomeView(maze, r, c, height, width));
                    }
                }

                //Pacman and ghost will be on top if more objects are on same field
                for (var object : result.maze()[r][c])
                {
                    if(object.contains("pacman"))
                    {
                        views.add(new PacmanView(maze, r, c, height, width, null, object));
                    }
                    else if(object.contains("ghost"))
                    {
                        views.add(new GhostView(maze, r, c, height, width, null, object));
                    }
                }
            }
        }
        insideUpdateObjects = false;
    }

    /**
     * pacmans number of lives has changed
     */
    public void UpdateLives()
    {
        int livesToUpdate = actLives - livesView.getChildren().size();
        if(livesToUpdate > 0)
        {
            for(int i=0; i<livesToUpdate; i++)
            {
                ImageView live = new ImageView(new Image("pacman_right_opened.png"));
                live.setFitHeight(25);
                live.setFitWidth(25);
                livesView.getChildren().add(live);
            }
        }
        else if(livesToUpdate < 0)
        {
            livesToUpdate = -livesToUpdate;
            for(int i=0; i<livesToUpdate; i++)
            {
                livesView.getChildren().remove(0);
            }
        }

    }

    /**
     * steps have changed
     */
    public void UpdateSteps()
    {
        stepsView.setText(result.steps() + " steps");
    }

    /**
     * change dimentions of views
     * @param height height of maze
     * @param width width of maze
     */
    public void UpdateSize(double height, double width)
    {
        this.width = width;
        this.height = height;
        for(var view : views)
        {
            view.SetFieldSize(height, width);
        }
    }

    /**
     * starts showing logs from earlier of game
     * @throws IOException file error
     */
    public void PlayBack() throws IOException {
        playing = true;
        new Thread(() -> {
            while (playing) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    try {
                        StepBack();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (result == null) {
                        playing = false;
                    }
                });
            }
        }).start();
    }

    /**
     * show log from earlier of game
     * @throws IOException file error
     */
    public void StepBack() throws IOException {
        result = logger.BackStep();
        if(result != null)
            UpdateObjects();
    }

    /**
     * Start/Stop of showing logs
     * @throws IOException file error
     */
    public void PlayPause() throws IOException {
        playing = !playing;
    }

    /**
     * show next log of the game
     * @throws IOException file error
     */
    public void StepForward() throws IOException {
        result = logger.NextStep();
        if(result != null)
            UpdateObjects();
    }

    /**
     * starts showing next logs of the game
     * @throws IOException file error
     */
    public void PlayForward() throws IOException {
        playing = true;
        new Thread(() -> {
            while (playing) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    try {
                        StepForward();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (result == null) {
                        playing = false;
                    }
                });
            }
        }).start();
    }
}
