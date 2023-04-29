package Game.Views;

import ConstantsEnums.Constants;
import ConstantsEnums.Direction;
import Game.Objects.PacmanObject;
import Interfaces.ICommonMazeObjectView;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Random;
import java.util.Observable;

import static ConstantsEnums.Constants.PacmanMoveDelay;
import static java.lang.Math.min;

public class PacmanView extends Observable implements ICommonMazeObjectView {

    private ImageView imageView;
    private GridPane maze;
    private int row;
    private int col;
    private double height;
    private double width;
    private Random random;
    private Timeline timeline;

    public PacmanView(GridPane maze, int row, int col, double height, double width, PacmanObject object) {
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
    public ImageView CreateView()
    {
        Image image = new Image(Constants.PacmanSource[0]);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(min(width, height));
        imageView.setFitHeight(min(width, height));
        maze.getChildren().add(imageView);
        maze.setRowIndex(imageView, row);
        maze.setColumnIndex(imageView, col);
        return imageView;
    }

    public void AnimatedMove(Direction direction)
    {
        switch (direction) {
            case Up -> {
                KeyValue keyValue = new KeyValue(imageView.translateYProperty(), -height, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
                System.out.println("duch pohyb up");
            }
            case Down -> {
                KeyValue keyValue = new KeyValue(imageView.translateYProperty(), height, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
                System.out.println("duch pohyb down");
            }
            case Left -> {
                KeyValue keyValue = new KeyValue(imageView.translateXProperty(), -width, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
                System.out.println("duch pohyb left");
            }
            case Right -> {
                KeyValue keyValue = new KeyValue(imageView.translateXProperty(), width, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
                System.out.println("duch pohyb right");
            }
        }
        timeline.setOnFinished(e -> AnimationCompleted());
        timeline.play();
    }

    private void AnimationCompleted()
    {
        maze.setRowIndex(imageView, row);
        maze.setColumnIndex(imageView, ++col);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
        System.out.println("Completed");
        setChanged();
        notifyObservers();
    }
}
