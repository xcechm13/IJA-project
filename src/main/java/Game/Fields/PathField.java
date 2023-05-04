package Game.Fields;

import ConstantsEnums.Direction;
import Game.Objects.GhostObject;
import Game.Objects.KeyObject;
import Game.Objects.PacmanObject;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.List;

public class PathField extends Observable implements ICommonField {

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
                    if(o.IsKey())
                    {
                        Remove(o);
                        ((KeyObject) o).RemoveFromMap();
                        ((PacmanObject) object).FindKey();
                    }

                    if(o.IsTarget() && ((PacmanObject) object).AllKeysFound())
                    {
                        ((PacmanObject) object).PacmanOnTarget();
                        System.out.println("VYHRAL JSI");
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

    @Override
    public boolean IsPathField()
    {
        return true;
    }

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
