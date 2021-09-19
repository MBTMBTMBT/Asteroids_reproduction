package gamingElements;

import controllers.Game;
import controllers.GameTimer;
import controllers.FPSController;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Random;

public class Alien implements Hittable, Drawable {
	private static final int REBORN_TIME = 4;
	private static final int SHOOTING_ERROR_RANGE = 60;

	public static Alien alien;
	Random random = new Random();
	private Size size = random.nextBoolean() ? Size.LARGE : Size.SMALL;
	private boolean isDestroyed = false;
	private int direction;
	private int[] objectPosition = new int[2];
	private int objectDirection;
	private Polygon polygon;
	private FPSController verticalMovementController = new FPSController(30);  // to control the vertical speed
	private GameTimer shootTimer = new GameTimer(8);
	private GameTimer rebornTimer;

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

	public Alien() {
		alien = this;
		this.createNew();
		this.kill();
	}

	private void createNew() {
		this.isDestroyed = false;
		this.size = random.nextBoolean() ? Size.LARGE : Size.SMALL;
		this.objectDirection = random.nextBoolean() ? 1 : -1;
		this.direction = (random.nextInt(3) -2)/2;
		if (this.size == Size.LARGE) {
			this.objectPosition[ 0 ] = objectDirection == 1 ? -30 : Game.WINDOW_WIDTH + 30;
		} else {
			this.objectPosition[ 0 ] = objectDirection == 1 ? -15 : Game.WINDOW_WIDTH + 15;
		}
		this.objectPosition[ 1 ] = (int) (random.nextDouble() * (Game.WINDOW_HEIGHT) * 0.6);
	}

	@Override
	public void kill() {
		this.isDestroyed = true;
		this.rebornTimer = new GameTimer(REBORN_TIME);
	}

	@Override
	public boolean isKilled() {
		return this.isDestroyed;
	}

	private void move() {
		this.objectPosition[0] += objectDirection;
		if (objectPosition[0] % (Game.WINDOW_WIDTH / 5) == 0) {
			direction = (random.nextInt(5) - 2);
		}
		if (this.verticalMovementController.timeCheck()) {
			this.objectPosition[1] += direction;
		}
		if (this.objectPosition[1] > Game.WINDOW_HEIGHT - 20) {
			this.objectPosition[1] = 0;
			direction = 3;
		} else if (this.objectPosition[1] < 0) {
			this.objectPosition[1] = Game.WINDOW_HEIGHT - 20;
			direction = -3;
		}
		if (this.size == Size.LARGE) {
			if (this.objectPosition[ 0 ] > Game.WINDOW_WIDTH + 30 || this.objectPosition[ 0 ] < -30) {
				this.kill();
			}
		} else {
			if (this.objectPosition[ 0 ] > Game.WINDOW_WIDTH + 15 || this.objectPosition[ 0 ] < -15) {
				this.kill();
			}
		}
	}

	private void shoot() {
		if (this.shootTimer.timeCheck()) {
			this.shootTimer = new GameTimer(3);
			int shootingAngle;
			int shootingPointX;
			int shootingPointY;
			if (this.size == Size.LARGE) {
				shootingAngle = random.nextInt(180) - 90;
				if (MyShip.positionY < this.objectPosition[1]) {
					shootingAngle -= 180;
				}
				shootingPointX = (int) (this.objectPosition[0] + 30 * Math.sin(Math.toRadians(shootingAngle)));
				shootingPointY = (int) (this.objectPosition[1] + 30 * Math.cos(Math.toRadians(shootingAngle)));
			} else {
				double arcTanValue = Math.atan((MyShip.positionX - this.objectPosition[0]) / (MyShip.positionY - this.objectPosition[1]));
				shootingAngle = (int) Math.toDegrees(arcTanValue);
				if (MyShip.positionY > this.objectPosition[1]) {
					shootingPointX = (int) (this.objectPosition[0] + 15 * Math.sin(arcTanValue));
					shootingPointY = (int) (this.objectPosition[1] + 15 * Math.cos(arcTanValue));
				} else {
					shootingAngle -= 180;
					shootingPointX = (int) (this.objectPosition[0] - 15 * Math.sin(arcTanValue));
					shootingPointY = (int) (this.objectPosition[1] - 15 * Math.cos(arcTanValue));
				}
			}
			shootingAngle += (random.nextInt(SHOOTING_ERROR_RANGE) - SHOOTING_ERROR_RANGE / 2);
			new Bullet(shootingAngle, shootingPointX, shootingPointY, false);
		}
	}

	private void getShape() {
		if (this.size == Size.LARGE) {
			/* big ship */
			this.pointAX = objectPosition[ 0 ] + 6;
			this.pointAY = objectPosition[ 1 ] - 18;
			this.pointBX = objectPosition[ 0 ] - 6;
			this.pointBY = objectPosition[ 1 ] - 18;
			this.pointCX = objectPosition[ 0 ] - 15;
			this.pointCY = objectPosition[ 1 ] - 6;
			this.pointDX = objectPosition[ 0 ] - 30;
			this.pointDY = objectPosition[ 1 ];
			this.pointEX = objectPosition[ 0 ] - 12;
			this.pointEY = objectPosition[ 1 ] + 6;
			this.pointFX = objectPosition[ 0 ] + 12;
			this.pointFY = objectPosition[ 1 ] + 6;
			this.pointGX = objectPosition[ 0 ] + 30;
			this.pointGY = objectPosition[ 1 ];
			this.pointHX = objectPosition[ 0 ] + 15;
			this.pointHY = objectPosition[ 1 ] - 6;
		} else {
			/* small ship */
			this.pointAX = (objectPosition[0] + 3);
			this.pointAY = (objectPosition[1] - 9);
			this.pointBX = (objectPosition[0] - 3);
			this.pointBY = (objectPosition[1] - 9);
			this.pointCX = (objectPosition[0] - 7.5);
			this.pointCY = (objectPosition[1] - 3);
			this.pointDX = (objectPosition[0] - 15);
			this.pointDY = (objectPosition[1]);
			this.pointEX = (objectPosition[0] - 6);
			this.pointEY = (objectPosition[1] + 3);
			this.pointFX = (objectPosition[0] + 6);
			this.pointFY = (objectPosition[1] + 3);
			this.pointGX = (objectPosition[0] + 15);
			this.pointGY = (objectPosition[1]);
			this.pointHX = (objectPosition[0] + 7.5);
			this.pointHY = (objectPosition[1] - 3);
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
	}

	@Override
	public void drawMe(Graphics graphics) {
		if (this.isDestroyed && this.rebornTimer.timeCheck()) {
			this.createNew();
		}
		if (! this.isDestroyed) {
			graphics.setColor(Color.CYAN);
			this.move();
			this.shoot();
			this.getShape();
			graphics.drawPolygon(this.polygon);
			graphics.drawLine((int) pointHX, (int) pointHY, (int) pointCX, (int) pointCY);
			graphics.drawLine((int) pointGX, (int) pointGY, (int) pointDX, (int) pointDY);
		}
	}

	@Override
	public Area getArea() {
			return new Area(this.polygon);
	}
}
