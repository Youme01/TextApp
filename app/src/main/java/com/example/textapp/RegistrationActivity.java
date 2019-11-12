package com.example.textapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;

public class RegistrationActivity extends AppCompatActivity {

    private Button signupR;
    FirebaseAuth firebaseAuth;
    TextView loginR;
    private EditText usernameR, passwordR, emailR, passwordR2;
    String name, email,pass, pass2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        setupViews();

        firebaseAuth = FirebaseAuth.getInstance();
        signupR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {


                    String userEmail = emailR.getText().toString().trim();
                    String userPass = passwordR.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendemailverification();
                                Log.d("TAG", "createUserWithEmail:success");

                            } else {
                                Log.d("ERROR",task.getException().getMessage());
                                Toast.makeText(RegistrationActivity.this, "Registration Failed !!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }


        });
        loginR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });

    }

    private void sendUserdata() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());

        UserProfile userProfile = new UserProfile(name, email);

        myref.setValue(userProfile);
    }

    private void sendemailverification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserdata();
                        Toast.makeText(RegistrationActivity.this, "Registration Successful! Verification email has been sent", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Verification email is not sent", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public Boolean validate() {

        Boolean res = false;

        name = usernameR.getText().toString();
        pass = passwordR.getText().toString();
        email = emailR.getText().toString();
        pass2 = passwordR2.getText().toString();
        if (name.isEmpty() || pass2.isEmpty() || pass.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please Enter All The Details!", Toast.LENGTH_LONG).show();
        } else if (pass.compareTo(pass2) != 0) {
            Toast.makeText(this, "Password doesn't match . Try Again!", Toast.LENGTH_LONG).show();
        } else {
            res = true;
        }
        return res;

    }


    private void setupViews() {
        usernameR = (EditText) findViewById(R.id.nameP);
        passwordR = (EditText) findViewById(R.id.passR);
        passwordR2 = (EditText) findViewById(R.id.passR2);
        emailR = (EditText) findViewById(R.id.emailR);
        signupR = (Button) findViewById(R.id.signupR);
        loginR = (TextView) findViewById(R.id.signinR);

    }

}


