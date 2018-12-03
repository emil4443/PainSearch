package com.example.rrota.pain_s;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by EmilLatypov
 * Работа бокового меню
 */
public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;
    private ImageView photo;
    private int ph;

    //Получение данных пользователя
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    private static final String TAG = "DrawerActivity";


    //Создание бокового меню
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        photo = findViewById(R.id.imageView);

        //Обращение к Firebase
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());

        //Поиск элементов разметки
        final TextView inp_name = (TextView) findViewById(R.id.nav_user_name);
        final TextView inp_em = (TextView) findViewById(R.id.nav_user_email);

        //Вывод данных из Firebase
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                inp_name.setText(user.usersurname + " " + user.username);
                inp_em.setText(user.email);
                ph = user.photo;
                changePhoto(ph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mUserReference.addValueEventListener(postListener);

        mUserListener = postListener;
        return super.onCreatePanelMenu(featureId, menu);
    }

    //Обновление фото по данным из Firebase
    private void changePhoto(int ph) {
        switch (ph) {
            case 1:
                photo.setImageResource(R.drawable.user_1);
                break;
            case 2:
                photo.setImageResource(R.drawable.user_2);
                break;
            case 3:
                photo.setImageResource(R.drawable.user_3);
                break;
            case 4:
                photo.setImageResource(R.drawable.user_4);
                break;
            case 5:
                photo.setImageResource(R.drawable.user_5);
                break;
            case 6:
                photo.setImageResource(R.drawable.user_6);
                break;
            case 7:
                photo.setImageResource(R.drawable.user_7);
                break;
            case 8:
                photo.setImageResource(R.drawable.user_8);
                break;
            case 9:
                photo.setImageResource(R.drawable.user_9);
                break;
            case 10:
                photo.setImageResource(R.drawable.user_10);
                break;
            case 11:
                photo.setImageResource(R.drawable.user_11);
                break;
            case 12:
                photo.setImageResource(R.drawable.user_12);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.main, new MainFragment());


    }


    //Обработка нажатия кнопки Назад на панели упревления телефона
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    //Обработка нажатия элементов бокового меню
    private void displaySelectedScreen(int id, MainFragment mainFragment) {
        Fragment fragment = null;
        switch (id) {
            case R.id.main:
                fragment = new MainFragment();
                break;
            case R.id.pain:
                fragment = new PainFragment();
                break;
            case R.id.drug:
                startActivity(new Intent(this, Drugs.class));
                finish();
                break;
            case R.id.off:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Index.class));
                finish();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id, new MainFragment());
        return true;

    }


}