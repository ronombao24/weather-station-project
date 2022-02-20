package com.example.weatherdetector;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText emailAddress;
    EditText pass;
    Button register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress = findViewById(R.id.Email);
        pass = findViewById(R.id.EnterPassword);
        register = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                auth.signInWithEmailAndPassword()

            }
        });

    }
}

