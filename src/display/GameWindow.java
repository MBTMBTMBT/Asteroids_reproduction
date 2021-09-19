package display;

import controllers.Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public static JFrame gameWindow;

    public GameWindow() {
        gameWindow = new JFrame();
        gameWindow.setTitle("Asteroids - Game");
        gameWindow.add(new GameComponent());
        gameWindow.getContentPane().setBackground(Color.BLACK);
        ImageIcon img = new ImageIcon("src/images/back1.jpg");
        JLabel background = new JLabel(img);
        gameWindow.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        Container contain = gameWindow.getContentPane();
        ((JPanel) contain).setOpaque(false);
        gameWindow.addKeyListener(Game.playerListener);
        gameWindow.setSize(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setResizable(false);
        gameWindow.setVisible(true);
    }
}
