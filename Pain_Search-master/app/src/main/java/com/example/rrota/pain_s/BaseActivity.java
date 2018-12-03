package com.example.rrota.pain_s;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by KamillaKhairullina
 * Запуск ProgressDialog
 */
class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    //Сделать видимым
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                mProgressDialog = new ProgressDialog(this, R.style.MyDialogStyle2);
            } else
                mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Загрузка...");
        }

        mProgressDialog.show();
    }

    //Спрятать
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}