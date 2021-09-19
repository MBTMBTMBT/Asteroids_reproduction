package display;

import java.awt.*;

import javax.swing.*;

import controllers.Game;
import recorder.ScoreKeeper;

public class ScoreInfoScreen extends JFrame {
	private static final int MENU_WIDTH = 900;
	private static final int MENU_HEIGHT = 690;

	enum ScreenType {
		SCORE_SCREEN, INFO_SCREEN
	}

	public ScoreInfoScreen(ScreenType type) {
		this.setTitle("Asteroids - Scores");
		if (type == ScreenType.SCORE_SCREEN) {
			this.add(new ScoreScreenTexts());
			this.setTitle("Asteroids - Scores");
		} else {
			this.add(new InfoScreenTexts());
			this.setTitle("Asteroids - Information");
		}
		this.getContentPane().setBackground(Color.BLACK);
		ImageIcon img = new ImageIcon("src/images/back.jpg");
		JLabel background = new JLabel(img);
		this.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		Container contain = this.getContentPane();
		((JPanel) contain).setOpaque(false);
		this.addKeyListener(MainMenu.menuListener);
		this.setSize(MENU_WIDTH, MENU_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	public void run() {
		this.setVisible(true);
		this.repaint();
		while (!MainMenu.menuListener.isM()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.setVisible(false);
	}

	private static class ScoreScreenTexts extends JComponent {
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			Image image = new ImageIcon("src/images/back.jpg").getImage();
			graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
			graphics.setColor(Color.lightGray);
			Font font = new Font("Arial", Font.BOLD, 48);
			graphics.setFont(font);
			graphics.drawString("Asteroids Hall of Fame", MENU_WIDTH / 5, MENU_HEIGHT / 7);
			ScoreKeeper.Score[] scores = Game.scoreKeeper.getScores();
			graphics.setFont(new Font("Arial", Font.BOLD, 24));
			for (int i = 0; i < scores.length; i++) {
				ScoreKeeper.Score score = scores[i];
				if (i == 9) {
					graphics.drawString((i + 1) + "    " + score.getName(), 2 * Game.WINDOW_WIDTH / 7, 180 + i * 32);
				} else {
					graphics.drawString((i + 1) + "      " + score.getName(), 2 * Game.WINDOW_WIDTH / 7, 180 + i * 32);
				}
				graphics.drawString("" + score.getScore(), 4 * Game.WINDOW_WIDTH / 7, 180 + i * 32);
			}
			graphics.drawString("Press 'M' to return to the Main Menu", MENU_WIDTH / 4, MENU_HEIGHT * 4 / 5);
		}
	}

	private static class InfoScreenTexts extends JComponent {
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			Image image = new ImageIcon("src/images/back.jpg").getImage();
			graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
			graphics.setColor(Color.lightGray);
			Font font = new Font("Arial", Font.BOLD, 48);
			graphics.setFont(font);
			graphics.drawString("How to Play", 2 * Game.WINDOW_WIDTH / 9, MENU_HEIGHT / 5);
			graphics.setFont(new Font("Arial", Font.BOLD, 24));
			graphics.drawString("Press 'UP ARROW' to accelerate", 2 * Game.WINDOW_WIDTH / 9, 180 + 2 * 32);
			graphics.drawString("Press 'LEFT ARROW' & 'RIGHT ARROW' to rotate", 2 * Game.WINDOW_WIDTH / 9, 180 + 4 * 32);
			graphics.drawString("Press 'SPACE' to shoot bullets", 2 * Game.WINDOW_WIDTH / 9, 180 + 6 * 32);
			graphics.drawString("Press 'SHIFT' to do hyperspace jump", 2 * Game.WINDOW_WIDTH / 9, 180 + 8 * 32);
			graphics.drawString("Press 'M' to return to the Main Menu", 2 * MENU_WIDTH / 9, MENU_HEIGHT * 4 / 5);
		}
	}
}
