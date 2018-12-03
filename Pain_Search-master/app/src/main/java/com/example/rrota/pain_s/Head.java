package com.example.rrota.pain_s;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContacts.Data;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created by KamillaKhairullina
 * Тест Голова
 */
public class Head extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String TAG = "Head";

    //Получение данных пользователя
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    //Общий список симптомов
    final String name[] = {
            "Пульсирующая",
            "Давящая",
            "Стреляющая",
            "Не очень сильная",
            "Длится до 3 суток",
            "Длится до 7 дней",
            "Длится несколько часов",
            "Усиливается утром",
            "В одной половине головы",
            "В области лба и глаза",
            " В темени, области лба, может захватывать лицо, зубы ",
            "Во всей голове",
            "Невротические расстройства",
            "Покраснение и отек лица",
            "Слезоточение глаза",
            "Спазм мышц лица",
            "'Мушки' перед глазами",
            "Шум в ушах",
            "Потеря слуха на одно ухо",
            "Повышение температуры",
            "Насморк",
            "Кашель",
            "Тошнота и рвота",
            "Головокружение",
            "Скачки давления",
            "Плохая переносимость яркого света,шума",
            "Ощущаение эмоционального или физического напряжения ",
            "Ощущение тревоги",
            "Ощущение раздражительности",
            "Снижение концентрации внимания"
    };

    //Вопросы
    final String question[] = {
            "Характер боли",
            "Боль",
            "Боль ощущается",
            "У Вас",
            "Катаральные симптомы",
            "Симптомы"
    };

    //Болезни
    final String disease[] = {
            "Мигрень",
            "Головная боль напряжения (ГБН)",
            "Кластерная головная боль",
            "Головная и лицевая боль из-за невралгии тройничного нерва",
            "Шейный остеохондроз",
            "Грипп или простуда",
            "Повышенное внутричерепное давление",
            "Сотрясение головного мозга",
            "Вегетососудистая дистония",
            "Гипертоническая болезнь",
            "Синусит, фронтит, гайморит"
    };
    byte count[] = new byte[11];

    //Сипмптомы по болезням
    byte d0[] = {
            0,
            4,
            22,
            25
    };
    byte d1[] = {
            1,
            11,
            5,
            26
    };
    byte d2[] = {
            2,
            9,
            14,
            13
    };
    byte d3[] = {
            2,
            10,
            6,
            15
    };
    byte d4[] = {
            16,
            8,
            18,
            17
    };
    byte d5[] = {
            19,
            20,
            21,
            3
    };
    byte d6[] = {
            3,
            7,
            22,
            6
    };
    byte d7[] = {
            23,
            29,
            27,
            28
    };
    byte d8[] = {
            22,
            23,
            24,
            12
    };
    byte d9[] = {
            8,
            19,
            23,
            16
    };
    byte d10[] = {
            8,
            19,
            20,
            1
    };
    TextView tx, tx2;
    CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9;
    Button btn;
    int c = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        for (int i = 0; i < 11; i++) {
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
            if (d6[i] == index) count[6]++;
            if (d7[i] == index) count[7]++;
            if (d8[i] == index) count[8]++;
            if (d9[i] == index) count[9]++;
            if (d10[i] == index) count[10]++;
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
        ch5.setVisibility(View.GONE);
        ch6.setVisibility(View.GONE);
        ch7.setVisibility(View.GONE);
        ch8.setVisibility(View.GONE);
        ch9.setVisibility(View.GONE);

        tx.setText(question[0]);
        ch1.setText(name[0]);
        ch2.setText(name[1]);
        ch3.setText(name[2]);
        ch4.setText(name[3]);
    }

    //Вывод 2 вопроса
    public void q2() {
        tx.setText(question[1]);
        ch1.setText(name[4]);
        ch2.setText(name[5]);
        ch3.setText(name[6]);
        ch4.setText(name[7]);
    }

    //Вывод 3 вопроса
    public void q3() {
        tx.setText(question[2]);
        ch1.setText(name[8]);
        ch2.setText(name[9]);
        ch3.setText(name[10]);
        ch4.setText(name[11]);
    }

    //Вывод 4 вопроса
    public void q4() {
        ch5.setVisibility(View.VISIBLE);
        tx.setText(question[3]);
        ch1.setText(name[25]);
        ch2.setText(name[26]);
        ch3.setText(name[27]);
        ch4.setText(name[28]);
        ch5.setText(name[29]);
    }

    //Вывод 5 вопроса
    public void q5() {
        tx.setText(question[4]);
        ch1.setText(name[14]);
        ch2.setText(name[19]);
        ch3.setText(name[20]);
        ch4.setText(name[21]);
    }

    //Вывод 6 вопроса
    public void q6() {
        ch6.setVisibility(View.VISIBLE);
        ch7.setVisibility(View.VISIBLE);
        ch8.setVisibility(View.VISIBLE);
        ch9.setVisibility(View.VISIBLE);
        tx.setText(question[5]);
        ch1.setText(name[12]);
        ch2.setText(name[13]);
        ch3.setText(name[15]);
        ch4.setText(name[16]);
        ch5.setText(name[17]);
        ch6.setText(name[18]);
        ch7.setText(name[22]);
        ch8.setText(name[23]);
        ch9.setText(name[24]);
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
            q4();
        } else
        if (c == 4) {
            c++;
            clear();
            q5();
        } else
        if (c == 5) {
            c++;
            clear();
            q6();
        } else
        if (c == 6) {
            c++;
            clear();
            finish();
            max();
        }
    }

    //Вывод результата в Log
    public void finish() {
        for (byte i = 0; i < 11; i++) {
            String s = Integer.toString(count[i]);
            Log.d("myLog", s);
        }
    }
    public void max() {
        byte m = count[0];
        //Поиск наилучшего совпадения
        for (byte i = 1; i < 11; i++)
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
        for (byte i = 0; i < 11; i++)
            if (count[i] == m) {
                s += disease[i] + ", ";
                count[i] = -1;
            }

        //Удаление запятой
        StringBuffer stringBuffer = new StringBuffer(s);
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length() - 1);
        s = stringBuffer.toString();

        tx.setText(s);
        tx.setTextSize(20);
        tx2.setPadding(40, 60, 10, 40);

        //Вывод специалистов в зависимости от болезни
        String s2 = "Советуем вам обратиться к терапевту.\n ";
        boolean f = false;
        if ((count[0] == -1) || (count[1] == -1) || (count[2] == -1) || (count[3] == -1) || (count[4] == -1) || (count[6] == -1) || (count[7] == -1) || (count[8] == -1)) {
            if (f) s2 += "- Невролог\n ";
            else {
                s2 += "\n Также специалисты:\n ";
                s2 += "- Невролог\n ";
                f = true;
            }
        }
        if ((count[4] == -1)) {
            if (f) s2 += "- Вертеброневролог\n ";
            else {
                s2 += "\n Также специалисты:\n ";
                s2 += "- Вертеброневролог\n ";
                f = true;
            }
        }
        if ((count[7] == -1)) {
            if (f) s2 += "- Травматолог\n - Нейрохирург\n";
            else {
                s2 += "\n Также специалисты:\n ";
                s2 += "- Травматолог\n - Нейрохирург\n ";
                f = true;
            }
        }
        if ((count[9] == -1)) {
            if (f) s2 += "- Кардиолог\n ";
            else {
                s2 += "\n Также специалисты:\n ";
                s2 += "- Кардиолог\n ";
                f = true;
            }
        }
        if ((count[10] == -1)) {
            if (f) s2 += "- Отоларинголог\n ";
            else {
                s2 += "\n Также специалисты:\n" +
                        ", ";
                s2 += "- Отоларинголог\n ";
                f = true;
            }
        }
        tx2.setText(s2);
        tx2.setTextSize(20);
        tx2.setPadding(40, 60, 10, 40);
        final String d = s;
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