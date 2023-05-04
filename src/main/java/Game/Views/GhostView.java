package Game.Views;

import ConstantsEnums.Constants;
import ConstantsEnums.Direction;
import Game.Objects.GhostObject;
import Interfaces.ICommonMazeObjectView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Random;

import static java.lang.Math.min;

public class GhostView extends Observable implements ICommonMazeObjectView {

    private ImageView imageView;
    private GridPane maze;
    private int row;
    private int col;
    private double height;
    private double width;
    private Random random;
    private Timeline timeline;
    private Direction actDirection;

    public GhostView(GridPane maze, int row, int col, double height, double width, GhostObject object)
    {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
        this.random = new Random();
        this.imageView = CreateView();
        this.addObserver(object);
    }

    @Override
    public void SetFieldSize(double height, double width)
    {
        this.height = height;
        this.width = width;
        imageView.setFitWidth(min(width, height));
        imageView.setFitHeight(min(width, height));
    }

    @Override
    public synchronized ImageView CreateView()
    {
        Image image = new Image(Constants.GhostSource[random.nextInt(4)]);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(min(width, height));
        imageView.setFitHeight(min(width, height));
        maze.getChildren().add(imageView);
        maze.setRowIndex(imageView, row);
        maze.setColumnIndex(imageView, col);
        return imageView;
    }

    @Override
    public void RemoveView() {
        maze.getChildren().remove(imageView);
    }

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
