package Game.Fields;

import ConstantsEnums.Direction;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;

import java.util.List;

public class WallField implements ICommonField {

    private int row;
    private int col;
    private ICommonField[][] maze;

    public WallField(int row, int col, ICommonField[][] maze)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }

    @Override
    public boolean IsEmpty()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ICommonMazeObject> GetMazeObjects()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void Put(ICommonMazeObject object)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void Remove(ICommonMazeObject object)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean IsPathField()
    {
        return false;
    }

    @Override
    public ICommonField NextField(Direction direction)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean Contains(ICommonMazeObject object) {
        // TODO
        throw new UnsupportedOperationException();
    }
}