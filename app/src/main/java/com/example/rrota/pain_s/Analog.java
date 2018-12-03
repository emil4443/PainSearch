package com.example.rrota.pain_s;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created by KamillaKhairullina
 * Аналоги лекарств
 */
public class Analog extends AppCompatActivity implements View.OnClickListener  {

    TextView Name, Price, Subs, DrName, DrPrice;
    ImageView img, imgDr;

    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;
    Cursor userCursor;
    long id = 0;
    String price,an_price,link,an_link;
    LinearLayout danger;
    Button aninf,inf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analog);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Поиск элементов разметки
        Name = findViewById(R.id.an_name);
        Price = findViewById(R.id.an_price);
        Subs = findViewById(R.id.substance);
        img = findViewById(R.id.an_img);
        DrName = findViewById(R.id.dr_name);
        DrPrice = findViewById(R.id.dr_price);
        imgDr = findViewById(R.id.dr_img);
        danger=findViewById(R.id.danger);
        inf=findViewById(R.id.inf);
        inf.setOnClickListener(this);
        aninf=findViewById(R.id.an_inf);
        aninf.setOnClickListener(this);

        //Подключение к SQLite
        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        //Получение id эл-та, который выбрали в ListView
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");
        }

        // получение элемента по id из бд
        userCursor = mDb.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_ID + " =? ", new String[] {
                String.valueOf(id)
        });
        userCursor.moveToFirst();

        //Вывод данных на экран
        DrName.setText(userCursor.getString(1));
        Name.setText(userCursor.getString(5));
        Subs.setText(userCursor.getString(9));
        img.setImageResource(userCursor.getInt(8));
        imgDr.setImageResource(userCursor.getInt(4));
        link=userCursor.getString(2);
        an_link=userCursor.getString(6);

        if (userCursor.getInt(10)==1) danger.setVisibility(View.VISIBLE);
        userCursor.close();
        new JSo().execute();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inf:
                Intent intent = new Intent(getApplicationContext(), DrugsInfo.class);
                intent.putExtra("url", link);
                startActivity(intent);
                break;
            case R.id.an_inf:
                intent = new Intent(getApplicationContext(), DrugsInfo.class);
                intent.putExtra("url", an_link);
                startActivity(intent);
                break;
        }

    }

    class JSo extends AsyncTask<String, String, String> {

        protected String doInBackground(String... urls) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link).get();
                Elements els = doc.select("div[class=price m--mobile_font ]");
                price=els.select("span").text();
                Log.w("Price", price);

                doc = Jsoup.connect(an_link).get();
                els = doc.select("div[class=price m--mobile_font ]");
                an_price=els.select("span").text();
                Log.w("Price", an_price);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            DrPrice.setText(price+" \u20BD");
            Price.setText(an_price+" \u20BD");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Обработка нажатия клавиши Назад
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}