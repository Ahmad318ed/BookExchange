package com.example.bookexchange.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookexchange.R;
import com.example.bookexchange.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewRequestActivity extends AppCompatActivity {

    TextView tv_username,tv_user_email,tv_book_name,tv_book_edition,tv_book_price,tv_book_collage,tv_book_details,tv_book_states;
    ImageView user_image;
    FirebaseAuth auth;
    FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        inflateComponents();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        Request request=(Request) getIntent().getSerializableExtra("request");



         String username=request.getBookSellerName();
         String userEmail=request.getBookSellerEmail();
         String userId=request.getBookSellerId();


         user_image.setImageURI(user.getPhotoUrl());
        tv_username.setText(username);
        tv_user_email.setText(userEmail);
        tv_book_name.setText(request.getBookName());
        tv_book_edition.setText(request.getBookEdition());
        tv_book_collage.setText(request.getBookCollege());
        tv_book_price.setText(request.getBookPrice());
        tv_book_states.setText(request.getBookStates());
        tv_book_details.setText(request.getBookDetails());






    }

    private void inflateComponents() {

        user_image=findViewById(R.id.user_image_request);
        tv_username=findViewById(R.id.username_view_request);
        tv_user_email=findViewById(R.id.user_email_view_request);
        tv_book_name=findViewById(R.id.book_name_view_request);
        tv_book_collage=findViewById(R.id.book_collage_view_request);
        tv_book_details=findViewById(R.id.book_details_view_request);
        tv_book_edition=findViewById(R.id.book_edition_view_request);
        tv_book_states=findViewById(R.id.book_states_view_request);
        tv_book_price=findViewById(R.id.book_price_view_request);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}