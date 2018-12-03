package com.example.rrota.pain_s;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DrugsInfo extends BaseActivity {
    private WebView webView;
    int count = 0;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url");
        }

        //Загрузка WebView
        setContentView(R.layout.drug_information);
        webView = (WebView) findViewById(R.id.dr_info);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new DrugsInfo.MyWebviewClient());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        webView.loadUrl(url);
    }

    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //Проверка загрузки страницы WebView
            if (Uri.parse(url).getHost().equals("apteka.ru")) {
                return false;
            } else {
                //Открытие в браузере, если не удается загрузить
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

        }

        //ProgressDialogue
        ProgressDialog pd = null;

        //Запуск
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (count == 0) {
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    pd = new ProgressDialog(DrugsInfo.this, R.style.MyDialogStyle1);
                } else
                    pd = new ProgressDialog(DrugsInfo.this);
                pd.setTitle("Подождите");
                pd.setMessage("Идет загрузка страницы..");

                pd.show();
                Log.w("initializte ", url);
                count++;
            }

            super.onPageStarted(view, url, favicon);
        }

        //Отключение
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.w("initializte ", "stop");
            pd.dismiss();
            count = 0;
            super.onPageFinished(view, url);
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Обработка нажатия клавиши Назад
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                hideProgressDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}