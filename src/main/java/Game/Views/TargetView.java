package Game.Views;

import ConstantsEnums.Constants;
import Interfaces.ICommonMazeObjectView;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Random;

import static java.lang.Math.min;

public class TargetView implements ICommonMazeObjectView {

    private ImageView imageView;
    private GridPane maze;
    private int row;
    private int col;
    private double height;
    private double width;
    private double commonObjectSize;

    public TargetView(GridPane maze, int row, int col, double height, double width)
    {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
        this.imageView = CreateView();
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
        Image image = new Image(Constants.TargetSource);
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
        maze.getChildren().remove(imageView);
    }
}
