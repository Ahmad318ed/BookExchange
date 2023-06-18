package com.example.bookexchange.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bookexchange.R;

public class CollageActivity extends AppCompatActivity {

    CardView it,Arts_and_Sciences,Dawah,Sheikh_Noah,Educational_Sciences,Islamic_Architecture,Money_and_Business
            ,Maliki_Hanafi_Shafii,Graduate_Studies,engineering;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        initialization();

        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","it");
                startActivity(intent);


            }
        });

        Arts_and_Sciences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Arts_and_Sciences");
                startActivity(intent);
            }
        });
        Dawah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Dawah");
                startActivity(intent);
            }
        });
        Sheikh_Noah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Sheikh_Noah");
                startActivity(intent);
            }
        });
        Educational_Sciences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Educational_Sciences");
                startActivity(intent);
            }
        });
        Islamic_Architecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Islamic_Architecture");
                startActivity(intent);
            }
        });
        Money_and_Business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Money_and_Business");
                startActivity(intent);
            }
        });
        Maliki_Hanafi_Shafii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Maliki_Hanafi_Shafii");
                startActivity(intent);
            }
        });
        Graduate_Studies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Collages","Graduate_Studies");
                startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}