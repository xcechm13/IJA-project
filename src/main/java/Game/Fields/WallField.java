package Game.Fields;

import Enums.Direction;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;

import java.util.List;

/**
 * Class for Wallfield
 */
public class WallField implements ICommonField {

    /**
     * row of field
     */
    private int row;
    /**
     * column of field
     */
    private int col;
    /**
     * whole maze (2d array)
     */
    private ICommonField[][] maze;

    /**
     * Constructor
     * @param row row of field
     * @param col column of field
     * @param maze whole maze
     */
    public WallField(int row, int col, ICommonField[][] maze)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }

    /**
     * Cannot use in Wallfield Class
     * @return error
     */
    @Override
    public boolean IsEmpty()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Cannot use in Wallfield Class
     * @return error
     */
    @Override
    public List<ICommonMazeObject> GetMazeObjects()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Cannot use in Wallfield Class
     * @return error
     */
    @Override
    public void Put(ICommonMazeObject object)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Cannot use in Wallfield Class
     * @return error
     */
    @Override
    public void Remove(ICommonMazeObject object)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Cannot use in Wallfield Class
     * @return error
     */
    @Override
    public boolean IsPathField()
    {
        return false;
    }

    /**
     * Cannot use in Wallfield Class
     * @return error
     */
    @Override
    public ICommonField NextField(Direction direction)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Cannot use in Wallfield Class
     * @return error
     */
    @Override
    public boolean Contains(ICommonMazeObject object)
    {
        throw new UnsupportedOperationException();
    }
}
