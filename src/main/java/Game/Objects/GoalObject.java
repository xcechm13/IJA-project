package Game.Objects;

import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

public class GoalObject implements ICommonMazeObject {

    private GridPane maze;
    private int row;
    private int col;

    public GoalObject(GridPane maze, int row, int col) {
        this.maze = maze;
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean IsPacman()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean IsGhost()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean IsKey()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean IsGoal()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public ICommonField GetField()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}
