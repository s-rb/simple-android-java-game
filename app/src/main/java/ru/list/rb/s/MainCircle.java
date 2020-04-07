package ru.list.rb.s;

import android.graphics.Color;

public class MainCircle extends SimpleCircle
{
    public static final int INIT_RADIUS = 50;
    public static final int SPEED_OF_CIRCLE_MOVE = 60;
    public static final int MAIN_CIRCLE_COLOR = Color.BLUE;

    public MainCircle(int x, int y) {
        super(x, y, INIT_RADIUS);
        setColor(MAIN_CIRCLE_COLOR);
    }


    public void moveMainCircleWhenTouchAt(int x1, int y1) {
        int dx = (x1 - x) * SPEED_OF_CIRCLE_MOVE / GameManager.getWidth(); // 30 Услования скорость
        int dy = (y1 - y) * SPEED_OF_CIRCLE_MOVE / GameManager.getHeight();
        x += dx;
        y += dy;
    }

    public void initRadius() {
        radius = INIT_RADIUS;
    }

    public void growRadius(SimpleCircle circle) {
        // увеличиваем свой радиус на радиус съеденного круга
        // pi * newR ^ 2 = pi * r^2 + pi * Reat ^ 2
        // newR = sqrt(r^2 + Reat^2)
        radius = (int) Math.sqrt(Math.pow(radius, 2) + Math.pow(circle.radius, 2));
    }
}
