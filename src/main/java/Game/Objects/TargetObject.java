package Game.Objects;

import Game.Views.TargetView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

public class TargetObject implements ICommonMazeObject {

    private GridPane maze;
    private TargetView targetView;
    private int row;
    private int col;

    public TargetObject(GridPane maze, int row, int col, double height, double width) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.targetView = new TargetView(maze, row, col, height, width);
    }

    @Override
    public boolean IsPacman()
    {
        return false;
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
        return true;
    }

    @Override
    public boolean IsHome() {
        return false;
    }

    @Override
    public ICommonField GetField()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void SetFieldSize(double height, double width) {
        targetView.SetFieldSize(height, width);
    }
}
