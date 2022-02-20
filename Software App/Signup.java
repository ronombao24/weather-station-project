package com.example.weatherdetector;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    EditText email, password;
    Button signup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup_button);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext().MainActivity.class));
            finish();
        }

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = email.getText().toString().trim();
                String password = password.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    email.setError("email is needed");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    password.setError("password needed");
                    return;
                }
                if(password.length() < 8)
                {
                    password.setError("Password must be greater than 8 characters");
                    return;
                }
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Signup.this, "Created user", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext().MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Signup.this, "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
            }
        }
    }
}
