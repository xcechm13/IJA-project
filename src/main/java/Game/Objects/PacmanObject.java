package Game.Objects;

import ConstantsEnums.Direction;
import Game.Fields.PathField;
import Game.Views.PacmanView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class PacmanObject extends Thread implements ICommonMazeObject, Observer {

    private int row;
    private int col;
    private GridPane maze;
    private int lives;
    private int foundKeys;
    private int totalKeys;
    private int steps;
    private PacmanView pacmanView;
    private PathField field;

    public PacmanObject(GridPane maze, int row, int col, int totalKeys, double height, double width, ICommonField field) {
        this.row = row;
        this.col = col;
        this.maze = maze;
        this.field = (PathField) field;
        this.lives = 3;
        this.foundKeys = 0;
        this.totalKeys = totalKeys;
        this.steps = 0;
        this.pacmanView = new PacmanView(maze, row, col, height, width, this);

    }

    public void run()
    {
        pacmanView.AnimatedMove(Direction.Right);
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
        return field;
    }

    public boolean CanMove(Direction direction)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public boolean Move (Direction direction)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public int GetLives()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Observable o, Object arg) {
        pacmanView.AnimatedMove(Direction.Right);
    }
}
