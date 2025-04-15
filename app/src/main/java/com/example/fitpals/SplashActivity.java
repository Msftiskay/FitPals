package com.example.fitpals;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitpals.R;
import com.example.fitpals.SessionManager;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // 2 seconds
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new SessionManager(getApplicationContext());

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is already logged in
                if (session.isLoggedIn()) {
                    // User is already logged in, redirect to main activity
                    Intent intent = new Intent(SplashActivity.this, com.example.fitpals.MainActivity.class);
                    startActivity(intent);
                } else {
                    // User is not logged in, redirect to login activity
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_DELAY);
    }
}