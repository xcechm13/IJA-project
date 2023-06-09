package Game;

import Constants.Constants;
import Enums.FieldNeighbour;
import Constants.FieldPixels;
import Game.Fields.PathField;
import Game.Fields.WallField;
import Game.Objects.*;
import Game.Records.LoggerResult;
import Interfaces.ICommonField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Main class representing the game
 */
public class Game extends Application {

    /**
     * Holds a reference to the parser of the map text file
     */
    private static MapParser mapParser;
    /**
     * Holds a reference to the logger
     */
    private static Logger logger;
    /**
     * Holds a reference to the logo player
     */
    private static LoggerPlayer loggerPlayer;
    /**
     * The main visual element of the UI
     */
    private StackPane root;
    /**
     * A grid representing the game's maze
     */
    private GridPane gameGridPane;
    /**
     * Visual element of the top menu in game
     */
    private StackPane topGameMenu;
    /**
     * Visual element of the bottom menu in game
     */
    private HBox bottomGameMenu;
    /**
     * Visual element of the game status
     */
    private Label gameStatusLabel;
    /**
     * Visual element displaying information about whether the game is paused or running
     */
    private ImageView playPause;
    /**
     * Holds information about the number of rows in game
     */
    private int rows;
    /**
     * Holds information about the number of columns in game
     */
    private int cols;
    /**
     * Holds information about the number of pacman starting row
     */
    private int pacmanStartingRow;
    /**
     * Holds information about the number of pacman starting column
     */
    private int pacmanStartingCol;
    /**
     * Holds information about width of one field
     */
    private double fieldWidth;
    /**
     * Holds information about height of one field
     */
    private double fieldHeight;
    /**
     * Holds information about number of keys in maze
     */
    private int keys;
    /**
     * Holds information about number of pacman step
     */
    private int pacmanSteps = 0;
    /**
     * Visual element displaying information about number of pacman step
     */
    private Label nSteps = new Label();
    /**
     * Holds information about actual window width
     */
    private static double windowWidth;
    /**
     * Holds information about actual window height
     */
    private static double windowHeight;
    /**
     * Holds a 2D array of fields representing maze
     */
    private ICommonField[][] maze;
    /**
     * Holds a reference to the process runner
     */
    private static ProcessRunner processRunner;
    /**
     * Holds a reference to UI scene
     */
    private Scene scene;
    /**
     * Holds information about whether the game is open
     */
    private boolean gameOpen = false;
    /**
     * Holds information about whether the game is stopped
     */
    private boolean gameStopped = false;
    /**
     * Holds information about whether the game is finished
     */
    private boolean gameFinished = false;
    /**
     * Holds information about whether the logger player is opened
     */
    private boolean loggerPlayerOpened = false;
    /**
     * Visual element displaying menu
     */
    private VBox menuVbox;
    /**
     * Holds information about selected log to play
     */
    private String selectedLog;
    /**
     * Holds information about selected map
     */
    private String selectedMap;


    /**
     * Creates event handlers responsive to window resizing and opens the game's main menu
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage)
    {
        stage.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            if(loggerPlayerOpened)
            {
                loggerPlayer.UpdateSize(windowHeight*0.8 / rows, (double)newWidth / cols);
            }
            if(!Double.isNaN((double)oldWidth))
            {
                windowWidth = (double)newWidth;
                if(gameOpen)
                {
                    UpdateFieldSize(windowHeight*0.8 / rows, windowWidth / cols);
                }
            }
        });
        stage.heightProperty().addListener((observableValue, oldHeight, newHeight) -> {
            if(loggerPlayerOpened)
            {
                loggerPlayer.UpdateSize((double)newHeight*0.8 / rows, windowWidth / cols);
            }
            if(!Double.isNaN((double)oldHeight))
            {
                windowHeight = (double)newHeight;
                if(gameOpen)
                {
                    UpdateFieldSize(windowHeight*0.8 / rows, windowWidth / cols);
                    topGameMenu.setMaxHeight(windowHeight*0.075);
                    bottomGameMenu.setMaxHeight(windowHeight*0.075);
                }
            }
        });

        StackPane root = new StackPane();
        this.root = root;
        root.setStyle("-fx-background-color: #000000;");

        scene = new Scene(root, Constants.WindowWidth, Constants.WindowHeight);
        scene.setUserAgentStylesheet(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PACMAN");
        stage.show();
        OpenMainMenu();
    }

    /**
     * Creates the necessary class instances
     * @param args program arguments (not used)
     */
    public static void main(String[] args) {
        windowWidth = Constants.WindowWidth;
        windowHeight = Constants.WindowHeight*0.8;
        processRunner = new ProcessRunner();
        mapParser = new MapParser();
        launch();
    }

