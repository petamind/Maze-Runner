package com.petaminds.doan.mazerunner.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.petaminds.doan.mazerunner.utils.Constants;

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {

        this.rectangle = new Rect(0, startY, startX, startY + rectHeight);
        this.rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
        this.color = color;
    }

    public boolean collide(Player player) {
        return rectangle.intersect(player.getRectangle())
                || rectangle2.intersect(player.getRectangle());
    }

    public void increaseY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        canvas.drawRoundRect(new RectF(rectangle), 5f, 5f, paint);
        canvas.drawRoundRect(new RectF(rectangle2), 5f, 5f, paint);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }
}
