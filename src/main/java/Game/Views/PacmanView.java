package Game.Views;

import ConstantsEnums.Constants;
import ConstantsEnums.Direction;
import Game.Objects.PacmanObject;
import Interfaces.ICommonMazeObjectView;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Random;
import java.util.Observable;
import static java.lang.Math.min;

public class PacmanView extends Observable implements ICommonMazeObjectView {

    private ImageView imageView;
    private GridPane maze;
    private PacmanObject pacmanObject;
    private int row;
    private int col;
    private double height;
    private double width;
    private Random random;
    private Timeline timeline;
    private Direction actDirection;
    private boolean stopped;
    private String imageSource;

    public PacmanView(GridPane maze, int row, int col, double height, double width, PacmanObject object, String ImageSource) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
        this.random = new Random();
        this.pacmanObject = object;
        this.stopped = false;
        this.imageSource = ImageSource;
        this.imageView = CreateView();
        if(object != null)
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
        imageSource = imageSource == null ? Constants.PacmanSource[0] : imageSource;
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

    @Override
    public void RemoveView()
    {
        stopped = true;
        this.deleteObservers();
        maze.getChildren().remove(imageView);
        if(timeline != null)
            timeline.stop();
    }

    public void AnimatedMove(Direction direction)
    {
        if(stopped) return;
        actDirection = direction;
        switch (direction) {
            case Up -> {
                if(imageView.getImage().getUrl().contains("opened.png"))
                {
                    imageView.setImage(new Image(Constants.PacmanSource[0]));
                }
                else
                {
                    imageView.setImage(new Image(Constants.PacmanSource[4]));
                }

                KeyValue keyValue = new KeyValue(imageView.translateYProperty(), -height, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
            case Down -> {
                if(imageView.getImage().getUrl().contains("opened.png"))
                {
                    imageView.setImage(new Image(Constants.PacmanSource[1]));
                }
                else
                {
                    imageView.setImage(new Image(Constants.PacmanSource[5]));
                }

                KeyValue keyValue = new KeyValue(imageView.translateYProperty(), height, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
            case Left -> {
                if(imageView.getImage().getUrl().contains("opened.png"))
                {
                    imageView.setImage(new Image(Constants.PacmanSource[2]));
                }
                else
                {
                    imageView.setImage(new Image(Constants.PacmanSource[6]));
                }

                KeyValue keyValue = new KeyValue(imageView.translateXProperty(), -width, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
            case Right -> {
                if(imageView.getImage().getUrl().contains("opened.png"))
                {
                    imageView.setImage(new Image(Constants.PacmanSource[3]));
                }
                else
                {
                    imageView.setImage(new Image(Constants.PacmanSource[7]));
                }

                KeyValue keyValue = new KeyValue(imageView.translateXProperty(), width, Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(Constants.PacmanMoveDelay), keyValue);
                timeline = new Timeline(keyFrame);
            }
        }
        timeline.setOnFinished(e -> AnimationCompleted());
        timeline.play();
    }

    private void AnimationCompleted()
    {
        if(stopped) return;
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
