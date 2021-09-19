package gamingElements;

import controllers.Game;
import controllers.GameTimer;
import controllers.FPSController;
import recorder.MarkRecorder;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class MyShip implements Hittable, Drawable {
    private static final double LENGTH_A = 18;
    private static final double LENGTH_B = 10;
    private static final double MAX_SPEED = 12;
    private static final double ACCELERATION = 0.9;
    private static final double DECELERATION = 0.0025;
    private static final double ANGULAR_VELOCITY = 4;
    private static final double COOL_TIME = 0.3;
    private static final double JUMP_TIME_DELAY = 0.3;
    private static final int DEATH_TIME_DELAY = 500;  // milliseconds

    public static MyShip myShip;
    public static int lives;
    public static double positionX;
    public static double positionY;

    private boolean isDestroyed = true;
    private int angle = 180;  // clockwise
    private Vector speedVector = new Vector(0, 0);
    private Vector accelerateVector = new Vector(0, 0);
    private Polygon shipPolygon;
    private Polygon flamePolygon;
    private Polygon flamePolygon1;
    private FPSController FPSController = new FPSController(5);
    private GameTimer rebirthTimer = new GameTimer(4);
    private GameTimer jumpTimer = new GameTimer(0);
    private GameTimer shootTimer = new GameTimer(0);
    private long deathTime = System.currentTimeMillis();

    private double centerX = Game.WINDOW_WIDTH / 2;
    private double centerY = Game.WINDOW_HEIGHT / 2;
    private double pointAX;
    private double pointAY;
    private double pointBX;
    private double pointBY;
    private double pointCX;
    private double pointCY;
    private double flameKX;
    private double flameKY;
    private double flameAX;
    private double flameAY;
    private double flameBX;
    private double flameBY;
    private double flameCX;
    private double flameCY;
    private double flameDX;
    private double flameDY;
    private double flameEX;
    private double flameEY;
    private double flameIX;
    private double flameIY;
    private double flameJX;
    private double flameJY;

    public MyShip() {
        MyShip.myShip = this;
        this.kill();
        lives = 3;
    }

    private void rotate() {
        if (Game.playerListener.isRight()) {
            this.angle -= ANGULAR_VELOCITY;
        } else if (Game.playerListener.isLeft()) {
            this.angle += ANGULAR_VELOCITY;
        }

        /* To reset angle */
        if (this.angle >= 360) {
            this.angle = 0;
        } else if (this.angle <= -360) {
            this.angle = 0;
        }
    }

    private void accelerate() {
        if (Game.playerListener.isUp() && FPSController.timeCheck()) {
            this.accelerateVector = Vector.toVector(this.angle, ACCELERATION);
        } else {
            this.accelerateVector = Vector.toVector(this.angle, 0);

            /* When the speed is very small, it will go to zero finally */
            if (this.speedVector.getModule() < 0.4) {
                if (this.speedVector.displacementX > 0.01) {
                    this.speedVector.displacementX -= DECELERATION;
                } else if (this.speedVector.displacementX < -0.01) {
                    this.speedVector.displacementX += DECELERATION;
                } else {
                    this.speedVector.displacementX = 0;
                }
                if (this.speedVector.displacementY > 0.01) {
                    this.speedVector.displacementY -= DECELERATION;
                } else if (this.speedVector.displacementY < -0.01) {
                    this.speedVector.displacementY += DECELERATION;
                } else {
                    this.speedVector.displacementY = 0;
                }
            }
        }
        this.speedVector.displacementX += this.accelerateVector.displacementX;
        this.speedVector.displacementY += this.accelerateVector.displacementY;

        if (this.speedVector.getModule() >= MAX_SPEED) {
            this.speedVector.displacementX -= this.speedVector.displacementX / 100;
            this.speedVector.displacementY -= this.speedVector.displacementY / 100;
        } else if (this.speedVector.getModule() >= MAX_SPEED / 2) {
            this.speedVector.displacementX -= this.speedVector.displacementX / 150;
            this.speedVector.displacementY -= this.speedVector.displacementY / 150;
        } else {
            this.speedVector.displacementX -= this.speedVector.displacementX / 200;
            this.speedVector.displacementY -= this.speedVector.displacementY / 200;
        }

        this.centerX += this.speedVector.displacementX;
        this.centerY += this.speedVector.displacementY;
    }

    private void planeOut() {
        if (this.centerX > Game.WINDOW_WIDTH + 10) {
            this.centerX = -10;
        } else if (this.centerX < -10) {
            this.centerX = Game.WINDOW_WIDTH + 10;
        }
        if (this.centerY > Game.WINDOW_HEIGHT + 15) {
            this.centerY = -10;
        } else if (this.centerY < -10) {
            this.centerY = Game.WINDOW_HEIGHT + 15;
        }
    }

    private void shoot() {
        if (this.shootTimer.timeCheck() && Game.playerListener.isSpace()) {
            double radiansAngle = Math.toRadians(this.angle);
            double shootPointA = this.centerX + LENGTH_A * Math.sin(radiansAngle);
            double shootPointB = this.centerY + LENGTH_A * Math.cos(radiansAngle);
            new Bullet(this.angle, (int) shootPointA, (int) shootPointB, true, this.speedVector);
            this.shootTimer = new GameTimer(COOL_TIME);
            MarkRecorder.bullets++;
        }
    }

    private void reFreshPublicPosition() {
        MyShip.positionX = this.centerX;
        MyShip.positionY = this.centerY;
    }

    public void control() {
        this.planeOut();
        this.rotate();
        this.accelerate();
        this.shoot();
        this.hyperspaceJump();
        this.reFreshPublicPosition();
    }

    private void getShape() {
        double radiansAngle = Math.toRadians(this.angle);
        this.pointAX = this.centerX + (int) (LENGTH_A * Math.sin(radiansAngle));
        this.pointAY = this.centerY + (int) (LENGTH_A * Math.cos(radiansAngle));
        this.pointBX = this.centerX - (int) (LENGTH_B * 1.414213562 * Math.sin(Math.toRadians(45 + this.angle)));
        this.pointBY = this.centerY - (int) (LENGTH_B * 1.414213562 * Math.cos(Math.toRadians(45 + this.angle)));
        this.pointCX = this.centerX + (int) (LENGTH_B * 1.414213562 * Math.sin(Math.toRadians(45 - this.angle)));
        this.pointCY = this.centerY - (int) (LENGTH_B * 1.414213562 * Math.cos(Math.toRadians(45 - this.angle)));
        shipPolygon = new Polygon();
        shipPolygon.addPoint((int) this.pointAX, (int) this.pointAY);
        shipPolygon.addPoint((int) this.pointBX, (int) this.pointBY);
        shipPolygon.addPoint((int) this.centerX, (int) this.centerY);
        shipPolygon.addPoint((int) this.pointCX, (int) this.pointCY);
        this.flameAX = this.centerX - LENGTH_A / 3 * Math.sin(radiansAngle);
        this.flameAY = this.centerY - LENGTH_A / 3 * Math.cos(radiansAngle);
        this.flameKX = this.centerX - LENGTH_A / 4 * Math.sin(radiansAngle);
        this.flameKY = this.centerY - LENGTH_A / 4 * Math.cos(radiansAngle);
        this.flameBX = this.centerX - LENGTH_B * Math.sin(radiansAngle) - (LENGTH_B / 2 - 1) * 1.3 * Math.cos(radiansAngle);
        this.flameBY = this.centerY - LENGTH_B * Math.cos(radiansAngle) + (LENGTH_B / 2 - 1) * 1.3 * Math.sin(radiansAngle);
        this.flameCX = this.centerX - LENGTH_A * Math.sin(radiansAngle);
        this.flameCY = this.centerY - LENGTH_A * Math.cos(radiansAngle);
        this.flameDX = this.centerX - LENGTH_B * Math.sin(radiansAngle) + (LENGTH_B / 2 - 1) * 1.3 * Math.cos(radiansAngle);
        this.flameDY = this.centerY - LENGTH_B * Math.cos(radiansAngle) - (LENGTH_B / 2 - 1) * 1.3 * Math.sin(radiansAngle);
        this.flameEX = this.centerX - ( LENGTH_A + LENGTH_B )* Math.sin(radiansAngle);
        this.flameEY = this.centerY - ( LENGTH_A + LENGTH_B )* Math.cos(radiansAngle);
        this.flameIX = this.centerX - LENGTH_B * Math.sin(radiansAngle) - (LENGTH_B / 2 - 1)  /1.3 * Math.cos(radiansAngle);
        this.flameIY = this.centerY - LENGTH_B * Math.cos(radiansAngle) + (LENGTH_B / 2 - 1) /1.3 * Math.sin(radiansAngle);
        this.flameJX = this.centerX - LENGTH_B * Math.sin(radiansAngle) + (LENGTH_B / 2 - 1) /1.3 * Math.cos(radiansAngle);
        this.flameJY = this.centerY - LENGTH_B * Math.cos(radiansAngle) - (LENGTH_B / 2 - 1) /1.3 * Math.sin(radiansAngle);
        flamePolygon = new Polygon();
        flamePolygon.addPoint((int) this.flameKX, (int) this.flameKY);
        flamePolygon.addPoint((int) this.flameBX, (int) this.flameBY);
        flamePolygon.addPoint((int) this.flameEX, (int) this.flameEY);
        flamePolygon.addPoint((int) this.flameDX, (int) this.flameDY);
        flamePolygon1 = new Polygon();
        flamePolygon1.addPoint((int) this.flameAX, (int) this.flameAY);
        flamePolygon1.addPoint((int) this.flameIX, (int) this.flameIY);
        flamePolygon1.addPoint((int) this.flameCX, (int) this.flameCY);
        flamePolygon1.addPoint((int) this.flameJX, (int) this.flameJY);
    }

    @Override
    public void drawMe(Graphics graphics) {
        this.getShape();
    	if (this.isDestroyed) {
    	    if (!this.rebirthTimer.timeCheck()) {
    	    	try {
    	    	    this.Flash(graphics);
    	    	} catch (Exception ignore) {
    	    	}
    	    }
    	} else {
    		graphics.setColor(Color.yellow);	
            graphics.drawPolygon(this.shipPolygon);
            drawLives(graphics);
            if (Game.playerListener.isUp()) {
                graphics.setColor(Color.red);
                graphics.drawPolygon(this.flamePolygon);
                graphics.setColor(Color.ORANGE);
                graphics.drawPolygon(this.flamePolygon1);
            }
    	}
    }

    private void Flash(Graphics graphics){
    	long difference = System.currentTimeMillis() - this.deathTime;
    	if (difference > 3000) {
	    	this.isDestroyed = false;
    	}
    	if ((difference / 250) % 2 == 0) {
    		graphics.setColor(Color.yellow);
	    	graphics.drawPolygon(this.shipPolygon);
            drawLives(graphics);
            if (Game.playerListener.isUp()) {
                graphics.setColor(Color.red);
                graphics.drawPolygon(this.flamePolygon);
                graphics.setColor(Color.ORANGE);
                graphics.drawPolygon(this.flamePolygon1);
            }
    	}
    }

    private void hyperspaceJump() {
        if (Game.playerListener.isShift()) {
            if (!this.jumpTimer.timeCheck()) {
                return;
            } else {
                this.jumpTimer = new GameTimer(JUMP_TIME_DELAY);
                this.speedVector = new Vector(0, 0);
            }
            double sideLen = Math.sqrt((LENGTH_A + LENGTH_B) * (LENGTH_A + LENGTH_B) + LENGTH_B * LENGTH_B);
            int numX = (int) (Game.WINDOW_WIDTH / sideLen);
            int numY = (int) (Game.WINDOW_HEIGHT / sideLen);
            boolean[][] collisionMat = new boolean[ numX ][];
            for (int i = 0; i < collisionMat.length; i++) {
                collisionMat[ i ] = new boolean[ numY ];
            }
            for (boolean[] booleans : collisionMat) {
                Arrays.fill(booleans, true);
            }
            for (int i = 0; i < collisionMat.length; i++) {
                for (int j = 0; j < collisionMat[ i ].length; j++) {
                    Rectangle collisionRect = new Rectangle(i * (int) sideLen, j * (int) sideLen, (int) sideLen, (int) sideLen);
                    Area collisionArea = new Area(collisionRect);
                    try {
                        for (Bullet bullet : Bullet.bullets) {
                            try {
                                Area bulletArea = bullet.getArea();
                                bulletArea.intersect(collisionArea);
                                if (!bulletArea.isEmpty()) {
                                    collisionMat[ i ][ j ] = false;
                                }
                            } catch (Exception ignore) {
                                continue;
                            }
                        }
                        for (SpaceRocks spaceRock : SpaceRocks.spaceRocks) {
                            try {
                                Area rockArea = spaceRock.getArea();
                                rockArea.intersect(collisionArea);
                                if (!collisionMat[ i ][ j ]) continue;
                                if (!rockArea.isEmpty()) {
                                    collisionMat[ i ][ j ] = false;
                                }
                            } catch (Exception ignore) {
                                continue;
                            }
                        }
                    } catch (Exception ignore) {
                    }
                    try {
                        Area alienArea = Alien.alien.getArea();
                        alienArea.intersect(collisionArea);
                        if (!alienArea.isEmpty()) {
                            collisionMat[ i ][ j ] = false;
                        }
                    } catch (Exception ignored) {
                    }
                }
            }

            LinkedList<int[]> available = new LinkedList<>();
            for (int i = 0; i < collisionMat.length; i++) {
                for (int j = 0; j < collisionMat[ i ].length; j++) {
                    if (collisionMat[ i ][ j ]) {
                        available.add(new int[]{i, j});
                    }
                }
            }
            if (available.size() == 0) {
                this.jumpTimer = new GameTimer(0);
            } else {
                int index = new Random().nextInt(available.size());
                do {
                    this.centerX = sideLen * available.get(index)[ 0 ];
                    this.centerY = sideLen * available.get(index)[ 1 ];
                    index = new Random().nextInt(available.size());
                } while (this.centerX < 50 || this.centerY < 50 || this.centerX > Game.WINDOW_WIDTH - 50 || this.centerY > Game.WINDOW_HEIGHT - 50);
            }
        }
    }

    @Override
    public Area getArea() {
        return new Area(this.shipPolygon);
    }

    @Override
    public void kill() {
        Game.playerListener.refresh();
    	rebirthTimer = new GameTimer(4);
    	isDestroyed = true;
    	deathTime = System.currentTimeMillis();
        centerX = Game.WINDOW_WIDTH / 2;
        centerY = Game.WINDOW_HEIGHT / 2;
        angle = 180;
        speedVector = new Vector(0, 0);
        lives--;
        if (lives < 3) {
            try {
                Thread.sleep(DEATH_TIME_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isKilled() {
        return isDestroyed;
    }

    public static void drawLives(Graphics graphics) {
        for (int i = 0; i < lives; i++) {
            int topX = 6 + (int) LENGTH_B * (2 + 3 * i);
            int topY = 105;
            Polygon sign = new Polygon();
            sign.addPoint(topX, topY);
            sign.addPoint(topX - (int) LENGTH_B, topY + (int) LENGTH_A + (int) LENGTH_B);
            sign.addPoint(topX, topY + (int) LENGTH_A);
            sign.addPoint(topX + (int) LENGTH_B, topY + (int) LENGTH_A + (int) LENGTH_B);
            graphics.drawPolygon(sign);
        }
    }
}
