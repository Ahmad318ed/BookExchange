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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAOProfileInfo;
import com.example.bookexchange.models.Profile_info;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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

    public static String username, username_Id;
    public FirebaseAuth auth;
    Spinner spinner1;
    String selectedValue1 = ""; // Initialize a variable to store the selected value
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAX_IMAGE_SIZE = 500; // Maximum image size in pixels
    public static Uri selectedImageUri;


    String whatsAppNumber;
    Profile_info upload_profile_info;


    EditText edt_country_num;
    Button btn_edit_country_num;
    FirebaseUser user;
    EditText edtFacebook, edtInstagram, edtWhatsApp, edtMajor;
    EditText edt_name;
    Button btnSave;
    FirebaseUser currentUser;
    DAOProfileInfo daoProfile;
    int SelectedItem;

    ImageView img_profile;
    ImageButton btn_img_selector;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    // image as URI and as a string
    String str_imageSelectedURl;

    TextView btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editable_profile);

        editCountryNum();
        initialData();


        auth = FirebaseAuth.getInstance();
        daoProfile = new DAOProfileInfo();
        currentUser = auth.getCurrentUser();


        username = currentUser.getDisplayName();
        username_Id = currentUser.getUid();

        loadSpinners();
        getSpinnerSelectedItem();


        //to select the photo from gallery
        btn_img_selector.setOnClickListener(v -> pickImageFromGallery());


        //to save all the data
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(EditableProfile.this, "Loading ..", Toast.LENGTH_SHORT).show();
                Toast.makeText(EditableProfile.this, "Please wait ..", Toast.LENGTH_SHORT).show();

                String person_name = edt_name.getText().toString().trim();
                String facebookLink = edtFacebook.getText().toString().trim();
                String instagramLink = edtInstagram.getText().toString().trim();
                String number = edtWhatsApp.getText().toString().trim();
                String countryNum = edt_country_num.getText().toString().trim();


//
//                // To
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(person_name)
                        .build();
                user.updateProfile(profileUpdates);


                //ToDo : make the https and country code on the setData method and retreve just the number.

                if ((!edtWhatsApp.getText().toString().trim().equals("")) && edtWhatsApp.getText().toString().trim().length() >= 9) {
                    whatsAppNumber = "https://wa.me/" + countryNum + number;
                } else {
                    whatsAppNumber = "";
                }

                String spinner1Value = selectedValue1;
                String major = edtMajor.getText().toString().trim();


//
                if (!person_name.isEmpty()) {

                    if (!(spinner1.getSelectedItem().toString().equals("Collage"))) {

                        if (!major.isEmpty()) {

                            if (!countryNum.isEmpty()) {

                                if (validFacebook() || validInstagram() || validWhatsApp()) {
                                    ///////////////


                                    //  && img_profile.getDrawable() != null && !img_profile.getDrawable().getCurrent().equals( getResources().getDrawable(R.drawable.default_profile_img))

                                    if (selectedImageUri == null) {


                                        //to set username from edtName
                                        username = person_name;

                                        upload_profile_info = new Profile_info(username, spinner1Value, facebookLink, instagramLink, number, countryNum, whatsAppNumber, username, username_Id, major, SelectedItem);


                                        daoProfile.add(upload_profile_info).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(EditableProfile.this, "The Information has been added", Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(getApplicationContext(), CollageActivity.class));
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                startActivity(new Intent(getApplicationContext(), CollageActivity.class));
                                                Toast.makeText(EditableProfile.this, "Something Wrong ):", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });


                                    }


//                                    if (selectedImageUri != null)
                                    else {

                                        Toast.makeText(EditableProfile.this, "Wait ...", Toast.LENGTH_SHORT).show();


                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images");
                                        StorageReference imgfilePath = storageRef.child(selectedImageUri.getLastPathSegment());


                                        try {
                                            imgfilePath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                                    imgfilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {

                                                            str_imageSelectedURl = uri.toString();


                                                            // to set user image
                                                            auth = FirebaseAuth.getInstance();
                                                            user = auth.getCurrentUser();

                                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                                    .setPhotoUri(uri)
                                                                    .build();
                                                            user.updateProfile(profileUpdates);


                                                            //to set username from edtName
                                                            username = person_name;

                                                            upload_profile_info = new Profile_info(username, spinner1Value, facebookLink, instagramLink, str_imageSelectedURl, number, countryNum, whatsAppNumber, username, username_Id, major, SelectedItem);

                                                            finish();

                                                            daoProfile.add(upload_profile_info).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {

                                                                    Toast.makeText(EditableProfile.this, "The Information has been added", Toast.LENGTH_SHORT).show();

                                                                    str_imageSelectedURl = "";
                                                                    startActivity(new Intent(getApplicationContext(), CollageActivity.class));
                                                                    finish();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {

                                                                    startActivity(new Intent(getApplicationContext(), CollageActivity.class));
                                                                    Toast.makeText(EditableProfile.this, "Something Wrong ):", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                }
                                                            });


                                                        }
                                                    });

                                                }
                                            });
                                        } catch (Exception e) {
                                            System.out.println("hellllllllllllllllllo" + e);

                                            Toast.makeText(EditableProfile.this, "The Information has been added", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }


                                    }


                                    ///////////////
                                } else {

                                    Toast.makeText(getApplicationContext(), "Please enter at least one valid contact", Toast.LENGTH_LONG).show();


                                }

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

                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                }

