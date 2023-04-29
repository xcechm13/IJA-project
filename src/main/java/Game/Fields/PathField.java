package Game.Fields;

import ConstantsEnums.Direction;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;

import java.util.ArrayList;
import java.util.List;

public class PathField implements ICommonField {

    public int row;
    public int col;
    private ICommonField[][] maze;

    List<ICommonMazeObject> MazeObjects;

    public PathField(int row, int col, ICommonField[][] maze)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
        MazeObjects = new ArrayList<>();
    }

    @Override
    public boolean IsEmpty()
    {
        return MazeObjects.size() == 0;
    }

    @Override
    public List<ICommonMazeObject> GetMazeObjects()
    {
        return MazeObjects;
    }

    @Override
    public void Put(ICommonMazeObject object)
    {
        if(Contains(object))
        {
            throw new UnsupportedOperationException();
        }

        if(object.IsPacman())
        {
            //TODO addObserver
        }
        else if(object.IsGhost())
        {
            //TODO addObserver
        }
        MazeObjects.add(object);
    }

    @Override
    public void Remove(ICommonMazeObject object)
    {
        if(!Contains(object))
        {
            throw new UnsupportedOperationException();
        }

        if(object.IsPacman())
        {
            //TODO removeObserver
        }
        else if(object.IsGhost())
        {
            //TODO removeObserver
        }
        MazeObjects.remove(object);
        //TODO notify observers
    }

    @Override
    public boolean IsPathField()
    {
        return true;
    }

    @Override
    public ICommonField NextField(Direction direction)
    {
        switch (direction)
        {
            case Up:
                if(row == 0) return null;
                return maze[row -1][col];
            case Down:
                if(row == maze.length - 1) return null;
                return maze[row +1][col];
            case Left:
                if(col == 0) return null;
                return maze[row][col -1];
            case Right:
                if(row == maze[row].length - 1) return null;
                return maze[row][col +1];
            default: return null;
        }
    }

    @Override
    public boolean Contains(ICommonMazeObject object) {
        for (int i = 0; i < MazeObjects.size(); i++)
        {
            if(MazeObjects.get(i) == object)
            {
                return true;
            }
        }
        return false;
    }
}
