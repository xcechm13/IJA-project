package Game.Objects;

import Game.Views.HomeView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;
/**
 * Class for HomeObject
 */
public class HomeObject implements ICommonMazeObject {

    /**
     * reference to view
     */
    private HomeView homeView;

    /**
     * Constructor
     * @param maze maze layout
     * @param row row of object
     * @param col column of object
     * @param height height of maze
     * @param width width of maze
     */
    public HomeObject(GridPane maze, int row, int col, double height, double width) {
        this.homeView = new HomeView(maze, row, col, height, width);
    }

    /**
     * check if object is pacman
     * @return false
     */
    @Override
    public boolean IsPacman() {
        return false;
    }

    /**
     * check if object is ghost
     * @return false
     */
    @Override
    public boolean IsGhost() {
        return false;
    }

    /**
     * check if object is key
     * @return false
     */
    @Override
    public boolean IsKey() {
        return false;
    }

    /**
     * check if object is target
     * @return false
     */
    @Override
    public boolean IsTarget() {
        return false;
    }

    /**
     * check if object is home
     * @return true
     */
    @Override
    public boolean IsHome() {
        return true;
    }

    /**
     * Cannot use in this class
     * @return error
     */
    @Override
    public ICommonField GetField() {
        throw new UnsupportedOperationException();
    }

    /**
     * Update dimensions of object
     * @param height height of field
     * @param width width of field
     */
    @Override
    public void SetFieldSize(double height, double width) {
        homeView.SetFieldSize(height, width);
    }
}
