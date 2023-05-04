package Game.Objects;

import Game.Views.GhostView;
import Game.Views.KeyView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

public class KeyObject implements ICommonMazeObject {

    private GridPane maze;
    private KeyView keyView;
    private int row;
    private int col;

    public KeyObject(GridPane maze, int row, int col, double height, double width) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.keyView = new KeyView(maze, row, col, height, width);
    }

    @Override
    public boolean IsPacman()
    {
        return false;
    }

    @Override
    public boolean IsGhost()
    {
        return false;
    }

    @Override
    public boolean IsKey()
    {
        return true;
    }

    @Override
    public boolean IsTarget()
    {
        return false;
    }

    @Override
    public boolean IsHome() {
        return false;
    }

    @Override
    public ICommonField GetField()
    {
        throw new UnsupportedOperationException();
    }

    public void RemoveFromMap()
    {
        keyView.RemoveView();
    }

    @Override
    public void SetFieldSize(double height, double width)
    {
        keyView.SetFieldSize(height, width);
    }
}