    /**
     * Create one field (visual element) in the game grid
     * @param fieldNeighbour what neighbors does the field have
     * @return Visual element representing one field in the game grid
     */
    private VBox GetField(FieldNeighbour fieldNeighbour)
    {
        var booleanField = FieldPixels.CreateField(fieldNeighbour);

        // One field in the game grid
        VBox vBox = new VBox();

        GridPane vBoxGridPane = new GridPane();
        vBoxGridPane.setPrefWidth(10000);
        vBoxGridPane.setPrefHeight(10000);
        // adding a column definition
        ColumnConstraints vBoxGridPaneColumnConstraints = new ColumnConstraints();
        vBoxGridPaneColumnConstraints.setPercentWidth(100.0 / Constants.FieldPixelsNum);
        for(int i=0; i<Constants.FieldPixelsNum; i++)
        {
            vBoxGridPane.getColumnConstraints().add(vBoxGridPaneColumnConstraints);
        }
        // adding a rows definition
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

    /**
     * Determines whether the given field is Wall
     * @param maze maze in 2D String array format
     * @param row number of rows
     * @param col number of columns
     * @return true if field on position [row,col] is wall ('X'), otherwise false
     */
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

    /**
     * Creates a visual maze
     * @param percentageHeight how much of the window should be occupied in the vertical position in percentage
     * @param percentageWidth how much of the window should be occupied in the horizontal position in percentage
     * @param rows number of maze rows
     * @param cols number of maze cols
     * @param fields 2D String array representing the maze
     * @return A GridPane visual element with a created background
     */
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

    /**
     * Creates an empty GridPane visual element with defined rows and columns
     * @param percentageHeight how much of the window should be occupied in the vertical position in percentage
     * @param percentageWidth how much of the window should be occupied in the horizontal position in percentage
     * @param rows number of maze rows
     * @param cols number of maze cols
     * @return Empty GridPane visual element with defined rows and columns
     */
    private GridPane CreateGridPane(double percentageHeight, double percentageWidth, int rows, int cols)
    {
        GridPane GridPane = new GridPane();
        GridPane.getStyleClass().add("gridPane");
        GridPane.setPrefHeight(10000);
        GridPane.setPrefWidth(10000);

        // adding a column definition
        ColumnConstraints ColumnConstraints = new ColumnConstraints();
        ColumnConstraints.setPercentWidth(percentageWidth / cols);
        for(int i=0; i<cols; i++)
        {
            GridPane.getColumnConstraints().add(ColumnConstraints);
        }

        // adding a row definition
        RowConstraints RowConstraints = new RowConstraints();
        RowConstraints.setPercentHeight(percentageHeight / rows);
        for(int i=0; i<rows; i++)
        {
            GridPane.getRowConstraints().add(RowConstraints);
        }

        return GridPane;
    }

    /**
     * Opens the game's main menu
     */
    private void OpenMainMenu()
    {
        root.getChildren().clear();
        loggerPlayerOpened = false;

        VBox commonMenuVbox = new VBox();
        commonMenuVbox.setPadding(new Insets(10, 0, 0, 0));
        commonMenuVbox.setAlignment(Pos.TOP_CENTER);

        ImageView Logo = new ImageView(new Image(Constants.PacmanLogoSource));
        Logo.setFitWidth(scene.getWidth() * 0.5);
        Logo.setFitHeight(scene.getWidth() * 0.5 * (1.0 / 3.0));

        menuVbox = new VBox();
        menuVbox.setAlignment(Pos.TOP_CENTER);

        /****** PLAY ******/
        Label menuLabel1 = new Label("PLAY");
        menuLabel1.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        menuLabel1.setCursor(Cursor.HAND);
        menuLabel1.setOnMouseEntered(e -> {
            menuLabel1.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel1.setOnMouseExited(e -> {
            menuLabel1.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel1.setOnMouseClicked(e -> {
            OpenSubmenuPlay();
        });

        /****** REPLAY LAST GAME ******/
        Label menuLabel2 = new Label("REPLAY GAME");
        menuLabel2.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        menuLabel2.setCursor(Cursor.HAND);
        menuLabel2.setOnMouseEntered(e -> {
            menuLabel2.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel2.setOnMouseExited(e -> {
            menuLabel2.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel2.setOnMouseClicked(e -> {
            OpenSubmenuReplay();
        });


        /****** EXIT GAME ******/
        Label menuLabel3 = new Label("EXIT GAME");
        menuLabel3.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        menuLabel3.setCursor(Cursor.HAND);
        menuLabel3.setOnMouseEntered(e -> {
            menuLabel3.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel3.setOnMouseExited(e -> {
            menuLabel3.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel3.setOnMouseClicked(e -> {
            Platform.exit();
        });



        menuVbox.getChildren().addAll(menuLabel1, menuLabel2, menuLabel3);
        menuVbox.setAlignment(Pos.CENTER);
        menuVbox.setPadding(new Insets(0, 0, 0, 0));

        HBox menuGhostsImages = new HBox();
        menuGhostsImages.setMaxHeight(50);
        menuGhostsImages.setAlignment(Pos.CENTER);
        menuGhostsImages.setSpacing(15);
        ImageView pacman = new ImageView(new Image(Constants.PacmanSource[7]));
        ImageView redGhost = new ImageView(new Image(Constants.GhostSource[0]));
        ImageView blueGhost = new ImageView(new Image(Constants.GhostSource[3]));
        ImageView yellowGhost = new ImageView(new Image(Constants.GhostSource[1]));
        ImageView pinkGhost = new ImageView(new Image(Constants.GhostSource[2]));
        pacman.setFitHeight(30);
        pacman.setFitWidth(30);
        redGhost.setFitHeight(30);
        redGhost.setFitWidth(30);
        blueGhost.setFitHeight(30);
        blueGhost.setFitWidth(30);
        yellowGhost.setFitHeight(30);
        yellowGhost.setFitWidth(30);
        pinkGhost.setFitHeight(30);
        pinkGhost.setFitWidth(30);
        menuGhostsImages.getChildren().addAll(redGhost, blueGhost, yellowGhost, pinkGhost, pacman);


        ScrollPane scrollpane = new ScrollPane(menuVbox);
        scrollpane.setPadding(new Insets(180, 0, 60, 0));
        scrollpane.setFitToWidth(true);


        commonMenuVbox.getChildren().add(Logo);
        root.getChildren().add(commonMenuVbox);
        root.getChildren().add(scrollpane);
        root.setAlignment(scrollpane, Pos.CENTER);
        root.getChildren().add(menuGhostsImages);
        root.setAlignment(menuGhostsImages, Pos.BOTTOM_CENTER);

    }

    /**
     * Opens a sub-menu with a selection of games to replay by time and date
     */
    private void OpenSubmenuReplay()
    {
        menuVbox.getChildren().clear();

        logger = new Logger();
        var logNames = logger.GetLogs();

        for(int i=0; i<logNames.size(); i++)
        {
            var index = i;
            Label mapLabel = new Label(logNames.get(i).logDateTime());
            mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
            mapLabel.setCursor(Cursor.HAND);
            mapLabel.setOnMouseEntered(e -> {
                mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 25; -fx-font-weight: 700;");
            });
            mapLabel.setOnMouseExited(e -> {
                mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
            });
            mapLabel.setOnMouseClicked(e -> {
                selectedLog = logNames.get(index).logFileName();
                if(e.getButton() == MouseButton.PRIMARY)
                {
                    OpenSubmenuReplayMode();
                }
                else if(e.getButton() == MouseButton.SECONDARY)
                {
                    try {
                        logger.DeleteLog(selectedLog);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    menuVbox.getChildren().remove(mapLabel);
                }
            });
            menuVbox.getChildren().add(mapLabel);
        }


        /****** BACK ******/
        Label menuLabelBack = new Label("BACK");
        menuLabelBack.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        menuLabelBack.setCursor(Cursor.HAND);
        menuLabelBack.setOnMouseEntered(e -> {
            menuLabelBack.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabelBack.setOnMouseExited(e -> {
            menuLabelBack.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabelBack.setOnMouseClicked(e -> {
            OpenMainMenu();
        });

        menuVbox.getChildren().addAll(menuLabelBack);
    }

    /**
     * Opens a submenu with replay mode selection
     */
    private void OpenSubmenuReplayMode()
    {
        menuVbox.getChildren().clear();

        /****** REPLAY FROM START ******/
        Label menuLabel1 = new Label("Replay from start");
        menuLabel1.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
        menuLabel1.setCursor(Cursor.HAND);
        menuLabel1.setOnMouseEntered(e -> {
            menuLabel1.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 25; -fx-font-weight: 700;");
        });
        menuLabel1.setOnMouseExited(e -> {
            menuLabel1.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
        });
        menuLabel1.setOnMouseClicked(e -> {
            try {
                OpenLogger(logger.LoadLog(selectedLog, true));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        /****** REPLAY FROM END ******/
        Label menuLabel2 = new Label("Replay from end");
        menuLabel2.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
        menuLabel2.setCursor(Cursor.HAND);
        menuLabel2.setOnMouseEntered(e -> {
            menuLabel2.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 25; -fx-font-weight: 700;");
        });
        menuLabel2.setOnMouseExited(e -> {
            menuLabel2.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
        });
        menuLabel2.setOnMouseClicked(e -> {
            try {
                OpenLogger(logger.LoadLog(selectedLog, false));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        /****** BACK ******/
        Label menuLabel3 = new Label("BACK");
        menuLabel3.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        menuLabel3.setCursor(Cursor.HAND);
        menuLabel3.setOnMouseEntered(e -> {
            menuLabel3.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel3.setOnMouseExited(e -> {
            menuLabel3.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabel3.setOnMouseClicked(e -> {
            OpenSubmenuReplay();
        });

        menuVbox.getChildren().addAll(menuLabel1, menuLabel2, menuLabel3);
    }

    /**
     * Opens a sub-menu with a selection of maps
     */
    private void OpenSubmenuPlay()
    {
        List<String> mapNames = mapParser.GetListMaps();
        menuVbox.getChildren().clear();

        for(int i=0; i<mapNames.size(); i++)
        {
            var mapID = i;
            Label mapLabel = new Label(mapNames.get(i));
            mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
            mapLabel.setCursor(Cursor.HAND);
            mapLabel.setOnMouseEntered(e -> {
                mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 25; -fx-font-weight: 700;");
            });
            mapLabel.setOnMouseExited(e -> {
                mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
            });
            mapLabel.setOnMouseClicked(e -> {
                selectedMap = mapNames.get(mapID);
                try {
                    OpenGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            menuVbox.getChildren().add(mapLabel);
        }


        /****** BACK ******/
        Label menuLabelBack = new Label("BACK");
        menuLabelBack.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        menuLabelBack.setCursor(Cursor.HAND);
        menuLabelBack.setOnMouseEntered(e -> {
            menuLabelBack.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabelBack.setOnMouseExited(e -> {
            menuLabelBack.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 30; -fx-font-weight: 700;");
        });
        menuLabelBack.setOnMouseClicked(e -> {
            OpenMainMenu();
        });

        menuVbox.getChildren().add(menuLabelBack);
    }

    /**
     * Starts the game
     * @throws IOException
     */
    private void OpenGame() throws IOException
    {
        this.root.getChildren().clear();

        gameStatusLabel = new Label();
        gameStatusLabel.setLayoutX(0.5);
        gameStatusLabel.setLayoutY(0.5);
        gameStatusLabel.setPadding(new Insets(10));
        gameStatusLabel.setVisible(false);

        var mapParserResult = mapParser.getMap(selectedMap);
        rows = mapParserResult.rows();
        cols = mapParserResult.cols();
        keys = mapParserResult.keys();
        maze = new ICommonField[mapParserResult.rows()][mapParserResult.cols()];
        gameGridPane = GetGameGridPane(80.0, 100.0, mapParserResult.rows(), mapParserResult.cols(), mapParserResult.fields());
        CreateObjectMap(mapParserResult.fields());

        topGameMenu = new StackPane();
        topGameMenu.setMaxHeight(0.075 * scene.getHeight());

        pacmanSteps = 0;
        nSteps = new Label((pacmanSteps) + " steps");
        nSteps.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 30; -fx-font-weight: bold;");
        topGameMenu.getChildren().add(nSteps);
        topGameMenu.setAlignment(nSteps, Pos.CENTER_LEFT);

        Label goToMenu = new Label("MENU");
        goToMenu.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 30; -fx-font-weight: bold;");
        goToMenu.setCursor(Cursor.HAND);
        goToMenu.setOnMouseClicked(e -> {
            processRunner.stopRecorder();
            gameOpen = false;
            gameFinished = true;
            processRunner.stopAllObjects();
            processRunner.Clear();
            OpenMainMenu();
        });
        topGameMenu.getChildren().add(goToMenu);
        topGameMenu.setAlignment(goToMenu, Pos.CENTER_RIGHT);

        playPause = new ImageView(new Image(Constants.PauseSource));
        playPause.setFitHeight(25);
        playPause.setFitWidth(25);
        playPause.setCursor(Cursor.HAND);
        playPause.setOnMouseClicked(e -> {
            try {
                ESCPressed();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        topGameMenu.getChildren().add(playPause);
        topGameMenu.setAlignment(playPause, Pos.CENTER);


        bottomGameMenu = new HBox();
        bottomGameMenu.setMaxHeight(0.075 * scene.getHeight());
        ImageView lives1 = new ImageView(new Image(Constants.PacmanSource[7]));
        ImageView lives2 = new ImageView(new Image(Constants.PacmanSource[7]));
        ImageView lives3 = new ImageView(new Image(Constants.PacmanSource[7]));
        lives1.setFitHeight(25);
        lives1.setFitWidth(25);
        lives2.setFitHeight(25);
        lives2.setFitWidth(25);
        lives3.setFitHeight(25);
        lives3.setFitWidth(25);
        bottomGameMenu.getChildren().add(lives1);
        bottomGameMenu.getChildren().add(lives2);
        bottomGameMenu.getChildren().add(lives3);
        bottomGameMenu.setAlignment(Pos.CENTER_LEFT);
        bottomGameMenu.setSpacing(10);

        bottomGameMenu.setPadding(new Insets(0, 0, 0, 10));
        topGameMenu.setPadding(new Insets(0, 10, 0, 10));


        gameGridPane.setAlignment(Pos.CENTER);
        root.getChildren().add(gameGridPane);
        root.getChildren().add(topGameMenu);
        root.setAlignment(topGameMenu, Pos.TOP_CENTER);
        root.getChildren().add(bottomGameMenu);
        root.setAlignment(bottomGameMenu, Pos.BOTTOM_CENTER);
        root.getChildren().add(gameStatusLabel);
        gameOpen = true;
        gameFinished = false;
    }

    /**
     * Places objects on the map base
     * @param fields 2D String array representing the maze
     */
    public void CreateObjectMap(String[][] fields) {
        for(int r = 0; r < fields.length; r++)
        {
            for(int c = 0; c < fields[0].length; c++)
            {
                switch (fields[r][c])
                {
                    case "T":
                        var tObject = new TargetObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols);
                        maze[r][c] = new PathField(r,c, maze);
                        maze[r][c].Put(tObject);
                        break;
                    case "G":
                        maze[r][c] = new PathField(r,c, maze);
                        var gObject = new GhostObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols, maze[r][c]);
                        var gThread = new Thread(gObject);
                        processRunner.addGhost(gThread, gObject);
                        maze[r][c].Put(gObject);
                        break;
                    case "K":
                        var kObject = new KeyObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols);
                        maze[r][c] = new PathField(r,c, maze);
                        maze[r][c].Put(kObject);
                        break;
                    case "S":
                        maze[r][c] = new PathField(r,c, maze);
                        var pObject = new PacmanObject(gameGridPane, r, c, 3, keys, 0, 0, windowHeight / rows, windowWidth / cols, maze[r][c], scene, this);
                        var pThread = new Thread(pObject);
                        processRunner.setPacman(pThread, pObject);
                        maze[r][c].Put(pObject);
                        maze[r][c].Put(new HomeObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols));
                        pacmanStartingRow = r;
                        pacmanStartingCol = c;
                        fieldHeight = windowHeight / rows;
                        fieldWidth = windowWidth / cols;
                        break;
                    case ".":
                        maze[r][c] = new PathField(r,c, maze);
                        break;
                    case "X":
                        maze[r][c] = new WallField(r,c, maze);
                        break;
                }
            }
        }
        var loggerRecorder = new LoggerRecorder(maze);
        Thread loggerRecorderThread = new Thread(loggerRecorder);
        processRunner.addRecorder(loggerRecorderThread, loggerRecorder);

        processRunner.runRecorder();
        processRunner.runPacman();
        processRunner.runGhosts();
    }

    /**
     * Responds to pressing the ESC key in different states of the application
     * @throws IOException
     */
    public void ESCPressed() throws IOException
    {
        if(gameFinished)
        {
            OpenMainMenu();
            processRunner.Clear();
            gameStopped = false;
            gameFinished = false;
            return;
        }

        if(gameStopped)
        {
            gameStopped = false;
            topGameMenu.getChildren().remove(playPause);
            playPause = new ImageView(new Image(Constants.PauseSource));
            playPause.setFitHeight(25);
            playPause.setFitWidth(25);
            playPause.setCursor(Cursor.HAND);
            playPause.setOnMouseClicked(e -> {
                try {
                    ESCPressed();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            gameStatusLabel.setVisible(false);
            topGameMenu.getChildren().add(playPause);
            topGameMenu.setAlignment(playPause, Pos.CENTER);
            processRunner.runAllObjects();
        }
        else
        {
            gameStopped = true;
            topGameMenu.getChildren().remove(playPause);
            playPause = new ImageView(new Image(Constants.PlaySource));
            playPause.setFitHeight(25);
            playPause.setFitWidth(25);
            playPause.setCursor(Cursor.HAND);
            playPause.setOnMouseClicked(e -> {
                try {
                    ESCPressed();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            topGameMenu.getChildren().add(playPause);
            topGameMenu.setAlignment(playPause, Pos.CENTER);
            processRunner.stopAllObjects();
            gameStatusLabel.setText("PAUSE");
            gameStatusLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #FEF900; -fx-font-size: 70; -fx-font-weight: bold;");
            gameStatusLabel.setVisible(true);
        }
    }

    /**
     * Updates the size of all objects
     * @param height new field height
     * @param width new field widhth
     */
    private void UpdateFieldSize(double height, double width)
    {
        fieldWidth = width;
        fieldHeight = height;
        for(int r = 0; r < maze.length; r++)
        {
            for(int c = 0; c < maze[0].length; c++)
            {
                if(maze[r][c].IsPathField())
                {
                    var objects = maze[r][c].GetMazeObjects();
                    for(int i = 0; i < objects.size(); i++)
                    {
                        objects.get(i).SetFieldSize(height, width);
                    }
                }
            }
        }
    }

    /**
     * Responds to the death of PACAMAN
     * @param lives new number of lives after death
     * @param totalKeys information about total keys in maze
     * @param foundKeys information about how many keys have already been collected
     * @param steps information about how many steps pacman has taken
     * @throws IOException
     */
    public void PacmanDead(int lives, int totalKeys, int foundKeys, int steps) throws IOException {
        bottomGameMenu.getChildren().remove(0);
        if(lives == 0)
        {
            processRunner.stopRecorder();
            gameOpen = false;
            gameFinished = true;
            processRunner.stopAllObjects();
            gameStatusLabel.setText("GAME OVER");
            gameStatusLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF0000; -fx-font-size: 70; -fx-font-weight: bold;");
            gameStatusLabel.setVisible(true);
            return;
        }

        PacmanObject newPacmanObject = new PacmanObject(gameGridPane, pacmanStartingRow, pacmanStartingCol, lives, totalKeys, foundKeys, steps, fieldHeight, fieldWidth, maze[pacmanStartingRow][pacmanStartingCol], scene, this);
        var newPacmanThread = new Thread(newPacmanObject);
        processRunner.setPacman(newPacmanThread, newPacmanObject);
        maze[pacmanStartingRow][pacmanStartingCol].Put(newPacmanObject);
        processRunner.runPacman();
    }

    /**
     * Pauses the game logger and displays information about the user's winnings
     */
    public void PacmanWin()
    {
        processRunner.stopRecorder();
        gameOpen = false;
        gameFinished = true;
        processRunner.stopAllObjects();
        gameStatusLabel.setText("YOU WIN !");
        gameStatusLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #FEF900; -fx-font-size: 70; -fx-font-weight: bold;");
        gameStatusLabel.setVisible(true);
    }

    /**
     * Changes the information about the number of steps in the UI
     */
    public void PacmanStep()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                nSteps.setText((++pacmanSteps) + " steps");
            }
        });
    }

    /**
     * Open game replayer
     * @param result Log parsing result
     * @throws IOException
     */
    public void OpenLogger(LoggerResult result) throws IOException {
        this.root.getChildren().clear();

        gameStatusLabel = new Label();
        gameStatusLabel.setLayoutX(0.5);
        gameStatusLabel.setLayoutY(0.5);
        gameStatusLabel.setPadding(new Insets(10));
        gameStatusLabel.setVisible(false);

        var mapLoggerResult = logger.LogMapParse(selectedLog);
        rows = mapLoggerResult.rows();
        cols = mapLoggerResult.cols();
        maze = new ICommonField[mapLoggerResult.rows()][mapLoggerResult.cols()];
        gameGridPane = GetGameGridPane(80.0, 100.0, rows, cols, mapLoggerResult.fields());

        topGameMenu = new StackPane();
        topGameMenu.setMaxHeight(0.075 * scene.getHeight());

        pacmanSteps = 0;
        nSteps = new Label((pacmanSteps) + " steps");
        nSteps.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 30; -fx-font-weight: bold;");
        topGameMenu.getChildren().add(nSteps);
        topGameMenu.setAlignment(nSteps, Pos.CENTER_LEFT);

        Label goToMenu = new Label("MENU");
        goToMenu.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 30; -fx-font-weight: bold;");
        goToMenu.setCursor(Cursor.HAND);
        goToMenu.setOnMouseClicked(e -> {
            System.out.println("here");
            OpenMainMenu();
        });
        topGameMenu.getChildren().add(goToMenu);
        topGameMenu.setAlignment(goToMenu, Pos.CENTER_RIGHT);




        ImageView playBack = new ImageView(new Image(Constants.PlayBackSource));
        playBack.setFitHeight(25);
        playBack.setFitWidth(50);
        playBack.setCursor(Cursor.HAND);
        playBack.setOnMouseClicked(e -> {
            try {
                loggerPlayer.PlayBack();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        ImageView stepBack = new ImageView(new Image(Constants.StepBackSource));
        stepBack.setFitHeight(25);
        stepBack.setFitWidth(38);
        stepBack.setCursor(Cursor.HAND);
        stepBack.setOnMouseClicked(e -> {
            try {
                loggerPlayer.StepBack();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        playPause = new ImageView(new Image(Constants.PlaySource));
        playPause.setFitHeight(25);
        playPause.setFitWidth(25);
        playPause.setCursor(Cursor.HAND);
        playPause.setOnMouseClicked(e -> {
            try {
                loggerPlayer.PlayPause();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        ImageView stepForward = new ImageView(new Image(Constants.StepForwardSource));
        stepForward.setFitHeight(25);
        stepForward.setFitWidth(38);
        stepForward.setCursor(Cursor.HAND);
        stepForward.setOnMouseClicked(e -> {
            try {
                loggerPlayer.StepForward();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        ImageView playForward = new ImageView(new Image(Constants.PlayForwardSource));
        playForward.setFitHeight(25);
        playForward.setFitWidth(50);
        playForward.setCursor(Cursor.HAND);
        playForward.setOnMouseClicked(e -> {
            try {
                loggerPlayer.PlayForward();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox controls = new HBox();
        controls.getChildren().addAll(playBack, stepBack, playPause, stepForward, playForward);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(5,0,0,0));
        controls.setMaxWidth(280);
        controls.setSpacing(20);


        topGameMenu.getChildren().add(controls);
        topGameMenu.setAlignment(controls, Pos.CENTER);


        bottomGameMenu = new HBox();
        bottomGameMenu.setMaxHeight(0.075 * scene.getHeight());
        bottomGameMenu.setAlignment(Pos.CENTER_LEFT);
        bottomGameMenu.setSpacing(10);

        bottomGameMenu.setPadding(new Insets(0, 0, 0, 10));
        topGameMenu.setPadding(new Insets(0, 10, 0, 10));


        gameGridPane.setAlignment(Pos.CENTER);
        root.getChildren().add(gameGridPane);
        root.getChildren().add(topGameMenu);
        root.setAlignment(topGameMenu, Pos.TOP_CENTER);
        root.getChildren().add(bottomGameMenu);
        root.setAlignment(bottomGameMenu, Pos.BOTTOM_CENTER);
        root.getChildren().add(gameStatusLabel);

        loggerPlayer = new LoggerPlayer(gameGridPane, logger, result, bottomGameMenu, nSteps, windowHeight / rows, windowWidth / cols, rows, cols);
        Thread loggerPlayerThread = new Thread(loggerPlayer);
        loggerPlayerThread.start();
        loggerPlayerOpened = true;
    }
}