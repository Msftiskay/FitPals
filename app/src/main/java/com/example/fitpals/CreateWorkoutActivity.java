package com.example.fitpals;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitpals.R;
import com.example.fitpals.User;
import com.example.fitpals.Workout;
import com.example.fitpals.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class CreateWorkoutActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private SessionManager session;
    private User currentUser;

    private Toolbar toolbar;
    private TextInputEditText inputTitle;
    private TextInputEditText inputDescription;
    private RecyclerView recyclerExercises;
    private Button btnAddExercise;
    private Button btnSaveWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        db = new DatabaseHelper(this);
        session = new SessionManager(getApplicationContext());
        currentUser = session.getUser();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputTitle = findViewById(R.id.input_title);
        inputDescription = findViewById(R.id.input_description);
        recyclerExercises = findViewById(R.id.recycler_exercises);
        btnAddExercise = findViewById(R.id.btn_add_exercise);
        btnSaveWorkout = findViewById(R.id.btn_save_workout);

        // Set up RecyclerView
        recyclerExercises.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set up adapter for exercises

        // Set click listeners
        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Show dialog to add exercise
                Toast.makeText(CreateWorkoutActivity.this, "Add exercise feature coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveWorkout() {
        String title = inputTitle.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(title)) {
            inputTitle.setError("Please enter a title");
            inputTitle.requestFocus();
            return;
        }

        // Create workout
        Workout workout = new Workout(title, description, currentUser.getId());
        long workoutId = db.createWorkout(workout);

        if (workoutId > 0) {
            // TODO: Save workout exercises

            Toast.makeText(this, "Workout created successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to create workout!", Toast.LENGTH_SHORT).show();
        }
    }
}
