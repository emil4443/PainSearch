package com.example.rrota.pain_s;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by EmilLatypov
 * Обработчик первых активити при входе
 */
public class Welcome_manager {
    SharedPreferences sh_pr;
    SharedPreferences.Editor editor;
    Context context;
    public Welcome_manager(Context context) {
        sh_pr = context.getSharedPreferences("first", 0);
        editor = sh_pr.edit();
    }
    public void setFirst(boolean isFirst) {
        editor.putBoolean("check", isFirst);
        editor.commit();
    }
    public boolean Check() {
        return sh_pr.getBoolean("check", true);
    }
}