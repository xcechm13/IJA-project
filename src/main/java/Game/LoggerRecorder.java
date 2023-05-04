package Game;

import ConstantsEnums.Direction;
import Game.Objects.GhostObject;
import Game.Objects.PacmanObject;
import Game.Records.MapParserResult;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import ConstantsEnums.Constants;


public class LoggerRecorder extends Thread {

    public int logIncrement;
    public String logFolderPath;
    public String logFilePath;
    private ICommonField[][] maze;

    public LoggerRecorder(ICommonField[][] mazeIn) {
        logIncrement = 0;
        logFolderPath = Paths.get("data", "logs").toString();
        logFilePath = Paths.get(logFolderPath, "game" + logIncrement + ".log").toString();
        maze = mazeIn;
    }

    @Override
    public void run() {
        logIncrement = 0;

        int logFileNum = 0;
        String dirName = "LogNum0";

        while(logFileNum < 100)
        {
            dirName = "LogNum" + logFileNum;
            File directory = new File("data\\logs\\" + dirName);

            if(directory.exists())
            {
               logFileNum++;
               continue;
            }
            else
            {
                directory.mkdir();
                break;
            }
        }

        logFolderPath = Paths.get("data", "logs", dirName).toString();
        boolean dateSet = false;
        String datebuff = "";
        while (logIncrement < 50) {
            logFilePath = Paths.get(logFolderPath, "game" + logIncrement + ".log").toString();
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(logFilePath, false))) {
                //SaveLOG
                writer.println(maze.length + " " + maze[0].length);
                if (!dateSet)
                {
                    dateSet = true;
                    LocalDateTime date = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    datebuff = date.format(formatter);
                }
                writer.println(datebuff);
                for (int r = 0; r < maze.length; r++) {
                    for (int c = 0; c < maze[r].length; c++) {
                        writer.print("[" + r + "]" + "[" + c + "]: ");
                        if (maze[r][c].IsPathField()) {
                            writer.print(".");
                            List<ICommonMazeObject> objectsInPathField = maze[r][c].GetMazeObjects();
                            for (ICommonMazeObject o : objectsInPathField) {
                                //TODO pacman/ghost vzdy prvni
                                if (o.IsPacman()) {
                                    writer.print(" Pacman");
                                    writer.print(" " + ((PacmanObject) o).GetLives());
                                    writer.print(" " + ((PacmanObject) o).GetSteps());
                                    Direction buff = ((PacmanObject) o).GetDirection();
                                    String PacmanDirection = "pacman_up_closed.png";
                                    switch (buff) {
                                        case Up -> {
                                            PacmanDirection = "pacman_up_opened.png";
                                        }
                                        case Down -> {
                                            PacmanDirection = "pacman_down_opened.png";
                                        }
                                        case Left -> {
                                            PacmanDirection = "pacman_left_opened.png";
                                        }
                                        case Right -> {
                                            PacmanDirection = "pacman_right_opened.png";
                                        }
                                    }
                                    writer.print(" " + PacmanDirection);
                                } else if (o.IsGhost()) {
                                    writer.print(" Ghost");
                                    writer.print(" " + ((GhostObject) o).GetGhostColorFromView());
                                } else if (o.IsKey()) {
                                    writer.print(" Key");
                                } else if (o.IsTarget()) {
                                    writer.print(" Target");
                                } else if (o.IsHome()) {
                                    writer.print(" Home");
                                }
                            }
                        } else {
                            writer.print("X");
                        }
                        writer.println();
                    }
                }
                writer.print("END OF LOG");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //check if something changed
            if (logIncrement > 0) {
                String file1 = Paths.get(logFolderPath, "game" + (logIncrement - 1) + ".log").toString();
                String file2 = Paths.get(logFolderPath, "game" + logIncrement + ".log").toString();
                try {
                    System.out.println("file1: " + file1);
                    System.out.println("file2: " + file2);
                    boolean asdasd = areFilesIdentical(file1, file2);
                    if (asdasd) {
                        System.out.println("jsou stejne: ");
                        try {
                            sleep(Constants.LoggerSpeed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        continue;
                    }
                    System.out.println("nejsou stejne: ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            logIncrement++;

            try {
                sleep(Constants.LoggerSpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*public void DeleteLogFiles() {
        // Get the path to the log directory
        String resourcesFolderPath = Paths.get("data").toString();
        Path pathBuff = Paths.get(resourcesFolderPath, "logs");

        // Delete all files in the log directory
        try {
            Files.walk(pathBuff)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public boolean areFilesIdentical(String filePath1, String filePath2) throws IOException {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (line1 != null && line2 != null) {
                if (!line1.equals(line2)) {
                    return false;
                }

                line1 = reader1.readLine();
                line2 = reader2.readLine();
            }

            return line1 == null && line2 == null;
        }
    }
}