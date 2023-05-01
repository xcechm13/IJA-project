package Game.Objects;

import ConstantsEnums.Direction;
import Game.Fields.PathField;
import Game.Records.Point;
import Game.Views.PacmanView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.*;

import static java.lang.Math.max;

public class PacmanObject implements ICommonMazeObject, Observer, Runnable {

    private int row;
    private int col;
    private static GridPane maze;
    private int lives;
    private int foundKeys;
    private int totalKeys;
    private int steps;
    private PacmanView pacmanView;
    private static PathField actField;
    private PathField newField;
    private Scene scene;
    private boolean Moving = true;
    private boolean GotoFieldActive = false;
    private int GotoFieldRow = -1;
    private int GotoFieldCol = -1;
    List<Direction> path;
    List<PathField> visited = new ArrayList<>();
    Direction actDirection;
    Direction reqDirection;
    Random random;
    private volatile boolean isStopped = false;
    int pathIndex = 0;

    public PacmanObject(GridPane maze, int row, int col, int totalKeys, double height, double width, ICommonField field, Scene scene)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
        this.actField = (PathField) field;
        this.lives = 3;
        this.foundKeys = 0;
        this.totalKeys = totalKeys;
        this.steps = 0;
        this.pacmanView = new PacmanView(maze, row, col, height, width, this);
        this.scene = scene;
        random = new Random();
        actDirection = GetRandomDirection();
        reqDirection = actDirection;

        CrateControlHandlers();
    }

    public void run()
    {
        isStopped = false;
        Move();
    }

    public void stop()
    {
        isStopped = true;
    }

    @Override
    public boolean IsPacman()
    {
        return true;
    }

    @Override
    public boolean IsGhost()
    {
        return false;
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
        return actField;
    }

    @Override
    public void SetFieldSize(double height, double width)
    {
        pacmanView.SetFieldSize(height, width);
    }

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

    public int GetLives()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public boolean Move()
    {
        if(CanMove(actDirection))
        {
            newField = (PathField) actField.NextField(actDirection);
            pacmanView.AnimatedMove(actDirection);
            return true;
        }
        Moving = false;
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof PacmanView)
        {
            // update i PacmanView
        }
        else
        {
            // update z PathField
        }

        System.out.println(reqDirection);

        actField.Remove(this);
        newField.Put(this);
        actField = newField;
        if(!isStopped)
        {
            if(GotoFieldActive)
            {
                if(actField.row == GotoFieldRow && actField.col == GotoFieldCol)
                {
                    reqDirection = actDirection;
                    GotoFieldActive = false;
                }
                else
                {
                    try {
                        reqDirection = path.get(pathIndex++);
                    }catch (Exception e){}

                }
            }

            if(CanMove(reqDirection))
            {
                newField = (PathField) actField.NextField(reqDirection);
                actDirection = reqDirection;
                pacmanView.AnimatedMove(actDirection);
            }
            else if(CanMove(actDirection))
            {
                newField = (PathField) actField.NextField(actDirection);
                pacmanView.AnimatedMove(actDirection);
            }
            else
            {
                Moving = false;
            }
        }
    }

    private void CrateControlHandlers()
    {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W, UP -> {
                    ChangeCurrentDirection(Direction.Up);
                }
                case A, LEFT -> {
                    ChangeCurrentDirection(Direction.Left);
                }
                case S, DOWN -> {
                    ChangeCurrentDirection(Direction.Down);
                }
                case D, RIGHT -> {
                    ChangeCurrentDirection(Direction.Right);
                }
            }
        });



        maze.setOnMouseClicked(e -> {
            Node clickedNode = e.getPickResult().getIntersectedNode();
            // kliklo se na pathfield?
            if(clickedNode.getParent().getParent().getParent() instanceof StackPane)
            {
                Integer columnIndex = maze.getColumnIndex(clickedNode);
                Integer rowIndex = maze.getRowIndex(clickedNode);
                GoToField(rowIndex, columnIndex);
            }
        });
    }

    private Direction GetRandomDirection()
    {
        var a = GetField();
        return Direction.values()[random.nextInt(4)];
    }

    private void ChangeCurrentDirection(Direction direction)
    {
        reqDirection = direction;
        if(!Moving)
        {
            actDirection = reqDirection;
            if(CanMove(reqDirection))
            {
                Moving = true;
                newField = (PathField) actField.NextField(reqDirection);
                pacmanView.AnimatedMove(reqDirection);
            }
        }
    }

    private void GoToField(int row, int col)
    {
        var result = findPath(row, col);
        path = PointsToDirections(result);
        for(int i=0; i<path.size(); i++)
        {
            System.out.println(path.get(i));
        }

        if(path.size() == 0) return;
        GotoFieldActive = true;
        GotoFieldRow = row;
        GotoFieldCol = col;
        pathIndex = 0;
        if(!Moving)
        {
            actDirection = GotoFieldActive ? path.get(pathIndex++) : reqDirection;
            if(CanMove(actDirection))
            {
                Moving = true;
                newField = (PathField) actField.NextField(actDirection);
                pacmanView.AnimatedMove(actDirection);
            }
        }
        System.out.println("Najdi cestu z [" + actField.row + "," + actField.col + "] z " + "[" + row + "," + col + "]");
        //System.out.println("HERE:" + findPath(row, col).get(0));

    }

    private ArrayList<Point> findPath(int targetRow, int targetCol)
    {
        int maxDeep = 1;

        while (true)
        {
            visited.clear();
            visited.add(actField);
            var result = DLS(actField, targetRow, targetCol, ++maxDeep);
            if(result == null)
            {
                maxDeep++;
            }
            else
            {
                return result;
            }
        }
    }

    private ArrayList<Point> DLS(PathField field, int targetRow, int targetCol, int maxDeep)
    {
        if(field == null || maxDeep == 0)
        {
            return null;
        }

        ArrayList<Point> currentPath = new ArrayList<>();
        currentPath.add(new Point(field.row, field.col));

        for (Direction direction : Direction.values())
        {
            var nextField = field.NextField(direction);
            if(nextField == null || !nextField.IsPathField()) continue;

            var pathField = (PathField) nextField;

            if(pathField.row == targetRow && pathField.col == targetCol)
            {
                currentPath.add(new Point(pathField.row, pathField.col));
                return currentPath;
            }
            else if(!visited.contains(pathField))
            {
                visited.add(pathField);
                ArrayList<Point> result = DLS(pathField, targetRow, targetCol, maxDeep - 1);
                if(result != null)
                {
                    currentPath.addAll(result);
                    return currentPath;
                }
            }
        }
        return null;
    }

    public static List<Direction> PointsToDirections(List<Point> points) {
        List<Direction> directions = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            Point currentPoint = points.get(i);
            Point nextPoint = points.get(i + 1);

            if (currentPoint.col() < nextPoint.col()) {
                directions.add(Direction.Right);
            } else if (currentPoint.col() > nextPoint.col()) {
                directions.add(Direction.Left);
            } else if (currentPoint.row()  < nextPoint.row() ) {
                directions.add(Direction.Down);
            } else if (currentPoint.row() > nextPoint.row() ) {
                directions.add(Direction.Up);
            }
        }

        return directions;
    }
}
