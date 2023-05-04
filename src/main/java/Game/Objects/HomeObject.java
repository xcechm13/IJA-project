package Game.Objects;

import Game.Views.HomeView;
import Game.Views.KeyView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

public class HomeObject implements ICommonMazeObject {

    private GridPane maze;
    private HomeView homeView;
    private int row;
    private int col;

    public HomeObject(GridPane maze, int row, int col, double height, double width) {
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.homeView = new HomeView(maze, row, col, height, width);
    }

    @Override
    public boolean IsPacman() {
        return false;
    }

    @Override
    public boolean IsGhost() {
        return false;
    }

    @Override
    public boolean IsKey() {
        return false;
    }

    @Override
    public boolean IsTarget() {
        return false;
    }

    @Override
    public boolean IsHome() {
        return true;
    }

    @Override
    public ICommonField GetField() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void SetFieldSize(double height, double width) {
        homeView.SetFieldSize(height, width);
    }
}
