package com.example.fitpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitpals.R;
import com.example.fitpals.LoginActivity;
import com.example.fitpals.ProfileFragment;
import com.example.fitpals.DatabaseHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private DatabaseHelper db;
    private SessionManager session;
    private User currentUser;

    private CircleImageView profileImage;
    private TextView textUsername;
    private TextView textEmail;
    private TextView textWorkoutCount;
    private TextView textCalories;
    private TextView textDuration;
    private Button btnEditProfile;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_profile_fragment, container, false);

        db = new DatabaseHelper(getContext());
        session = new SessionManager(getContext());
        currentUser = session.getUser();

        profileImage = root.findViewById(R.id.profile_image);
        textUsername = root.findViewById(R.id.text_username);
        textEmail = root.findViewById(R.id.text_email);
        textWorkoutCount = root.findViewById(R.id.text_workout_count);
        textCalories = root.findViewById(R.id.text_calories);
        textDuration = root.findViewById(R.id.text_duration);
        btnEditProfile = root.findViewById(R.id.btn_edit_profile);
        btnLogout = root.findViewById(R.id.btn_logout);

        // Load user info
        loadUserInfo();

        // Set click listeners
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileFragment.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout user
                session.logoutUser();

                // Redirect to login activity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when fragment becomes visible
        loadUserInfo();
    }

    private void loadUserInfo() {
        if (currentUser != null) {
            textUsername.setText(currentUser.getName());
            textEmail.setText(currentUser.getEmail());

            // TODO: Load actual stats from database
            textWorkoutCount.setText("0");
            textCalories.setText("0");
            textDuration.setText("0");
        }
    }
}