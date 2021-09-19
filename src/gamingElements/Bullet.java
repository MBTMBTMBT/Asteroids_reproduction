package gamingElements;

import controllers.Game;
import controllers.GameTimer;

import java.awt.*;
import java.awt.geom.Area;
import java.util.LinkedList;

public class Bullet implements Hittable, Drawable {
    private static final double SIZE = 0.5;
    private static final double SPEED = 12;

    public static LinkedList<Bullet> bullets = new LinkedList<>();
    private boolean killed = false;
    private boolean byPlayer;
    private double centerX;
    private double centerY;
    private Vector speedVector;
    private double angle;
    private Polygon shape;
    private Polygon collisionPolygon;
    private GameTimer timer;

    private double AX;
    private double AY;
    private double BX;
    private double BY;
    private double CX;
    private double CY;
    private double DX;
    private double DY;
    private double EX;
    private double EY;
    private double FX;
    private double FY;

    public Bullet(int angle, int positionX, int positionY, boolean byPlayer, Vector platformSpeed) {
        Bullet.bullets.add(this);
        this.byPlayer = byPlayer;
        this.centerX = positionX;
        this.centerY = positionY;
        this.angle = angle;
        if (this.byPlayer) {
            this.speedVector = Vector.toVector(angle, SPEED * 1.25);
        } else {
            this.speedVector = Vector.toVector(angle, SPEED / 1.5);
        }
        // to make the speed based on the speed of the ship
        this.speedVector.displacementX += platformSpeed.displacementX;
        this.speedVector.displacementY += platformSpeed.displacementY;
        this.timer = new GameTimer(0.4);
    }

    public Bullet(int angle, int positionX, int positionY, boolean byPlayer) {
        Bullet.bullets.add(this);
        this.byPlayer = byPlayer;
        this.centerX = positionX;
        this.centerY = positionY;
        this.angle = angle;
        if (this.byPlayer) {
            this.speedVector = Vector.toVector(angle, SPEED * 1.25);
        } else {
            this.speedVector = Vector.toVector(angle, SPEED / 1.5);
        }
        this.timer = new GameTimer(0.4);
    }

    private void getShape() {
        this.shape = new Polygon();
        this.AX = centerX;
        this.AY = centerY + SIZE * 2;
        this.BX = centerX - SIZE * 1.732;
        this.BY = centerY + SIZE;
        this.CX = centerX - SIZE * 1.732;
        this.CY = centerY - SIZE;
        this.DX = centerX;
        this.DY = centerY - SIZE * 2;
        this.EX = centerX + SIZE * 1.732;
        this.EY = centerY - SIZE;
        this.FX = centerX + SIZE * 1.732;
        this.FY = centerY + SIZE;
        this.shape.addPoint((int)AX, (int)AY);
        this.shape.addPoint((int)BX, (int)BY);
        this.shape.addPoint((int)CX, (int)CY);
        this.shape.addPoint((int)DX, (int)DY);
        this.shape.addPoint((int)EX, (int)EY);
        this.shape.addPoint((int)FX, (int)FY);
        double nextPointX = this.centerX + this.speedVector.displacementX * 2;
        double nextPointY = this.centerY + this.speedVector.displacementY * 2;
        double lastPointX = this.centerX - this.speedVector.displacementX / 2;
        double lastPointY = this.centerY - this.speedVector.displacementY / 2;
        this.collisionPolygon = new Polygon();
        if ((angle <= 135 && angle >= 45) || (angle <= 315 && angle >= 225)){
            this.collisionPolygon.addPoint((int) lastPointX, (int) lastPointY + 3);
            this.collisionPolygon.addPoint((int) lastPointX, (int) lastPointY - 3);
            this.collisionPolygon.addPoint((int) nextPointX, (int) nextPointY - 3);
            this.collisionPolygon.addPoint((int) nextPointX, (int) nextPointY + 3);
        } else {
            this.collisionPolygon.addPoint((int) lastPointX - 3, (int) lastPointY);
            this.collisionPolygon.addPoint((int) lastPointX + 3, (int) lastPointY);
            this.collisionPolygon.addPoint((int) nextPointX + 3, (int) nextPointY);
            this.collisionPolygon.addPoint((int) nextPointX - 3, (int) nextPointY);
        }
    }

    private void move() {
        this.centerX += speedVector.displacementX;
        this.centerY += speedVector.displacementY;
        if (this.centerX > Game.WINDOW_WIDTH + 10) {
            this.centerX = -10;
        } else if (this.centerX < -10) {
            this.centerX = Game.WINDOW_WIDTH + 10;
        }
        if (this.centerY > Game.WINDOW_HEIGHT + 10) {
            this.centerY = -10;
        } else if (this.centerY < -10) {
            this.centerY = Game.WINDOW_HEIGHT + 10;
        }
    }

    @Override
    public void kill() {
        this.killed = true;
    }

    @Override
    public boolean isKilled() {
        return this.killed;
    }

    public void drawMe(Graphics graphics) {
        this.move();
        this.getShape();
        graphics.setColor(Color.WHITE);
        graphics.drawPolygon(this.shape);
    }
    
    public boolean isByPlayer() {
    	return this.byPlayer;
    }

    public static void drawBullets(Graphics graphics) {
        for (int i = 0; i < Bullet.bullets.size(); i++) {
            try {
                Bullet each = Bullet.bullets.get(i);
                if (each.timer.timeCheck() || each.killed) {
                    Bullet.bullets.remove(i);
                    continue;
                }
                each.drawMe(graphics);
            } catch (Exception e) {
                break;
            }
        }
    }

    @Override
    public Area getArea() {
        return new Area(this.collisionPolygon);
    }
}
