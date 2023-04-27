package Game.Views;

import Interfaces.ICommonMazeObjectView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class KeyView implements ICommonMazeObjectView {

    private ImageView imageView;
    private GridPane maze;
    private int row;
    private int col;
    private double height;
    private double width;

    public KeyView(ImageView imageView, GridPane maze, int row, int col, double height, double width) {
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

    public void Remove()
    {
        // TODO TOTO BY MOHLO BÝT ŘEŠENO PŘES DESTRUKTOR (SMAŽE KLÍČ Z MAZE)
        throw new UnsupportedOperationException();
    }
}
