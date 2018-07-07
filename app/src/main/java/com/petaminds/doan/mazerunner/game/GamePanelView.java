package com.petaminds.doan.mazerunner.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.petaminds.doan.mazerunner.utils.Constants;

public class GamePanelView extends SurfaceView implements GameActionListener {
    private GameThread thread;
    private Player player;
    private Point playerPoint;
    private int backgroundColor = Color.DKGRAY;
    private int width;
    private int height;
    private int score;
    private ObstacleManager obstacleManager;

    public volatile boolean gameover = true;

    public GamePanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GamePanelView(Context context) {
        super(context);
        init();
    }

    public boolean isGameover() {
        return gameover;
    }

    public void init() {
        setFocusable(true);
    }

    @Override
    public void start() {
        gameover = false;
        score = 0;
        player = new Player(new Rect(100, 100, 200, 200), Color.LTGRAY);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 200);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.rgb(139, 195, 74));
        thread = new GameThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void stop() {
        if (thread != null && thread.isAlive()) {
            thread.setRunning(false);
        }
        update();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    public int getScore() {
        return score;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameover) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //case MotionEvent.ACTION_MOVE:
                    if ((int) event.getX() > width / 2) {
                        playerPoint.set(playerPoint.x + player.getRectangle().width() / 2, playerPoint.y);
                    } else if ((int) event.getX() < width / 2) {
                        playerPoint.set(playerPoint.x - player.getRectangle().width() / 2, playerPoint.y);
                    }
                    break;
            }
        }
        return true;
    }

    public void update() {
        synchronized (this) {
            if (!gameover) {
                player.update(playerPoint);
                obstacleManager.update();
                this.score++;
                if (score % 200 == 0) {
                    thread.increaseSpeed();
                }

                if (obstacleManager.collide(player)) {
                    gameover = true;
                    stop();
                }

            }
            this.notify();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(backgroundColor);
        player.draw(canvas);
        obstacleManager.draw(canvas);
    }
}
