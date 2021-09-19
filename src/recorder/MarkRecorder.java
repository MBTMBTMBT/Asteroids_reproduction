package recorder;

import javax.swing.*;
import java.awt.*;

public class MarkRecorder {
	public static final int ALIEN_SCORE = 200;
	public static final int LARGE_ROCK = 20;
	public static final int MID_ROCK = 50;
	public static final int SMALL_ROCK = 100;
	
	public static long totalScore = 0;
	public static long instanceScore = 0;
	public static long lifeMark = 500;
	private static long startTime = System.currentTimeMillis();

	public static long bullets = 0;
	public static long aliens = 0;
	public static long largeRocks = 0;
	public static long midRocks = 0;
	public static long smallRocks = 0;
	
	public static void addScore(int score) {
		MarkRecorder.totalScore += score;
		MarkRecorder.instanceScore += score;
		System.out.println(MarkRecorder.totalScore);
	}
	
	public static void paintScore(Graphics g) {
			g.setColor(Color.lightGray);
			Font font = new Font("Arial", Font.BOLD, 15);
			g.setFont(font);
			g.drawString("Total Score: " + MarkRecorder.totalScore, 20, 30);
			g.drawString("Instance Score: " + MarkRecorder.instanceScore, 20, 50);
			g.drawString("Time: " + (System.currentTimeMillis() - startTime) / 1000 + "s", 20, 70);
	}

	public static void throwResultWindow() {
		String message;
		if (bullets != 0) {
			float hitShootRate = (float) (aliens + largeRocks + midRocks + smallRocks) / (float) bullets;
			message =
					"YOU SHOT " + bullets + " BULLETS\n" +
							"YOU KILLED " + aliens + " ALIENS; " + largeRocks + " LARGE ROCKS; " + midRocks + " MID ROCKS; " + smallRocks + " SMALL ROCKS\n" +
							"YOUR HIT-SHOOT RATE IS " + hitShootRate + ".";
		} else {
			message = "YOU HAVEN'T PROVE YOURSELF DURING THE GAME.";
		}
		JOptionPane.showMessageDialog(null, message, "GAME RESULT",JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void reset() {
		totalScore = 0;
		instanceScore = 0;
		startTime = System.currentTimeMillis();
		lifeMark = 500;
		bullets = 0;
		aliens = 0;
		largeRocks = 0;
		midRocks = 0;
		smallRocks = 0;
	}
}
