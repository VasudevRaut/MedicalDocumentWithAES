package com.example.dfcsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
//import com.android.volley.toolbox.InputStreamRequest;

public class PdfViewer extends AppCompatActivity {
    private static final int PICK_PDF_REQUEST = 1;
    private static final String TAG = "PDFDataManager";

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);


        Button btnOpenPdf = findViewById(R.id.btn_open_pdf);
        btnOpenPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseFirestore.getInstance();
                db.collection("PDFdata")
                        .document("Vasudev")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    // DocumentSnapshot data may be null if the document doesn't exist
                                    String pdfContent = documentSnapshot.getString("pdfContent");
//                                    Toast.makeText(PdfViewer.this, ""+pdfContent.length(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "PDF Content: " + pdfContent);
                                    decrypt(pdfContent);
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error getting PDF data", e);
                            }
                        });

            }
        });
    }




    public void decrypt(String response)
    {
        Log.d("URIiiiiiiiiiii ", response);
//        Toast.makeText(this, ""+response.length(), Toast.LENGTH_SHORT).show();

        byte[] byteArray = convertStringToByteArray(response);

        try {
            byte[] byteArrayd  = AESUtils.decrypt(byteArray,"MySecretKey12345");

            PDFView pdfViewer = findViewById(R.id.pdfView);

            pdfViewer.fromBytes(byteArrayd)
                    .defaultPage(0)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(10)
                    .load();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }



    private static byte[] convertStringToByteArray(String input) {
                byte[] pdfData = Base64.decode(input, Base64.DEFAULT);

                return pdfData;
    }


}