package controllers;

import gamingElements.*;
import recorder.MarkRecorder;

import java.awt.geom.Area;
import java.util.LinkedList;

public class ElementsController {
    public static ElementsController elementsController;
    public MyShip myShip;
    public Alien alien;

    public ElementsController(int rocks, boolean hasAlien) {
        SpaceRocks.spaceRocks = new LinkedList<>();
        this.myShip = MyShip.myShip;
        if (hasAlien) {
            new Alien();
            this.alien = Alien.alien;
        } else {
            this.alien = null;
        }
        for (int i = 0; i < rocks; i++) {
            new SpaceRocks(Size.LARGE);
        }
        elementsController = this;
    }

    public static boolean noElements() {
        return SpaceRocks.spaceRocks.isEmpty() && (Alien.alien == null || Alien.alien.isKilled());
    }

    public void collisionJudgement() {
        Area common;
        try {
            if (alien != null) {
                common = myShip.getArea();
                common.intersect(alien.getArea());
                if (!common.isEmpty() && !alien.isKilled() && !myShip.isKilled()) {
                    System.out.println("HIT!");
                    alien.kill();
                    myShip.kill();
                }
            }
        } catch (Exception ignore) {
        }
        try {
            for (int i = 0; i < Bullet.bullets.size(); i++) {
                Bullet eachBullet = Bullet.bullets.get(i);
                if (eachBullet.isKilled()) {
                    continue;
                }
                try {
                    if (alien != null) {
                        common = eachBullet.getArea();
                        common.intersect(alien.getArea());
                        if (!common.isEmpty() && !alien.isKilled()) {
                            System.out.println("HIT!");
                            MarkRecorder.addScore(MarkRecorder.ALIEN_SCORE);
                            eachBullet.kill();
                            alien.kill();
                            MarkRecorder.aliens++;
                            continue;
                        }
                        common = eachBullet.getArea();
                        common.intersect(myShip.getArea());
                        if (!common.isEmpty() && !myShip.isKilled()) {
                            System.out.println("HIT!");
                            eachBullet.kill();
                            myShip.kill();
                        }
                    }
                } catch (Exception ignore) {
                }
                for (int j = 0; j < SpaceRocks.spaceRocks.size(); j++) {
                    SpaceRocks eachRock = SpaceRocks.spaceRocks.get(j);
                    common = eachBullet.getArea();
                    common.intersect(eachRock.getArea());
                    if (!common.isEmpty() && !eachRock.isKilled()) {
                        System.out.println("HIT!");
                        if (eachBullet.isByPlayer()) {
                            switch (eachRock.getSize()) {
                                case SMALL:
                                    MarkRecorder.addScore(MarkRecorder.SMALL_ROCK);
                                    MarkRecorder.smallRocks++;
                                    break;
                                case MID:
                                    MarkRecorder.addScore(MarkRecorder.MID_ROCK);
                                    MarkRecorder.midRocks++;
                                    break;
                                default:
                                    MarkRecorder.addScore(MarkRecorder.LARGE_ROCK);
                                    MarkRecorder.largeRocks++;
                            }
                        }
                        eachBullet.kill();
                        eachRock.kill();
                    }
                }
            }
            for (int j = 0; j < SpaceRocks.spaceRocks.size(); j++) {
                SpaceRocks eachRock = SpaceRocks.spaceRocks.get(j);
                if (alien != null) {
                    common = alien.getArea();
                    common.intersect(eachRock.getArea());
                    if (!common.isEmpty() && !eachRock.isKilled() && !alien.isKilled()) {
                        System.out.println("HIT!");
                        alien.kill();
                        eachRock.kill();
                    }
                }
                common = myShip.getArea();
                common.intersect(eachRock.getArea());
                if (!common.isEmpty() && !eachRock.isKilled() && !myShip.isKilled()) {
                    System.out.println("HIT!");
                    myShip.kill();
                    eachRock.kill();
                }
            }
        } catch (Exception ignored) {
        }
    }
}
