package com.example.weatherdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button register_button;
    EditText email, password;
    //Declaring an instance of firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        register_button =findViewById(R.id.signup_button);

        //Initializing firebase
        auth = FirebaseAuth.getInstance();

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = RegisterActivity.this.email.getText().toString().trim();
                String password= RegisterActivity.this.password.getText().toString().trim();
                if(email.isEmpty())
                {
                    RegisterActivity.this.email.setError("Email is empty");
                    RegisterActivity.this.email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    RegisterActivity.this.email.setError("Enter the valid email address");
                    RegisterActivity.this.email.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    RegisterActivity.this.password.setError("Enter the password");
                    RegisterActivity.this.password.requestFocus();
                    return;
                }
                if(password.length() < 8)
                {
                    RegisterActivity.this.password.setError("Length of the password should be more than 8");
                    RegisterActivity.this.password.requestFocus();
                    return;
                }

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //Create directory for user that is the same as their UID in db under 'Users' directory
                            Toast.makeText(RegisterActivity.this,"You are successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"You are not Registered! Try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}