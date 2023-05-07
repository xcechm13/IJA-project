package Game.Objects;

import Enums.Direction;
import Game.Fields.PathField;
import Game.Game;
import Game.Records.Point;
import Game.Views.PacmanView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Thread.sleep;
/**
 * class for PacmanObject
 */
public class PacmanObject implements ICommonMazeObject, Observer, Runnable {

    /**
     * row of pacman
     */
    int row;
    /**
     * column of pacman
     */
    int col;
    /**
     * layout
     */
    private GridPane maze;
    /**
     * number of lives
     */
    private int lives;
    /**
     * number of keys found
     */
    private int foundKeys;
    /**
     * number of all keys
     */
    private int totalKeys;
    /**
     * numbef of steps
     */
    private int steps;
    /**
     * reference to view
     */
    private PacmanView pacmanView;
    /**
     * actual field
     */
    private PathField actField;
    /**
     * posible next field
     */
    private PathField newField;
    /**
     * pathfield where pacman started
     */
    private PathField startingField;
    /**
     * scene
     */
    private Scene scene;
    /**
     * is pacman moving
     */
    private boolean Moving = true;

    //TODO
    private boolean GotoFieldActive = false;
    private int GotoFieldRow = -1;
    private int GotoFieldCol = -1;
    /**
     * next moves if going to certain field
     */
    List<Direction> path;
    /**
     *  fields where pacman has already been
     */
    List<PathField> visited = new ArrayList<>();
    /**
     * actual direction
     */
    Direction actDirection;
    /**
     * next direction
     */
    Direction reqDirection;
    /**
     * randomize
     */
    Random random;
    /**
     * game
     */
    Game game;
    /**
     * pacman stopped
     */
    private volatile boolean isStopped = false;
    /**
     * path index
     */
    int pathIndex = 0;

    /**
     * Constructor
     * @param maze layout
     * @param row row of object
     * @param col columns of object
     * @param lives starting lives
     * @param totalKeys starting number of keys
     * @param foundKeys number of keys found
     * @param steps number of steps
     * @param height maze height
     * @param width maze width
     * @param field Pathfield where to put pacman
     * @param scene scene
     * @param game game
     */
    public PacmanObject(GridPane maze, int row, int col, int lives, int totalKeys, int foundKeys, int steps, double height, double width, ICommonField field, Scene scene, Game game)
    {
        this.row = row;
        this.col = col;
        this.maze = maze;
        this.steps = steps;
        this.actField = (PathField) field;
        this.startingField = (PathField) field;
        this.lives = lives;
        this.foundKeys = foundKeys;
        this.totalKeys = totalKeys;
        this.pacmanView = new PacmanView(maze, row, col, height, width, this, null);
        this.scene = scene;
        this.game = game;
        random = new Random();
        actDirection = GetRandomDirection();
        reqDirection = actDirection;

        CrateControlHandlers();
    }

    /**
     * start pacman thread
     */
    public void run()
    {
        isStopped = false;
        Move();
    }

    /**
     * stop pacman thread
     */
    public void stop()
    {
        isStopped = true;
    }

    /**
     * check if object is pacman
     * @return true
     */
    @Override
    public boolean IsPacman()
    {
        return true;
    }

    /**
     * check if object is ghost
     * @return false
     */
    @Override
    public boolean IsGhost()
    {
        return false;
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
        pacmanView.SetFieldSize(height, width);
    }

    /**
     * check if Field in direction is Pathfield
     * @param direction to move
     * @return true if field is pathfield
     */
    public boolean CanMove(Direction direction)
    {
        if(isStopped) return false;
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
     * get actual lives of pacman
     * @return lives
     */
    public int GetLives()
    {
        return lives;
    }

    /**
     * get actual steps of pacman
     * @return steps
     */
    public int GetSteps()
    {
        return steps;
    }

    /**
     * get actual direction of pacman
     * @return actual direction
     */
    public Direction GetDirection()
    {
        return actDirection;
    }

    /**
     * one key was found
     */
    public void FindKey()
    {
        foundKeys++;
    }

    /**
     * Pacman get to the target with all the keys
     */
    public void PacmanOnTarget()
    {
        game.PacmanWin();
    }

    /**
     * every key has been taken
     * @return
     */
    public boolean AllKeysFound()
    {
        if (foundKeys == totalKeys)
        {
            return true;
        }
        return false;
    }

    /**
     * Move pacman (called when pacman thread is started)
     * @return true if pacman has moved
     */
    public boolean Move()
    {
        if(CanMove(actDirection))
        {
            game.PacmanStep();
            steps++;
            newField = (PathField) actField.NextField(actDirection);
            pacmanView.AnimatedMove(actDirection);
            return true;
        }
        Moving = false;
        return false;
    }

    /**
     * Called when pacman moves somewhere or ghost stepped on field with pacman
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {

        // Check if pacman meets ghost
        if(o instanceof PathField)
        {
            boolean ghostOnField = false;
            for(var object : ((PathField)o).GetMazeObjects())
            {
                if(object instanceof GhostObject)
                {
                    ghostOnField = true;
                    break;
                }
            }
            if(!ghostOnField) return;

            if(isStopped || (actField.row == startingField.row && actField.col == startingField.col)) return;
            lives--;
            pacmanView.RemoveView();
            isStopped = true;
            actField.Remove(this);
            if(newField != null)
                newField.Remove(this);
            startingField.Put(this);
            actField = startingField;
            try {
                game.PacmanDead(lives, totalKeys, foundKeys, steps);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // movement of ghost
        else
        {
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
                    game.PacmanStep();
                    steps++;
                    newField = (PathField) actField.NextField(reqDirection);
                    actDirection = reqDirection;
                    pacmanView.AnimatedMove(actDirection);
                }
                else if(CanMove(actDirection))
                {
                    game.PacmanStep();
                    steps++;
                    newField = (PathField) actField.NextField(actDirection);
                    pacmanView.AnimatedMove(actDirection);
                }
                else
                {
                    Moving = false;
                }
            }
        }
    }

    /**
     * key bindings
     */
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
                case ESCAPE -> {
                    try {
                        game.ESCPressed();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });



        maze.setOnMouseClicked(e -> {
            Node clickedNode = e.getPickResult().getIntersectedNode();
            // was clicked on pathfield
            if(clickedNode.getParent().getParent().getParent() == null)
            {
                Integer columnIndex = maze.getColumnIndex(clickedNode);
                Integer rowIndex = maze.getRowIndex(clickedNode);
                GoToField(rowIndex, columnIndex);
            }
        });
    }

    /**
     * get random direction
     * @return direction
     */
    private Direction GetRandomDirection()
    {
        return Direction.values()[random.nextInt(4)];
    }

    /**
     * pacman has changed movement (keybord)
     * @param direction direction pressed
     */
    private void ChangeCurrentDirection(Direction direction)
    {
        GotoFieldActive = false;
        reqDirection = direction;
        if(!Moving)
        {
            actDirection = reqDirection;
            if(CanMove(reqDirection))
            {
                Moving = true;
                game.PacmanStep();
                steps++;
                newField = (PathField) actField.NextField(reqDirection);
                pacmanView.AnimatedMove(reqDirection);
            }
        }
    }

    /**
     * go to selected location
     * @param row row of where to go
     * @param col column of where to go
     */
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
                game.PacmanStep();
                steps++;
                newField = (PathField) actField.NextField(actDirection);
                pacmanView.AnimatedMove(actDirection);
            }
        }
    }

    //TODO
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

    //TODO
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

    //TODO
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
