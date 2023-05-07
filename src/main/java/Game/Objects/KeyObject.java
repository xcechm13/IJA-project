package Game.Objects;

import Game.Views.GhostView;
import Game.Views.KeyView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

/**
 * Class for KeyObject
 */
public class KeyObject implements ICommonMazeObject {

    /**
     * layout of maze
     */
    private GridPane maze;
    /**
     * reference to View
     */
    private KeyView keyView;
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
     * @param maze gridpane layout
     * @param row row of object
     * @param col column of object
     * @param height height of maze
     * @param width width of maze
     */
    public KeyObject(GridPane maze, int row, int col, double height, double width) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.keyView = new KeyView(maze, row, col, height, width);
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
     * @return true
     */
    @Override
    public boolean IsKey()
    {
        return true;
    }

    /**
     * check if object is target
     * @return false
     */
    @Override
    public boolean IsTarget()
    {
        return false;
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
     * Remove object (not visible)
     */
    public void RemoveFromMap()
    {
        keyView.RemoveView();
    }

    /**
     * Update dimensions of object
     * @param height height of maze
     * @param width width of maze
     */
    @Override
    public void SetFieldSize(double height, double width)
    {
        keyView.SetFieldSize(height, width);
    }
}
