public class Main {
    public static void main(String[] args) throws Exception {
        TilesScanner scanner = new TilesScanner("YOUR_WORLD_HERE", 0);
        TileMapViewer.openWindow(scanner);
    }
}
