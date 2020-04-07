package ru.list.rb.s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Убираем заголовок у окна //TODO спрятать заголовок окна, данная фича не работает
        setContentView(R.layout.activity_main);
    }
}
