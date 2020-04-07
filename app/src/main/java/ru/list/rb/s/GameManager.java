package ru.list.rb.s;

import java.util.ArrayList;
import java.util.List;

public class GameManager  {
    public static final int ENEMY_CIRCLES_QUANTITY = 10;
    private CanvasView canvasView;
    private static int height;
    private static int width;
    private MainCircle mainCircle;
    private List<EnemyCircle> enemyCircles;

    public GameManager(CanvasView canvasView, int width, int height) {
        GameManager.height = height;
        GameManager.width = width;
        this.canvasView = canvasView;
        initMainCircle();
        initEnemyCircles();
    }

    private void initEnemyCircles() {
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        enemyCircles = new ArrayList<EnemyCircle>();
        for (int i = 0; i < ENEMY_CIRCLES_QUANTITY; i++) {
            EnemyCircle enemyCircle;
            //
            do {
                enemyCircle = EnemyCircle.getRandomCircle();
            } while (enemyCircle.isIntersect(mainCircleArea)); // Создаем круг пока он пересается с главным кругом
            enemyCircles.add(enemyCircle);
        }
        calculateAndSetCirclesColor();
    }

    private void calculateAndSetCirclesColor() {
        for (EnemyCircle circle : enemyCircles) {
            circle.setEnemyOrFoodColorDependsOn(mainCircle);
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public void onDraw() {
      canvasView.drawCircle(mainCircle);
      for (EnemyCircle circle : enemyCircles) {
          canvasView.drawCircle(circle);
      }
    }

    private void initMainCircle() {
        mainCircle = new MainCircle(width / 2, height / 2); // По центру
    }

    public void onTouchEvent(int x, int y) {
        mainCircle.moveMainCircleWhenTouchAt(x, y);
        checkCollisions();
        moveCircles(); // При прикосновении и движении главного круга, остальные круги тоже должны двигаться
    }

    private void checkCollisions() {
        SimpleCircle circleForDelete = null;
        for (EnemyCircle circle : enemyCircles) {
            if (mainCircle.isIntersect(circle)) {
                if (circle.isSmallerThan(mainCircle)) {
                    mainCircle.growRadius(circle); // Съели и растем
                    circleForDelete = circle; // Удаляем съеденный круг
                    calculateAndSetCirclesColor(); // Делаем перерисовку кругов
                    break;
                } else {
                    gameEnd("YOU LOSE!");
                    break;
                }
            }
        }
        if (circleForDelete != null) {
            enemyCircles.remove(circleForDelete);
        }
        if (enemyCircles.isEmpty()) {
            gameEnd("YOU WIN!");
        }
    }

    private void gameEnd(String s) {
        canvasView.showMessage(s); // GameManager сам не должен отображать сообщения, просит канвас вью
        mainCircle.initRadius();
        initEnemyCircles();
        canvasView.redraw();
    }

    private void moveCircles() {
        for (EnemyCircle circle : enemyCircles) {
            circle.moveOneStep();
        }
    }
}
