package Game.Views;

import ConstantsEnums.Constants;
import Interfaces.ICommonMazeObjectView;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import static java.lang.Math.min;

/**
 * Class of HomeView
 */

public class HomeView implements ICommonMazeObjectView {
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
     * Constructor
     * @param maze maze layout
     * @param row row of object
     * @param col column of object
     * @param height height of maze
     * @param width width of maze
     */
    public HomeView(GridPane maze, int row, int col, double height, double width) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
        this.imageView = CreateView();
    }

    /**
     * Update dimensions of object
     * @param height maze height
     * @param width maze width
     */
    @Override
    public void SetFieldSize(double height, double width) {
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
    public ImageView CreateView() {
        Image image = new Image(Constants.HomeSource);
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
     * Remove object (not visible)
     */
    @Override
    public void RemoveView() {
        maze.getChildren().remove(imageView);
    }
}
