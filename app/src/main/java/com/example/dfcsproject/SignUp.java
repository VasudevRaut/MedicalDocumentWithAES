package com.example.dfcsproject;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dfcsproject.Doctor.Dashboard;
import com.example.dfcsproject.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;


public class SignUp extends AppCompatActivity {

    double longi;
    double lati;
    String token;
    private FirebaseAuth firebaseAuth;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    private EditText firstNameEditText, lastNameEditText, emailEditText, numberEditText, ageEditText,
            passwordEditText, confirmPasswordEditText, educationEditText, bioEditText, professionEditText,
            chargesEditText, hospitalNameEditText, addressEditText;
    private CheckBox agreeCheckBox;
    private Button createAccountButton;








    String firstName;
    String number ;
    String email ;
    String confirmPassword;
    String bio,type ;
    boolean agreed;

    FirebaseFirestore firebaseFirestore;

    private ActivitySignUpBinding binding;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);

        binding = ActivitySignUpBinding.inflate((getLayoutInflater()));
        init();

//        setEventLis();

        setContentView(binding.getRoot());

//        getToken();

        firstNameEditText = findViewById(R.id.first_name);
        lastNameEditText = findViewById(R.id.last_name);
        emailEditText = findViewById(R.id.email);
        numberEditText = findViewById(R.id.number);
        ageEditText = findViewById(R.id.age);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        educationEditText = findViewById(R.id.education);
        bioEditText = findViewById(R.id.bio);
        professionEditText = findViewById(R.id.profession);
        chargesEditText = findViewById(R.id.charges);
        hospitalNameEditText = findViewById(R.id.hospital_name);
        addressEditText = findViewById(R.id.address);
        agreeCheckBox = findViewById(R.id.agreeCheckBox);
        createAccountButton = findViewById(R.id.create_account);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();

                if (check() == 1) {
                    createNew();
                } else {
                    Toast.makeText(SignUp.this, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    private int check() {
        if (firstName.equals("-1") || number.equals("-1")) {
            return 0;
        }

        return 1;
    }

    private void getText() {



        firstName = firstNameEditText.getText().toString();

         number = numberEditText.getText().toString();
//         password = passwordEditText.getText().toString();
         confirmPassword = confirmPasswordEditText.getText().toString();
                email = emailEditText.getText().toString();

        bio = bioEditText.getText().toString();
         agreed = agreeCheckBox.isChecked();
         type = binding.type.getText().toString();

    }

    private void init() {
        firstName = number ="-1";

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void createNew() {
        firebaseAuth
                .createUserWithEmailAndPassword(email, confirmPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignUp.this, "Registered", Toast.LENGTH_SHORT).show();
                        firebaseFirestore
                                .collection("User")
                                .document(email)
                                .set(new User(firstName,number,bio, email ,type))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignUp.this, "Data Entered", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this, HomeScreen.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }












}