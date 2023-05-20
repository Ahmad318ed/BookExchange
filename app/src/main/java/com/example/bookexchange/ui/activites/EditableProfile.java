package com.example.bookexchange.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAOProfileInfo;
import com.example.bookexchange.models.Profile_info;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EditableProfile extends AppCompatActivity {

    Spinner spinner1;
    String selectedValue1 = ""; // Initialize a variable to store the selected value

    EditText edt_country_num;
    Button btn_edit_country_num;

    EditText edt_name, edtFacebook, edtInstagram, edtWhatsApp, edtMajor;
    Button btnSave;

    FirebaseUser currentUser;
    DAOProfileInfo daoProfile;

    public FirebaseAuth auth;

    public static String username, username_Id;

    int SelectedItem;

    ImageView img_profile;
    ImageButton btn_img_selector;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    // image as URI and as a string
    Uri uri_selectedImageUri;
    String str_imageSelectedURl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editable_profile);

        editCountryNum();
        initialData();

        auth = FirebaseAuth.getInstance();
        daoProfile = new DAOProfileInfo();
        currentUser = auth.getCurrentUser();



       // username = currentUser.getDisplayName();
        username_Id = currentUser.getUid();


        loadSpinners();
        getSpinnerSelectedItem();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(EditableProfile.this, "wait...", Toast.LENGTH_LONG).show();


                String name = edt_name.getText().toString().trim();
                String facebookLink = edtFacebook.getText().toString().trim();
                String instagramLink = edtInstagram.getText().toString().trim();
                String number = edtWhatsApp.getText().toString().trim();
                String countryNum = edt_country_num.getText().toString().trim();

                //ToDo : make the https and country code on the setData method and retreve just the number.
                String whatsAppNumber = "https://wa.me/" + countryNum + number;
                String spinner1Value = selectedValue1;
                String major = edtMajor.getText().toString().trim();


                if (!name.isEmpty()) {

                    if (!(spinner1.getSelectedItem().toString().equals("Collage"))) {

                        if (!major.isEmpty()) {

                            if (!countryNum.isEmpty()) {

                                if (facebookLink.isEmpty() && instagramLink.isEmpty() && number.isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Please enter at least one contact", Toast.LENGTH_LONG).show();

                                } else {


                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images");
                                    if (uri_selectedImageUri != null) {
                                        StorageReference imgfilePath = storageRef.child(uri_selectedImageUri.getLastPathSegment());


                                        imgfilePath.putFile(uri_selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                imgfilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {

                                                        str_imageSelectedURl = uri.toString();

                                                        Profile_info profile = new Profile_info(name, spinner1Value, facebookLink, instagramLink, str_imageSelectedURl, number, countryNum, whatsAppNumber, username, username_Id, major, SelectedItem);


                                                        daoProfile.add(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                Toast.makeText(EditableProfile.this, "good db", Toast.LENGTH_SHORT).show();
//                                                                setDataToProfile();
                                                                onBackPressed();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                onBackPressed();

                                                                Toast.makeText(EditableProfile.this, "wrong db", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                    }
                                                });

                                            }
                                        });


                                    } else {


                                        Profile_info profile = new Profile_info(name, spinner1Value, facebookLink, instagramLink,str_imageSelectedURl, number, countryNum, whatsAppNumber, username, username_Id, major, SelectedItem);


                                        daoProfile.add(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(EditableProfile.this, "good db", Toast.LENGTH_SHORT).show();
                                                // setDataToProfile();
                                                //onBackPressed();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // onBackPressed();

                                                Toast.makeText(EditableProfile.this, "wrong db", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                        Toast.makeText(EditableProfile.this, "no image selected.", Toast.LENGTH_SHORT).show();

                                    }


                                } // end of last else{}

                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter your country code", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toast.makeText(EditableProfile.this, "Please enter your major", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select your collage", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your full name.", Toast.LENGTH_LONG).show();
                }
            }
        });


        //to select the photo from gallery
        btn_img_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });


        setDataToProfile();
    }


    // start methods for the Profile image
    /////////////////////////////////


    private void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                uri_selectedImageUri = data.getData();
                if (null != uri_selectedImageUri) {
                    // update the preview image in the layout
                    img_profile.setImageURI(uri_selectedImageUri);
                }
            }
        }
    }


    private void editCountryNum() {
        btn_edit_country_num = findViewById(R.id.btn_editCountryNum);

        btn_edit_country_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edt_country_num.isEnabled()) {
                    edt_country_num.setEnabled(true);
                    edt_country_num.setAlpha(1.0F);
                    btn_edit_country_num.setText("Save");
                    edt_country_num.setTextColor(getResources().getColor(R.color.black));
                } else {
                    edt_country_num.setEnabled(false);
                    edt_country_num.setAlpha(0.7F);
                    btn_edit_country_num.setText("Edit");
                    edt_country_num.setTextColor(getResources().getColor(R.color.fog_3));
                }
            }
        });

    }

    private void loadSpinners() {

        spinner1 = findViewById(R.id.spinner1_editable_profile);


        String[] value1 = {"Collage", "Information Technology Collage", "College of Arts and Sciences", "College of Da'wah and Fundamentals of Religion", "Sheikh Noah College of Sharia and Law"
                , "Faculty of Educational Sciences", "College of arts and sciences", "College of Arts and Islamic Architecture", "College Money and Business", "Faculty of Al Maliki Jurisprudence"
                , "Faculty of Al Hanafi Jurisprudence", "Faculty of Al Shafi'i Jurisprudence ", "College Graduate Studies"};
        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList(value1));
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.style_spinn, arrayList1);
        spinner1.setAdapter(arrayAdapter1);
        spinner1.setSelection(0);


    }

    private void initialData() {
        edt_name = findViewById(R.id.edt_name_EditableProfile);
        edtFacebook = findViewById(R.id.edt_facebook_EditableProfile);
        edtInstagram = findViewById(R.id.edt_instagram_EditableProfile);
        edtWhatsApp = findViewById(R.id.edt_whatsup_EditableProfile);
        edt_country_num = findViewById(R.id.edt_country_num_EditableProfile);
        btnSave = findViewById(R.id.btn_save_EditProfile);
        edtMajor = findViewById(R.id.edt_major_EditableProfile);

        spinner1 = findViewById(R.id.spinner1_editable_profile);

        img_profile = findViewById(R.id.img_profile);
        btn_img_selector = findViewById(R.id.btn_selector_img_editableProfile);

    }

    private void getSpinnerSelectedItem() {
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selectedValue1 = parent.getItemAtPosition(position).toString(); // Get the selected item from the spinner and store it in the selectedValue variable

                SelectedItem = parent.getSelectedItemPosition();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedValue1 = parent.getItemAtPosition(0).toString();
            }
        });
    }

    private void setDataToProfile() {
        daoProfile.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot profilesnap : snapshot.getChildren()) {

                    Profile_info profile = profilesnap.getValue(Profile_info.class);

                    if (username_Id.equals(profile.getUserId())) {
                        edt_name.setText(profile.getName());
                        edtFacebook.setText(profile.getFacebook_link());
                        edtInstagram.setText(profile.getInstagram_link());
                        edtWhatsApp.setText(profile.getPhoneNum());
                        edtMajor.setText(profile.getMajor());
                        edt_country_num.setText(profile.getCountryNum());
                        spinner1.setSelection(profile.getSelectedItem());

                        if (!isDestroyed()) {
                            Glide.with(EditableProfile.this).load(profile.getImg()).fitCenter().centerCrop().into(img_profile);

                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}