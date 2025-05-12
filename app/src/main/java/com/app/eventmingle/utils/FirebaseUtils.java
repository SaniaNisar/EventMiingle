
package com.app.eventmingle.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    private static final String TAG = "FirebaseUtils";

    // Get Firebase Auth instance
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    // Get current authenticated user
    public static FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    // Get current user UID
    public static String getCurrentUserId() {
        FirebaseUser user = getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }

    // Get Realtime Database root reference
    public static DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    // Get reference to users node
    public static DatabaseReference getUsersRef() {
        return getDatabase().child("users");
    }

    // Get reference to events node
    public static DatabaseReference getEventsRef() {
        return getDatabase().child("events");
    }

    // Get reference to chats node
    public static DatabaseReference getChatsRef() {
        return getDatabase().child("chats");
    }

    // Get reference to vendors node
    public static DatabaseReference getVendorsRef() {
        return getDatabase().child("vendors");
    }

    // Get reference to notifications for a specific user
    public static DatabaseReference getNotificationsRef(String userId) {
        return getDatabase().child("notifications").child(userId);
    }

    // Get reference to invitations
    public static DatabaseReference getInvitationsRef() {
        return getDatabase().child("invitations");
    }
    public static DatabaseReference getGuestsRef() {
        return FirebaseDatabase.getInstance().getReference("guests");
    }

    public static DatabaseReference getBudgetsRef() {
        return FirebaseDatabase.getInstance().getReference("budgets");
    }


    // Check if user is logged in
    public static boolean isUserLoggedIn() {
        return getCurrentUser() != null;
    }

    // Sign out current user
    public static void signOut() {
        getAuth().signOut();
        Log.d(TAG, "User signed out.");
    }
}
