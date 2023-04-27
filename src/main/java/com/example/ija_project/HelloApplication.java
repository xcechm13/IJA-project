package com.example.ija_project;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.Math.decrementExact;
import static java.lang.Math.min;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        int rows = 15;
        int cols = 30;

        // grid reprezentující hrací pole
        GridPane GridPane = new GridPane();
        GridPane.getStyleClass().add("gridPane");
        GridPane.setPrefHeight(10000);
        GridPane.setPrefWidth(10000);

        // přidání definice sloupců
        ColumnConstraints ColumnConstraints = new ColumnConstraints();
        ColumnConstraints.setPercentWidth(100.0 / cols);
        for(int i=0; i<cols; i++)
        {
            GridPane.getColumnConstraints().add(ColumnConstraints);
        }

        // přidání definice řádků
        RowConstraints RowConstraints = new RowConstraints();
        RowConstraints.setPercentHeight(100.0 / rows);
        for(int i=0; i<rows; i++)
        {
            GridPane.getRowConstraints().add(RowConstraints);
        }



        var a = new String[][] {
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

        for(int r=0; r<a.length; r++)
        {
            for(int c=0; c<a[0].length; c++)
            {
                VBox vBox = new VBox();

                if(!isWall(a, r, c))
                {
                    GridPane.getChildren().add(vBox);
                    GridPane.setRowIndex(vBox, r);
                    GridPane.setColumnIndex(vBox, c);
                    continue;
                }

                if(!isWall(a, r-1, c) && !isWall(a, r+1, c) && !isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.NoNeighbour);
                }
                else if(!isWall(a, r-1, c) && !isWall(a, r+1, c) && !isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.RightNeighbour);
                }
                else if(!isWall(a, r-1, c) && !isWall(a, r+1, c) && isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.LeftNeighbour);
                }
                else if(!isWall(a, r-1, c) && !isWall(a, r+1, c) && isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.LeftRightNeighbour);
                }
                else if(!isWall(a, r-1, c) && isWall(a, r+1, c) && !isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.DownNeighbour);
                }
                else if(!isWall(a, r-1, c) && isWall(a, r+1, c) && !isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.DownRightNeighbour);
                }
                else if(!isWall(a, r-1, c) && isWall(a, r+1, c) && isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.DownLeftNeighbour);
                }
                else if(!isWall(a, r-1, c) && isWall(a, r+1, c) && isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.DownLeftRightNeighbour);
                }
                else if(isWall(a, r-1, c) && !isWall(a, r+1, c) && !isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpNeighbour);
                }
                else if(isWall(a, r-1, c) && !isWall(a, r+1, c) && !isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpRightNeighbour);
                }
                else if(isWall(a, r-1, c) && !isWall(a, r+1, c) && isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpLeftNeighbour);
                }
                else if(isWall(a, r-1, c) && !isWall(a, r+1, c) && isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpLeftRightNeighbour);
                }
                else if(isWall(a, r-1, c) && isWall(a, r+1, c) && !isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpDownNeighbour);
                }
                else if(isWall(a, r-1, c) && isWall(a, r+1, c) && !isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpDownRightNeighbour);
                }
                else if(isWall(a, r-1, c) && isWall(a, r+1, c) && isWall(a, r, c-1) && !isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpDownLeftNeighbour);
                }
                else if(isWall(a, r-1, c) && isWall(a, r+1, c) && isWall(a, r, c-1) && isWall(a, r, c+1))
                {
                    vBox = getField(FieldNeighbour.UpDownLeftRightNeighbour);
                }

                GridPane.getChildren().add(vBox);
                GridPane.setRowIndex(vBox, r);
                GridPane.setColumnIndex(vBox, c);
            }
        }


        var fieldWidth = 1000.0 / cols;
        var fieldHeight = 600.0 / rows;

        var smaller = min(fieldWidth, fieldHeight);


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










        StackPane root = new StackPane();
        root.getChildren().add(GridPane);

        Scene scene = new Scene(root, 1000, 600);
        scene.setUserAgentStylesheet(getClass().getResource("style.css").toExternalForm());
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.RIGHT)
            {
                transition.play();
            }
        });
        stage.setScene(scene);
        stage.setTitle("PACMAN");
        stage.show();
        transition.setToX(stage.getWidth() / cols);
    }

    public static void main(String[] args) {
        launch();
    }

    private VBox getField(FieldNeighbour fieldNeighbour)
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

    private boolean isWall(String[][] maze, int row, int col)
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
}