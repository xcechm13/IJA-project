package Game;

import Game.Records.LogName;
import Game.Records.LoggerResult;
import Game.Records.MapLoggerResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Class for parsing data from logs
 */
public class Logger {

    /**
     * counter of log of replayed game
     */
    public int logDataNum;
    /**
     * Name of Game that is replaying
     */
    public String logFile;

    /**
     * Checks all Games saved in log in path data/logs
     * @return All saved games (date + name)
     */
    public List<LogName> GetLogs() {
        List<LogName> Logs = new ArrayList<>();
        String logFoldersPath = "data\\logs";
        final String[] logFileName = new String[1];
        final String[] logDateTime = new String[1];

        Path directory = Paths.get(logFoldersPath);
        try (Stream<Path> stream = Files.list(directory))
        {
            stream
                    .filter(Files::isDirectory)
                    .forEach(dir -> {
                        //reads date from each first log on second line
                        String PathToLog = dir.toString() + "\\game0.log";
                        try {
                            List<String> lines = Files.readAllLines(Path.of(PathToLog));
                            if (lines.size() > 1) {
                                String secondLine = lines.get(1);
                                logDateTime[0] = secondLine;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        logFileName[0] = dir.getFileName().toString();
                        Logs.add(new LogName(logDateTime[0], logFileName[0]));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Logs;
    }

    /**
     * Load first/last log from given logFileName
     * @param logFileName  name of log
     * @param fromStart true if load first log else load last
     * @return lives + steps + [row][col]List of strings(object type)
     * @throws IOException File error
     */
    public LoggerResult LoadLog(String logFileName, boolean fromStart) throws IOException {
        logDataNum = 0;
        logFile = logFileName;

        File file;
        if(!fromStart)
        {
            //Get to the last valid log
            while(true)
            {
                file = new File("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log");
                if (!file.exists()) {
                    logDataNum--;
                    break;
                }

                if(!checkEndOfLog("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log"))
                {
                    logDataNum--;
                    break;
                }
                logDataNum++;
            }
        }

        return LoadLogBasedOnLogDataNum();
    }

    /**
     * Get next log based on logDataNum
     * @return null if there are no more logs / lives + steps + [row][col]List of strings(object type)
     * @throws IOException File error
     */
    public LoggerResult NextStep() throws IOException {
        File file;
        logDataNum++;

        file = new File("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log");
        if (!file.exists()) {
            logDataNum--;
            return null;
        }

        if(!checkEndOfLog("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log"))
        {
            logDataNum--;
            return null;
        }

        return LoadLogBasedOnLogDataNum();
    }

    /**
     * Get previous log based on logDataNum
     * @return null if there are no more valid logs / lives + steps + [row][col]List of strings(object type)
     * @throws IOException File error
     */
    public LoggerResult BackStep() throws IOException {
        File file;
        logDataNum--;

        file = new File("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log");
        if (!file.exists()) {
            logDataNum++;
            return null;
        }

        if(!checkEndOfLog("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log"))
        {
            logDataNum++;
            return null;
        }

        return LoadLogBasedOnLogDataNum();
    }

    /**
     * Load log from file based on logDataNum
     * @return lives + steps + [row][col]List of strings(object type)
     * @throws IOException file error
     */
    public LoggerResult LoadLogBasedOnLogDataNum() throws IOException {
        final int[] lives = {0};
        final int[] steps = {0};

        //Inicialize maze[row][col] (first line of log)
        List<String> lines = Files.readAllLines(Path.of("data\\logs\\" + logFile + "\\game"+ logDataNum +".log"));
        String firstLine = lines.get(0);
        String[] rowCols = firstLine.split(" ");
        List<String>[][] maze = new List[Integer.parseInt(rowCols[0])][Integer.parseInt(rowCols[1])];
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[i].length; j++)
            {
                maze[i][j] = new ArrayList<>();
            }
        }

        // save Objects to maze[][]
        try (Stream<String> stream = Files.lines(Paths.get("data\\logs\\" + logFile + "\\game"+ logDataNum +".log"))) {
            stream.filter(line -> line.contains("Target") || line.contains("Key") || line.contains("Home")
                            || line.contains("Pacman") || line.contains("Ghost"))
                    .forEach(line -> {
                        String[] parts = line.split(" ");
                        //row and col
                        String[] indices = parts[0].substring(1, parts[0].length() - 2).split("]\\[");
                        for(int i = 0; i < parts.length; i++)
                        {
                            if (parts[i].equals("Pacman"))
                            {
                                //save lives + steps
                                lives[0] = Integer.parseInt(parts[i + 1]);
                                steps[0] = Integer.parseInt(parts[i + 2]);
                                maze[Integer.parseInt(indices[0])][Integer.parseInt(indices[1])].add(parts[i + 3]);
                            }
                            else if (parts[i].equals("Ghost"))
                            {
                                maze[Integer.parseInt(indices[0])][Integer.parseInt(indices[1])].add(parts[i + 1]);
                            }
                            else if (parts[i].equals("Target") || parts[i].equals("Key") || parts[i].equals("Home"))
                            {
                                maze[Integer.parseInt(indices[0])][Integer.parseInt(indices[1])].add(parts[i]);
                            }
                        }
                    });
        }

        return new LoggerResult(steps[0], lives[0], maze);
    }

    /**
     * check if log is valid
     * @param filePath path to Log
     * @return true if valid
     */
    public boolean checkEndOfLog(String filePath) {
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String lastLine = "";
            while ((line = br.readLine()) != null) {
                lastLine = line;
            }
            br.close();
            return lastLine.startsWith("END OF LOG");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Parse log to map
     * @param LogNum LogName
     * @return rows, cols, [][] of X(Wall) or . (Path)
     * @throws FileNotFoundException file error
     */
    public MapLoggerResult LogMapParse(String LogNum) throws FileNotFoundException {
        String logFilePath = Paths.get("data", "logs", LogNum, "game0.log").toString();

        int rows = 0;
        int cols = 0;
        String[][] field = null;
        try (Scanner scanner = new Scanner(new File(logFilePath))) {
            // Read the first line of the file to get the row and col of the field
            String firstLine = scanner.nextLine();
            String[] dimensions = firstLine.split(" ");
            rows = Integer.parseInt(dimensions[0]);
            cols = Integer.parseInt(dimensions[1]);

            field = new String[rows][cols];

            // Loop through the remaining lines of the file to fill in the field array
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Ignore any lines that dont start with a "["
                if (!line.startsWith("[")) {
                    continue;
                }

                // Split the line into its component parts
                String[] parts = line.split(": ");
                String[] coords = parts[0].substring(1, parts[0].length() - 1).split("]\\[");
                int row = Integer.parseInt(coords[0]);
                int col = Integer.parseInt(coords[1]);
                String value = parts[1];

                field[row][col] = value;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return new MapLoggerResult(rows, cols, field);
    }

    /**
     * Delete log on given LogName
     * @param logFileName LogName
     * @throws IOException file error
     */
    public void DeleteLog(String logFileName) throws IOException {
        File file = new File("data\\logs\\" + logFileName);
        if (!file.exists()) {
            return;
        }

        Files.walk(Path.of("data\\logs\\" + logFileName))
                .sorted(java.util.Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
