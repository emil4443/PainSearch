package com.example.rrota.pain_s;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
/**
 * Created by KamillaKhairullina
 * Класс лекарств
 */
public class Drugs extends AppCompatActivity {
    //Объявим переменные компонентов
    TextView textView,nointernet;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle actBar;
    ImageView nowifi;


    //Переменная для работы с БД
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    Cursor userCursor;
    ListView userList;
    SimpleCursorAdapter userAdapter;

    Cursor cursor;
    Search searching;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drugs);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        searching = new Search(this);

        mDBHelper = new DatabaseHelper(this);

        //Обращение к бд
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("Не удается обновить бд");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        nointernet=findViewById(R.id.nointernet);
        nowifi=findViewById(R.id.no_wifi);

        //Обработка нажатия на элементы списка
        userList = (ListView) findViewById(R.id.list);
        if (!checkNetworkStatus(getApplicationContext())){
            userList.setVisibility(View.GONE);
            nowifi.setVisibility(View.VISIBLE);
            nointernet.setVisibility(View.VISIBLE);
        }
            userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > parent, View view, int position, long id) {
                Log.d("myLog", "Начал");
                Intent intent = new Intent(getApplicationContext(), Analog.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
    public boolean checkNetworkStatus(Context context){

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo nInfo = connMgr.getActiveNetworkInfo();

        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( (wifi.isAvailable() || mobile.isAvailable()) && (nInfo != null) && nInfo.isConnected() ) return true;

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение и получаем данные из бд в виде курсора
        userCursor = mDb.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {
                DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_IMG
        };
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.card,
                userCursor, headers, new int[] {
                R.id.name, R.id.drug
        }, 0);
        userList.setAdapter(userAdapter);

    }

    //Настрока работы строки поиска
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        Log.d("Menu", "Created");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    cursor = searching.getDrugsListByKeyword(s);
                    if (cursor == null) {
                        Toast.makeText(Drugs.this, "Не удалось найти", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Drugs.this, "Найдено " + cursor.getCount() + " лекарств ", Toast.LENGTH_LONG).show();
                    }
                    userAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    cursor = searching.getDrugsListByKeyword(s);
                    if (cursor != null) {
                        userAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

    }



    //Обработка нажатия кнопки назад
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}