package com.example.bookexchange.ui.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.ui.fragments.PostsFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {
    Spinner spinner1, spinner2, spinner3;
    private static final int PICK_IMAGE_REQUEST = 1;
    public static String username, userEmail, username_Id;
    private static final int MAX_IMAGE_SIZE = 500; // Maximum image size in pixels

    Post post;

    Button btn_submit;
    String selectedValue1 = ""; // Initialize a variable to store the selected value
    String selectedValue2 = ""; // Initialize a variable to store the selected value
    String selectedValue3 = ""; // Initialize a variable to store the selected value

    FirebaseUser currentUser;
    DAOPost daoPost;

    public FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public static Uri selectedImageUri;
    String imageSelectedURl;

    ImageView imageView;

    ImageButton imagePicker;
    EditText ed_name, ed_book_name, ed_book_edition, details;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        auth = FirebaseAuth.getInstance();
        daoPost = new DAOPost();
        currentUser = auth.getCurrentUser();
        btn_submit = findViewById(R.id.btn_submit_post);
        mProgressBar = findViewById(R.id.progressBar);
        ed_book_name = findViewById(R.id.ed_book_name_post);
        ed_book_edition = findViewById(R.id.ed_book_edition_post);
        details = findViewById(R.id.ed_details_post);
        loadSpinners();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedValue1 = parent.getItemAtPosition(position).toString(); // Get the selected item from the spinner and store it in the selectedValue variable


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedValue1 = parent.getItemAtPosition(0).toString();
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValue2 = parent.getItemAtPosition(position).toString(); // Get the selected item from the spinner and store it in the selectedValue variable
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedValue2 = parent.getItemAtPosition(0).toString();
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValue3 = parent.getItemAtPosition(position).toString(); // Get the selected item from the spinner and store it in the selectedValue variable
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedValue3 = parent.getItemAtPosition(0).toString();
            }
        });


        imageView = findViewById(R.id.book_imageView_post);
        imagePicker = findViewById(R.id.img_camera_post);
        imagePicker.setOnClickListener(v -> pickImageFromGallery());

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = currentUser.getDisplayName();
                userEmail = currentUser.getEmail();
                username_Id = currentUser.getUid();
                String bookName = ed_book_name.getText().toString().trim();
                String bookEdition = ed_book_edition.getText().toString().trim();
                String detailsText = details.getText().toString().trim();
                String spinner1Value = selectedValue1;
                String spinner2Value = selectedValue2;
                String spinner3Value = selectedValue3;

                if (!bookName.isEmpty()) {

                    if (!bookEdition.isEmpty()) {

                        if (!(selectedImageUri == null)) {

                            if (!(spinner1.getSelectedItem().toString() == "None")) {


                                if (!(spinner2.getSelectedItem().toString() == "None")) {


                                    if (!(spinner3.getSelectedItem().toString() == "None")) {


                                        Toast.makeText(CreatePostActivity.this, "Loading ..", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(CreatePostActivity.this, "Please wait ..", Toast.LENGTH_SHORT).show();


                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("posts_images");
                                        StorageReference imgfilePath = storageRef.child(selectedImageUri.getLastPathSegment());
                                        imgfilePath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        mProgressBar.setProgress(0);
                                                    }
                                                }, 500);


                                                imgfilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {


                                                        imageSelectedURl = uri.toString();


                                                        Date date = Calendar.getInstance().getTime();
                                                        String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);


                                                        post = new Post(imageSelectedURl, username, userEmail, username_Id, bookName, bookEdition, spinner1Value, spinner2Value, spinner3Value, detailsText, dateFormat);
                                                        daoPost.add(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                Toast.makeText(CreatePostActivity.this, "The book has been added", Toast.LENGTH_SHORT).show();

                                                                onBackPressed();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                onBackPressed();

                                                                Toast.makeText(CreatePostActivity.this, "Something Wrong ):", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                    }
                                                });

                                            }
                                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                                double progress = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();

                                                mProgressBar.setProgress((int) progress);
                                            }
                                        });


                                        ////


                                    } else {

                                        Toast.makeText(getApplicationContext(), "Plz put a States for the Book !", Toast.LENGTH_LONG).show();


                                    }

                                } else {

                                    Toast.makeText(getApplicationContext(), "Plz put a Price for the Book !", Toast.LENGTH_LONG).show();


                                }

                            } else {

                                Toast.makeText(getApplicationContext(), "Plz put a Collage for the Book !", Toast.LENGTH_LONG).show();


                            }


                        } else {

                            Toast.makeText(getApplicationContext(), "Plz put a image for the Book !", Toast.LENGTH_LONG).show();

                        }

                    } else {

                        ed_book_edition.setError("Plz put a Edition for the Book !");

                    }

                } else {

                    ed_book_name.setError("Plz set a name for the Book !");

                }


                ///////////////////////////END OF SUBMIT


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void loadSpinners() {

        spinner1 = findViewById(R.id.spinner1_post);
        spinner2 = findViewById(R.id.spinner2_post);
        spinner3 = findViewById(R.id.spinner3_post);

        String[] value1 = {"None", "Information  technology  collage", "College  of  Arts  and  Sciences", "College  of  Da'wah  and  Fundamentals  of  Religion", "Sheikh  Noah  College  of  Sharia  and  Law"
                , "Faculty  of  Educational  Sciences", "College  of  arts  and  sciences", "College  of  Arts  and  Islamic  Architecture", "College  Money  and  Business", "Faculty  of  Al  Maliki  Jurisprudence"
                , "Faculty  of  Al  Hanafi  Jurisprudence", "Faculty  of  Al  Shafi'i  Jurisprudence ", "College  Graduate  Studies"};
        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList(value1));
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.style_spinn, arrayList1);
        spinner1.setAdapter(arrayAdapter1);
        spinner1.setSelection(0);


        String[] value2 = {"None", "Free", "0.5", "1", "1.5"};
        ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList(value2));
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.style_spinn, arrayList2);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setSelection(0);


        String[] value3 = {"None", "Used", "New"};
        ArrayList<String> arrayList3 = new ArrayList<>(Arrays.asList(value3));
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, R.layout.style_spinn, arrayList3);
        spinner3.setAdapter(arrayAdapter3);
        spinner3.setSelection(0);

    }


    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {

                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(event);
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}