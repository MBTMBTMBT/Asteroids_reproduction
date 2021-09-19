package gamingElements;

import controllers.Game;

import java.awt.*;
import java.awt.geom.Area;
import java.util.LinkedList;
import java.util.Random;

public class SpaceRocks implements Hittable, Drawable {
    public static LinkedList<SpaceRocks> spaceRocks = new LinkedList<>();

    private Random random = new Random();
    private boolean killed;
    private double[] objectPos = new double[2];
    private Vector movementVector;
    private Color [] RANDOM = {Color.lightGray,Color.pink,Color.orange,Color.WHITE};
    private int randomNumber = (int) (Math.random()*4);
    private Color coLor = RANDOM[ randomNumber ];
    private boolean type;
    private Size size;
    private Polygon polygon;

    private double pointAX;
    private double pointAY;
    private double pointBX;
    private double pointBY;
    private double pointCX;
    private double pointCY;
    private double pointDX;
    private double pointDY;
    private double pointEX;
    private double pointEY;
    private double pointFX;
    private double pointFY;
    private double pointGX;
    private double pointGY;
    private double pointHX;
    private double pointHY;
    private double pointIX;
    private double pointIY;
    private double pointJX;
    private double pointJY;
    private double pointKX;
    private double pointKY;
    private double pointLX;
    private double pointLY;
    private double pointMX;
    private double pointMY;
    private double afterBX;
    private double afterBY;

    /* Random constructor for large rocks */
    public SpaceRocks(Size size) {
        this.size = size;
        // 1. ensure the entering side
        // 2. ensure the exact position
        // 3. ensure the entering direction
        int enteringSide = random.nextInt(4);
        double speedModule = random.nextDouble() + 0.3;
        double angle;
        switch (enteringSide) {  // 0 UP; 1 DOWN; 2 LEFT; 3 RIGHT
            case 0:
                angle = random.nextInt(120) - 60;
                this.movementVector = Vector.toVector(angle, speedModule);
                this.objectPos[1] = -50;
                this.objectPos[0] = random.nextDouble() * Game.WINDOW_WIDTH * 0.8 + Game.WINDOW_WIDTH * 0.1;
                break;
            case 1:
                angle = random.nextInt(120) + 120;
                this.movementVector = Vector.toVector(angle, speedModule);
                this.objectPos[1] = Game.WINDOW_HEIGHT + 50;
                this.objectPos[0] = random.nextDouble() * Game.WINDOW_WIDTH * 0.8 + Game.WINDOW_WIDTH * 0.1;
                break;
            case 2:
                angle = random.nextInt(120) - 120;
                this.movementVector = Vector.toVector(angle, speedModule);
                this.objectPos[1] = random.nextDouble() * Game.WINDOW_HEIGHT * 0.8 + Game.WINDOW_HEIGHT * 0.1;
                this.objectPos[0] = -50;
                break;
            default:
                angle = random.nextInt(120) - 180;
                this.movementVector = Vector.toVector(angle, speedModule);
                this.objectPos[1] = random.nextDouble() * Game.WINDOW_HEIGHT * 0.8 + Game.WINDOW_HEIGHT * 0.1;
                this.objectPos[0] = Game.WINDOW_WIDTH + 50;
        }
        this.killed = false;
        type = random.nextBoolean();
        SpaceRocks.spaceRocks.add(this);
    }
    /* for mid and small rocks which starts form same position */
    public SpaceRocks(Size size, double positionX, double positionY) {
        this.size = size;
        this.objectPos[0] = positionX;
        this.objectPos[1] = positionY;
        double angle;
        do {
            angle = random.nextInt(360);
        } while ((angle <= 105 && angle >= 75) || (angle <= 285 && angle >= 255) || (angle <= 15 && angle >= -15) || (angle <= 195 && angle >= 165));
        double speedModule = random.nextDouble() + 0.2;
        if (this.size == Size.MID) {
            speedModule *= random.nextDouble() * 0.2 + 1;
        } else {
            speedModule *= random.nextDouble() * 0.45 + 1.5;
        }
        this.movementVector = Vector.toVector(angle, speedModule);
        this.killed = false;
        type = random.nextBoolean();
        SpaceRocks.spaceRocks.add(this);
    }

