package com.example.rrota.pain_s;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by KamillaKhairullina
 * Класс болезней
 */
public class Disease {
    public String date;
    public String disease;
    public String doctors;

    public Disease() {
        //Стандартный конструктор, необходимый для DataSnapshot.getValue(User.class)
    }

    //Конструктор
    public Disease(String date, String disease, String doctors) {
        this.date = date;
        this.disease = disease;
        this.doctors = doctors;
    }

    public Map < String, Object > toMap() {
        HashMap < String, Object > result = new HashMap < > ();
        result.put("date", date);
        result.put("disease", disease);
        result.put("doctors", doctors);
        return result;
    }
}