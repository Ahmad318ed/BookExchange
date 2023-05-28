package com.example.bookexchange.ui.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.makeramen.roundedimageview.RoundedImageView;

import java.net.URI;
import java.net.URL;

public class ViewPostActivity extends AppCompatActivity {

    TextView tv_username, tv_user_email, tv_book_name, tv_book_edition, tv_book_price, tv_book_collage, tv_book_details, tv_book_states;
    ImageView user_image;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        inflateComponents();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        Post post = (Post) getIntent().getSerializableExtra("post");


        String username = post.getBookSellerName();
        String userEmail = post.getBookSellerEmail();
        String userId = post.getBookSellerId();



        Glide.with(ViewPostActivity.this).load(user.getPhotoUrl()).fitCenter().centerCrop().into(user_image);
        tv_username.setText(username);
        tv_user_email.setText(userEmail);
        tv_book_name.setText(post.getBookName());
        tv_book_edition.setText(post.getBookEdition());
        tv_book_collage.setText(post.getBookCollege());
        tv_book_price.setText(post.getBookPrice());
        tv_book_states.setText(post.getBookStates());
        tv_book_details.setText(post.getBookDetails());


    }

    private void inflateComponents() {

        user_image = findViewById(R.id.user_image);
        tv_username = findViewById(R.id.username_view);
        tv_user_email = findViewById(R.id.user_email_view);
        tv_book_name = findViewById(R.id.book_name_view);
        tv_book_collage = findViewById(R.id.book_collage_view);
        tv_book_details = findViewById(R.id.book_details_view);
        tv_book_edition = findViewById(R.id.book_edition_view);
        tv_book_states = findViewById(R.id.book_states_view);
        tv_book_price = findViewById(R.id.book_price_view);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}