    public void objectMove() {
        this.objectPos[0] += this.movementVector.displacementX;
        this.objectPos[1] += this.movementVector.displacementY;
        switch (this.size) {
            case SMALL:
                if (this.objectPos[ 1 ] > Game.WINDOW_HEIGHT + 15) {
                    this.objectPos[ 1 ] = -5;
                } else if (this.objectPos[ 1 ] < -5) {
                    this.objectPos[ 1 ] = Game.WINDOW_HEIGHT + 15;
                } else if (this.objectPos[ 0 ] > Game.WINDOW_WIDTH + 20) {
                    this.objectPos[ 0 ] = -20;
                } else if (this.objectPos[ 0 ] < -20) {
                    this.objectPos[ 0 ] = Game.WINDOW_WIDTH + 20;
                }
                break;
            case MID:
                if (this.objectPos[ 1 ] > Game.WINDOW_HEIGHT + 20) {
                    this.objectPos[ 1 ] = -10;
                } else if (this.objectPos[ 1 ] < -10) {
                    this.objectPos[ 1 ] = Game.WINDOW_HEIGHT + 20;
                } else if (this.objectPos[ 0 ] > Game.WINDOW_WIDTH + 30) {
                    this.objectPos[ 0 ] = -20;
                } else if (this.objectPos[ 0 ] < -20) {
                    this.objectPos[ 0 ] = Game.WINDOW_WIDTH + 30;
                }
                break;
            default:
                if (this.objectPos[ 1 ] > Game.WINDOW_HEIGHT + 60) {
                    this.objectPos[ 1 ] = -40;
                } else if (this.objectPos[ 1 ] < -40) {
                    this.objectPos[ 1 ] = Game.WINDOW_HEIGHT + 60;
                } else if (this.objectPos[ 0 ] > Game.WINDOW_WIDTH + 60) {
                    this.objectPos[ 0 ] = -60;
                } else if (this.objectPos[ 0 ] < -60) {
                    this.objectPos[ 0 ] = Game.WINDOW_WIDTH + 60;
                }
                break;
        }
    }

    private void setSmallRock() {
        if (this.type) {
            this.pointAX = objectPos[ 0 ];
            this.pointAY = objectPos[ 1 ];
            this.pointBX = objectPos[ 0 ] + 8;
            this.pointBY = objectPos[ 1 ] - 8;
            this.pointCX = objectPos[ 0 ];
            this.pointCY = objectPos[ 1 ] - 16;
            this.pointDX = objectPos[ 0 ] - 8;
            this.pointDY = objectPos[ 1 ] - 8;
            this.pointEX = objectPos[ 0 ] - 16;
            this.pointEY = objectPos[ 1 ] - 8;
            this.pointFX = objectPos[ 0 ] - 16;
            this.pointFY = objectPos[ 1 ];
            this.pointGX = objectPos[ 0 ] - 8;
            this.pointGY = objectPos[ 1 ];
            this.pointHX = objectPos[ 0 ] - 8;
            this.pointHY = objectPos[ 1 ] + 8;
            this.pointIX = objectPos[ 0 ];
            this.pointIY = objectPos[ 1 ] + 8;
            this.pointJX = objectPos[ 0 ] + 8;
            this.pointJY = objectPos[ 1 ] + 4;
        } else {
            this.pointAX = objectPos[ 0 ];
            this.pointAY = objectPos[ 1 ];
            this.pointBX = objectPos[ 0 ] + 8;
            this.pointBY = objectPos[ 1 ] - 8;
            this.pointCX = objectPos[ 0 ];
            this.pointCY = objectPos[ 1 ] - 16;
            this.pointDX = objectPos[ 0 ] - 4;
            this.pointDY = objectPos[ 1 ] - 8;
            this.pointEX = objectPos[ 0 ] - 8;
            this.pointEY = objectPos[ 1 ] - 16;
            this.pointFX = objectPos[ 0 ] - 16;
            this.pointFY = objectPos[ 1 ] - 12;
            this.pointGX = objectPos[ 0 ] - 8;
            this.pointGY = objectPos[ 1 ] - 4;
            this.pointHX = objectPos[ 0 ] - 16;
            this.pointHY = objectPos[ 1 ];
            this.pointIX = objectPos[ 0 ] - 8;
            this.pointIY = objectPos[ 1 ] + 8;
            this.pointJX = objectPos[ 0 ];
            this.pointJY = objectPos[ 1 ] + 8;
        }
        polygon = new Polygon();
        polygon.addPoint((int) this.pointAX, (int) this.pointAY);
        polygon.addPoint((int) this.pointBX, (int) this.pointBY);
        polygon.addPoint((int) this.pointCX, (int) this.pointCY);
        polygon.addPoint((int) this.pointDX, (int) this.pointDY);
        polygon.addPoint((int) this.pointEX, (int) this.pointEY);
        polygon.addPoint((int) this.pointFX, (int) this.pointFY);
        polygon.addPoint((int) this.pointGX, (int) this.pointGY);
        polygon.addPoint((int) this.pointHX, (int) this.pointHY);
        polygon.addPoint((int) this.pointIX, (int) this.pointIY);
        polygon.addPoint((int) this.pointJX, (int) this.pointJY);
    }

