package com.example.rrota.pain_s;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by KamillaKhairullina
 * Активити "История болезни"
 */
public class History extends AppCompatActivity {
    private LinkedList < Disease > diseases;
    private RecyclerView rv;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    ImageView im;
    TextView tx;

    RVAdapter adapter;
    private static final String TAG = "History";

    //Получение данных пользователя
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        //Обращение к данным бд
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("disease").child(user.getUid());
        Log.d("initializte ", mUserReference.getKey());
        im = findViewById(R.id.imageView3);
        tx = findViewById(R.id.textView);


    }



    @Override
    public void onStart() {
        super.onStart();
        diseases = new LinkedList < > ();
        Log.d("initializte ", "start");
        //получение данных из Firebase
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Change ", "started");
                Iterable < DataSnapshot > iterator = dataSnapshot.getChildren();
                Iterator < DataSnapshot > iterator1 = iterator.iterator();
                diseases.clear();
                while (iterator1.hasNext()) {
                    DataSnapshot next = iterator1.next();
                    Disease value = next.getValue(Disease.class);
                    diseases.addFirst(value);
                }
                adapter.notifyDataSetChanged();

                if (diseases.size() != 0) {
                    im.setVisibility(View.GONE);
                    tx.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Не удалось загрузить пользователя", databaseError.toException());
            }
        };

        mUserReference.addValueEventListener(postListener);

        mUserListener = postListener;

        adapter = new RVAdapter(diseases);

        //Добавление данных из адаптера в RecyclerView
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //Создание верхней панели управления
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //Обработка нажатия кнопок на верхней панели
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_remove:
                mUserReference.setValue(null);
                im.setVisibility(View.VISIBLE);
                tx.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}