//


            }
        });


        setDataToProfile();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditableProfile.this, CollageActivity.class));
                finish();
            }
        });


    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        InputStream input = getContentResolver().openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, options);
        input.close();

        int width = options.outWidth;
        int height = options.outHeight;
        int scale = 1;
        while (width / 2 >= MAX_IMAGE_SIZE && height / 2 >= MAX_IMAGE_SIZE) {
            width /= 2;
            height /= 2;
            scale *= 2;
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = scale;
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
        input.close();
        return bitmap;
    }

    private void pickImageFromGallery() {
        // Intent.ACTION_PICK

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scale = Math.min((float) maxSize / width, (float) maxSize / height);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    // start methods for the Profile image
    /////////////////////////////////


    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();


            try {
                Bitmap bitmap = getBitmapFromUri(selectedImageUri);
                if (bitmap != null) {
                    // Scale the bitmap if it is larger than the maximum size
                    if (bitmap.getWidth() > MAX_IMAGE_SIZE || bitmap.getHeight() > MAX_IMAGE_SIZE) {
                        bitmap = scaleBitmap(bitmap, MAX_IMAGE_SIZE);
                    }
                    img_profile.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
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

        btnCancel = findViewById(R.id.btnCancel);

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

                    edt_name.setText(currentUser.getDisplayName());


                    if (username_Id.equals(profile.getUserId())) {

                        edtFacebook.setText(profile.getFacebook_link());
                        edtInstagram.setText(profile.getInstagram_link());
                        edtWhatsApp.setText(profile.getPhoneNum());
                        edtMajor.setText(profile.getMajor());
                        edt_country_num.setText(profile.getCountryNum());
                        spinner1.setSelection(profile.getSelectedItem());
                        if (!isDestroyed()) {
                            Glide.with(EditableProfile.this).load(currentUser.getPhotoUrl()).fitCenter().centerCrop().into(img_profile);

                        }


                        if (!profile.getName().isEmpty() && !profile.getMajor().isEmpty()) {
                            btnCancel.setVisibility(View.VISIBLE);
                        }


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private boolean validFacebook() {
        if ((!edtFacebook.getText().toString().trim().contains("https://")) || edtFacebook.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }


    private boolean validInstagram() {
        if ((!edtInstagram.getText().toString().trim().contains("https://")) || edtInstagram.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validWhatsApp() {
        if (edtWhatsApp.getText().toString().trim().equals("") || (!(edtWhatsApp.getText().toString().trim().length() >= 9))) {
            return false;
        }
        return true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}