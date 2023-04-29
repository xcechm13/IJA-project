package Game.Objects;

import ConstantsEnums.Direction;
import Game.Fields.PathField;
import Game.Views.GhostView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.control.TableRow;
import javafx.scene.layout.GridPane;

import java.util.*;

public class GhostObject extends Thread implements ICommonMazeObject, Observer {

    private GridPane maze;
    private GhostView ghostView;
    PathField field;
    private int row;
    private int col;
    private Random random;
    private Direction actDirection;

    public GhostObject(GridPane maze, int row, int col, double height, double width, ICommonField field) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.ghostView = new GhostView(maze, row, col, height, width, this);
        this.field = (PathField)field;
        random = new Random();
        actDirection = GetRandomPossibleDirection();
    }

    public void run()
    {
        Move();
    }

    @Override
    public boolean IsPacman()
    {
        return false;
    }

    @Override
    public boolean IsGhost()
    {
        return true;
    }

    @Override
    public boolean IsKey()
    {
        return false;
    }

    @Override
    public boolean IsTarget()
    {
        return false;
    }

    @Override
    public ICommonField GetField()
    {
        return field;
    }

    public boolean CanMove(Direction direction)
    {
        if(field.NextField(direction) != null)
        {
            if(field.NextField(direction).IsPathField())
            {
                return true;
            }
        }
        return false;
    }

    public boolean Move()
    {
        field.Remove(this);
        actDirection = GetRandomPossibleDirectionWithoutOpposite();
        field = (PathField)field.NextField(actDirection);
        ghostView.AnimatedMove(actDirection);
        return true;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        field.Put(this);
        field.Remove(this);
        actDirection = GetRandomPossibleDirectionWithoutOpposite();
        field = (PathField)field.NextField(actDirection);
        ghostView.AnimatedMove(actDirection);
    }

    private Direction GetRandomPossibleDirectionWithoutOpposite()
    {
        List<Direction> directions = new ArrayList<>();

        if(CanMove(Direction.Left) && actDirection != Direction.Right) directions.add(Direction.Left);
        if(CanMove(Direction.Right) && actDirection != Direction.Left) directions.add(Direction.Right);
        if(CanMove(Direction.Up) && actDirection != Direction.Down) directions.add(Direction.Up);
        if(CanMove(Direction.Down) && actDirection != Direction.Up) directions.add(Direction.Down);

        if(directions.size() == 0) return GetRandomPossibleDirection();

        return directions.get(random.nextInt(directions.size()));
    }

    private Direction GetRandomPossibleDirection()
    {
        List<Direction> directions = new ArrayList<>();

        if(CanMove(Direction.Left)) directions.add(Direction.Left);
        if(CanMove(Direction.Right)) directions.add(Direction.Right);
        if(CanMove(Direction.Up)) directions.add(Direction.Up);
        if(CanMove(Direction.Down)) directions.add(Direction.Down);

        return directions.get(random.nextInt(directions.size()));
    }
}
