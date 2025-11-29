import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static JTextField worldInput;
    public static JTextField levelInput;
    public static JLabel errorMsg;
    public static JFrame frame;
    public static void onButtonPressed() {
        String world = worldInput.getText();
        if (world.isEmpty()) {
            errorMsg.setText("Please Enter a world!");
            return;
        }

        Integer level;
        try {
            level = Integer.parseInt(levelInput.getText());
        } catch(NumberFormatException e) {
            errorMsg.setText("Level must be an integer!");
            return;
        }
        TilesScanner scanner;
        try {
            scanner = new TilesScanner(world, level);
        } catch(IOException e) {
            errorMsg.setText("Couldn't find level!");
            return;
        }

        TileMapViewer.openWindow(scanner);
        frame.setVisible(false);
        frame.dispose();
    }

    public static void main(String[] args) throws Exception {
        frame = new JFrame("MinicraftUnlocked");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel worldPanel = new JPanel();
        worldPanel.setMaximumSize(new Dimension(250,30));
        JPanel levelPanel = new JPanel();
        
        JPanel errorPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setMaximumSize(new Dimension(250, 30));

        JLabel worldLabel = new JLabel("set World!");
        JLabel levelLabel = new JLabel("set Level!");
        worldInput = new JTextField(10);
        levelInput = new JTextField(4);

        worldPanel.add(worldLabel);
        worldPanel.add(worldInput);
        levelPanel.add(levelLabel);
        levelPanel.add(levelInput);
        
        JButton submitButton = new JButton("Submit!");
        submitButton.addActionListener(e -> {
            onButtonPressed();
        });
        buttonPanel.add(submitButton);

        errorMsg = new JLabel();
        errorMsg.setForeground(Color.RED);
        errorPanel.add(errorMsg);


        mainPanel.add(worldPanel);
        mainPanel.add(levelPanel);
        mainPanel.add(errorPanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        frame.add(mainPanel);
        frame.setSize(250,200);
        frame.setVisible(true);
        // TilesScanner scanner = new TilesScanner(world, level);
        // TileMapViewer.openWindow(scanner);
    }
}
