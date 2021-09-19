// About 2000 lines approximately
// Entrance of the whole program

package display;

import controllers.Game;
import controllers.PlayerListener;
import recorder.MarkRecorder;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private static final int MENU_WIDTH = 900;
    private static final int MENU_HEIGHT = 690;

    public static JFrame menu;
    public static PlayerListener menuListener = new PlayerListener();
    public static ScoreInfoScreen scoreScreen = new ScoreInfoScreen(ScoreInfoScreen.ScreenType.SCORE_SCREEN);
    public static ScoreInfoScreen infoScreen = new ScoreInfoScreen(ScoreInfoScreen.ScreenType.INFO_SCREEN);

    public MainMenu() {
        menu = new JFrame();
        menu.setTitle("Asteroids - Main Menu");
        menu.add(new menuTexts());
        menu.getContentPane().setBackground(Color.BLACK);
        ImageIcon img = new ImageIcon("src/images/back2.jpg");
        JLabel background = new JLabel(img);
        menu.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        Container contain = menu.getContentPane();
        ((JPanel) contain).setOpaque(false);
        menu.addKeyListener(menuListener);
        menu.setSize(MENU_WIDTH, MENU_HEIGHT);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLocationRelativeTo(null);
        menu.setResizable(false);
        menu.setVisible(true);
    }

    private static class menuTexts extends JComponent {
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Font font = new Font("Arial", Font.BOLD, 48);
            graphics.setFont(font);
            graphics.setColor(Color.lightGray);
            graphics.drawString("Welcome to Asteroids!!!!", MENU_WIDTH / 6, MENU_HEIGHT / 4 - 20);
            font = new Font("Arial", Font.BOLD, 36);
            graphics.setFont(font);
            graphics.drawString("To play a game press N", MENU_WIDTH / 4, (int) (1.8 * MENU_HEIGHT / 5));
            graphics.drawString("To see how to play press I", (int) (MENU_WIDTH / 4.2), (int) (2.55 * MENU_HEIGHT / 5));
            graphics.drawString("To see the High scores press H", (int) (MENU_WIDTH / 5.2), (int) (3.3 * MENU_HEIGHT / 5));
            graphics.drawString("To exit press X", MENU_WIDTH / 3, 4 * MENU_HEIGHT / 5);
        }
    }

    public static void main(String[] args) {
        new MainMenu();
        while (true) {
            MainMenu.menu.repaint();
            if (menuListener.isX()) {
                menuListener.refresh();
                break;
            } else if (menuListener.isN()) {
                menu.setVisible(false);
                MarkRecorder.reset();
                menuListener.refresh();
                Game game = new Game();
                game.run();
                scoreScreen.run();
                menu.setVisible(true);
                menuListener.refresh();
            } else if (menuListener.isH()) {
                menu.setVisible(false);
                scoreScreen.run();
                menu.setVisible(true);
                menuListener.refresh();
            } else if (menuListener.isI()) {
                menu.setVisible(false);
                infoScreen.run();
                menu.setVisible(true);
                menuListener.refresh();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MainMenu.menu.dispose();
        System.exit(0);
    }
}
