package Game;

import ConstantsEnums.Constants;
import ConstantsEnums.FieldNeighbour;
import ConstantsEnums.FieldPixels;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game extends Application {

    private static MapParser mapParser;
    private static Logger logger;
    private static LoggerPlayer loggerPlayer;
    private StackPane root;
    private GridPane gameGridPane;
    private StackPane topGameMenu;
    private HBox bottomGameMenu;
    private Label gameStatusLabel;
    private ImageView playPause;
    private int rows;
    private int cols;
    private int pacmanStartingRow;
    private int pacmanStartingCol;
    private double fieldWidth;
    private double fieldHeight;
    private int keys;
    private int pacmanSteps = 0;
    private Label nSteps = new Label();
    private static double windowWidth;
    private static double windowHeight;
    private ICommonField[][] maze;
    private static ProcessRunner processRunner;
    private Scene scene;
    private boolean gameOpen = false;
    private boolean gameStopped = false;
    private boolean gameFinished = false;
    private boolean loggerPlayerOpened = false;
    private VBox menuVbox;
    private String selectedLog;


    @Override
    public void start(Stage stage) throws IOException
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
        //OpenGame(0);
    }

    public static void main(String[] args) {
        windowWidth = Constants.WindowWidth;
        windowHeight = Constants.WindowHeight*0.8;
        processRunner = new ProcessRunner();
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

    private void LoadGame(int mapNumber) throws IOException {
        OpenGame(mapNumber);
        System.out.println("Byla vybrána mapa č. " + mapNumber + ".");
    }

    private void OpenMainMenu()
    {
        root.getChildren().clear();
        loggerPlayerOpened = false;

        VBox commonMenuVbox = new VBox();
        commonMenuVbox.setPadding(new Insets(10, 0, 0, 0));
        commonMenuVbox.setAlignment(Pos.TOP_CENTER);

        ImageView Logo = new ImageView(new Image("pacman_logo.png"));
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
        ImageView pacman = new ImageView(new Image("pacman_right_opened.png"));
        ImageView redGhost = new ImageView(new Image("ghost_red.png"));
        ImageView blueGhost = new ImageView(new Image("ghost_blue.png"));
        ImageView yellowGhost = new ImageView(new Image("ghost_yellow.png"));
        ImageView pinkGhost = new ImageView(new Image("ghost_pink.png"));
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
        //scrollpane.setStyle("-fx-background-color: #123456");
        //menuVbox.setStyle("-fx-background-color: #654321");
        //scrollpane.setMinHeight(210);
        scrollpane.setPadding(new Insets(180, 0, 60, 0));
        scrollpane.setFitToWidth(true);


        commonMenuVbox.getChildren().add(Logo);
        root.getChildren().add(commonMenuVbox);
        root.getChildren().add(scrollpane);
        root.setAlignment(scrollpane, Pos.CENTER);
        root.getChildren().add(menuGhostsImages);
        root.setAlignment(menuGhostsImages, Pos.BOTTOM_CENTER);

    }

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
                OpenSubmenuReplayMode();
                selectedLog = logNames.get(index).logFileName();
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

    private void OpenSubmenuPlay()
    {
        String[] mapNames = {"Map 1", "Map 2", "Map 3", "Map 4"};
        menuVbox.getChildren().clear();

        for(int i=0; i<mapNames.length; i++)
        {
            var mapID = i;
            Label mapLabel = new Label(mapNames[i]);
            mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
            mapLabel.setCursor(Cursor.HAND);
            mapLabel.setOnMouseEntered(e -> {
                mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #FCD56A; -fx-font-size: 25; -fx-font-weight: 700;");
            });
            mapLabel.setOnMouseExited(e -> {
                mapLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #F9C328; -fx-font-size: 25; -fx-font-weight: 700;");
            });
            mapLabel.setOnMouseClicked(e -> {
                try {
                    OpenGame(mapID);
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

    private void OpenGame(int mapNum) throws IOException
    {
        this.root.getChildren().clear();

        gameStatusLabel = new Label();
        gameStatusLabel.setLayoutX(0.5);
        gameStatusLabel.setLayoutY(0.5);
        gameStatusLabel.setPadding(new Insets(10));
        gameStatusLabel.setVisible(false);

        var mapParserResult = mapParser.getMap(mapNum);
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

        playPause = new ImageView(new Image("pause.png"));
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
        ImageView lives1 = new ImageView(new Image("pacman_right_opened.png"));
        ImageView lives2 = new ImageView(new Image("pacman_right_opened.png"));
        ImageView lives3 = new ImageView(new Image("pacman_right_opened.png"));
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

    public void CreateObjectMap(String[][] fields) throws FileNotFoundException {
        for(int r = 0; r < fields.length; r++)
        {
            for(int c = 0; c < fields[0].length; c++)
            {
                switch (fields[r][c])
                {
                    case "T" -> {
                        var object = new TargetObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols);
                        maze[r][c] = new PathField(r,c, maze);
                        maze[r][c].Put(object);
                    }
                    case "G" -> {
                        maze[r][c] = new PathField(r,c, maze);
                        var object = new GhostObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols, maze[r][c]);
                        var thread = new Thread(object);
                        processRunner.addGhost(thread, object);
                        maze[r][c].Put(object);
                    }
                    case "K" -> {
                        var object = new KeyObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols);
                        maze[r][c] = new PathField(r,c, maze);
                        maze[r][c].Put(object);
                    }
                    case "S" -> {
                        maze[r][c] = new PathField(r,c, maze);
                        var object = new PacmanObject(gameGridPane, r, c, 3, keys, 0, 0, windowHeight / rows, windowWidth / cols, maze[r][c], scene, this);
                        var thread = new Thread(object);
                        processRunner.setPacman(thread, object);
                        maze[r][c].Put(object);
                        maze[r][c].Put(new HomeObject(gameGridPane, r, c, windowHeight / rows, windowWidth / cols));
                        pacmanStartingRow = r;
                        pacmanStartingCol = c;
                        fieldHeight = windowHeight / rows;
                        fieldWidth = windowWidth / cols;
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
        var loggerRecorder = new LoggerRecorder(maze);
        Thread loggerRecorderThread = new Thread(loggerRecorder);
        processRunner.addRecorder(loggerRecorderThread, loggerRecorder);

        processRunner.runRecorder();
        processRunner.runPacman();
        processRunner.runGhosts();
    }

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
            playPause = new ImageView(new Image("pause.png"));
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
            playPause = new ImageView(new Image("play.png"));
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

    public void PacmanStep()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                nSteps.setText((++pacmanSteps) + " steps");
            }
        });
    }

    public void OpenLogger(LoggerResult result) throws IOException {
        this.root.getChildren().clear();

        gameStatusLabel = new Label();
        gameStatusLabel.setLayoutX(0.5);
        gameStatusLabel.setLayoutY(0.5);
        gameStatusLabel.setPadding(new Insets(10));
        gameStatusLabel.setVisible(false);

        cols = logger.GetCols();
        rows = logger.GetRows();

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




        ImageView playBack = new ImageView(new Image("play_back.png"));
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

        ImageView stepBack = new ImageView(new Image("step_back.png"));
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

        playPause = new ImageView(new Image("play.png"));
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

        ImageView stepForward = new ImageView(new Image("step_forward.png"));
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

        ImageView playForward = new ImageView(new Image("play_forward.png"));
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