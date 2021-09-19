package controllers;

import display.GameWindow;
import gamingElements.MyShip;
import recorder.MarkRecorder;
import recorder.ScoreKeeper;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 690;
    private static final int FPS = 60;
    private static final double DEATH_TIME_DELAY = 0.5;

    public static PlayerListener playerListener = new PlayerListener();
    public static FPSController fpsController = new FPSController(FPS);
    private static int level;
    public static ScoreKeeper scoreKeeper = new ScoreKeeper();

    public String playerName;

    public Game() {
        scoreKeeper = new ScoreKeeper();
        playerListener.refresh();
        new GameWindow();
        level = 1;
    }

    public static void setLevel() {
        new ElementsController((level + 1) * 2, true);
    }

    public void run() {
        new MyShip();
        GAME_LOOP: while (true) {
            playerListener.refresh();
            setLevel();
            while (!ElementsController.noElements()) {
                if (fpsController.timeCheck()) {
                    MyShip.myShip.control();
                    ElementsController.elementsController.collisionJudgement();
                    if (MyShip.lives <= 0) {
                        if (MarkRecorder.instanceScore >= MarkRecorder.lifeMark) {
                            Object[] options = {"SURE!", "NO, LET ME DIE..."};
                            int rst = JOptionPane.showOptionDialog(null, "YOU'VE BEEN KILLED, WOULD YOU LIKE TO USE " + MarkRecorder.lifeMark + " INSTANCE SCORE TO BUY A NEW LIFE?", "YOU HAVE BEEN KILLED!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[ 0 ]);
                            if (rst == 0) {
                                MarkRecorder.instanceScore -= MarkRecorder.lifeMark;
                                MarkRecorder.lifeMark *= 2;
                                ElementsController.elementsController.myShip = new MyShip();
                                MyShip.lives = 1;
                            } else {
                                playerName = JOptionPane.showInputDialog(null,"PLEASE TYPE YOUR NAME:\n","GAME OVER!",JOptionPane.PLAIN_MESSAGE);
                                break GAME_LOOP;
                            }
                        } else {
                            playerName = JOptionPane.showInputDialog(null,"YOU'VE BEEN KILLED AND CANNOT AFFORD ANOTHER LIFE, PLEASE TYPE YOUR NAME:\n","GAME OVER!",JOptionPane.PLAIN_MESSAGE);
                            break GAME_LOOP;
                        }
                    }
                    do {
                        GameWindow.gameWindow.repaint();
                    } while (fpsController.gapExists());
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            level++;
            GameTimer gameTimer = new GameTimer(DEATH_TIME_DELAY);
            while (!gameTimer.timeCheck()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(this.playerName);  // real name / empty string / null
        if (this.playerName != null && MarkRecorder.totalScore > scoreKeeper.getLowestScore()) {
            if (this.playerName.equals("")) {
                scoreKeeper.addScore("Player", (int) MarkRecorder.totalScore);
            } else {
                scoreKeeper.addScore(this.playerName, (int) MarkRecorder.totalScore);
            }
            scoreKeeper.saveScores();
        }

        MarkRecorder.throwResultWindow();

        GameTimer gameTimer = new GameTimer(0.5);
        while (!gameTimer.timeCheck()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GameWindow.gameWindow.dispose();
    }

    public static void printLevel(Graphics graphics) {
        Font font = new Font("Arial", Font.BOLD, 15);
        graphics.setFont(font);
        graphics.drawString("LEVEL: " + level, 20, 90);
    }

    /*
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }*/
}
