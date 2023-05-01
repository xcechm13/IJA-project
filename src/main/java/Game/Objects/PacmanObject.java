package Game.Objects;

import ConstantsEnums.Direction;
import Game.Fields.PathField;
import Game.Records.DLSResult;
import Game.Records.DLSState;
import Game.Records.Point;
import Game.Views.PacmanView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.*;

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
    Direction actDirection;
    Direction reqDirection;
    Random random;
    private volatile boolean isStopped = false;
    private List<Direction> path;
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
                    GotoFieldActive = false;
                }
                else
                {
                    reqDirection = path.get(pathIndex++);
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
        path = findPath(row,col);
        if(path.size() == 0) return;
        System.out.println(path);
        //GotoFieldActive = true;
        GotoFieldRow = row;
        GotoFieldCol = col;
        pathIndex = 0;
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
        System.out.println("Najdi cestu z [" + actField.row + "," + actField.col + "] z " + "[" + row + "," + col + "]");
        //System.out.println("HERE:" + findPath(row, col).get(0));

    }

    private List<Direction> findPath(int targetRow, int targetCol)
    {
        int maxDeep = 1;
        List<Direction> directions = new ArrayList<>();

        while (true)
        {
            var dlsResult = DLS(targetRow, targetCol, maxDeep);
            if(dlsResult.success())
            {
                System.out.println(dlsResult.path());
                var result = pointsToDirections(dlsResult.path());
                System.out.println(result);
                return result;
            }
            else
            {
                //System.out.println("Cesta nenalezena v hlouce " +  maxDeep);
            }

            if(dlsResult.NotExpandedBeacuseOfDeep())
            {
                maxDeep++;
            }
        }
    }

    private DLSResult DLS(int targetRow, int targetCol, int maxDeep)
    {
        List<DLSState> CLOSED = new ArrayList<>();
        Stack<DLSState> OPEN = new Stack<>();
        List<PathField> closed = new ArrayList<>();
        List<PathField> open = new ArrayList<>();
        OPEN.push(new DLSState(actField, null, 0));
        var NotExpandedBeacuseOfDeep = false;

        //path.add(new Point(last_state_parent.row, last_state_parent.col));
        while(!OPEN.empty())
        {
            DLSState top = OPEN.pop();
            List<Point> path = new ArrayList<>();

            if(top.field().col == targetCol && top.field().row == targetRow)
            {
                // TODO CELÉ ŠPATNĚ - DODĚLÁM
                System.out.println(targetRow + " " + targetCol);
                var closed_top = CLOSED.get(CLOSED.size() - 1);
                var actField = closed_top.field();
                var parentField = closed_top.parent();

                while (!CLOSED.isEmpty())
                {
                    closed_top = CLOSED.get(CLOSED.size() - 1);
                    CLOSED.remove(closed_top);

                    if(closed_top.parent() == null)
                    {
                        System.out.println("[[" + closed_top.field().row + "," + closed_top.field().col + "],[-]");

                    }
                    else
                    {
                        System.out.println("[[" + closed_top.field().row + "," + closed_top.field().col + "],[" + closed_top.parent().row + "," + closed_top.parent().col + "]");

                    }
                }
                return new DLSResult(NotExpandedBeacuseOfDeep, true, path);
            }

            if(top.deep() < maxDeep)
            {
                for (Direction direction : Direction.values())
                {
                    var expended = top.field().NextField(direction);

                    if(expended == null)
                        continue;
                    if(!expended.IsPathField())
                        continue;
                    if(closed.contains((PathField) expended))
                        continue;
                    if(open.contains((PathField) expended))
                        continue;

                    var expandedState = new DLSState((PathField) top.field().NextField(direction), top.field(), top.deep() + 1);
                    OPEN.push(expandedState);
                    open.add(expandedState.field());
                }
            }
            else
            {
                NotExpandedBeacuseOfDeep = true;
            }
            CLOSED.add(top);
            closed.add(top.field());
        }



        System.out.println("Cesta nenalezena");
        return new DLSResult(NotExpandedBeacuseOfDeep, false, null);
    }

    public static List<Direction> pointsToDirections(List<Point> points) {
        List<Direction> directions = new ArrayList<>();

        List<Point> reversedPoints = new ArrayList<>();
        for (int i = points.size() - 1; i >= 0; i--) {
            reversedPoints.add(points.get(i));
        }

        for (int i = 0; i < reversedPoints.size() - 1; i++) {
            Point currentPoint = reversedPoints.get(i);
            Point nextPoint = reversedPoints.get(i + 1);

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
