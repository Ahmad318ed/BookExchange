package com.example.bookexchange.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAOProfileInfo;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.Profile_info;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.net.URI;
import java.net.URL;

public class ViewPostActivity extends AppCompatActivity {

    TextView tv_username, tv_user_email, tv_book_name, tv_book_edition, tv_book_price, tv_book_collage, tv_book_details, tv_book_states;
    ImageView user_image;
    FirebaseAuth auth;
    FirebaseUser user;

    LinearLayout btnGoToProfile;

    DAOProfileInfo daoProfile;
    Profile_info load_profile_info;
    String userId;
    private ImageView btnFacebook, btnInstagram, btnWhatsApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        inflateComponents();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        daoProfile = new DAOProfileInfo();

        Post post = (Post) getIntent().getSerializableExtra("post");


        String username = post.getBookSellerName();
        String userEmail = post.getBookSellerEmail();
        // declared the user id out of onCreate to use it when i get contact info from Profile_info
        userId = post.getBookSellerId();


        // To send the user to Profile screen
        ///////////////////////////////////////

        btnGoToProfile = findViewById(R.id.btnGoToProfile);
        btnGoToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPostActivity.this,Profile.class);
                intent.putExtra("user_id",userId);
                startActivity(intent);
            }
        });



        daoProfile.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot profilesnap : snapshot.getChildren()) {

                    Profile_info profile = profilesnap.getValue(Profile_info.class);



                    if (userId.equals(profile.getUserId())) {

                       if (profile.getImg() != null){
                           Glide.with(ViewPostActivity.this).load(profile.getImg()).fitCenter().centerCrop().into(user_image);

                       }else{
                           Glide.with(ViewPostActivity.this).load(getDrawable(R.drawable.default_profile_img)).fitCenter().centerCrop().into(user_image);

                       }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ///////////////////////////////////////////////////////



        tv_username.setText(username);
        tv_user_email.setText(userEmail);
        tv_book_name.setText(post.getBookName());
        tv_book_edition.setText(post.getBookEdition());
        tv_book_collage.setText(post.getBookCollege());
        tv_book_price.setText(post.getBookPrice());
        tv_book_states.setText(post.getBookStates());
        tv_book_details.setText(post.getBookDetails());

        setContacts();

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
        btnFacebook = findViewById(R.id.btn_imgFacebook);
        btnInstagram = findViewById(R.id.btn_imgInstagram);
        btnWhatsApp = findViewById(R.id.btn_imgWhatsApp);

    }


    private void setContacts() {
        daoProfile.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot profilesnap : snapshot.getChildren()) {

                    load_profile_info = profilesnap.getValue(Profile_info.class);

                    //first if to check if the user is the same user that u take his data
                    //Second if to check the contact info if it is empty or not then show it to the users



                    if (userId.equals(load_profile_info.getUserId())) {


                        String WhatsApp =load_profile_info.getWhatsAppNum();
                        String Facebook = load_profile_info.getFacebook_link();
                        String Instagram = load_profile_info.getInstagram_link();



                        //Second (3 ifs) to check the contact info if it is empty or not then show it to the users

                        //check facebook link
                        if (load_profile_info.getFacebook_link().equals("")) {
                            btnFacebook.setClickable(false);
                            btnFacebook.setAlpha(0.3f);

                        }else if (!load_profile_info.getFacebook_link().contains("https://")) {
                            btnFacebook.setAlpha(0.5f);
                            btnFacebook.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(ViewPostActivity.this, "invalid facebook link", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            try {

                                btnFacebook.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        GoToURL(Facebook);
                                    }
                                });
                            } catch (Exception e) {
                                System.out.println("heeeeeelp" + e);
                            }

                        }




                        //check Instagram link

                        if (load_profile_info.getInstagram_link().equals("")) {
                            btnInstagram.setClickable(false);
                            btnInstagram.setAlpha(0.3f);

                        } else if (!load_profile_info.getInstagram_link().contains("https://")) {
                            btnInstagram.setAlpha(0.5f);
                            btnInstagram.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(ViewPostActivity.this, "invalid instagram link", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            btnInstagram.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    GoToURL(Instagram);
                                }
                            });

                        }



                        //check whatsapp link
                        if (load_profile_info.getWhatsAppNum().equals("")) {
                            btnWhatsApp.setClickable(false);
                            btnWhatsApp.setAlpha(0.3f);

                        } else {
                            btnWhatsApp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    GoToURL(WhatsApp);
                                }
                            });
                        }


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }


    private void GoToURL(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}