    private void setMediumRock() {
         if (this.type) {
             this.pointAX = objectPos[ 0 ];
             this.pointAY = objectPos[ 1 ];
             this.pointBX = objectPos[ 0 ] + 8;
             this.pointBY = objectPos[ 1 ] + 8;
             this.pointCX = objectPos[ 0 ] + 24;
             this.pointCY = objectPos[ 1 ];
             this.pointDX = objectPos[ 0 ] + 40;
             this.pointDY = objectPos[ 1 ] + 16;
             this.pointEX = objectPos[ 0 ] + 24;
             this.pointEY = objectPos[ 1 ] + 24;
             this.pointFX = objectPos[ 0 ] + 32;
             this.pointFY = objectPos[ 1 ] + 32;
             this.pointGX = objectPos[ 0 ] + 16;
             this.pointGY = objectPos[ 1 ] + 48;
             this.pointHX = objectPos[ 0 ] - 16;
             this.pointHY = objectPos[ 1 ] + 48;
             this.pointIX = objectPos[ 0 ] - 16;
             this.pointIY = objectPos[ 1 ] + 16;
             polygon = new Polygon();
             polygon.addPoint((int) this.pointAX, (int) this.pointAY);
             polygon.addPoint((int) this.pointBX, (int) this.pointBY);
             polygon.addPoint((int) this.pointCX, (int) this.pointCY);
             polygon.addPoint((int) this.pointDX, (int) this.pointDY);
             polygon.addPoint((int) this.pointEX, (int) this.pointEY);
             polygon.addPoint((int) this.pointFX, (int) this.pointFY);
             polygon.addPoint((int) this.pointGX, (int) this.pointGY);
             polygon.addPoint((int) this.pointHX, (int) this.pointHY);
             polygon.addPoint((int) this.pointIX, (int) this.pointIY);
         } else {
             this.pointAX = objectPos[ 0 ];
             this.pointAY = objectPos[ 1 ];
             this.pointBX = objectPos[ 0 ] + 16;
             this.pointBY = objectPos[ 1 ] + 16;
             this.pointCX = objectPos[ 0 ] + 32;
             this.pointCY = objectPos[ 1 ];
             this.pointDX = objectPos[ 0 ] + 16;
             this.pointDY = objectPos[ 1 ] - 8;
             this.pointEX = objectPos[ 0 ] + 32;
             this.pointEY = objectPos[ 1 ] - 16;
             this.pointFX = objectPos[ 0 ] + 16;
             this.pointFY = objectPos[ 1 ] - 40;
             this.pointGX = objectPos[ 0 ];
             this.pointGY = objectPos[ 1 ] - 32;
             this.pointHX = objectPos[ 0 ] - 16;
             this.pointHY = objectPos[ 1 ] - 40;
             this.pointIX = objectPos[ 0 ] - 32;
             this.pointIY = objectPos[ 1 ] - 32;
             this.pointJX = objectPos[ 0 ] - 16;
             this.pointJY = objectPos[ 1 ] - 16;
             this.pointKX = objectPos[ 0 ] - 32;
             this.pointKY = objectPos[ 1 ];
             this.pointLX = objectPos[ 0 ] - 16;
             this.pointLY = objectPos[ 1 ] + 16;
             polygon = new Polygon();
             polygon.addPoint((int) this.pointAX, (int) this.pointAY);
             polygon.addPoint((int) this.pointBX, (int) this.pointBY);
             polygon.addPoint((int) this.pointCX, (int) this.pointCY);
             polygon.addPoint((int) this.pointDX, (int) this.pointDY);
             polygon.addPoint((int) this.pointEX, (int) this.pointEY);
             polygon.addPoint((int) this.pointFX, (int) this.pointFY);
             polygon.addPoint((int) this.pointGX, (int) this.pointGY);
             polygon.addPoint((int) this.pointHX, (int) this.pointHY);
             polygon.addPoint((int) this.pointIX, (int) this.pointIY);
             polygon.addPoint((int) this.pointJX, (int) this.pointJY);
             polygon.addPoint((int) this.pointKX, (int) this.pointKY);
             polygon.addPoint((int) this.pointLX, (int) this.pointLY);
         }
    }

