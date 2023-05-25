package com.example.bookexchange.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bookexchange.R;

public class CollageActivity extends AppCompatActivity {

    CardView it,Arts_and_Sciences,Dawah,Sheikh_Noah,Educational_Sciences,Islamic_Architecture,Money_and_Business
            ,Maliki_Hanafi_Shafii,Graduate_Studies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        initialization();

        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });

        Arts_and_Sciences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });
        Dawah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });
        Sheikh_Noah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });
        Educational_Sciences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });
        Islamic_Architecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });
        Money_and_Business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });
        Maliki_Hanafi_Shafii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });
        Graduate_Studies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });














    }

    private void initialization() {

        it=findViewById(R.id.it_collage);
        Arts_and_Sciences=findViewById(R.id.Arts_and_Sciences_collage);
        Dawah=findViewById(R.id.Da_wah_collage);
        Sheikh_Noah=findViewById(R.id.sheikh_noah_college);
        Educational_Sciences=findViewById(R.id.Educational_Sciences_college);
        Islamic_Architecture=findViewById(R.id.Arts_and_Islamic_Architecture);
        Money_and_Business=findViewById(R.id.Money_and_Business);
        Maliki_Hanafi_Shafii=findViewById(R.id.Malik_Hanafi_Shafi);
        Graduate_Studies=findViewById(R.id.Graduate_Studies);

    }


}