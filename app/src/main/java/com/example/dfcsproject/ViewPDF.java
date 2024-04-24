package com.example.dfcsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class ViewPDF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        String resp = getIntent().getStringExtra("pdfcontent");
        decrypt(resp);


    }



    public void decrypt(String response)
    {

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