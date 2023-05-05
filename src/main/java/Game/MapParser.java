package Game;

import Game.Records.MapParserResult;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse maps from path data/maps
 */
public class MapParser{

    /**
     * get list of maps located in data/maps (without the .txt)
     * @return list of map names
     */
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

    /**
     * Parse map from data/maps into 2d field X(walls) and objects/paths(S,T,K,G,.)
     * @param mapName Map that has to be parsed
     * @return rows, cols, keys, [rows][cols] X/.
     * @throws IOException file error
     */
    public MapParserResult getMap(String mapName) throws IOException {
        File file = new File("data\\maps\\" +  mapName + ".txt");
        InputStream chosenMap = new FileInputStream(file);

        int keys = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(chosenMap));
        String[] dimensions = reader.readLine().split(" ");
        int numRows = Integer.parseInt(dimensions[0]);
        int numCols = Integer.parseInt(dimensions[1]);

        // 2D array with extra rows and columns to represent the walls
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
