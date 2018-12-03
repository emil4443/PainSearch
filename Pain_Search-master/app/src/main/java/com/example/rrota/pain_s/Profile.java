package com.example.rrota.pain_s;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by EmilLatypov
 * Активити Профиля
 */
public class Profile extends AppCompatActivity implements View.OnClickListener {
    private TextView UserName, UserSurname, editPhoto;

    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;
    private FirebaseAuth mAuth;
    private LinearLayout NameLi, SurnameLi, NameLiEdit, SurnameLiEdit;
    private ImageButton edName, edSurname;
    private Button saName, saSurname;
    private EditText changeName, changeSurname;
    private String name, surname, email;
    private ImageView photo;
    private int ph;



    //Получение данных пользователя
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    private static final String TAG = "Profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Обращение к бд
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());

        //Вывод имени
        UserName = findViewById(R.id.name_lable);
        UserSurname = findViewById(R.id.surname_lable);

        //LinearLayout для отображения имени/фамилии
        NameLi = findViewById(R.id.name_linear);
        SurnameLi = findViewById(R.id.surname_linear);

        //LinearLayout для изменения имени/фамилии
        NameLiEdit = findViewById(R.id.name_linear_edit);
        SurnameLiEdit = findViewById(R.id.surname_linear_edit);

        //ImageButton для изменения имени/фамилии
        edName = findViewById(R.id.edit_name);
        edName.setOnClickListener(this);
        edSurname = findViewById(R.id.edit_surname);
        edSurname.setOnClickListener(this);

        //Button для сохранения имени/фамилии
        saName = findViewById(R.id.save_name);
        saName.setOnClickListener(this);
        saSurname = findViewById(R.id.save_surname);
        saSurname.setOnClickListener(this);

        //Поле ввода нового имени/фамилии
        changeName = findViewById(R.id.input_name_prof);
        changeSurname = findViewById(R.id.input_surname_prof);

        photo = findViewById(R.id.imageView2);
        editPhoto = findViewById(R.id.edit_photo);
        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentActivity(v);
            }
        });
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int)(view.getX() + view.getWidth() / 2);
        int revealY = (int)(view.getY() + view.getHeight() / 2);
        Intent intent = new Intent(this, change_img.class);
        intent.putExtra(change_img.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(change_img.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }


    @Override
    public void onStart() {
        super.onStart();

        //Вывод данных на экран из бд
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name = user.username.toString();
                surname = user.usersurname.toString();
                email = user.email.toString();
                ph = user.photo;
                changePhoto(ph);
                UserName.setText(user.username);
                UserSurname.setText(user.usersurname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Загрузка пользователя: ", databaseError.toException());
                Toast.makeText(Profile.this, "Не удалось загрузить пользователя",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(postListener);

        mUserListener = postListener;


    }

    //Изменение фотографии профиля
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

    //Обработка нажатия кнопки назад
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


    //Обработка нажатия кнопок
    @Override
    public void onClick(View v) {
        String newName, newSurname;
        switch (v.getId()) {
            case R.id.edit_name:
                NameLi.setVisibility(View.GONE);
                NameLiEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.save_name:
                newName = changeName.getText().toString();
                User user1 = new User(surname, newName, email, ph);
                Map < String, Object > userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                NameLiEdit.setVisibility(View.GONE);
                NameLi.setVisibility(View.VISIBLE);
                break;
            case R.id.edit_surname:
                SurnameLi.setVisibility(View.GONE);
                SurnameLiEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.save_surname:

                newSurname = changeSurname.getText().toString();
                user1 = new User(newSurname, name, email, ph);
                userValues = user1.toMap();
                mUserReference.updateChildren(userValues);
                SurnameLiEdit.setVisibility(View.GONE);
                SurnameLi.setVisibility(View.VISIBLE);
                break;
        }
    }
}