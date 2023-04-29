package Game;

import ConstantsEnums.Constants;
import ConstantsEnums.FieldNeighbour;
import ConstantsEnums.FieldPixels;
import Game.Fields.PathField;
import Game.Fields.WallField;
import Game.Objects.GhostObject;
import Game.Objects.KeyObject;
import Game.Objects.PacmanObject;
import Game.Objects.TargetObject;
import Game.Records.MapParserResult;
import Interfaces.ICommonField;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Math.min;

public class Game extends Application {

    private static MapParser mapParser;
    private static Stage stage;
    private static StackPane root;
    private static GridPane gameGridPane;
    private static double CommonObjectSize;
    private static int rows;
    private static int cols;
    private static int keys;
    private ICommonField[][] maze;

    @Override
    public void start(Stage stage) throws IOException
    {
        Game.stage = stage;
        stage.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            // TODO reakce na změnu šířky okna
            System.out.println("Nová šířka okna: " + newWidth);
        });
        stage.heightProperty().addListener((observableValue, oldHeight, newHeight) -> {
            // TODO reakce na změnu výšky okna
            System.out.println("Nová výška okna: " + newHeight);
        });

        StackPane root = new StackPane();
        Game.root = root;
        root.setStyle("-fx-background-color: #000000;");

        Scene scene = new Scene(root, Constants.WindowWidth, Constants.WindowHeight);
        scene.setUserAgentStylesheet(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PACMAN");
        stage.show();
        OpenGame(0);
    }

    public static void main(String[] args) {
        mapParser = new MapParser();
        launch();
    }

    private VBox GetField(FieldNeighbour fieldNeighbour)
    {
        var booleanField = FieldPixels.CreateField(fieldNeighbour);

        // Položka v hracím poli
        VBox vBox = new VBox();

        GridPane vBoxGridPane = new GridPane();
        vBoxGridPane.setPrefWidth(10000);
        vBoxGridPane.setPrefHeight(10000);
        // přidání definice sloupců
        ColumnConstraints vBoxGridPaneColumnConstraints = new ColumnConstraints();
        vBoxGridPaneColumnConstraints.setPercentWidth(100.0 / Constants.FieldPixelsNum);
        for(int i=0; i<Constants.FieldPixelsNum; i++)
        {
            vBoxGridPane.getColumnConstraints().add(vBoxGridPaneColumnConstraints);
        }
        // přidání definice řádků
        RowConstraints vBoxGridPaneRowConstraints = new RowConstraints();
        vBoxGridPaneRowConstraints.setPercentHeight(100.0 / Constants.FieldPixelsNum);
        for(int i=0; i<Constants.FieldPixelsNum; i++)
        {
            vBoxGridPane.getRowConstraints().add(vBoxGridPaneRowConstraints);
        }

        for(int r = 0; r < Constants.FieldPixelsNum; r++)
        {
            for(int c = 0; c < Constants.FieldPixelsNum; c++)
            {
                VBox pixel = new VBox();
                if(booleanField[r][c])
                {
                    pixel.getStyleClass().add("vboxBlue");
                }
                vBoxGridPane.getChildren().add(pixel);
                vBoxGridPane.setRowIndex(pixel, r);
                vBoxGridPane.setColumnIndex(pixel, c);
            }
        }

        vBox.getChildren().add(vBoxGridPane);
        return vBox;
    }

    private boolean IsWall(String[][] maze, int row, int col)
    {
        if(row < 0 || row >= maze.length || col < 0 || col >= maze[0].length)
        {
            return false;
        }
        if(maze[row][col].startsWith("X"))
        {
            return true;
        }
        return false;
    }

    private GridPane GetGameGridPane(double percentageHeight, double percentageWidth, int rows, int cols, String[][] fields)
    {
        GridPane GridPane = CreateGridPane(percentageHeight, percentageWidth, rows, cols);

        for(int r=0; r<fields.length; r++)
        {
            for(int c=0; c<fields[0].length; c++)
            {
                VBox vBox = new VBox();

                if(!IsWall(fields, r, c))
                {
                    GridPane.getChildren().add(vBox);
                    GridPane.setRowIndex(vBox, r);
                    GridPane.setColumnIndex(vBox, c);
                    continue;
                }

                if(!IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.NoNeighbour);
                }
                else if(!IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.RightNeighbour);
                }
                else if(!IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.LeftNeighbour);
                }
                else if(!IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.LeftRightNeighbour);
                }
                else if(!IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.DownNeighbour);
                }
                else if(!IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.DownRightNeighbour);
                }
                else if(!IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.DownLeftNeighbour);
                }
                else if(!IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.DownLeftRightNeighbour);
                }
                else if(IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpNeighbour);
                }
                else if(IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpRightNeighbour);
                }
                else if(IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpLeftNeighbour);
                }
                else if(IsWall(fields, r-1, c) && !IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpLeftRightNeighbour);
                }
                else if(IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpDownNeighbour);
                }
                else if(IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && !IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpDownRightNeighbour);
                }
                else if(IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && !IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpDownLeftNeighbour);
                }
                else if(IsWall(fields, r-1, c) && IsWall(fields, r+1, c) && IsWall(fields, r, c-1) && IsWall(fields, r, c+1))
                {
                    vBox = GetField(FieldNeighbour.UpDownLeftRightNeighbour);
                }

                GridPane.getChildren().add(vBox);
                GridPane.setRowIndex(vBox, r);
                GridPane.setColumnIndex(vBox, c);
            }
        }
        return GridPane;
    }

    private GridPane CreateGridPane(double percentageHeight, double percentageWidth, int rows, int cols)
    {
        GridPane GridPane = new GridPane();
        GridPane.getStyleClass().add("gridPane");
        GridPane.setPrefHeight(10000);
        GridPane.setPrefWidth(10000);

        // přidání definice sloupců
        ColumnConstraints ColumnConstraints = new ColumnConstraints();
        ColumnConstraints.setPercentWidth(percentageWidth / cols);
        for(int i=0; i<cols; i++)
        {
            GridPane.getColumnConstraints().add(ColumnConstraints);
        }

        // přidání definice řádků
        RowConstraints RowConstraints = new RowConstraints();
        RowConstraints.setPercentHeight(percentageHeight / rows);
        for(int i=0; i<rows; i++)
        {
            GridPane.getRowConstraints().add(RowConstraints);
        }

        return GridPane;
    }

    private GridPane CreteMenuGridPane()
    {
        GridPane GridPane = new GridPane();
        GridPane.getStyleClass().add("gridPane");
        GridPane.setPrefHeight(10000);
        GridPane.setPrefWidth(10000);

        // přidání definice sloupce
        ColumnConstraints cc;
        cc = new ColumnConstraints();
        cc.setPercentWidth(100.0);
        GridPane.getColumnConstraints().add(cc);

        // přidání definice řádku1 (10% výška)
        RowConstraints rc;
        rc = new RowConstraints();
        rc.setPercentHeight(10);
        GridPane.getRowConstraints().add(rc);
        // přidání definice řádku2 (90% výška)
        rc = new RowConstraints();
        rc.setPercentHeight(90);
        GridPane.getRowConstraints().add(rc);

        return GridPane;
    }

    private void MapMouseEntered(VBox map)
    {
        map.setCursor(Cursor.HAND);
    }

    private void ReplayEntered(Label label)
    {
        label.setCursor(Cursor.HAND);
    }

    private void LoadReplay()
    {
        // TODO PŘEPNOUT SE NA PŘEHRÁVÁNÍ POSLEDNÍ HRY
        System.out.println("Přehrávání poslední hry.");
    }

    private void LoadGame(int mapNumber)
    {
        // TODO PŘEPNOUT SE NA HRU
        System.out.println("Byla vybrána mapa č. " + mapNumber + ".");
    }

    private void CreateObjectsMap(String map[][])
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private void Run()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private void Resume()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private void OpenMenu() throws IOException {
        root.getChildren().removeAll();

        VBox vBox = new VBox();
        MapParserResult mapParserResult;
        vBox.setStyle("-fx-background-color: #FFFFFF;");

        GridPane newGridPane = CreateGridPane(100.0, 100.0, 2, 2);
        newGridPane.setHgap(10);
        newGridPane.setVgap(10);

        VBox vBox1 = new VBox();
        mapParserResult = mapParser.getMap(0);
        vBox1.getChildren().add(GetGameGridPane(100.0, 100.0, mapParserResult.rows(), mapParserResult.cols(), mapParserResult.fields()));
        vBox1.setOnMouseEntered(e -> MapMouseEntered(vBox1));
        vBox1.setOnMouseClicked(e -> LoadGame(0));

        VBox vBox2 = new VBox();
        mapParserResult = mapParser.getMap(1);
        vBox2.getChildren().add(GetGameGridPane(100.0, 100.0, mapParserResult.rows(), mapParserResult.cols(), mapParserResult.fields()));
        vBox2.setOnMouseEntered(e -> MapMouseEntered(vBox2));
        vBox2.setOnMouseClicked(e -> LoadGame(1));

        VBox vBox3 = new VBox();
        mapParserResult = mapParser.getMap(2);
        vBox3.getChildren().add(GetGameGridPane(100.0, 100.0, mapParserResult.rows(), mapParserResult.cols(), mapParserResult.fields()));
        vBox3.setOnMouseEntered(e -> MapMouseEntered(vBox3));
        vBox3.setOnMouseClicked(e -> LoadGame(2));

        VBox vBox4 = new VBox();
        mapParserResult = mapParser.getMap(3);
        vBox4.getChildren().add(GetGameGridPane(100.0, 100.0, mapParserResult.rows(), mapParserResult.cols(), mapParserResult.fields()));
        vBox4.setOnMouseEntered(e -> MapMouseEntered(vBox4));
        vBox4.setOnMouseClicked(e -> LoadGame(3));

        newGridPane.getChildren().add(vBox1);
        newGridPane.setRowIndex(vBox1, 0);
        newGridPane.setColumnIndex(vBox1, 0);

        newGridPane.getChildren().add(vBox2);
        newGridPane.setRowIndex(vBox2, 0);
        newGridPane.setColumnIndex(vBox2, 1);

        newGridPane.getChildren().add(vBox3);
        newGridPane.setRowIndex(vBox3, 1);
        newGridPane.setColumnIndex(vBox3, 0);

        newGridPane.getChildren().add(vBox4);
        newGridPane.setRowIndex(vBox4, 1);
        newGridPane.setColumnIndex(vBox4, 1);

        var menuGridPane = CreateGridPane(100.0, 100.0, 1, 2);

        var LabelMenuLeft = new Label("SELECT MAP");
        LabelMenuLeft.setStyle("-fx-background-color: #000000;");
        LabelMenuLeft.setFont(new Font(30));
        menuGridPane.getChildren().add(LabelMenuLeft);
        menuGridPane.setRowIndex(LabelMenuLeft, 0);
        menuGridPane.setColumnIndex(LabelMenuLeft, 0);
        menuGridPane.setHalignment(LabelMenuLeft, HPos.LEFT);

        var LabelMenuRight = new Label("REPLAY LAST GAME");
        LabelMenuRight.setStyle("-fx-background-color: #000000;");
        LabelMenuRight.setFont(new Font(20));
        LabelMenuRight.setOnMouseEntered(e -> ReplayEntered(LabelMenuRight));
        LabelMenuRight.setOnMouseClicked(e -> LoadReplay());
        menuGridPane.getChildren().add(LabelMenuRight);
        menuGridPane.setRowIndex(LabelMenuRight, 0);
        menuGridPane.setColumnIndex(LabelMenuRight, 1);
        menuGridPane.setHalignment(LabelMenuRight, HPos.RIGHT);

        var menuLayoutGridPane = CreteMenuGridPane();
        menuLayoutGridPane.getChildren().add(menuGridPane);
        menuLayoutGridPane.setRowIndex(menuGridPane, 0);
        menuLayoutGridPane.setColumnIndex(menuGridPane, 0);
        menuLayoutGridPane.getChildren().add(newGridPane);
        menuLayoutGridPane.setRowIndex(newGridPane, 1);
        menuLayoutGridPane.setColumnIndex(newGridPane, 0);

        vBox.getChildren().add(menuLayoutGridPane);
        root.getChildren().add(vBox);
    }

    private void OpenGame(int mapNum) throws IOException {

        root.getChildren().removeAll();

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #FFFFFF;");

        var mapParserResult = mapParser.getMap(mapNum);
        rows = mapParserResult.rows();
        cols = mapParserResult.cols();
        keys = mapParserResult.keys();
        maze = new ICommonField[mapParserResult.rows()][mapParserResult.cols()];
        Game.gameGridPane = GetGameGridPane(100.0, 100.0, mapParserResult.rows(), mapParserResult.cols(), mapParserResult.fields());
        Game.CommonObjectSize = min(Constants.WindowWidth / mapParserResult.cols(), Constants.WindowHeight / mapParserResult.rows());
        CreateObjectMap(mapParserResult.fields());

        vBox.getChildren().add(gameGridPane);
        Game.root.getChildren().add(vBox);
    }

    public void CreateObjectMap(String[][] fields) throws FileNotFoundException {
        for(int r = 0; r < fields.length; r++)
        {
            for(int c = 0; c < fields[0].length; c++)
            {
                switch (fields[r][c])
                {
                    case "T" -> {
                        var object = new TargetObject(gameGridPane, r, c, Constants.WindowHeight / rows, Constants.WindowWidth / cols);
                        maze[r][c] = new PathField(r,c, maze);
                        maze[r][c].Put(object);
                    }
                    case "G" -> {
                        maze[r][c] = new PathField(r,c, maze);
                        var object = new GhostObject(gameGridPane, r, c, Constants.WindowHeight / rows, Constants.WindowWidth / cols, maze[r][c]);
                        object.start();
                        maze[r][c].Put(object);
                    }
                    case "K" -> {
                        var object = new KeyObject(gameGridPane, r, c, Constants.WindowHeight / rows, Constants.WindowWidth / cols);
                        maze[r][c] = new PathField(r,c, maze);
                        maze[r][c].Put(object);
                    }
                    case "S" -> {
                        maze[r][c] = new PathField(r,c, maze);
                        var object = new PacmanObject(gameGridPane, r, c, keys, Constants.WindowHeight / rows, Constants.WindowWidth / cols, maze[r][c]);
                        //object.start();
                        maze[r][c].Put(object);
                    }
                    case "." -> {
                        maze[r][c] = new PathField(r,c, maze);
                    }
                    case "X" -> {
                        maze[r][c] = new WallField(r,c, maze);
                    }
                }
            }
        }
    }
}