package com.example.rrota.pain_s;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
/**
 * Created by EmilLatypov
 * Активити выбора профиля
 */
public class PainMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_main);

        //Поиск элементов разметки
        ImageButton bman = (ImageButton) findViewById(R.id.man);
        ImageButton bwoman = (ImageButton) findViewById(R.id.woman);
        bman.setOnClickListener(onClickListener);
        bwoman.setOnClickListener(onClickListener);
    }

    //Обработка нажатия
    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.man:
                    Intent im = new Intent(PainMain.this, MainManActivity.class);
                    startActivity(im);
                    break;
                case R.id.woman:
                    Intent iw = new Intent(PainMain.this, MainWomanActivity.class);
                    startActivity(iw);
                    break;
                default:
                    break;


            }
        }
    };
}