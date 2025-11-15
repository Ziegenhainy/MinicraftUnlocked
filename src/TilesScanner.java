import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.abs;

class TilesScanner {
    public String[] data;
    public int mapWidth;
    public int mapHeight;
    private int level;
    private long seed;


    public TilesScanner(String worldName, int level) throws IOException {
        this.level = level;

        String saveDir = System.getProperty("user.home")
                + "\\AppData\\Roaming\\playminicraft\\mods\\Minicraft_Plus\\saves";

        String tilesPath = saveDir + "\\" + worldName + "\\Level" + level + ".miniplussave";
        System.out.println("Loading: " + tilesPath);

        try (BufferedReader br = new BufferedReader(new FileReader(tilesPath))) {
            String line = br.readLine();
            if (line == null) throw new IOException("File is empty: " + tilesPath);

            String[] parts = line.split(",");

            this.mapWidth = abs(Integer.parseInt(parts[0]));
            this.mapHeight = abs(Integer.parseInt(parts[1]));
            this.seed = Math.abs(Long.parseLong(parts[2]));

            this.data = new String[parts.length - 4];
            System.arraycopy(parts, 4, this.data, 0, this.data.length);
        }
    }

    public String[] getTiles() { return data; }
    public int getLevel() { return level; }
    public long getSeed() { return seed; }
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
}