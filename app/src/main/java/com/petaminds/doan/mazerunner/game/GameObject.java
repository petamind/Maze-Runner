package com.petaminds.doan.mazerunner.game;

import android.graphics.Canvas;

public interface GameObject {
    void draw(Canvas canvas);

    void update();
}
