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

public class Logger {

    public int logDataNum;
    public String logFile;
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
                        String PathToLog = dir.toString() + "\\game0.log";
                        try {
                            List<String> lines = Files.readAllLines(Path.of(PathToLog));
                            if (lines.size() > 1) {
                                String secondLine = lines.get(1);
                                logDateTime[0] = secondLine;
                            }
                        } catch (IOException e) {
                            // handle file read error
                        }
                        logFileName[0] = dir.getFileName().toString();
                        Logs.add(new LogName(logDateTime[0], logFileName[0]));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Logs;
    }

    public LoggerResult LoadLog(String logFileName, boolean fromStart) throws IOException {
        logDataNum = 0;
        logFile = logFileName;

        File file;
        if(!fromStart)
        {
            while(true)
            {
                file = new File("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log");
                if (!file.exists()) {
                    System.out.println("File does not exist.");
                    logDataNum--; //TODO HANDLE ABY NESLO POD 0
                    break;
                }

                if(!checkEndOfLog("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log"))
                {
                    System.out.println("Chybi end of log" + logDataNum);
                    logDataNum--;
                    break;
                }
                logDataNum++;
            }
        }

        return LoadLogBasedOnLogDataNum();
    }

    public int GetCols()
    {
        // TODO
        return 0;
    }

    public int GetRows()
    {
        // TODO
        return 0;
    }
    public String[][] GetFields()
    {
        // TODO
        return null;
    }

    // Vracíš null pokud již další nejsou
    public LoggerResult NextStep() throws IOException {
        File file;
        logDataNum++;

        file = new File("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log");
        if (!file.exists()) {
            System.out.println("File does not exist.");
            logDataNum--;
            return null;
        }

        if(!checkEndOfLog("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log"))
        {
            System.out.println("Chybi end of log" + logDataNum);
            logDataNum--;
            return null;
        }

        return LoadLogBasedOnLogDataNum();
    }

    // Vracíš null pokud již další nejsou
    public LoggerResult BackStep() throws IOException {
        File file;
        logDataNum--;

        file = new File("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log");
        if (!file.exists()) {
            System.out.println("File does not exist.");
            logDataNum++;
            return null;
        }

        if(!checkEndOfLog("data\\logs\\"+ logFile +"\\game"+(logDataNum)+".log"))
        {
            System.out.println("Chybi end of log" + logDataNum);
            logDataNum++;
            return null;
        }

        return LoadLogBasedOnLogDataNum();
    }

    public LoggerResult LoadLogBasedOnLogDataNum() throws IOException {
        final int[] lives = {0};
        final int[] steps = {0};

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

    public MapLoggerResult LogMapParse(String LogNum) throws FileNotFoundException {
        String logFilePath = Paths.get("data", "logs", LogNum, "game0.log").toString();


        int rows = 0;
        int cols = 0;
        String[][] field = null;
        try (Scanner scanner = new Scanner(new File(logFilePath))) {
            // Read the first line of the file to get the dimensions of the field
            String firstLine = scanner.nextLine();
            String[] dimensions = firstLine.split(" ");
            rows = Integer.parseInt(dimensions[0]);
            cols = Integer.parseInt(dimensions[1]);

            // Create the field array
            field = new String[rows][cols];

            // Loop through the remaining lines of the file to fill in the field array
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Ignore any lines that don't start with a "[" character
                if (!line.startsWith("[")) {
                    continue;
                }

                // Split the line into its component parts
                String[] parts = line.split(": ");
                String[] coords = parts[0].substring(1, parts[0].length() - 1).split("]\\[");
                int row = Integer.parseInt(coords[0]);
                int col = Integer.parseInt(coords[1]);
                String value = parts[1];

                // Store the value in the field array
                field[row][col] = value;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Print the field array to the console
        for (String[] row : field) {
            for (String value : row) {
                System.out.print(value == null ? "." : value.charAt(0));
                System.out.print(" ");
            }
            System.out.println();
        }
        return new MapLoggerResult(rows, cols, field);
    }
}
