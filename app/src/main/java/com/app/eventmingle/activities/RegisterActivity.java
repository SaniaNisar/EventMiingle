package com.app.eventmingle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.eventmingle.R;
import com.app.eventmingle.models.User;
import com.app.eventmingle.utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin;
    Button btnSignup;
    EditText etName, etPassword, etCPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide action bar if present
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        init();

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        btnSignup.setOnClickListener(v -> {
            String email = etName.getText().toString().trim();
            String password = etPassword.getText().toString();
            String cpassword = etCPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                etName.setError("Enter your email");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Enter your password");
                return;
            }

            if (TextUtils.isEmpty(cpassword)) {
                etCPassword.setError("Confirm your password");
                return;
            }

            if (!password.equals(cpassword)) {
                etPassword.setError("Password mismatch");
                etCPassword.setError("Password mismatch");
                return;
            }

            // Firebase Auth registration
            FirebaseUtils.getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        String uid = FirebaseUtils.getCurrentUserId();

                        // Initialize empty user profile
                        User user = new User();
                        user.setEmail(email);
                        user.setRole("attendee"); // default role, can be updated later
                        user.setName("");
                        user.setPhoneNumber("");
                        user.setProfileImageUrl("");
                        user.setCreatedEvents(new HashMap<>());
                        user.setInvitedEvents(new HashMap<>());
                        user.setAcceptedInvites(new HashMap<>());
                        user.setManagingEvents(new HashMap<>());
                        user.setVendorServices(new HashMap<>());

                        // Save user profile to Realtime DB
                        FirebaseUtils.getUsersRef().child(uid).setValue(user)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, DashboardActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Failed to save profile: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }

    private void init() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvLogin = findViewById(R.id.tvLogin);
        btnSignup = findViewById(R.id.btnSignup);
        etName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etCPassword = findViewById(R.id.etConfirmPassword);
    }
}
