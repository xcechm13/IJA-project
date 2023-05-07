package Game.Fields;

import Enums.Direction;
import Game.Objects.GhostObject;
import Game.Objects.KeyObject;
import Game.Objects.PacmanObject;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.List;

/**
 * Class for Pathfield
 */
public class PathField extends Observable implements ICommonField {

    /**
     * row in maze
     */
    public int row;
    /**
     * column in maze
     */
    public int col;
    /**
     * whole maze (2d array)
     */
    private ICommonField[][] maze;

    /**
     * MazeObjects inside this Pathfield
     */
    List<ICommonMazeObject> MazeObjects;

    /**
     * Contructor
     * @param row row of field
     * @param col column of field
     * @param maze whole maze
     */
    public PathField(int row, int col, ICommonField[][] maze)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
        MazeObjects = new ArrayList<>();
    }

    /**
     * check if pathfield does have any objects
     * @return true if empty
     */
    @Override
    public boolean IsEmpty()
    {
        return MazeObjects.size() == 0;
    }

    /**
     * Find all MazeObjects in Pathfield
     * @return MazeObjects
     */
    @Override
    public List<ICommonMazeObject> GetMazeObjects()
    {
        return MazeObjects;
    }

    /**
     * Put object inside Field
     * @param object that you want to put inside the field
     */
    @Override
    public void Put(ICommonMazeObject object)
    {
        synchronized (MazeObjects)
        {
            if(Contains(object))
            {
                throw new UnsupportedOperationException();
            }

            MazeObjects.add(object);
            if(object.IsPacman())
            {
                this.addObserver((PacmanObject)object);

                for (ICommonMazeObject o: MazeObjects)
                {
                    //Pacman takes key
                    if(o.IsKey())
                    {
                        Remove(o);
                        ((KeyObject) o).RemoveFromMap();
                        ((PacmanObject) object).FindKey();
                    }

                    //Pacman wins
                    if(o.IsTarget() && ((PacmanObject) object).AllKeysFound())
                    {
                        ((PacmanObject) object).PacmanOnTarget();
                    }
                }

                setChanged();
                notifyObservers();
            }
            else if(object.IsGhost())
            {
                this.addObserver((GhostObject)object);
                setChanged();
                notifyObservers();
            }
        }
    }

    /**
     * remove object from field
     * @param object that you want to remove from this field
     */
    @Override
    public void Remove(ICommonMazeObject object)
    {
        synchronized (MazeObjects)
        {
            if(!Contains(object))
            {
                return;
            }

            if(object.IsPacman())
            {
                this.deleteObserver((PacmanObject)object);
            }
            else if(object.IsGhost())
            {
                this.deleteObserver((GhostObject)object);
            }
            MazeObjects.remove(object);
        }
    }

    /**
     * check if field is pathfield/wallfield
     * @return true if field is Pathfield
     */
    @Override
    public boolean IsPathField()
    {
        return true;
    }

    /**
     * Get field next to this field
     * @param direction field on which side
     * @return field in the direction/ null incase of error
     */
    @Override
    public ICommonField NextField(Direction direction)
    {
        switch (direction) {
            case Up -> {
                if (row == 0) return null;
                return maze[row - 1][col];
            }
            case Down -> {
                if (row == maze.length - 1) return null;
                return maze[row + 1][col];
            }
            case Left -> {
                if (col == 0) return null;
                return maze[row][col - 1];
            }
            case Right -> {
                if (row == maze[row].length - 1) return null;
                return maze[row][col + 1];
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * check if object is in field
     * @param object searched MazeObject
     * @return true if object is in the field
     */
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
