package com.example.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookexchange.dao.DAOUser;
import com.example.bookexchange.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    CustomProgressDialog dialog;
    Button btn_sign;
    EditText ed_name,ed_email,ed_pass;
    private  FirebaseDatabase database;
    private  DatabaseReference myRef ;

    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Inflate
        ed_name=findViewById(R.id.ed_name_sign);
        ed_email=findViewById(R.id.ed_email_sign);
        ed_pass=findViewById(R.id.ed_pass_sign);
        btn_sign=findViewById(R.id.btn_sign);



        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
        DAOUser daoUser=new DAOUser();


        //SignIn Button
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ed_email.getText().toString().trim();
                String pass = ed_pass.getText().toString().trim();
                String name = ed_name.getText().toString().trim();


                dialog = new CustomProgressDialog(SignUpActivity.this);
//              dialog.show();


                if (email.isEmpty()) {
                    ed_email.setError("Email can not be empty !");
                } else if (pass.isEmpty()) {
                    ed_pass.setError("Password can not be empty !");

                } else {

                    if (pass.length() < 6) {
                        ed_pass.setError("Password can not be less than (6) Numbers !");
                    } else {


                        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {


                                    User user = new User(email, pass);

                                    daoUser.add(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            String id = auth.getCurrentUser().getUid().toString();
                                            String password = pass;
                                            String email = auth.getCurrentUser().getEmail().toString();

//                                        dialog.cancel();

                                            Toast.makeText(SignUpActivity.this, " Successfully Sign Up ", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                                        }
                                    });


                                } else {


                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(SignUpActivity.this, "Failed Sign in Because :  "+e.getMessage() , Toast.LENGTH_SHORT).show();


                            }
                        });


                    }

                }




            }
        });




    }
}