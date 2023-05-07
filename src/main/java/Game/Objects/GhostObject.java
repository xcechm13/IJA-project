package Game.Objects;

import Enums.Direction;
import Game.Fields.PathField;
import Game.Views.GhostView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

import java.util.*;

/**
 * Class for GhostObject
 */
public class GhostObject implements ICommonMazeObject, Observer, Runnable {

    /**
     * layout
     */
    private GridPane maze;
    /**
     * reference of view
     */
    private GhostView ghostView;
    /**
     * actual field
     */
    private PathField actField;
    /**
     * possible next field
     */
    private PathField newField;
    /**
     * starting row
     */
    private int row;
    /**
     * starting column
     */
    private int col;
    /**
     * randomize direction of next move
     */
    private Random random;
    /**
     * actual direction of ghost
     */
    private Direction actDirection;
    private volatile boolean isStopped = false;

    /**
     * Constructor
     * @param maze layout
     * @param row row of object
     * @param col column of object
     * @param height maze height
     * @param width maze width
     * @param field Pathfield to put ghost in it
     */
    public GhostObject(GridPane maze, int row, int col, double height, double width, ICommonField field) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.ghostView = new GhostView(maze, row, col, height, width, this, null);
        this.actField = (PathField)field;
        random = new Random();
        actDirection = GetRandomPossibleDirection();
    }

    /**
     * start thread
     */
    public void run()
    {
        isStopped = false;
        Move();
    }

    /**
     * stop thread
     */
    public void stop()
    {
        isStopped = true;
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
     * @return true
     */
    @Override
    public boolean IsGhost()
    {
        return true;
    }

    /**
     * check if object is key
     * @return false
     */
    @Override
    public boolean IsKey()
    {
        return false;
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
        return actField;
    }

    /**
     * Update dimensions of object
     * @param height height of maze
     * @param width width of maze
     */
    @Override
    public void SetFieldSize(double height, double width)
    {
        ghostView.SetFieldSize(height, width);
    }

    /**
     * check if Field in direction is Pathfield
     * @param direction to move
     * @return true if field is pathfield
     */
    public boolean CanMove(Direction direction)
    {
        if(actField.NextField(direction) != null)
        {
            if(actField.NextField(direction).IsPathField())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Move ghost (called when ghost thread is started)
     * @return true / false incase of error
     */
    public boolean Move()
    {
        actDirection = GetRandomPossibleDirectionWithoutOpposite();
        if(actDirection == null) return false;
        newField = (PathField) actField.NextField(actDirection);
        ghostView.AnimatedMove(actDirection);
        return true;
    }

    /**
     * Move ghost around the maze
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof PathField)
        {

        }
        else
        {
            actField.Remove(this);
            newField.Put(this);
            actField = newField;
            if(!isStopped)
            {
                actDirection = GetRandomPossibleDirectionWithoutOpposite();
                if(actDirection == null) return;
                newField = (PathField) actField.NextField(actDirection);
                ghostView.AnimatedMove(actDirection);
            }
        }
    }

    /**
     * Get random direction where ghost can go (not opposite so the ghost movement is natural)
     * @return direction where ghost will go
     */
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

    /**
     * Get random direction where ghost can go
     * @return direction where ghost will go
     */
    private Direction GetRandomPossibleDirection()
    {
        List<Direction> directions = new ArrayList<>();

        if(CanMove(Direction.Left)) directions.add(Direction.Left);
        if(CanMove(Direction.Right)) directions.add(Direction.Right);
        if(CanMove(Direction.Up)) directions.add(Direction.Up);
        if(CanMove(Direction.Down)) directions.add(Direction.Down);

        if(directions.size() == 0)
        {
            stop();
            return null;
        }

        return directions.get(random.nextInt(directions.size()));
    }

    /**
     * get color of ghost
     * @return image source
     */
    public String GetGhostColorFromView()
    {
        return ghostView.GetGhostColor();
    }
}
