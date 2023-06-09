package com.example.bookexchange.ui.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAOProfileInfo;
import com.example.bookexchange.dao.DAOUser;
import com.example.bookexchange.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    ProgressDialog dialog;
    Button btn_login;

    TextView tv_forget;
    public EditText password, email_t;


    public static User current_User;

    public FirebaseAuth auth;


    //To check if the user already login , if that the case, the home page open direct
    // but I comment it cause we don't have logout button yet, which is easy to accomplish.


    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        boolean isSignUp = getIntent().getBooleanExtra("isSignUp", false);
        if (isSignUp) {
            // Show a welcome message or something else for new users

        } else {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {
                Intent intent = new Intent(this, CollageActivity.class);
                startActivity(intent);
                finish();

            }


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();

        email_t = findViewById(R.id.ed_email_log);
        password = findViewById(R.id.ed_pass_log);
        btn_login = findViewById(R.id.btn_take);
        tv_forget = findViewById(R.id.tv_forget);

//
        //Database Class : it contain all the methods that u want from real time data base .
        DAOUser daoUser = new DAOUser();
        DAOProfileInfo daoProfileInfo = new DAOProfileInfo();
        //Dialog For loading time
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please Wait..");


        //Login Bottom
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = email_t.getText().toString().trim();
                String pass = password.getText().toString().trim();


                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    if (!pass.isEmpty()) {


                        if (!(pass.length() < 6)) {

                            dialog.show();


                            auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    FirebaseUser user1 = authResult.getUser();
                                    String name = user1.getDisplayName();
                                    String id = user1.getUid();
                                    String emaill = user1.getEmail();


                                    current_User = new User(id, emaill, name);


                                    if (auth.getCurrentUser().isEmailVerified()) {
                                        //Add the Verified user to database
                                        daoUser.add(current_User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                            }
                                        });


                                        Toast.makeText(LoginActivity.this, "Welcome " + name, Toast.LENGTH_LONG).show();
                                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                                        daoProfileInfo.get().addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot data : snapshot.getChildren()) {
                                                    String userId = data.getKey();

                                                    // Check if the current user matches the specified user
                                                    if (userId.equals(current_User.getId())) {
                                                        // User has a profile and has inputted profile information before
                                                        // Intent the user to the home page directly
                                                        Intent homeIntent = new Intent(LoginActivity.this, CollageActivity.class);
                                                        startActivity(homeIntent);
                                                        finish(); // Optional: close the current activity if needed
                                                        return; // Exit the loop if the user is found
                                                    }
                                                }

                                                // User doesn't have a profile or hasn't inputted profile information before
                                                // Intent the user to the Profile Input activity to insert profile info
                                                Intent profileInputIntent = new Intent(LoginActivity.this, EditableProfile.class);
                                                startActivity(profileInputIntent);
                                                finish(); // Optional: close the current activity if needed



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });




                                    } else {

                                        Toast.makeText(LoginActivity.this, "Please Verify your account !", Toast.LENGTH_LONG).show();


                                    }


                                    dialog.hide();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    dialog.hide();
                                    Toast.makeText(LoginActivity.this, " Failed Log In = " + e.getMessage(), Toast.LENGTH_LONG).show();


                                }
                            });


                        } else {

                            password.setError("Password can not be less than (6) Numbers !");
                        }

                    } else {

                        password.setError("Password can not be empty !");
                    }


                } else if (email.isEmpty()) {

                    email_t.setError("Email can not be empty !");

                } else {

                    email_t.setError("Enter valid Email Please !");

                }


            }
        });


        //Forget password Text
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forget_pass, null);
                EditText emailBox = dialogView.findViewById(R.id.email_forget);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.reset_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String userEmail = emailBox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(LoginActivity.this, "Enter your registered email !", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Unable to send, failed ! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });


                dialogView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();

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


    public void goToSignUpScreen(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

}