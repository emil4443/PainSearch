package com.example.rrota.pain_s;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by EmilLatypov
 * Выбор картинки профиля
 */
public class change_img extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;
    public ImageView image;
    private int revealX;
    private int revealY;
    private String name, surname, email;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    private static final String TAG = "change_image";

    //Получаение данных пользователя
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_img);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        final Intent intent = getIntent();
        changeStatusBarColor();
        rootLayout = findViewById(R.id.root_layout);

        //Фото профиля
        ImageView u1 = findViewById(R.id.us_1);
        u1.setOnClickListener(this);
        ImageView u2 = findViewById(R.id.us_2);
        u2.setOnClickListener(this);
        ImageView u3 = findViewById(R.id.us_3);
        u3.setOnClickListener(this);
        ImageView u4 = findViewById(R.id.us_4);
        u4.setOnClickListener(this);
        ImageView u5 = findViewById(R.id.us_5);
        u5.setOnClickListener(this);
        ImageView u6 = findViewById(R.id.us_6);
        u6.setOnClickListener(this);
        ImageView u7 = findViewById(R.id.us_7);
        u7.setOnClickListener(this);
        ImageView u8 = findViewById(R.id.us_8);
        u8.setOnClickListener(this);
        ImageView u9 = findViewById(R.id.us_9);
        u9.setOnClickListener(this);
        ImageView u10 = findViewById(R.id.us_10);
        u10.setOnClickListener(this);
        ImageView u11 = findViewById(R.id.us_11);
        u11.setOnClickListener(this);
        ImageView u12 = findViewById(R.id.us_12);
        u12.setOnClickListener(this);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

        //Обращение к данным в Firebase
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());
    }


    //Получение данных из Firebase
    @Override
    public void onStart() {
        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name = user.username.toString();
                surname = user.usersurname.toString();
                email = user.email.toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Загрузка пользователя:", databaseError.toException());
                Toast.makeText(change_img.this, "Не удается найти пользователя",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(userListener);
        mUserListener = userListener;

    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float)(Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            // Создание анимации для view (начальный радиус 0)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());
            // Сделать view видимым и начать анимацию
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float)(Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);
            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });
            circularReveal.start();
        }
    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //Обработка нажатия на картинки для профиля и добавление новых данных в Firebase
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.us_1:
                User user1 = new User(surname, name, email, 1);
                Map < String, Object > userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_2:
                user1 = new User(surname, name, email, 2);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_3:
                user1 = new User(surname, name, email, 3);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_4:
                user1 = new User(surname, name, email, 4);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_5:
                user1 = new User(surname, name, email, 5);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_6:
                user1 = new User(surname, name, email, 6);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_7:
                user1 = new User(surname, name, email, 7);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_8:
                user1 = new User(surname, name, email, 8);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);

                onBackPressed();
                break;
            case R.id.us_9:
                user1 = new User(surname, name, email, 9);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_10:
                user1 = new User(surname, name, email, 10);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_11:
                user1 = new User(surname, name, email, 11);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
            case R.id.us_12:
                user1 = new User(surname, name, email, 12);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                onBackPressed();
                break;
        }
    }
}
