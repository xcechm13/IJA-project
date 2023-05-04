package Game.Views;

import ConstantsEnums.Constants;
import Interfaces.ICommonMazeObjectView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import static java.lang.Math.min;

public class HomeView implements ICommonMazeObjectView {
    ImageView imageView;
    private GridPane maze;
    private int row;
    private int col;
    private double height;
    private double width;

    public HomeView(GridPane maze, int row, int col, double height, double width) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
        this.imageView = CreateView();
    }

    @Override
    public void SetFieldSize(double height, double width) {
        this.height = height;
        this.width = width;
        imageView.setFitWidth(min(width, height));
        imageView.setFitHeight(min(width, height));
    }

    @Override
    public ImageView CreateView() {
        Image image = new Image(Constants.HomeSource);
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
}