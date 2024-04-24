package com.example.dfcsproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Random;



public class Login extends AppCompatActivity {
    String email, pass;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    String data;
    final String sharedPreferencesFileTitle = "health";
    TextView account, forg;

    EditText userLogin, userPassword;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.new_account);
//        forgot_pass = findViewById(R.id.forgot);
        login_btn = findViewById(R.id.login_button);
        init();

        setEventLis();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Successful login ";
            String description = "Healthशास्त्र wishes you a healthy life ";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        Button login_button = findViewById(R.id.login_button);
        forg = findViewById(R.id.forgot);
        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Login.this,EnterOTP.class);
//                startActivity(intent);
            }
        });

//        eye = findViewById(R.id.eye);
        userLogin = findViewById(R.id.userLogin);
        userPassword = findViewById(R.id.userPassword);
//        TextView signup = findViewById(R.id.signup);




        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });


//        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);
//
//        if (sharedPreferences.contains("user_phone") && sharedPreferences.contains("user_pass")) {
//            String mobile = sharedPreferences.getString("user_phone", "");
//            String pass = sharedPreferences.getString("user_pass", "");
//
//
//            if (!mobile.equals("") && !pass.equals("")) {
//                Intent intent = new Intent(Login.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//
//        }
//
//
//        account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Login.this, SignUp.class);
//                startActivity(intent);
//
//            }
//        });
//
//
//        login_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                String userlogins = userLogin.getText().toString();
//                String userPasswords = userPassword.getText().toString();
//
//                userValidation(userlogins, userPasswords);
//            }
//        });
    }
    private void init() {

        email = pass = "-1";

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void getText() {
        email = userLogin.getText().toString().trim();
        pass = userPassword.getText().toString().trim();

//        userLogin = findViewById(R.id.userLogin);
//        userPassword = findViewById(R.id.userPassword);

        if (email.equals("")) email = "-1";
        if (pass.equals("")) pass = "-1";
    }

    private int check() {
        if (email.equals("-1") || pass.equals("-1")) return 0;
        return 1;
    }

    private void setEventLis() {


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getText();

                if (check() == 1) {
                    firebaseAuth
                            .signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, HomeScreen.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(Login.this, "Mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private static final String CHANNEL_ID = "my_notification_channel";

    // Method to generate a unique notificationId
    private int generateNotificationId() {
        return new Random().nextInt(100000);
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo1)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        int notificationId = generateNotificationId();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}