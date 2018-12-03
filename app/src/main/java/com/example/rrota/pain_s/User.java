package com.example.rrota.pain_s;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by KamillaKhairullina
 * Класс пользователя
 */
public class User {
    public String usersurname;
    public String username;
    public String email;
    public int photo;

    public User() {
        //Стандартный конструктор, необходимый для DataSnapshot.getValue(User.class)
    }

    public User(String usersurname, String username, String email, int photo) {
        this.usersurname = usersurname;
        this.username = username;
        this.email = email;
        this.photo = photo;
    }

    @Exclude
    public Map < String, Object > toMap() {
        HashMap < String, Object > result = new HashMap < > ();
        result.put("usersurname", usersurname);
        result.put("username", username);
        result.put("email", email);
        result.put("photo", photo);

        return result;
    }
}