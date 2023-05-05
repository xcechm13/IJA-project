package Game;

import Game.Records.MapParserResult;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class MapParser{

    public List<String> GetListMaps() {
        List<String> maps = new ArrayList<>();
        String mapsFolderPath = "data/maps";
        Path mapsDirectory = Paths.get(mapsFolderPath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mapsDirectory, "*.txt")) {
            for (Path path : stream) {
                String fileName = path.getFileName().toString();
                String mapName = fileName.substring(0, fileName.lastIndexOf('.'));
                maps.add(mapName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maps;
    }

    public MapParserResult getMap(String mapName) throws IOException {
        mapName = "map1";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream chosenMap = classLoader.getResourceAsStream( mapName + ".txt");

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