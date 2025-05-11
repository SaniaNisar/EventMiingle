    package com.app.eventmingle.activities;

    import android.os.Bundle;
    import android.content.Intent;
    import android.os.Handler;
    //import android.view.animation.Animation;
    //import android.view.animation.AnimationUtils;
    //import android.widget.FrameLayout;
    import androidx.appcompat.app.AppCompatActivity;

    import com.app.eventmingle.R;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

    public class SplashActivity extends AppCompatActivity {
        private FirebaseAuth mAuth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            // Apply animation to the FrameLayout (logo + background circle)

            //Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);

            new Handler().postDelayed(() -> {
                mAuth =  FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // User is signed in, go to MainActivity
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                } else {
                    // User is not signed in, go to LoginActivity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish(); // finish splash
            }, 5000);
        }
    }
