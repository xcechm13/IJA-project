package Game.Objects;

import ConstantsEnums.Direction;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

public class GhostObject implements ICommonMazeObject {

    private GridPane maze;
    private int row;
    private int col;

    public GhostObject(GridPane maze, int row, int col) {
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
    public boolean IsTarget()
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

    public boolean CanMove(Direction direction)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public boolean Move (Direction direction)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}
