package com.example.weatherdetector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailAddr, password;
    Button btn_login, btn_sign;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddr=findViewById(R.id.email);
        password =findViewById(R.id.password);
        Button btn_login = findViewById(R.id.login_button);
        Button btn_sign = findViewById(R.id.signup_button);

        auth=FirebaseAuth.getInstance();

        btn_login.setOnClickListener(v ->
        {
            String email= emailAddr.getText().toString().trim();
            String password= this.password.getText().toString().trim();
            if(email.isEmpty())
            {
                emailAddr.setError("Email is empty");
                emailAddr.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                emailAddr.setError("Enter the valid email");
                emailAddr.requestFocus();
                return;
            }
            if(password.isEmpty())
            {
                this.password.setError("Password is empty");
                this.password.requestFocus();
                return;
            }
            if(password.length()<6)
            {
                this.password.setError("Length of password is more than 8");
                this.password.requestFocus();
                return;
            }
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this,
                            "Please Check Your LoginActivity Credentials",
                            Toast.LENGTH_SHORT).show();
                }

            });
        });
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class ));
            }
        });
    }

}


