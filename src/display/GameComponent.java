package display;

import controllers.Game;
import gamingElements.Alien;
import gamingElements.Bullet;
import gamingElements.MyShip;
import gamingElements.SpaceRocks;
import recorder.MarkRecorder;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {
    public GameComponent() {
        super();
    }
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (Alien.alien != null) Alien.alien.drawMe(graphics);
        MyShip.myShip.drawMe(graphics);
        Bullet.drawBullets(graphics);
        SpaceRocks.drawSpaceRocks(graphics);
        printTexts(graphics);
    }

    private static void printTexts(Graphics graphics) {
        MarkRecorder.paintScore(graphics);
        Game.fpsController.printFPS(graphics);
        Game.printLevel(graphics);
    }
}
