package Game.Objects;

import Game.Views.TargetView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

/**
 * Class fo TargetObject
 */
public class TargetObject implements ICommonMazeObject {

    /**
     * layout of maze
     */
    private GridPane maze;
    /**
     * reference of view
     */
    private TargetView targetView;
    /**
     * row of object
     */
    private int row;
    /**
     * column of object
     */
    private int col;

    /**
     * Constructor
     * @param maze layout maze
     * @param row row of object
     * @param col column of object
     * @param height height of maze
     * @param width width of maze
     */
    public TargetObject(GridPane maze, int row, int col, double height, double width) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.targetView = new TargetView(maze, row, col, height, width);
    }

    /**
     * check if object is pacman
     * @return false
     */
    @Override
    public boolean IsPacman()
    {
        return false;
    }

    /**
     * check if object is ghost
     * @return false
     */
    @Override
    public boolean IsGhost()
    {
        return false;
    }

    /**
     * check if object is key
     * @return false
     */
    @Override
    public boolean IsKey()
    {
        return false;
    }

    /**
     * check if object is target
     * @return true
     */
    @Override
    public boolean IsTarget()
    {
        return true;
    }

    /**
     * check if object is home
     * @return false
     */
    @Override
    public boolean IsHome() {
        return false;
    }

    /**
     * Cannot use in this class
     * @return error
     */
    @Override
    public ICommonField GetField()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Update dimensions of object
     * @param height height of maze
     * @param width width of maze
     */
    @Override
    public void SetFieldSize(double height, double width) {
        targetView.SetFieldSize(height, width);
    }
}
