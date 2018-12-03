package com.example.rrota.pain_s;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.rrota.pain_s.R.color.deep_purple;
import static com.example.rrota.pain_s.R.color.yellow;

/**
 * Created by EmilLatypov
 * Активити появляющееся сначала
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private Welcome_manager welcome_manager;
    private int[] layouts;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    Button btn_next,yes,no;
    Button skip;
    private View line;
    private FirebaseAuth mAuth;
    ConstraintLayout llBottomSheet;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView sogl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(WelcomeActivity.this, DrawerActivity.class);
                    startActivity(intent);

                }

            }
        };
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(WelcomeActivity.this, DrawerActivity.class);
            startActivity(intent);
        }



        welcome_manager = new Welcome_manager(this);
        if (!welcome_manager.Check()) {
            welcome_manager.setFirst(false);
            Intent i = new Intent(WelcomeActivity.this, Index.class);
            startActivity(i);
            finish();
        }



        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.Layout_main);
        btn_next = (Button) findViewById(R.id.btn_next);
        skip = (Button) findViewById(R.id.skip);
        llBottomSheet = (ConstraintLayout) findViewById(R.id.bottom_sheet);
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        line=findViewById(R.id.view);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        layouts = new int[] {
                R.layout.welcome_screen1, R.layout.welcome_screen2, R.layout.welcome_screen3
        };
        addBottomDots(0);
        changeStatusBarColor();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);
        sogl = (TextView) findViewById(R.id.soglash);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    llBottomSheet.setVisibility(View.VISIBLE);
                    dotsLayout.removeAllViews();
                    btn_next.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llBottomSheet.setVisibility(View.VISIBLE);
                dotsLayout.removeAllViews();
                btn_next.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            }
        });


    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.yes:
                Intent ih = new Intent(WelcomeActivity.this, Index.class);
                startActivity(ih);
                break;
            case R.id.no:
                moveTaskToBack(true);
                finish();
                System.runFinalizersOnExit(true);
                System.exit(0);
                break;

        }}

    private void addBottomDots(int position) {
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_Active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_Inctive);
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(colorActive[position]);
        }
    }


    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }





    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                btn_next.setText("НАЧАТЬ");
                skip.setVisibility(View.GONE);
            } else {
                btn_next.setText("ДАЛЕЕ");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }
    }
}