    private void setLargeRock() {
        if (this.type) {
            this.pointAX = objectPos[ 0 ];
            this.pointAY = objectPos[ 1 ];
            this.pointBX = objectPos[ 0 ] + 60;
            this.pointBY = objectPos[ 1 ] - 30;
            this.afterBX = objectPos[ 0 ] + 15;
            this.afterBY = objectPos[ 1 ] - 40;
            this.pointCX = objectPos[ 0 ] + 60;
            this.pointCY = objectPos[ 1 ] - 75;
            this.pointDX = objectPos[ 0 ] + 10;
            this.pointDY = objectPos[ 1 ] - 90;
            this.pointEX = objectPos[ 0 ];
            this.pointEY = objectPos[ 1 ] - 75;
            this.pointFX = objectPos[ 0 ] - 30;
            this.pointFY = objectPos[ 1 ] - 90;
            this.pointGX = objectPos[ 0 ] - 60;
            this.pointGY = objectPos[ 1 ] - 60;
            this.pointHX = objectPos[ 0 ] - 60;
            this.pointHY = objectPos[ 1 ] - 30;
            this.pointIX = objectPos[ 0 ] - 30;
            this.pointIY = objectPos[ 1 ] - 30;
            this.pointJX = objectPos[ 0 ] - 45;
            this.pointJY = objectPos[ 1 ];
            polygon = new Polygon();
            polygon.addPoint((int) this.pointAX, (int) this.pointAY);
            polygon.addPoint((int) this.pointBX, (int) this.pointBY);
            polygon.addPoint((int) this.afterBX, (int) this.afterBY);
            polygon.addPoint((int) this.pointCX, (int) this.pointCY);
            polygon.addPoint((int) this.pointDX, (int) this.pointDY);
            polygon.addPoint((int) this.pointEX, (int) this.pointEY);
            polygon.addPoint((int) this.pointFX, (int) this.pointFY);
            polygon.addPoint((int) this.pointGX, (int) this.pointGY);
            polygon.addPoint((int) this.pointHX, (int) this.pointHY);
            polygon.addPoint((int) this.pointIX, (int) this.pointIY);
            polygon.addPoint((int) this.pointJX, (int) this.pointJY);
        } else {
            this.pointAX = objectPos[ 0 ];
            this.pointAY = objectPos[ 1 ];
            this.pointBX = objectPos[ 0 ] + 30;
            this.pointBY = objectPos[ 1 ] - 30;
            this.pointCX = objectPos[ 0 ];
            this.pointCY = objectPos[ 1 ] - 60;
            this.pointDX = objectPos[ 0 ] + 30;
            this.pointDY = objectPos[ 1 ] - 75;
            this.pointEX = objectPos[ 0 ] + 30;
            this.pointEY = objectPos[ 1 ] - 120;
            this.pointFX = objectPos[ 0 ];
            this.pointFY = objectPos[ 1 ] - 90;
            this.pointGX = objectPos[ 0 ] - 30;
            this.pointGY = objectPos[ 1 ] - 120;
            this.pointHX = objectPos[ 0 ] - 60;
            this.pointHY = objectPos[ 1 ] - 105;
            this.pointIX = objectPos[ 0 ] - 90;
            this.pointIY = objectPos[ 1 ] - 75;
            this.pointJX = objectPos[ 0 ] - 90;
            this.pointJY = objectPos[ 1 ] - 60;
            this.pointKX = objectPos[ 0 ] - 60;
            this.pointKY = objectPos[ 1 ] - 45;
            this.pointLX = objectPos[ 0 ] - 60;
            this.pointLY = objectPos[ 1 ] - 15;
            this.pointMX = objectPos[ 0 ] - 30;
            this.pointMY = objectPos[ 1 ];
            polygon = new Polygon();
            polygon.addPoint((int) this.pointAX, (int) this.pointAY);
            polygon.addPoint((int) this.pointBX, (int) this.pointBY);
            polygon.addPoint((int) this.pointCX, (int) this.pointCY);
            polygon.addPoint((int) this.pointDX, (int) this.pointDY);
            polygon.addPoint((int) this.pointEX, (int) this.pointEY);
            polygon.addPoint((int) this.pointFX, (int) this.pointFY);
            polygon.addPoint((int) this.pointGX, (int) this.pointGY);
            polygon.addPoint((int) this.pointHX, (int) this.pointHY);
            polygon.addPoint((int) this.pointIX, (int) this.pointIY);
            polygon.addPoint((int) this.pointJX, (int) this.pointJY);
            polygon.addPoint((int) this.pointKX, (int) this.pointKY);
            polygon.addPoint((int) this.pointLX, (int) this.pointLY);
            polygon.addPoint((int) this.pointMX, (int) this.pointMY);
        }
    }

