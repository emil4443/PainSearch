package com.example.rrota.pain_s;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EmilLatypov
 * Активити входа с фоновой анимацией
 */
public class Index extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private AppCompatButton mSignInButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Создать/скрыть WebView в зависимости от версии
        setContentView(R.layout.activity_index);
        webView = (WebView) findViewById(R.id.webView);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            webView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl("file:///android_asset/www/index.html");
        } else
            webView.setVisibility(View.GONE);


        TextView txt = (TextView) findViewById(R.id.link_signup);
        txt.setOnClickListener(this);

        //Обращение к бд
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //Поиск элементов разметки
        mEmailField = findViewById(R.id.input_email);
        mPasswordField = findViewById(R.id.input_password);
        mSignInButton = findViewById(R.id.btn_login);
        mSignInButton.setOnClickListener(this);
    }

    //Метод для Входа
    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog(); //Включение ProgressDialog до завершения обработки данных

        //Получение данных из EditText
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        //Проверка возможности входа в приложение
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener < AuthResult > () {
                    @Override
                    public void onComplete(@NonNull Task < AuthResult > task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(Index.this, "Проверьте введенные данные",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Вызывается при удачной авторизации
    private void onAuthSuccess(FirebaseUser user) {
        startActivity(new Intent(Index.this, DrawerActivity.class));
        finish();
    }



    //Проверка на корректность введенных данных
    private boolean validateForm() {
        boolean result = true;
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


    //Обработка нажатия кнопок
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_login) {
            signIn();
        } else if (i == R.id.link_signup) {
            startActivity(new Intent(Index.this, MainActivity.class));
        }
    }

    //Обработка нажатия кнопки Назад на панели упревления телефона
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }
}