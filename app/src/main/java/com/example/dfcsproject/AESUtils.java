package com.example.dfcsproject;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static byte[] encrypt(byte[] input, String keyString) throws Exception {
        byte[] key = keyString.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, String keyString) throws Exception {
        byte[] key = keyString.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    public static void savePDFToStorage(Context context, byte[] pdfByteArray) {
        File folder = new File(Environment.getExternalStorageDirectory(), "DFCS");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                Log.e("PDFFileManager", "Failed to create directory");
                return;
            }
        }

        File pdfFile = new File(folder, "example.pdf");
        FileOutputStream fos = null;
        try {
            if (!pdfFile.exists()) {
                if (!pdfFile.createNewFile()) {
                    Log.e("PDFFileManager", "Failed to create file");
                    return;
                }
            }
            fos = new FileOutputStream(pdfFile);
            fos.write(pdfByteArray);
            fos.close();
            Log.d("PDFFileManager", "PDF saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("PDFFileManager", "Error saving PDF: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public static void savePdf(byte[] pdfBytes, String folderName, String fileName) {
        // Check if external storage is available
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // Handle error: external storage not available
            return;
        }

        // Get the directory for the external storage.
        File file = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!file.mkdirs()) {
            // Directory not created or already exists
        }

        try {
            File pdfFile = new File(file, fileName);
            FileOutputStream fos = new FileOutputStream(pdfFile);
            fos.write(pdfBytes);
            fos.close();
        } catch (IOException e) {
//            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            // Handle error
        }
    }

































}
