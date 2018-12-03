package com.example.rrota.pain_s;

import android.content.Intent;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by EmilLatypov
 * Активити с регистрацией
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String TAG = "SignInActivity";
    private EditText mSurnameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;
    private Button mSignUpButton;
    private TextInputLayout mTextInputLayout;
    private static final int MIN_TEXT_LENGTH = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt = (TextView) findViewById(R.id.link_login);
        txt.setOnClickListener(this);


        //Обращение к бд
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //Поиск элементов разметки
        mSurnameField = findViewById(R.id.input_surname);
        mNameField = findViewById(R.id.input_name);
        mEmailField = findViewById(R.id.input_email);
        mPasswordField = findViewById(R.id.input_password);
        mSignUpButton = findViewById(R.id.btn_signup);
        mTextInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        mSignUpButton.setOnClickListener(this);
    }

    //Регистрация
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog(); //Включение ProgressDialog до завершения обработки данных
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        //Попытка создания нового пользователя
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener < AuthResult > () {
                    @Override
                    public void onComplete(@NonNull Task < AuthResult > task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(MainActivity.this, "Регистрация провалена",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Проверка корректности введенных данных
    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(mSurnameField.getText().toString())) {
            mSurnameField.setError("Обязательно");
            result = false;
        } else {
            mSurnameField.setError(null);
        }

        if (TextUtils.isEmpty(mNameField.getText().toString())) {
            mNameField.setError("Обязательно");
            result = false;
        } else {
            mNameField.setError(null);
        }
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Обязательно");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Обязательно");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    //Метод, вызываемый при удачном создании пользователя
    private void onAuthSuccess(FirebaseUser user) {
        String username = mNameField.getText().toString();
        String usersurname = mSurnameField.getText().toString();

        writeNewUser(user.getUid(), usersurname, username, user.getEmail());

        startActivity(new Intent(MainActivity.this, DrawerActivity.class));
        finish();
    }

    //Создание нового пользователя и сохранение его данных в бд
    private void writeNewUser(String userId, String surname, String name, String email) {
        User user = new User(surname, name, email, 1);

        mDatabase.child("users").child(userId).setValue(user);
    }

    //Обраотка нажатия кнопок
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_signup) {
            signUp();
        } else if (i == R.id.link_login) {
            onBackPressed();
        }
    }
}