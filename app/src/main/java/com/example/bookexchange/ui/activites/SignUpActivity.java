package com.example.bookexchange.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookexchange.R;
import com.example.bookexchange.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    ProgressDialog dialog;
    Button btn_sign;
    EditText ed_name, ed_email, ed_pass;


    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Inflate
        ed_name = findViewById(R.id.ed_name_sign);
        ed_email = findViewById(R.id.ed_email_sign);
        ed_pass = findViewById(R.id.ed_pass_sign);
        btn_sign = findViewById(R.id.btn_sign);


        auth = FirebaseAuth.getInstance();


        //SignIn Button
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ed_email.getText().toString().trim();
                String pass = ed_pass.getText().toString().trim();
                String name = ed_name.getText().toString().trim();


                dialog = new ProgressDialog(SignUpActivity.this);
                dialog.setTitle("Loading");
                dialog.setMessage("Please Wait...");


                if (!(name.isEmpty())) {


                if (email.isEmpty()) {
                    ed_email.setError("Email can not be empty !");
                } else if (pass.isEmpty()) {
                    ed_pass.setError("Password can not be empty !");

                } else {


                    if (pass.length() < 6) {
                        ed_pass.setError("Password can not be less than (6) Numbers !");
                    } else {


                        dialog.show();


                        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();
                                    user.updateProfile(profileUpdates);
                                    user.sendEmailVerification();

                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                Toast.makeText(SignUpActivity.this, "Successfully Registered \nPlease Verify your account before Login !", Toast.LENGTH_LONG).show();
                                                ed_name.setText("");
                                                ed_pass.setText("");
                                                ed_email.setText("");
                                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                intent.putExtra("isSignUp", true);
                                                startActivity(intent);
                                                finish();


                                            } else {

                                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });


                                } else {

                                    dialog.hide();

                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                dialog.hide();

                                Toast.makeText(SignUpActivity.this, "Failed Sign in Because :  " + e.getMessage(), Toast.LENGTH_LONG).show();


                            }
                        });


                    }

                }////


            }else{

                    ed_name.setError("Name can not be empty !");


                }


            }
        });


    }


    //Press on any place in screen to cancel the keyboard
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

}