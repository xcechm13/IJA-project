package Game;

import ConstantsEnums.MazeObject;
import Game.Fields.PathField;
import Game.Fields.WallField;
import Game.Records.MapParserResult;

import java.io.*;

public class MapParser{

    public MapParserResult getMap(int mapNum) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream chosenMap = classLoader.getResourceAsStream("map1.txt");

        switch (mapNum)
        {
            case 0 -> {
                chosenMap = classLoader.getResourceAsStream("map1.txt");
            }
            case 1 -> {
                chosenMap = classLoader.getResourceAsStream("map2.txt");
            }
            case 2 -> {
                chosenMap = classLoader.getResourceAsStream("map3.txt");
            }
            case 3 -> {
                chosenMap = classLoader.getResourceAsStream("map4.txt");
            }
        }

        int keys = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(chosenMap));
        String[] dimensions = reader.readLine().split(" ");
        int numRows = Integer.parseInt(dimensions[0]);
        int numCols = Integer.parseInt(dimensions[1]);

        // create a 2D array with extra rows and columns to represent the walls
        var map = new String[numRows + 2][numCols + 2];

        // initialize the borders of the map with walls (X)
        for (int i = 0; i < map.length; i++) {
            map[i][0] = "X";
            map[i][map[0].length - 1] = "X";
        }
        for (int j = 0; j < map[0].length; j++) {
            map[0][j] = "X";
            map[map.length - 1][j] = "X";
        }

        // read the remaining lines of the file and populate the map array
        for (int i = 1; i <= numRows; i++) {
            String line = reader.readLine();
            for (int j = 1; j <= numCols; j++) {
                map[i][j] = String.valueOf(line.charAt(j - 1));
                if(line.charAt(j-1) == 'K')
                    keys++;
            }
        }

        return new MapParserResult(numRows + 2, numCols + 2, keys, map);
    }
}
