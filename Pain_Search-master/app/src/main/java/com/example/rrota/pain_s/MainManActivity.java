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
 * Активити Мужчина
 */
public class MainManActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_man);

        //Поиск элементов разметки
        ImageButton bh = (ImageButton) findViewById(R.id.mh);
        ImageButton bn = (ImageButton) findViewById(R.id.mn);
        ImageButton bs = (ImageButton) findViewById(R.id.ms);
        ImageButton bb = (ImageButton) findViewById(R.id.mb);
        ImageButton brs = (ImageButton) findViewById(R.id.mrs);
        ImageButton bls = (ImageButton) findViewById(R.id.mls);
        ImageButton bra = (ImageButton) findViewById(R.id.mra);
        ImageButton bla = (ImageButton) findViewById(R.id.mla);
        ImageButton brf = (ImageButton) findViewById(R.id.mrf);
        ImageButton blf = (ImageButton) findViewById(R.id.mlf);
        ImageButton bbb = (ImageButton) findViewById(R.id.mbb);
        ImageButton bl = (ImageButton) findViewById(R.id.ml);
        ImageButton bf = (ImageButton) findViewById(R.id.mf);
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
                case R.id.mh:
                    Intent ih = new Intent(MainManActivity.this, Head.class);
                    startActivity(ih);
                    break;
                case R.id.mn:
                    ih = new Intent(MainManActivity.this, Neck.class);
                    startActivity(ih);
                    break;
                case R.id.mb:
                    ih = new Intent(MainManActivity.this, Chest.class);
                    startActivity(ih);
                    break;
                case R.id.mls:
                    ih = new Intent(MainManActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.mrs:
                    ih = new Intent(MainManActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.mla:
                    ih = new Intent(MainManActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.mra:
                    ih = new Intent(MainManActivity.this, Arms.class);
                    startActivity(ih);
                    break;
                case R.id.ms:
                    ih = new Intent(MainManActivity.this, StomachMan.class);
                    startActivity(ih);
                    break;
                case R.id.mlf:
                    ih = new Intent(MainManActivity.this, Hands.class);
                    startActivity(ih);
                    break;
                case R.id.mrf:
                    ih = new Intent(MainManActivity.this, Hands.class);
                    startActivity(ih);
                    break;
                case R.id.mbb:
                    ih = new Intent(MainManActivity.this, Thigs.class);
                    startActivity(ih);
                    break;
                case R.id.ml:
                    ih = new Intent(MainManActivity.this, Shank.class);
                    startActivity(ih);
                    break;
                case R.id.mf:
                    ih = new Intent(MainManActivity.this, Feet.class);
                    startActivity(ih);
                    break;

                default:
                    break;

            }
        }
    };
}