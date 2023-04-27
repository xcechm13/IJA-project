package Game;

import ConstantsEnums.Constants;
import ConstantsEnums.FieldNeighbour;
import ConstantsEnums.FieldPixels;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.Math.min;

public class Game extends Application {

    @Override
    public void start(Stage stage) throws IOException
    {
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
                // TODO reakce na změnu šířky okna
                System.out.println("Nová šířka okna: " + newWidth);
            }
        });

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                // TODO reakce na změnu výšky okna
                System.out.println("Nová výška okna: " + newHeight);
            }
        });

        int rows = 15;
        int cols = 30;
        var fields = new String[][] {
                {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
                {"X","S",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","X"},
                {"X",".","X","X","X","X","X","X",".","X",".","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X",".","X"},
                {"X",".","X",".",".",".",".","X",".","X",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","X"},
                {"X",".","X",".","X","X",".","X",".","X",".","X","X","X","X",".","X","X","X","X","X","X","X","X","X","X","X",".",".","X"},
                {"X",".","X",".",".","X",".","X",".","X",".",".",".","X",".","X",".",".",".",".",".",".",".",".",".",".",".",".",".","X"},
                {"X",".","X",".",".","X",".","X",".","X","X","X",".","X",".","X",".","X","X","X","X","X","X","X","X","X","X",".",".","X"},
                {"X",".","X",".",".","X",".","X",".",".",".","X",".","X",".","X",".","X",".",".",".",".",".",".",".",".",".",".",".","X"},
                {"X",".","X","X","X","X",".","X","X","X",".","X",".","X",".","X",".","X",".","X","X","X","X","X","X","X","X",".",".","X"},
                {"X",".",".",".",".",".",".",".",".",".",".","X",".","X",".","X",".","X",".",".",".",".",".",".",".",".",".",".",".","X"},
                {"X",".","X","X","X","X","X","X","X","X",".","X",".","X",".","X",".","X",".","X","X","X","X","X","X","X","X",".",".","X"},
                {"X",".",".",".",".",".",".",".",".",".",".","X",".","X",".","X",".","X",".",".",".",".",".",".",".",".",".",".",".","X"},
                {"X",".","X","X","X","X",".","X","X","X",".","X",".","X",".","X",".","X",".","X","X","X","X","X","X","X",".","X",".","X"},
                {"X",".","X",".",".","X",".",".",".",".",".","X",".","X",".","X",".","X",".",".",".",".",".",".",".",".",".",".",".","X"},
                {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"}};

        var GridPane = GetGameGridPane(100.0, 100.0, rows, cols, fields);

        var smaller = min(Constants.WindowWidth / cols, Constants.WindowHeight / rows);

        Image image = new Image(new FileInputStream("C:\\Users\\Roman Čechmánek\\Desktop\\pacman.gif"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(smaller);
        imageView.setFitHeight(smaller);
        GridPane.getChildren().add(imageView);
        GridPane.setRowIndex(imageView, 1);
        GridPane.setColumnIndex(imageView, 2);


        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), imageView);
        imageView.setOnMouseClicked(e -> transition.play());
        transition.setOnFinished(e -> {
            System.out.println(stage.getWidth() / cols);
            GridPane.setRowIndex(imageView, 1);
            GridPane.setColumnIndex(imageView, 3);
            imageView.setTranslateX(0);
        });


        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #FFFFFF;");

        /************************************************************************************************************/


        GridPane newGridPane = CreateGridPane(100.0, 100.0, 2, 2);
        newGridPane.setHgap(10);
        newGridPane.setVgap(10);

        VBox vBox1 = new VBox();
        vBox1.getChildren().add(GetGameGridPane(100.0, 100.0, rows, cols, fields));
        vBox1.setOnMouseEntered(e -> MapMouseEntered(vBox1));
        vBox1.setOnMouseClicked(e -> LoadGame(1));

        VBox vBox2 = new VBox();
        vBox2.getChildren().add(GetGameGridPane(100.0, 100.0, rows, cols, fields));
        vBox2.setOnMouseEntered(e -> MapMouseEntered(vBox2));
        vBox2.setOnMouseClicked(e -> LoadGame(2));

        VBox vBox3 = new VBox();
        vBox3.getChildren().add(GetGameGridPane(100.0, 100.0, rows, cols, fields));
        vBox3.setOnMouseEntered(e -> MapMouseEntered(vBox3));
        vBox3.setOnMouseClicked(e -> LoadGame(3));

        VBox vBox4 = new VBox();
        vBox4.getChildren().add(GetGameGridPane(100.0, 100.0, rows, cols, fields));
        vBox4.setOnMouseEntered(e -> MapMouseEntered(vBox4));
        vBox4.setOnMouseClicked(e -> LoadGame(4));

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


        /************************************************************************************************************/


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



        //vBox.getChildren().add(menuLayoutGridPane);  // odkomentovat pro menu výběr mapy a zakomentovat další příkaz
        vBox.getChildren().add(GridPane); // zakomentovat pro menu výběr mapy a odkomentovat předchozí příkaz



        StackPane root = new StackPane();
        root.getChildren().add(GridPane);
        //root.setStyle("-fx-background-color: #000000;");
        //root.setPadding(new Insets(0,10,10,10));

        Scene scene = new Scene(root, Constants.WindowWidth, Constants.WindowHeight);
        scene.setUserAgentStylesheet(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PACMAN");
        stage.show();
        transition.setToX(stage.getWidth() / cols);
    }

    public static void main(String[] args) {
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
        if(maze[row][col] == "X")
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
}