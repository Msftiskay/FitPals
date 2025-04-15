package com.example.fitpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitpals.R;
import com.example.fitpals.CreateWorkoutActivity;
import com.example.fitpals.DatabaseHelper;
import com.example.fitpals.User;
import com.example.fitpals.Workout;
import com.example.fitpals.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WorkoutsFragment extends Fragment {
    private DatabaseHelper db;
    private SessionManager session;
    private User currentUser;

    private RecyclerView recyclerWorkouts;
    private TextView textNoWorkouts;
    private FloatingActionButton fabAddWorkout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workouts, container, false);

        db = new DatabaseHelper(getContext());
        session = new SessionManager(getContext());
        currentUser = session.getUser();

        recyclerWorkouts = root.findViewById(R.id.recycler_workouts);
        textNoWorkouts = root.findViewById(R.id.text_no_workouts);
        fabAddWorkout = root.findViewById(R.id.fab_add_workout);

        // Set up RecyclerView
        recyclerWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set click listener for FAB
        fabAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when fragment becomes visible
        loadWorkouts();
    }

    private void loadWorkouts() {
        if (currentUser != null) {
            List<Workout> workouts = db.getUserWorkouts(currentUser.getId());

            if (workouts.isEmpty()) {
                textNoWorkouts.setVisibility(View.VISIBLE);
                recyclerWorkouts.setVisibility(View.GONE);
            } else {
                textNoWorkouts.setVisibility(View.GONE);
                recyclerWorkouts.setVisibility(View.VISIBLE);

                // TODO: Set up adapter for workouts
                // recyclerWorkouts.setAdapter(new WorkoutAdapter(getContext(), workouts));
            }
        }
    }
}