package ru.list.rb.s;

import android.graphics.Color;

import java.util.Random;

public class EnemyCircle extends SimpleCircle {

    public static final int MIN_ENEMY_CIRCLE_RADIUS = 10;
    public static final int MAX_ENEMY_CIRCLE_RADIUS = 100;
    public static final int ENEMY_COLOR = Color.RED;
    public static final int FOOD_COLOR = Color.rgb(0, 200, 0);
    public static final int RANDOM_SPEED_MAX = 10;
    public static final int RANDOM_MIN_SPEED = 1;
    private int dx;
    private int dy;

    public EnemyCircle(int x, int y, int radius, int dx, int dy) {
        super(x, y, radius);
        this.dx = dx;
        this.dy = dy;
    }

    public static EnemyCircle getRandomCircle() {
        Random random = new Random();
        int x = random.nextInt((GameManager.getWidth())); // От нуля до ширины
        int y = random.nextInt(GameManager.getHeight()); // От нуля до высоты
        // Случайные скорости
        int dx = RANDOM_MIN_SPEED + random.nextInt(RANDOM_SPEED_MAX - RANDOM_MIN_SPEED);
        int dy = RANDOM_MIN_SPEED + random.nextInt(RANDOM_SPEED_MAX - RANDOM_MIN_SPEED);
        int radius = MIN_ENEMY_CIRCLE_RADIUS + random.nextInt(MAX_ENEMY_CIRCLE_RADIUS - MIN_ENEMY_CIRCLE_RADIUS);
        EnemyCircle enemyCircle = new EnemyCircle(x, y, radius, dx, dy);
        return enemyCircle;
    }

    public void setEnemyOrFoodColorDependsOn(MainCircle mainCircle) {
        if (isSmallerThan(mainCircle)) {
            setColor(FOOD_COLOR);
        } else {
            setColor(ENEMY_COLOR);
        }
    }

    protected boolean isSmallerThan(SimpleCircle circle) {
        if (radius < circle.radius) {
            return true;
        }
        return false;
    }

    public void moveOneStep() {
        x += dx;
        y += dy;
        checkBounds();
    }

    private void checkBounds() {
        if (x > GameManager.getWidth() || x < 0) { // Если координата ушла за предел экрана, меняем направление движения
            dx = -dx;
        }
        if (y > GameManager.getHeight() || y < 0) {
            dy = -dy;
        }
    }
}
