package com.example.fitpals;

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
import com.example.fitpals.DatabaseHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private DatabaseHelper db;
    private SessionManager session;
    private User currentUser;

    private CircleImageView profileImage;
    private TextView textUsername;
    private TextView textWorkoutCount;
    private TextView textFriendCount;
    private TextView textJointWorkoutCount;
    private RecyclerView recyclerInvites;
    private TextView textNoInvites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home_fragment, container, false);

        db = new DatabaseHelper(getContext());
        session = new SessionManager(getContext());
        currentUser = session.getUser();

        profileImage = root.findViewById(R.id.profile_image);
        textUsername = root.findViewById(R.id.text_username);
        textWorkoutCount = root.findViewById(R.id.text_workout_count);
        textFriendCount = root.findViewById(R.id.text_friend_count);
        textJointWorkoutCount = root.findViewById(R.id.text_joint_workout_count);
        recyclerInvites = root.findViewById(R.id.recycler_invites);
        textNoInvites = root.findViewById(R.id.text_no_invites);

        // Set up RecyclerView
        recyclerInvites.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load data
        loadUserInfo();
        loadStats();
        loadWorkoutInvites();

        return root;
    }

    private void loadUserInfo() {
        if (currentUser != null) {
            textUsername.setText(currentUser.getName());
            // TODO: Load profile image if available
        }
    }

    private void loadStats() {
        if (currentUser != null) {
            // TODO: Implement getting actual counts from the database
            textWorkoutCount.setText("0");
            textFriendCount.setText("0");
            textJointWorkoutCount.setText("0");
        }
    }

    private void loadWorkoutInvites() {
        if (currentUser != null) {
            List<Workout> invites = db.getJointWorkoutInvites(currentUser.getId());

            if (invites.isEmpty()) {
                textNoInvites.setVisibility(View.VISIBLE);
                recyclerInvites.setVisibility(View.GONE);
            } else {
                textNoInvites.setVisibility(View.GONE);
                recyclerInvites.setVisibility(View.VISIBLE);

                // TODO: Set up adapter for workout invites
                // recyclerInvites.setAdapter(new WorkoutInviteAdapter(getContext(), invites));
            }
        }
    }
}
