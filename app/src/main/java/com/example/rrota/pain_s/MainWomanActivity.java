package com.example.rrota.pain_s;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
/**
 * Created by KamillaKhairullina
 * Активити Женщина
 */
public class MainWomanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_woman);

        //Поиск элементов разметки
        ImageButton bh = (ImageButton) findViewById(R.id.wh);
        ImageButton bn = (ImageButton) findViewById(R.id.wn);
        ImageButton bs = (ImageButton) findViewById(R.id.ws);
        ImageButton bb = (ImageButton) findViewById(R.id.wb);
        ImageButton brs = (ImageButton) findViewById(R.id.wrs);
        ImageButton bls = (ImageButton) findViewById(R.id.wls);
        ImageButton bra = (ImageButton) findViewById(R.id.wra);
        ImageButton bla = (ImageButton) findViewById(R.id.wla);
        ImageButton brf = (ImageButton) findViewById(R.id.wrf);
        ImageButton blf = (ImageButton) findViewById(R.id.wlf);
        ImageButton bbb = (ImageButton) findViewById(R.id.wbb);
        ImageButton bl = (ImageButton) findViewById(R.id.wl);
        ImageButton bf = (ImageButton) findViewById(R.id.wf);
        bh.setOnClickListener(onClickListener);
        bn.setOnClickListener(onClickListener);
        bs.setOnClickListener(onClickListener);
        bb.setOnClickListener(onClickListener);
        brs.setOnClickListener(onClickListener);
        bls.setOnClickListener(onClickListener);
        bra.setOnClickListener(onClickListener);
        bla.setOnClickListener(onClickListener);
        brf.setOnClickListener(onClickListener);
        blf.setOnClickListener(onClickListener);
        bbb.setOnClickListener(onClickListener);
        bl.setOnClickListener(onClickListener);
        bf.setOnClickListener(onClickListener);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //Обработка нажатия кнопок
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Обработка нажатия на различные части тела
    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String t;
            switch (v.getId()) {
                case R.id.wh:
                    Intent ih = new Intent(MainWomanActivity.this, Head.class);
                    startActivity(ih);
                    break;
                case R.id.wn:
                    ih = new Intent(MainWomanActivity.this, Neck.class);
                    startActivity(ih);
                    break;
                case R.id.wb:
                    ih = new Intent(MainWomanActivity.this, Chest.class);
                    startActivity(ih);
                    break;
                case R.id.wls:
                    ih = new Intent(MainWomanActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.wrs:
                    ih = new Intent(MainWomanActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.wla:
                    ih = new Intent(MainWomanActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.wra:
                    ih = new Intent(MainWomanActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.ws:
                    ih = new Intent(MainWomanActivity.this, StomachWoman.class);
                    startActivity(ih);
                    break;
                case R.id.wlf:
                    ih = new Intent(MainWomanActivity.this, Hands.class);
                    startActivity(ih);
                    break;
                case R.id.wrf:
                    ih = new Intent(MainWomanActivity.this, Hands.class);
                    startActivity(ih);
                    break;
                case R.id.wbb:
                    ih = new Intent(MainWomanActivity.this, Thigs.class);
                    startActivity(ih);
                    break;
                case R.id.wl:
                    ih = new Intent(MainWomanActivity.this, Shank.class);
                    startActivity(ih);
                    break;
                case R.id.wf:
                    ih = new Intent(MainWomanActivity.this, Feet.class);
                    startActivity(ih);
                    break;

                default:
                    break;

            }
        }
    };
}