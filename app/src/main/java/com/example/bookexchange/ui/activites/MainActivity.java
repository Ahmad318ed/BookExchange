package com.example.bookexchange.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.MyPagerAdapter;
import com.example.bookexchange.models.Slides;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    LinearLayout splashLayout;
    RelativeLayout slidesLayout;

    MyPagerAdapter adapter;

    MaterialCardView btnSkip;


//    @Override
//    protected void onStart() {
//        super.onStart();
//        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashLayout = findViewById(R.id.splashLayout);
        slidesLayout = findViewById(R.id.slidesLayout);
        btnSkip = findViewById(R.id.btnSkip);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                splashLayout.setVisibility(View.GONE);
                slidesLayout.setVisibility(View.VISIBLE);
                btnSkip.setVisibility(View.VISIBLE);

            }
        },2000);

        runAnimation();

       setViewPager();

       btnSkip.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,CollageActivity.class));
               finish();
           }
       });

    }

    private void runAnimation(){
        Animation a = AnimationUtils.loadAnimation(this , R.anim.splash_anim);
        a.reset();

        splashLayout.clearAnimation();
        splashLayout.startAnimation(a);
    }
    private void runAnimationOut(){
        Animation a = AnimationUtils.loadAnimation(this , R.anim.splash_andim_out);
        a.reset();

        splashLayout.clearAnimation();
        splashLayout.startAnimation(a);
    }


    private void setViewPager(){

        List<Slides> slides = new ArrayList<>();


        slides.add(new Slides(R.drawable.wise_logo_sized,getString(R.string.wise),getString(R.string.wise_desc)));
        slides.add(new Slides(R.drawable.it_logo,getString(R.string.it_collage_slides),getString(R.string.it_desc)));
        slides.add(new Slides(R.drawable.app_logo_sized,getString(R.string.app_name),getString(R.string.app_desc)));




        adapter = new MyPagerAdapter(this);
        adapter.setSlides(slides);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setPaddingRelative(60,0,60,0);

        viewPager.setAdapter(adapter);

        CircleIndicator3 circleIndicator3 = findViewById(R.id.indicator);
        circleIndicator3.setViewPager(viewPager);

    }

}