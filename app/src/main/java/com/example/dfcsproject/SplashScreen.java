package com.example.dfcsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME_OUT = 1000;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageView logoImageView = findViewById(R.id.logoImageView);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        logoImageView.startAnimation(scaleAnimation);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                init();

                checkLoginStatus();
                // Intent is used to switch from one activity to another.
//                Intent i = new Intent(SplashScreen.this, Login.class);
//                startActivity(i); // invoke the SecondActivity.
//                finish(); // the current activity will get finished.
//            }
//        }, SPLASH_SCREEN_TIME_OUT);



    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void checkLoginStatus() {
//        Toast.makeText(this, ""+firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                    finish();
                } else {

                    startActivity(new Intent(SplashScreen.this, Login.class));
                    finish();

                }
            }
        }, 1000);
    }
}