package com.example.fitpals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitpals.R;
import com.example.fitpals.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FriendsFragment extends Fragment {
    private DatabaseHelper db;
    private SessionManager session;
    private User currentUser;

    private RecyclerView recyclerFriends;
    private TextView textNoFriends;
    private FloatingActionButton fabAddFriend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_friends_fragment, container, false);

        db = new DatabaseHelper(getContext());
        session = new SessionManager(getContext());
        currentUser = session.getUser();

        recyclerFriends = root.findViewById(R.id.recycler_friends);
        textNoFriends = root.findViewById(R.id.text_no_friends);
        fabAddFriend = root.findViewById(R.id.fab_add_friend);

        // Set up RecyclerView
        recyclerFriends.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set click listener for FAB
        fabAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Show dialog to add friend
                Toast.makeText(getContext(), "Add friend feature coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        // Load data
        loadFriends();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when fragment becomes visible
        loadFriends();
    }

    private void loadFriends() {
        if (currentUser != null) {
            List<Friend> friends = db.getUserFriends(currentUser.getId());

            if (friends.isEmpty()) {
                textNoFriends.setVisibility(View.VISIBLE);
                recyclerFriends.setVisibility(View.GONE);
            } else {
                textNoFriends.setVisibility(View.GONE);
                recyclerFriends.setVisibility(View.VISIBLE);

                // TODO: Set up adapter for friends
                // recyclerFriends.setAdapter(new FriendAdapter(getContext(), friends));
            }
        }
    }
}
