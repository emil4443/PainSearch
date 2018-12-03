package com.example.rrota.pain_s;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Created by KamillaKhairullina
 * Тест Живот Женщина
 */

public class StomachWoman extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String TAG = "Head";

    //Получение данных пользователя
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    //Общий список симптомов
    final String name[] = {
            "в левой нижней части брюшины",
            "в правом подреберье",
            "в брюшной полости",
            "в верхней и средней частях живота",
            "болезненные ощущения внизу живота",
            "Тошнота и рвота",
            "Повышенная температура",
            "Озноб",
            "Диарея",
            "Нарушение стула",
            "Продолжительные болезненные менструации",
            "Нарушения менструального цикла",
            "Желтуха (пожелтение кожи и белков глаз)",
            "Изжога",
            "Вздутие живота",
            "Давление в животе",
            "Боль в пояснице",
            "Дрожание рук"
    };

    //Вопросы
    final String question[] = {
            "Боль...",
            "Общие симптомы",
            "Локальные симптомы"
    };

    //Болезни
    final String disease[] = {
            "Дивертикулит",
            "Холецистит",
            "Синдром раздражённой толстой кишки",
            "Язва",
            "Эндометриоз",
            "Фиброма матки"
    };
    byte count[] = new byte[6];

    //Сипмптомы по болезням
    byte d0[] = {
            0,
            7,
            5,
            6
    };
    byte d1[] = {
            6,
            7,
            12,
            1
    };
    byte d2[] = {
            2,
            14,
            9,
            8
    };
    byte d3[] = {
            3,
            13,
            5,
            9
    };
    byte d4[] = {
            4,
            11,
            10,
            12
    };
    byte d5[] = {
            14,
            15,
            16,
            17
    };
    TextView tx, tx2;
    CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9;
    Button btn;
    int c = 1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        for (int i = 0; i < 6; i++) {
            count[i] = 0;
        }

        //Поиск элементов разметки
        tx = (TextView) findViewById(R.id.textView);
        tx2 = (TextView) findViewById(R.id.textView2);
        ch1 = (CheckBox) findViewById(R.id.checkBox3);
        ch2 = (CheckBox) findViewById(R.id.checkBox2);
        ch3 = (CheckBox) findViewById(R.id.checkBox4);
        ch4 = (CheckBox) findViewById(R.id.checkBox5);
        ch5 = (CheckBox) findViewById(R.id.checkBox6);
        ch6 = (CheckBox) findViewById(R.id.checkBox7);
        ch7 = (CheckBox) findViewById(R.id.checkBox8);
        ch8 = (CheckBox) findViewById(R.id.checkBox9);
        ch9 = (CheckBox) findViewById(R.id.checkBox10);

        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(this);
        q1();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Подключение к Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Обработка нажатия клавиши Назад
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent inte = new Intent(this, DrawerActivity.class);
                startActivity(inte);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Проверка наличия данного симптома у всех болезней
    public void find(int index) {
        for (int i = 0; i < 4; i++) {
            if (d0[i] == index) count[0]++;
            if (d1[i] == index) count[1]++;
            if (d2[i] == index) count[2]++;
            if (d3[i] == index) count[3]++;
            if (d4[i] == index) count[4]++;
            if (d5[i] == index) count[5]++;
        }
    }

    //Очистка CheckBox
    public void clear() {
        ch1.setChecked(false);
        ch2.setChecked(false);
        ch3.setChecked(false);
        ch4.setChecked(false);
        ch5.setChecked(false);
        ch6.setChecked(false);
        ch7.setChecked(false);
        ch8.setChecked(false);
        ch9.setChecked(false);
    }

    //Вывод 1 вопроса
    public void q1() {
        tx2.setVisibility(View.GONE);
        ch6.setVisibility(View.GONE);
        ch7.setVisibility(View.GONE);
        ch8.setVisibility(View.GONE);
        ch9.setVisibility(View.GONE);

        tx.setText(question[0]);
        ch1.setText(name[0]);
        ch2.setText(name[1]);
        ch3.setText(name[2]);
        ch4.setText(name[3]);
        ch5.setText(name[4]);
    }

    //Вывод 2 вопроса
    public void q2() {
        ch6.setVisibility(View.VISIBLE);
        ch7.setVisibility(View.VISIBLE);
        tx.setText(question[1]);
        ch1.setText(name[5]);
        ch2.setText(name[6]);
        ch3.setText(name[7]);
        ch4.setText(name[8]);
        ch5.setText(name[9]);
        ch6.setText(name[10]);
        ch7.setText(name[11]);

    }

    //Вывод 3 вопроса
    public void q3() {
        ch6.setVisibility(View.GONE);
        tx.setText(question[2]);
        ch1.setText(name[12]);
        ch2.setText(name[13]);
        ch3.setText(name[14]);
        ch4.setText(name[15]);
        ch5.setText(name[16]);
        ch6.setText(name[17]);
    }

    //Обработка нажатия CheckBox
    public void onClick(View v) {

        if (ch1.isChecked()) {
            find(Arrays.asList(name).indexOf(ch1.getText()));
        }
        if (ch2.isChecked()) {
            find(Arrays.asList(name).indexOf(ch2.getText()));
        }
        if (ch3.isChecked()) {
            find(Arrays.asList(name).indexOf(ch3.getText()));
        }
        if (ch4.isChecked()) {
            find(Arrays.asList(name).indexOf(ch4.getText()));
        }
        if (ch5.isChecked()) {
            find(Arrays.asList(name).indexOf(ch5.getText()));
        }
        if (ch6.isChecked()) {
            find(Arrays.asList(name).indexOf(ch6.getText()));
        }
        if (ch7.isChecked()) {
            find(Arrays.asList(name).indexOf(ch7.getText()));
        }
        if (ch8.isChecked()) {
            find(Arrays.asList(name).indexOf(ch8.getText()));
        }
        if (ch9.isChecked()) {
            find(Arrays.asList(name).indexOf(ch9.getText()));
        }

        //Определение следующего вопроса
        if (c == 1) {
            c++;
            clear();
            q2();
        } else
        if (c == 2) {
            c++;
            clear();
            q3();
        } else
        if (c == 3) {
            c++;
            clear();
            finish();
            max();
        }
    }

    //Вывод результата в Log
    public void finish() {
        for (byte i = 0; i < 6; i++) {
            String s = Integer.toString(count[i]);
            Log.d("myLog", s);
        }
    }
    public void max() {
        byte m = count[0];
        //Поиск наилучшего совпадения
        for (byte i = 1; i < 6; i++)
            if (count[i] > m) m = count[i];
        ch1.setVisibility(View.GONE);
        ch2.setVisibility(View.GONE);
        ch3.setVisibility(View.GONE);
        ch4.setVisibility(View.GONE);
        ch5.setVisibility(View.GONE);
        ch6.setVisibility(View.GONE);
        ch7.setVisibility(View.GONE);
        ch8.setVisibility(View.GONE);
        ch9.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        tx2.setVisibility(View.VISIBLE);

        //Вывод результата
        String s = "Возможные причины:\n ";
        String k = Integer.toString(m);
        Log.d("myLog", k + " max");
        for (byte i = 0; i < 6; i++)
            if (count[i] == m) {
                s += disease[i] + ", ";
                count[i] = -1;
            }

        //Удаление запятой
        StringBuffer stringBuffer = new StringBuffer(s);
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length() - 1);
        s = stringBuffer.toString();

        tx.setText(s);
        tx.setTextSize(30);
        tx2.setPadding(40, 60, 10, 40);

        //Вывод специалистов в зависимости от болезни
        String s2 = "Советуем вам обратиться к терапевту.\n ";

        s2 += "\n \nТакже специалисты:\n ";
        if ((count[0] == -1) || (count[1] == -1) || (count[2] == -1) || (count[3] == -1)) s2 += "- Хирург\n - Гастроэнтеролог\n ";
        if ((count[2] == -1)) s2 += "- Проктолог\n ";
        if ((count[3] == -1) || (count[4] == -1)) s2 += "- Гинеколог\n ";
        tx2.setText(s2);
        tx2.setTextSize(20);
        tx2.setPadding(40, 60, 10, 40);
        final String doc = s2;
        final String userId = user.getUid();

        //Сохранение даты прохождения теста
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy' 'HH:mm");
        String dateString = sdf.format(date);

        writeNewDisease(userId, dateString, s, s2);
    }

    //Добавление результата в бд
    private void writeNewDisease(String userId, String date, String disease, String doctors) {
        String key = mDatabase.child("disease").child(userId).push().getKey();
        Disease dis = new Disease(date, disease, doctors);

        mDatabase.child("disease").child(userId).child(key).setValue(dis);
    }
}