    public void getShape_fallingStone() {
        switch (this.size) {
            case SMALL:
                setSmallRock();
                break;
            case MID:
                setMediumRock();
                break;
            default:
                setLargeRock();
        }
    }

    @Override
    public void drawMe(Graphics graphics) {
        graphics.setColor(coLor);
        this.objectMove();
        this.getShape_fallingStone();
        graphics.drawPolygon(this.polygon);
    }

    @Override
    public Area getArea() {
        return new Area(this.polygon);
    }

    @Override
    public void kill() {
        this.killed = true;
    }

    @Override
    public boolean isKilled() {
        return this.killed;
    }

    public Size getSize() {
        return this.size;
    }
    
    public double getPositionX() {
        return this.objectPos[0];
    }

    public double getPositionY() {
        return this.objectPos[1];
    }

    public static void drawSpaceRocks(Graphics graphics) {
        for (int i = 0; i < SpaceRocks.spaceRocks.size(); i++) {
            try {
                SpaceRocks each = SpaceRocks.spaceRocks.get(i);
                if (each.killed) {
                    Size size = each.getSize();
                    double x = each.getPositionX();
                    double y = each.getPositionY();
                    switch (size) {
                        case LARGE:
                            for (int j = 0; j < 3; j++) {
                                new SpaceRocks(Size.MID, x, y);
                            }
                            break;
                        case MID:
                            for (int j = 0; j < 3; j++) {
                                new SpaceRocks(Size.SMALL, x, y);
                            }
                            break;
                    }
                    SpaceRocks.spaceRocks.remove(i);
                    continue;
                }
                each.drawMe(graphics);
            } catch (Exception e) {
                break;
            }
        }
    }
}
