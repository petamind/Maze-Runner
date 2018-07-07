package com.petaminds.doan.mazerunner.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import com.petaminds.doan.mazerunner.game.GameObject;

public class Player implements GameObject {

    private Rect rectangle;
    private int color;

    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(new RectF(rectangle), 50f, 50f, paint);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2,
                point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }
}
