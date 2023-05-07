package Game;

import Enums.Direction;
import Game.Objects.GhostObject;
import Game.Objects.PacmanObject;
import Interfaces.ICommonField;
import Interfaces.ICommonMazeObject;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Constants.Constants;

import static java.lang.Thread.sleep;

/**
 * save logs from each game played
 */
public class LoggerRecorder implements Runnable{

    public int logIncrement;
    /**
     * Path to LogDirectory
     */
    public String logFolderPath;
    /**
     * Path to exact Log
     */
    public String logFilePath;
    private ICommonField[][] maze;
    private boolean isRecording;

    /**
     * Constructor
     * @param mazeIn Actual maze full of PathFields/WallFields (contains MazeObjects)
     */
    public LoggerRecorder(ICommonField[][] mazeIn) {
        logIncrement = 0;
        logFolderPath = Paths.get("data", "logs").toString();
        logFilePath = Paths.get(logFolderPath, "game" + logIncrement + ".log").toString();
        maze = mazeIn;
        isRecording = false;
    }

    /**
     * Stop the saving of logs
     */
    public void stop()
    {
        isRecording = false;
    }

    /**
     * Start logging actual game
     */
    @Override
    public void run() {
        isRecording = true;
        logIncrement = 0;

        int logFileNum = 0;
        String dirName = "LogNum0";

        // max 100 games can be saved
        // checks first free name
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
        while (isRecording) {
            logFilePath = Paths.get(logFolderPath, "game" + logIncrement + ".log").toString();
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(logFilePath, false))) {
                //Save LOG
                writer.println(maze.length + " " + maze[0].length);
                // same date for every log
                if (!dateSet)
                {
                    dateSet = true;
                    LocalDateTime date = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    datebuff = date.format(formatter);
                }
                writer.println(datebuff);
                //check save all objects of every Pathfield
                for (int r = 0; r < maze.length; r++) {
                    for (int c = 0; c < maze[r].length; c++) {
                        writer.print("[" + r + "]" + "[" + c + "]: ");
                        if (maze[r][c].IsPathField()) {
                            writer.print(".");
                            List<ICommonMazeObject> objectsInPathField = maze[r][c].GetMazeObjects();
                            for (ICommonMazeObject o : objectsInPathField) {
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

            //check if something changed (2 consecutive cannot be the same)
            if (logIncrement > 0) {
                String file1 = Paths.get(logFolderPath, "game" + (logIncrement - 1) + ".log").toString();
                String file2 = Paths.get(logFolderPath, "game" + logIncrement + ".log").toString();
                try {
                    if (areFilesIdentical(file1, file2)) {
                        try {
                            sleep(Constants.LoggerSpeed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        continue;
                    }
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

    /**
     * Check if two files have identical content
     * @param filePath1 first file path
     * @param filePath2 second file path
     * @return true if identical
     * @throws IOException file error
     */
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