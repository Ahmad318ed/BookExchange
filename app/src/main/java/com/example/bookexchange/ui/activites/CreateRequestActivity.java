package com.example.bookexchange.ui.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookexchange.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateRequestActivity extends AppCompatActivity {
    Spinner spinner1, spinner2, spinner3;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAX_IMAGE_SIZE = 500; // Maximum image size in pixels


    ImageView imageView, imagepicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);


        imageView = findViewById(R.id.book_imageView_request);
        imagepicker = findViewById(R.id.fab_camera_request);
        imagepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pickImageFromGallery();


            }
        });


        loadSpinners();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
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

        spinner1 = findViewById(R.id.spinner1_request);
        spinner2 = findViewById(R.id.spinner2_request);
        spinner3 = findViewById(R.id.spinner3_request);

        String[] value1 = {"None", "Information  technology  collage", "College  of  Arts  and  Sciences", "College  of  Da'wah  and  Fundamentals  of  Religion", "Sheikh  Noah  College  of  Sharia  and  Law"
                , "Faculty  of  Educational  Sciences", "College  of  arts  and  sciences", "College  of  Arts  and  Islamic  Architecture", "College  Money  and  Business", "Faculty  of  Al  Maliki  Jurisprudence"
                , "Faculty  of  Al  Hanafi  Jurisprudence", "Faculty  of  Al  Shafi'i  Jurisprudence ", "College  Graduate  Studies"};
        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList(value1));
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.style_spinn, arrayList1);
        spinner1.setAdapter(arrayAdapter1);
        spinner1.setSelection(0);


        String[] value2 = {"Free", "0.5", "1", "1.5"};
        ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList(value2));
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.style_spinn, arrayList2);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setSelection(0);


        String[] value3 = {"Used", "New"};
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


}