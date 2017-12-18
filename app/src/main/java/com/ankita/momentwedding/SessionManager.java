package com.ankita.momentwedding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by shyam group on 10/9/2017.
 */

public class SessionManager {

    public static final String guest_id = "guest_id";
    public static final String wedding_id = "wedding_id";
    public static final String profile_id = "profile_id";
    public static final String mobile = "mobile";

    // Sharedpref file name
    private static final String PREF_NAME = "userdetail";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    private Editor editor;
    // Context
    private Context _context;
    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * @param id
     * @param weddingId
     * @param profileId
     * @param mobileNo
     */
    public void createLoginSession(String id, String weddingId, String profileId, String mobileNo) {
        // Storing login value as TRUE
        try {

            editor.putBoolean(IS_LOGIN, true);
            // Storing name in pref
            editor.putString(guest_id, id);
            editor.putString(wedding_id, weddingId);
            editor.putString(profile_id, profileId);
            editor.putString(mobile, mobileNo);

            // commit changes
            editor.commit();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    public int checkLogin() {
        // Check login status

        int flag;

        if (!this.isLoggedIn()) {
            flag = 0;
        } else {

            flag = 1;
        }

        return flag;
    }

    /**
     * Get stored session data
     */

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(guest_id, pref.getString(guest_id, null));
        user.put(wedding_id, pref.getString(wedding_id, null));
        user.put(profile_id, pref.getString(profile_id, null));
        user.put(mobile, pref.getString(mobile, null));

        return user;
    }

    /**
     * Clear session details
     */
    public void clearUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
    private boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void RemoveUserdata() {
        editor.remove(guest_id);
        editor.remove(wedding_id);
        editor.remove(profile_id);
        editor.remove(mobile);

        editor.commit();
    }
}
