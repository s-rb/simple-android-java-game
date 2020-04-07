package ru.list.rb.s;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class CanvasView extends View implements ICanvasView { // канвас вью занимается именно графикой
    private static int width; // Ширина и высота экрана
    private static int height;
    private GameManager gameManager;
    private Paint paint; // На канвасе когда рисуем выбираем кисточку. Класс Paint
    private Canvas canvas;
    private Toast toast; // Сообщения в андроиде

    public CanvasView(Context context, @Nullable AttributeSet attrs) { // Когда CanvasView будет отображаться на экране, у него будет вызываться метод onDraw
        super(context, attrs);
        initWidthAndHeight(context);
        initPaint();
        gameManager = new GameManager(this, width, height);
    } // Чтобы рисовать на экране - наследуем от View

    private void initWidthAndHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE); // Спрашиваем у контекста виндовссервис
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point); //  он не возвращает размер экрана, а меняет значения самой переданной точки
        width = point.x;
        height = point.y; // прямое обращение к полям в несколько раз быстрее, чем через геттеры и сеттеры
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        gameManager.onDraw(canvas);
        this.canvas = canvas;
        gameManager.onDraw();
    }

    private void initPaint() {
        paint = new Paint(); // Инициализируем
        paint.setAntiAlias(true); // Сглаживание
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void drawCircle(SimpleCircle circle) {
        paint.setColor(circle.getColor());
        canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(),
                paint); // Передаем кисточку с помощью которой мы хотим нарисовать круг
        // Рисуем
    }

    @Override
    public void redraw() {
        invalidate();
    }

    @Override
    public void showMessage(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT); // Создаем сообщение
        toast.setGravity(Gravity.CENTER, 0, 0); // Отображаем по центру
        toast.show(); // Отображаем
    }

    @Override // Отслеживаем прикосновения
    public boolean onTouchEvent(MotionEvent event) {
        // коорд касания
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            gameManager.onTouchEvent(x, y);
        }
        invalidate(); // Надо после всех пересчетов. чтобы view себя перерисовала
        return true;
    }

    //TODO сделать recalculateRadius в зависимости от размеров экрана
    // return radius * 768 / width < height ? width : height
}