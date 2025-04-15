package com.example.fitpals;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitpals.R;
import com.example.fitpals.User;
import com.example.fitpals.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText inputName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;
    private TextInputEditText inputConfirmPassword;
    private Button btnRegister;
    private TextView linkLogin;

    private DatabaseHelper db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        session = new SessionManager(getApplicationContext());

        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPassword = findViewById(R.id.input_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        linkLogin = findViewById(R.id.link_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Go back to login activity
            }
        });
    }

    private void register() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String confirmPassword = inputConfirmPassword.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(name)) {
            inputName.setError("Please enter your name");
            inputName.requestFocus();
            return;
        }

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

        if (password.length() < 6) {
            inputPassword.setError("Password must be at least 6 characters");
            inputPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            inputConfirmPassword.setError("Passwords do not match");
            inputConfirmPassword.requestFocus();
            return;
        }

        // Check if user already exists
        if (db.getUserByEmail(email) != null) {
            inputEmail.setError("Email already registered");
            inputEmail.requestFocus();
            return;
        }

        // Create user
        User user = new User(name, email, password);
        long userId = db.addUser(user);

        if (userId > 0) {
            user.setId(userId);

            // Create login session
            session.createLoginSession(user);

            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

            // Launch main activity
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_LONG).show();
        }
    }
}
