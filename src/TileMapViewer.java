import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

class TileMapViewer extends JPanel {
    private final String[] tiles;
    private final int width;
    private final int height;
    private final Map<String, Color> tileColors = new HashMap<>();


    private int tileSize = 8;
    private Point lastDrag;

    public TileMapViewer(TilesScanner scanner) {
        this.tiles = scanner.getTiles();
        this.width = scanner.getMapWidth();
        this.height = scanner.getMapHeight();

        initColors();
        updatePreferredSize();
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    lastDrag = e.getPoint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                lastDrag = null;
                setCursor(Cursor.getDefaultCursor());
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastDrag != null) {
                    JViewport viewport = (JViewport) getParent();
                    Point viewPos = viewport.getViewPosition();
                    int dx = lastDrag.x - e.getX();
                    int dy = lastDrag.y - e.getY();
                    viewPos.translate(dx, dy);
                    viewPos.x = Math.max(0, Math.min(viewPos.x, getWidth() - viewport.getWidth()));
                    viewPos.y = Math.max(0, Math.min(viewPos.y, getHeight() - viewport.getHeight()));
                    viewport.setViewPosition(viewPos);
                }
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JViewport viewport = (JViewport) getParent();
                Point viewPos = viewport.getViewPosition();

                if (SwingUtilities.isRightMouseButton(e)) {
                    viewPos.x += e.getWheelRotation() * 32;
                    viewPos.x = Math.max(0, Math.min(viewPos.x, getWidth() - viewport.getWidth()));
                } else if (e.isControlDown()) {
                    if (e.getWheelRotation() < 0) zoomIn();
                    else zoomOut();
                    revalidate();
                    return;
                } else {
                    viewPos.y += e.getWheelRotation() * 32;
                    viewPos.y = Math.max(0, Math.min(viewPos.y, getHeight() - viewport.getHeight()));
                }
                viewport.setViewPosition(viewPos);
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    private void initColors() {
        tileColors.put("WATER", new Color(0, 0, 200));
        tileColors.put("IRON ORE", new Color(150, 150, 150));
        tileColors.put("GOLD ORE", new Color(255, 220, 0));
        tileColors.put("GEM ORE", new Color(0, 200, 200));
        tileColors.put("GRASS", new Color(0, 180, 0));
        tileColors.put("ROCK", new Color(100, 100, 100));
        tileColors.put("DIRT", new Color(120, 72, 0));
        tileColors.put("SAND", new Color(240, 230, 120));
        tileColors.put("STONE BRICKS", new Color(130, 130, 130));
        tileColors.put("TREE", new Color(34, 139, 34));
        tileColors.put("FLOWER", new Color(255, 0, 200));
        tileColors.put("CACTUS", new Color(0, 120, 0));
        tileColors.put("OBSIDIAN WALL", new Color(20, 20, 60));
        tileColors.put("OBSIDIAN", new Color(40, 0, 80));
        tileColors.put("LAVA", new Color(255, 80, 0));
        tileColors.put("CLOUD", new Color(220, 220, 255));
        tileColors.put("STAIRS DOWN", new Color(60, 60, 60));
        tileColors.put("STAIRS UP", new Color(255, 255, 255));
        tileColors.put("CLOUD CACTUS", new Color(200, 255, 200));
        tileColors.put("ORNATE OBSIDIAN", new Color(80, 0, 120));
        tileColors.put("RAW OBSIDIAN", new Color(60, 0, 90));
    }

    private Color getColorFor(String type) {
        return tileColors.getOrDefault(type.toUpperCase(), Color.PINK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int index = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (index >= tiles.length) return;
                String tile = tiles[index];
                Color c = getColorFor(tile);
                g.setColor(c);
                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                index++;
            }
        }
    }

    public void zoomIn() {
        tileSize += 2;
        updatePreferredSize();
        repaint();
    }

    public void zoomOut() {
        if (tileSize > 2) {
            tileSize -= 2;
            updatePreferredSize();
            repaint();
        }
    }

    private void updatePreferredSize() {
        setPreferredSize(new Dimension(width * tileSize, height * tileSize));
        revalidate();
    }

    public static void openWindow(TilesScanner scanner) {
        TileMapViewer viewer = new TileMapViewer(scanner);

        JFrame frame = new JFrame("Tile Map Viewer - Level " + scanner.getLevel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(viewer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(32);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(32);
        frame.add(scrollPane);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_PLUS, KeyEvent.VK_EQUALS -> viewer.zoomIn();
                    case KeyEvent.VK_MINUS -> viewer.zoomOut();
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
