package com.example.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookexchange.dao.DAOUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    Button btn_login;
   public EditText password,email_t;

    private FirebaseDatabase database;

    private DatabaseReference myRef ;

    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth=FirebaseAuth.getInstance();
        email_t= findViewById(R.id.ed_email_log);
        password=findViewById(R.id.ed_pass_log);
        btn_login=findViewById(R.id.btn_log);

        DAOUser daoUser=new DAOUser();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=email_t.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    if(!pass.isEmpty()){

                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {

                                        Toast.makeText(LoginActivity.this, " Successfully Log In  ", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {


                                        Toast.makeText(LoginActivity.this, " Failed Log In = "+e, Toast.LENGTH_LONG).show();

                                    }
                                });


                    }else{
                        password.setError("Password cant be empty!");
                    }


                }else if (email.isEmpty()) {
                    email_t.setError("Email cant be empty!");

                }else{
                    email_t.setError("Enter valid Email PLZ!");

                }



            }
        });



    }
}