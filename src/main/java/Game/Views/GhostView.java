package Game.Views;

import ConstantsEnums.Direction;
import Interfaces.ICommonMazeObjectView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GhostView implements ICommonMazeObjectView {

    private ImageView imageView;
    private GridPane maze;
    private int row;
    private int col;
    private double height;
    private double width;

    public GhostView(ImageView imageView, GridPane maze, int row, int col, double height, double width) {
        this.imageView = imageView;
        this.maze = maze;
        this.row = row;
        this.col = col;
        this.height = height;
        this.width = width;
    }

    @Override
    public void SetFieldSize(double height, double width)
    {
        // TODO VOLÁNO PO ZMĚNĚ ŠÍŘKY OKNA
        throw new UnsupportedOperationException();
    }

    public void AnimatedMove(Direction direction)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}
