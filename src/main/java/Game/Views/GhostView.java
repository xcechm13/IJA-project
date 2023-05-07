package Game.Views;

import Constants.Constants;
import Enums.Direction;
import Game.Objects.GhostObject;
import Interfaces.ICommonMazeObjectView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Random;

import static java.lang.Math.min;

/**
 * Class for GhostView
 */

public class GhostView extends Observable implements ICommonMazeObjectView {

    /**
     * textures in ImageView
     */
    private ImageView imageView;
    /**
     * layout
     */
    private GridPane maze;
    /**
     * row of object
     */
    private int row;
    /**
     * column of object
     */
    private int col;
    /**
     * height of maze
     */
    private double height;
    /**
     * width of maze
     */
    private double width;
    /**
     * randomize ghost color
     */
    private Random random;
    /**
     * animation
     */
    private Timeline timeline;
    /**
     * ghost movement direction
     */
    private Direction actDirection;
    /**
     * ghost color (image source)
     */
    private String imageSource;

    /**
     * Constructor
     * @param maze layout maze
     * @param row row of object
     * @param col column of object
     * @param height maze height
     * @param width maze width
     * @param object GhostObject
     * @param ImageSource ghost color
     */
    public GhostView(GridPane maze, int row, int col, double height, double width, GhostObject object, String ImageSource)
    {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
        this.random = new Random();
        this.imageSource = ImageSource;
        this.imageView = CreateView();
        if(object != null)
            this.addObserver(object);
    }

    /**
     * Update dimensions of object
     * @param height maze height
     * @param width maze width
     */
    @Override
    public void SetFieldSize(double height, double width)
    {
        this.height = height;
        this.width = width;
        imageView.setFitWidth(min(width, height));
        imageView.setFitHeight(min(width, height));
    }

    /**
     * Helps with "drawing" the object
     * @return textures of object
     */
    @Override
    public synchronized ImageView CreateView()
    {
        imageSource = imageSource == null ? Constants.GhostSource[random.nextInt(4)] : imageSource;
        Image image = new Image(imageSource);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(min(width, height));
        imageView.setFitHeight(min(width, height));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                maze.getChildren().add(imageView);
            }
        });
        maze.setRowIndex(imageView, row);
        maze.setColumnIndex(imageView, col);
        return imageView;
    }

    /**
     * remove the object (not visible)
     */
    @Override
    public void RemoveView() {
        maze.getChildren().remove(imageView);
    }

    /**
     * gets color of ghost
     * @return ghost color
     */
    public String GetGhostColor()
    {
        return imageSource;
    }

    /**
     * Ghost is moving from one field to another
     * @param direction direction where he should move
     */
    public void AnimatedMove(Direction direction)
    {
        actDirection = direction;
        switch (direction) {
            case Up -> {
                KeyValue keyValue = new KeyValue(imageView.translateYProperty(), -height, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.GhostMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
            case Down -> {
                KeyValue keyValue = new KeyValue(imageView.translateYProperty(), height, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.GhostMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
            case Left -> {
                KeyValue keyValue = new KeyValue(imageView.translateXProperty(), -width, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.GhostMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
            case Right -> {
                KeyValue keyValue = new KeyValue(imageView.translateXProperty(), width, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.GhostMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
        }
        timeline.setOnFinished(e -> AnimationCompleted());
        timeline.play();
    }

    /**
     * finish the movement animation
     */
    private synchronized void AnimationCompleted()
    {
        switch (actDirection) {
            case Up -> {
                maze.setRowIndex(imageView, --row);
            }
            case Down -> {
                maze.setRowIndex(imageView, ++row);
            }
            case Left -> {
                maze.setColumnIndex(imageView, --col);
            }
            case Right -> {
                maze.setColumnIndex(imageView, ++col);
            }
        }
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
        setChanged();
        notifyObservers();
    }
}
