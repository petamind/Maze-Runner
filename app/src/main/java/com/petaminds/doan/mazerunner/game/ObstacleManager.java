package com.petaminds.doan.mazerunner.game;

import android.graphics.Canvas;

import com.petaminds.doan.mazerunner.utils.Constants;

import java.util.ArrayList;

public class ObstacleManager implements GameObject {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        obstacles = new ArrayList<>();
        startTime = System.currentTimeMillis();

        populateObstacles();
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_WIDTH / 4;
        while (currY < 0) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }

    }

    public boolean collide(Player player) {
        for (Obstacle ob : obstacles) {
            if (ob.collide(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);

        float speed = Constants.SCREEN_HEIGHT / 10000.0f;
        for (Obstacle ob :
                obstacles) {
            ob.increaseY(speed * elapsedTime);
        }

        if (obstacles.get(obstacles.size() - 1).getRectangle().top + obstacleHeight >= Constants.SCREEN_HEIGHT) {
            int startX = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            int startY = obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap;
            obstacles.add(0, new Obstacle(obstacleHeight, color, startX, startY,
                    playerGap));
            obstacles.remove(obstacles.size() - 1);
        }
        startTime = System.currentTimeMillis();
    }
}
