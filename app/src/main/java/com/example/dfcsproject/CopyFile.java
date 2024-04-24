package com.example.dfcsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFile extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 101;
    private static final int PICK_PDF_REQUEST_CODE = 102;
    private static final int PICK_FOLDER_REQUEST_CODE = 103;

    private Button btnReadPdf;
    private Button btnCopyPdf;
    private File pickedPdfFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_file);

        btnReadPdf = findViewById(R.id.btn_read_pdf);
        btnCopyPdf = findViewById(R.id.btn_copy_pdf);

        btnReadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndReadPdf();
            }
        });

        btnCopyPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickedPdfFile != null) {
                    openFolderChooser();
                } else {
                    Toast.makeText(CopyFile.this, "Please select a PDF first", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void checkPermissionAndReadPdf() {
        if (ContextCompat.checkSelfPermission(CopyFile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openFileChooser();
        } else {
            ActivityCompat.requestPermissions(CopyFile.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_REQUEST_CODE);
    }

    private void openFolderChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, PICK_FOLDER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_PDF_REQUEST_CODE) {
                if (data != null) {
                    Uri pdfUri = data.getData();
                    if (pdfUri != null) {
                        pickedPdfFile = new File(pdfUri.getPath());
                        Toast.makeText(this, "PDF selected", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == PICK_FOLDER_REQUEST_CODE) {
                if (data != null) {
                    Uri folderUri = data.getData();
                    if (folderUri != null) {
                        copyPdfToFolder(folderUri);
                    }
                }
            }
        }
    }



// Inside MainActivity class

    private void copyPdfToFolder(Uri folderUri) {
        DocumentFile pickedPdfDocument = DocumentFile.fromSingleUri(this, Uri.fromFile(pickedPdfFile));
        if (pickedPdfDocument != null && pickedPdfDocument.exists()) {
            DocumentFile destFolder = DocumentFile.fromTreeUri(this, folderUri);
            if (destFolder != null && destFolder.exists() && destFolder.isDirectory()) {
                DocumentFile destFile = destFolder.createFile("application/pdf", pickedPdfDocument.getName());
                if (destFile != null) {
                    try {
                        InputStream in = getContentResolver().openInputStream(Uri.fromFile(pickedPdfFile));
                        OutputStream out = getContentResolver().openOutputStream(destFile.getUri());
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        out.close();
                        Toast.makeText(this, "PDF copied to folder", Toast.LENGTH_SHORT).show();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Toast.makeText(this, "Failed to copy PDF", Toast.LENGTH_SHORT).show();
    }


}