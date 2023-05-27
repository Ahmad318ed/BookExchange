package com.example.bookexchange.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAOProfileInfo;
import com.example.bookexchange.models.Profile_info;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Profile extends AppCompatActivity {

    public static String username, username_Id;
    public FirebaseAuth auth;
    TextView name, major, collage;
    FirebaseUser currentUser;
    DAOProfileInfo daoProfile;
    ImageView img_profile;

    ImageView btnFacebook, btnInstagram, btnWhatsApp;


    Profile_info load_profile_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.username_profile);
        major = findViewById(R.id.major_profile);
        collage = findViewById(R.id.collage_profile);
        img_profile = findViewById(R.id.img_profile);

        btnFacebook = findViewById(R.id.btn_imgFacebook);
        btnInstagram = findViewById(R.id.btn_imgInstagram);
        btnWhatsApp = findViewById(R.id.btn_imgWhatsApp);

        auth = FirebaseAuth.getInstance();
        daoProfile = new DAOProfileInfo();
        currentUser = auth.getCurrentUser();


        username = currentUser.getDisplayName();
        username_Id = currentUser.getUid();


        setDataToProfile();
    }


    private void setDataToProfile() {
        daoProfile.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot profilesnap : snapshot.getChildren()) {

                    load_profile_info = profilesnap.getValue(Profile_info.class);

                    //first if to check if the user is the same user that u take his data
                    //Second if to check the contact info if it is empty or not then show it to the users

                    name.setText(username);

                    if (username_Id.equals(load_profile_info.getUserId())) {

                        major.setText(load_profile_info.getMajor());
                        collage.setText(load_profile_info.getCollage());

                        String WhatsApp =load_profile_info.getWhatsAppNum();
                        String Facebook = load_profile_info.getFacebook_link();
                        String Instagram = load_profile_info.getInstagram_link();




                        if (!isDestroyed()) {
                            Glide.with(Profile.this).load(currentUser.getPhotoUrl()).fitCenter().centerCrop().into(img_profile);

                        }


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
                                       Toast.makeText(Profile.this, "invalid facebook link", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(Profile.this, "invalid instagram link", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            btnInstagram.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        GoToURL(Instagram);
                                    }
                                });


//                            try {
//
//                                btnInstagram.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        GoToURL(load_profile_info.getInstagram_link());
//                                    }
//                                });
//                            } catch (Exception e) {
//                                System.out.println("heeeeeelp" + e);
//                            }
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

//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            intent.setPackage("com.android.chrome"); // Package name of Chrome browser
//
//            // Verify that there's at least one activity that can handle the intent
//            PackageManager packageManager = getPackageManager();
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivity(intent);
//            } else {
//                // Handle the case where Chrome is not installed
//                Toast.makeText(getApplicationContext(), "Chrome browser not installed", Toast.LENGTH_SHORT).show();
//            }
//        } catch (ActivityNotFoundException e) {
//            // Handle the case where Chrome is not installed
//            Toast.makeText(getApplicationContext(), "Chrome browser not installed", Toast.LENGTH_SHORT).show();
//        }
    }

}