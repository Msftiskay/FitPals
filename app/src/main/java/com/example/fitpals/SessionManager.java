package com.example.fitpals;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    private static final String PREF_NAME = "FitFriendPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PROFILE_PIC = "profilePic";

    private SharedPreferences pref;
    private Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putLong(KEY_USER_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PROFILE_PIC, user.getProfilePic());
        editor.commit();
    }

    public User getUser() {
        if (!isLoggedIn()) {
            return null;
        }

        User user = new User();
        user.setId(pref.getLong(KEY_USER_ID, -1));
        user.setName(pref.getString(KEY_NAME, ""));
        user.setEmail(pref.getString(KEY_EMAIL, ""));
        user.setProfilePic(pref.getString(KEY_PROFILE_PIC, ""));

        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}