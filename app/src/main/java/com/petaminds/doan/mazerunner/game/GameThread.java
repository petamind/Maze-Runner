package com.petaminds.doan.mazerunner.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private int speed = 30;
    private int delay;
    private final SurfaceHolder surfaceHolder;
    private GamePanelView gamePanel;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, GamePanelView gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void increaseSpeed() {
        this.speed++;
        this.delay = 1000 / 30;
    }

    @Override
    public void run() {
        while (running) {

            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                if (delay > 0) {
                    this.sleep(delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
