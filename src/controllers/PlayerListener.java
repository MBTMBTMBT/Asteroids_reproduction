package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean space;
    private boolean shift;
    private boolean N;
    private boolean H;
    private boolean X;
    private boolean M;
    private boolean I;

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.left = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = true;
                break;
            case KeyEvent.VK_UP:
                this.up = true;
                break;
            case KeyEvent.VK_DOWN:
                this.down = true;
                break;
            case KeyEvent.VK_SPACE:
                this.space = true;
                break;
            case 16:   // 16 is shift
                this.shift = true;
                break;
            case KeyEvent.VK_N:
                this.N = true;
                break;
            case KeyEvent.VK_H:
                this.H = true;
                break;
            case KeyEvent.VK_X:
                this.X = true;
                break;
            case KeyEvent.VK_M:
                this.M = true;
                break;
            case KeyEvent.VK_I:
                this.I = true;
                break;
        }
        //System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.left = false;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = false;
                break;
            case KeyEvent.VK_UP:
                this.up = false;
                break;
            case KeyEvent.VK_DOWN:
                this.down = false;
                break;
            case KeyEvent.VK_SPACE:
                this.space = false;
                break;
            case 16:   // 16 is shift
                this.shift = false;
                break;
            case KeyEvent.VK_N:
                this.N = false;
                break;
            case KeyEvent.VK_H:
                this.H = false;
                break;
            case KeyEvent.VK_X:
                this.X = false;
                break;
            case KeyEvent.VK_M:
                this.M = false;
                break;
            case KeyEvent.VK_I:
                this.I = false;
                break;
        }
        //System.out.println(e.getKeyCode());
    }

    public void refresh() {
        this.N = false;
        this.H = false;
        this.X = false;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.shift = false;
        this.space = false;
        this.M = false;
        this.I = false;
    }

    public boolean isLeft() {
        return this.left;
    }
    public boolean isRight() {
        return this.right;
    }
    public boolean isUp() {
        return this.up;
    }
    public boolean isDown() {
        return this.down;
    }
    public boolean isSpace() {
        return this.space;
    }
    public boolean isShift() {
        return this.shift;
    }
    public boolean isN() {
        return this.N;
    }
    public boolean isH() {
        return this.H;
    }
    public boolean isX() {
        return this.X;
    }
    public boolean isM() {
        return this.M;
    }
    public boolean isI() {
        return this.I;
    }

    /*
    public static void main(String[] args) {
        JFrame window = new JFrame();
        PlayerListener playerListener = new PlayerListener();
        window.addKeyListener(playerListener);
        window.setSize(900, 900);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
     */
}
