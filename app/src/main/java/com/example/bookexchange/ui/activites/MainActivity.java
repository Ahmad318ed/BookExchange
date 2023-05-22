package com.example.bookexchange.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookexchange.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout splashLayout;


    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashLayout = findViewById(R.id.splashLayout);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },1500);

        runAnimation();

    }

    private void runAnimation(){
        Animation a = AnimationUtils.loadAnimation(this , R.anim.splash_anim);
        a.reset();

        splashLayout.clearAnimation();
        splashLayout.startAnimation(a);
    }
}