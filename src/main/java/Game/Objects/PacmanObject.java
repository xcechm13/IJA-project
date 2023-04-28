package Game.Objects;

import ConstantsEnums.Direction;
import Game.Views.PacmanView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

public class PacmanObject implements ICommonMazeObject {

    private int row;
    private int col;
    private GridPane maze;
    private int lives;
    private int foundKeys;
    private int totalKeys;
    private int steps;
    private PacmanView pacmanView;

    public PacmanObject(int row, int col, GridPane maze, int foundKeys, int totalKeys, PacmanView pacmanView) {
        this.row = row;
        this.col = col;
        this.maze = maze;
        this.lives = 3;
        this.foundKeys = foundKeys;
        this.totalKeys = totalKeys;
        this.steps = 0;
        this.pacmanView = pacmanView;
    }

    @Override
    public boolean IsPacman()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean IsGhost()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean IsKey()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean IsTarget()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public ICommonField GetField()
    {
        // TODO
        throw new UnsupportedOperationException();
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
}
