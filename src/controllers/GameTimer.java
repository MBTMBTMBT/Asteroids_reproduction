package controllers;

public class GameTimer {
    private long zeroTime;
    private long difference;

    public GameTimer(double seconds) {
        this.difference = (long) (seconds * 1000);
        this.zeroTime = System.currentTimeMillis();
    }

    public boolean timeCheck() {
        return System.currentTimeMillis() - this.zeroTime >= this.difference;
    }
}
