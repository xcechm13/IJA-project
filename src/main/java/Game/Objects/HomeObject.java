package Game.Objects;

import Game.Views.HomeView;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;
import javafx.scene.layout.GridPane;

public class HomeObject implements ICommonMazeObject {

    private HomeView homeView;

    public HomeObject(GridPane maze, int row, int col, double height, double width) {
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
