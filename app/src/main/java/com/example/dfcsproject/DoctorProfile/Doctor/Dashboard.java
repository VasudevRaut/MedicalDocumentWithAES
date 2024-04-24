package com.example.dfcsproject.DoctorProfile.Doctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dfcsproject.AESUtils;
import com.example.dfcsproject.IncomingRequests.RequestModel;
import com.example.dfcsproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Dashboard extends AppCompatActivity {
    RecyclerView upcomingrecycler;
    List<InvestorModel> data;
    List<InvestorModel> data_list;
    private MentorAdapter pageInfoAdapter;
    LinearLayoutManager layoutManager;



    private String email,user_name,user_bio;


    private FirebaseFirestore db;
    private static final String TAG = "PDFDataManager";
    private static final int CHUNK_SIZE = 500;
    private static final int PICK_PDF_FILE = 1;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        upcomingrecycler = findViewById(R.id.mentorlist);
//        layoutManager = new LinearLayoutManager(this);
//
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        upcomingrecycler.setLayoutManager(layoutManager);
//
////        fetchUserInfo();
//        data_list = new ArrayList<>();
//
////.whereEqualTo("type", "user")
////        data_list.addAll(data);
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//
//        data_list = new ArrayList<>();
//        firebaseFirestore.collection("User")
//                .whereEqualTo("user_type", "doctor")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//
//                        List<InvestorModel>  users= queryDocumentSnapshots.toObjects(InvestorModel.class);
////                        Toast.makeText(Dashboard.this, ""+users.size(), Toast.LENGTH_SHORT).show();
//                        data_list.addAll(users);
////
////                        for(int i = 0 ; i < users.size();i++)
////                        {
//////                            data_list.add(new User(users.get(i).getUser_name(),);
////                        Toast.makeText(Dashboard.this, "3", Toast.LENGTH_SHORT).show();
//                        pageInfoAdapter = new MentorAdapter(data_list, Dashboard.this,Dashboard.this);
//                        upcomingrecycler.setAdapter(pageInfoAdapter);
//                        pageInfoAdapter.setOnItemClickListener(new MentorAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(String emails,String user_n,String user_b) {
//                                // Pass the email value to the Adapter
////                                Toast.makeText(Dashboard.this, "mail"+ emails, Toast.LENGTH_SHORT).show();
//                                email = emails;
//                                user_name  = user_n;
//                                user_bio = user_b;
//                            }
//                        });
//
////                        }
//
//
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
////                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
////        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
////        data_list.add(new InvestorModel());
////        pageInfoAdapter = new MentorAdapter(data_list, Dashboard.this,Dashboard.this);
////        upcomingrecycler.setAdapter(pageInfoAdapter);
//
//


    }



    public void pickPdfFile() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_FILE && resultCode == RESULT_OK && data != null) {


            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                byte[] pdfByteArray = convertInputStreamToByteArray(inputStream);


                // Now   you have the byte array, you can use it as needed
                // For example, pass it to another method for further processing
                processPdfByteArray(pdfByteArray);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading PDF file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }















    private void processPdfByteArray(byte[] pdfByteArray) {


        try {
//            Toast.makeText(Dashboard.this, "Something wrong1", Toast.LENGTH_SHORT).show();
            String keyString = "MySecretKey12345";
            byte[] encryptedPdf = AESUtils.encrypt(pdfByteArray, keyString);
//        byte[] after = AESUtils.decrypt(encryptedPdf,keyString);
            String sn =  Base64.encodeToString(encryptedPdf, Base64.DEFAULT);
//            Toast.makeText(Dashboard.this, "Something wrong2", Toast.LENGTH_SHORT).show();
//            PDFView pdfViewer = findViewById(R.id.pdfView);
//
//            pdfViewer.fromBytes(pdfByteArray)
//                    .defaultPage(0)
//                    .scrollHandle(new DefaultScrollHandle(this))
//                    .spacing(10)
//                    .load();

//            Toast.makeText(Dashboard.this, "Something wrong5", Toast.LENGTH_SHORT).show();
// Get the SharedPreferences object
            SharedPreferences sharedPreferences = getSharedPreferences("my_shared_prefs", MODE_PRIVATE);

// Retrieve a value from SharedPreferences
//            String email = sharedPreferences.getString("key_email", "default_value_if_not_found");


            LocalDateTime currentDateTime = LocalDateTime.now();

            // Create a formatter to format the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format the date and time using the formatter
            String formattedDateTime = currentDateTime.format(formatter);

            db = FirebaseFirestore.getInstance();
            Map<String, Object> pdfData = new HashMap<>();
            pdfData.put("pdfContent", sn);
            String id = UUID.randomUUID().toString();
            db.collection("Request")
                    .document(id)
                    .set(new RequestModel(sn,email,firebaseAuth.getCurrentUser().getEmail(),formattedDateTime,user_name,user_bio,"usertodr"))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "PDF data successfully written!");
                            Toast.makeText(Dashboard.this, "Uplode / Send Successfuly", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
//                            Toast.makeText(Dashboard.this, "Something wrong", Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "Error writing PDF data", e);
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }



    }



}