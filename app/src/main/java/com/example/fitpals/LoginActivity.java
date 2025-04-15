package com.example.fitpals;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitpals.DatabaseHelper;
import com.example.fitpals.R;
import com.example.fitpals.User;
import com.example.fitpals.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;
    private Button btnLogin;
    private TextView linkRegister;
    private TextView linkForgotPassword;

    private DatabaseHelper db;
    private SessionManager session;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in
        if (session.isLoggedIn()) {
            // User is already logged in, redirect to main activity
            Intent intent = new Intent(LoginActivity.this, com.example.fitpals.MainActivity.class);
            startActivity(intent);
            finish();
        }

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        linkRegister = findViewById(R.id.link_register);
        linkForgotPassword = findViewById(R.id.link_forgot_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, com.example.fitpals.RegisterActivity.class);
                startActivity(intent);
            }
        });

        linkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Forgot password feature coming soon!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Check for empty inputs
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Please enter your email address");
            inputEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Please enter your password");
            inputPassword.requestFocus();
            return;
        }

        // Attempt to find the user
        User user = db.getUserByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            // Create login session
            session.createLoginSession(user);

            // Launch main activity
            Intent intent = new Intent(LoginActivity.this, com.example.fitpals.MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid email or password!", Toast.LENGTH_LONG).show();
        }
    }
}