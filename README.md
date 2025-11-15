# MinicraftUnlocked
A utility tool for Minicraft that assists with diagnostics, analysis, and data manipulation.

# Features

- Access game save files to read a given level's data and display a full map.
  Usage:
  - Right-click + scroll wheel → scroll horizontally.
  - Scroll wheel → scroll vertically.
  - Ctrl + scroll wheel → zoom in/out.
  - Ctrl + +/- → zoom in/out.
  - Left-click + drag → move the map around.

Example of the map tool in use:

<img width="770" height="761" alt="Capture d’écran 2025-11-15 033759" src="https://github.com/user-attachments/assets/c26e5728-64ba-42b3-9f8d-8388ffedd1d9" />

# Launching the Code
This code is in a primitive state and is not user-friendly. In the future, we plan to add a comprehensive UI. For now, you can use it by downloading the source code and modifying the Main.class file. Replace "YOUR_WORLD_NAME" with your world name and YOUR_CHOSEN_LEVEL with the world level you want to view:

```
public class Main {
    public static void main(String[] args) throws Exception {
        TilesScanner scanner = new TilesScanner("YOUR_WORLD_NAME", YOUR_CHOSEN_LEVEL);
        TileMapViewer.openWindow(scanner);
    }
}
```

If you are unsure what LEVEL refers to, please check the official [Minicraft Wiki](https://minicraft.wiki.gg/wiki/Staircase)

# Known Issues and Ambiguities
- The code currently only works on Windows.
- Tile colors are temporary and will be replaced with proper textures in a future update.
