package controllers;

import java.awt.*;

public class FPSController {
    private long zeroTime;
    private int fps;
    private int fDifference;
    long lastTime;
    private boolean needRefresh = false;
    private int gaps;
    private int realFPS;

    public FPSController(int fps) {
        this.fps = fps;
        this.zeroTime = System.currentTimeMillis();
        this.fDifference = 1000 / fps;
        this.lastTime = this.zeroTime;
    }

    public boolean timeCheck() {
        long now = System.currentTimeMillis();
        long difference = now - this.lastTime;
        if (difference >= this.fDifference) {
            this.needRefresh = true;
            this.gaps = (int) difference / this.fDifference;
            this.lastTime = now;
            this.realFPS = (int) (1000 / difference);
            return true;
        } else {
            this.needRefresh = false;
            return false;
        }
    }

    public boolean gapExists() {
        if (this.gaps <= 0) {
            this.gaps = 0;
            return false;
        } else {
            this.gaps--;
            return true;
        }
    }

    public void printFPS(Graphics graphics) {
        graphics.setColor(Color.lightGray);
        Font font = new Font("Arial", Font.BOLD, 10);
        graphics.setFont(font);
        graphics.drawString("FPS: " + this.realFPS, 10, Game.WINDOW_HEIGHT - 50);
    }
}
