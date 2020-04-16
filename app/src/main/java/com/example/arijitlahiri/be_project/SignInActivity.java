package com.example.arijitlahiri.be_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    Button signInButton;
    String email, password;
    EditText emailField, passwordField;
    TextView sign_up_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passField);

        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();

                if(validateEmail(email) && validatePassword(password)) {
                    if (email.equals("admin") && password.equals("admin")) {
                        // Sign in success, update UI with the signed-in user's information

                        Intent signinIntent = new Intent(SignInActivity.this, MainActivity.class);
                        SignInActivity.this.startActivity(signinIntent);
                    } else {
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (!validatePassword(password)) {
                    Toast.makeText(getApplicationContext(), "Password has to be atleast 6 characters", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter Valid Email and Password", Toast.LENGTH_LONG).show();
                }
            }
        });
        sign_up_text = (TextView) findViewById(R.id.signUpText);
        sign_up_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent signupIntent = new Intent(SignInActivity.this, SignUpActivity.class);
                SignInActivity.this.startActivity(signupIntent);
            }
        });
    }
    public boolean validateEmail(String email){
        return true;//!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public boolean validatePassword(String password){
        if(password.length() < 0 )
            return false;
        else
            return true;
    }
}

