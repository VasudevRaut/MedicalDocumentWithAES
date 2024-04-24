package com.example.dfcsproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private FirebaseFirestore db;
    private static final String TAG = "PDFDataManager";
    private static final int CHUNK_SIZE = 500;
    private static final int PICK_PDF_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pickPdfButton = findViewById(R.id.pick_pdf_button);
        pickPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPdfFile();
            }
        });

        Button pickPdfButton1 = findViewById(R.id.pick_pdf_buttonnext);
        pickPdfButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,PdfViewer.class);
                startActivity(intent);


            }
        });



    }

    private void pickPdfFile() {
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



//
//
//        byte[] byteArray = new byte[100];
//
//        // Add some values into the byte array
//        for (int i = 0; i < byteArray.length; i++) {
//            // For demonstration, let's add the ASCII value of 'A' + index to the byte array
//            byteArray[i] = (byte) ('A' + i);
//        }

//        String sn =  Base64.encodeToString(byteArray, Base64.DEFAULT);
//        Log.d("PDF string data", sn+"");

//        byte[] pdfData = Base64.decode(sn, Base64.DEFAULT);

//        for (byte b : byteArray) {
////            System.out.print(b + " ");
//            Log.d("URIiii ", b+"");
//
//        }
//        Log.d("nnnnnnnnnnnn ", "-------------------------------------------------------------------");
//
//
//        for (byte b : pdfData) {
////            System.out.print(b + " ");
//            Log.d("new dattt ", b + "");
//        }
//










//        String ss = "";
        try {
        String keyString = "MySecretKey12345";
        byte[] encryptedPdf = AESUtils.encrypt(pdfByteArray, keyString);
//        byte[] after = AESUtils.decrypt(encryptedPdf,keyString);
//            Toast.makeText(this, ""+encryptedPdf.length, Toast.LENGTH_SHORT).show();
            String sn =  Base64.encodeToString(encryptedPdf, Base64.DEFAULT);
//            Toast.makeText(MainActivity.this, ""+sn.length(), Toast.LENGTH_SHORT).show();

            PDFView pdfViewer = findViewById(R.id.pdfView);

            pdfViewer.fromBytes(pdfByteArray)
                    .defaultPage(0)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(10)
                    .load();




//            String sn =  Base64.encodeToString(encryptedPdf, Base64.DEFAULT);
            db = FirebaseFirestore.getInstance();
            Map<String, Object> pdfData = new HashMap<>();
            pdfData.put("pdfContent", sn);
//            addChunkedPDFData("Vasudev",sn);
//            ChunkedPDFDataManager chunkedPDFDataManager = new ChunkedPDFDataManager();
//            chunkedPDFDataManager.getChunkedPDFData("Vasudev",this);
//            getChunkedPDFData("Vasudev",this);

            db.collection("PDFdata")
                    .document("Vasudev")
                    .set(pdfData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "PDF data successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.e(TAG, "Error writing PDF data", e);
                        }
                    });








//            RequestQueue queue = Volley.newRequestQueue(this);
//            String url = "https://ecoviewproperties.in/PCCOE/try.php";
//
//// Create a byte array
////            byte[] byteArray = ...; // Your byte array
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // Handle the response from PHP file
////                            Toast.makeText(MainActivity.this, "vv" + response, Toast.LENGTH_SHORT).show();
//                            Log.d("Response", response);
//                            // You can parse the response here if needed
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    // Handle errors
////                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    Log.e("Error", error.toString());
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<>();
//                    // Encode byte array as Base64 string before sending
//                    String base64Data = Base64.encodeToString(encryptedPdf, Base64.DEFAULT);
//                    // Put the Base64 string in the POST parameters
//                    params.put("base64_data", base64Data);
//                    Log.e("Error", base64Data);
//
//                    return params;
//                }
//            };
//
//// Add the request to the RequestQueue.
//            queue.add(stringRequest);
//
//









//
//
//
//
//
//
//
//


//
//            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }





//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    private void savePdfAndLogUri() {
//        try {
//            InputStream in = getAssets().open("eliza.pdf");
//            Uri savedFileUri = savePDFFile(getApplicationContext(), in, "files/pdf", "eliza.pdf", "resume");
//            Log.d("URI: ", savedFileUri.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    @NonNull
//    private Uri savePDFFile(@NonNull final Context context, @NonNull InputStream in,
//                            @NonNull final String mimeType,
//                            @NonNull final String displayName, @Nullable final String subFolder) throws IOException {
//        String relativeLocation = Environment.DIRECTORY_DOCUMENTS;
//
//        if (!TextUtils.isEmpty(subFolder)) {
//            relativeLocation += File.separator + subFolder;
//        }
//
//        final ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
//        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
//        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);
//        contentValues.put(MediaStore.Video.Media.TITLE, "SomeName");
//        contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
//        contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
//        final ContentResolver resolver = context.getContentResolver();
//        OutputStream stream = null;
//        Uri uri = null;
//
//        try {
//            final Uri contentUri = MediaStore.Files.getContentUri("external");
//            uri = resolver.insert(contentUri, contentValues);
//            ParcelFileDescriptor pfd;
//            try {
//                assert uri != null;
//                pfd = getContentResolver().openFileDescriptor(uri, "w");
//                assert pfd != null;
//                FileOutputStream out = new FileOutputStream(pfd.getFileDescriptor());
//
//                byte[] buf = new byte[4 * 1024];
//                int len;
//                while ((len = in.read(buf)) > 0) {
//
//                    out.write(buf, 0, len);
//                }
//                out.close();
//                in.close();
//                pfd.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            contentValues.clear();
//            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);
//            getContentResolver().update(uri, contentValues, null, null);
//            stream = resolver.openOutputStream(uri);
//            if (stream == null) {
//                throw new IOException("Failed to get output stream.");
//            }
//            return uri;
//        } catch (IOException e) {
//            // Don't leave an orphan entry in the MediaStore
//            resolver.delete(uri, null, null);
//            throw e;
//        } finally {
//            if (stream != null) {
//                stream.close();
//            }
//        }
//    }
//
//
//
//
//
//
//
//    private String pdfByteArrayToBase64(byte[] pdfByteArray) {
//        return android.util.Base64.encodeToString(pdfByteArray, android.util.Base64.DEFAULT);
//    }
//
//    // Dummy method to simulate fetching the PDF byte array
//    private byte[] getYourPdfByteArray() {
//        // Replace this with your method to fetch the PDF byte array
//        return new byte[]{/* Your PDF byte array */};
//    }
//
//
//
//
//
//
//
//
//
//
//    public void addChunkedPDFData(String pdfName, String pdfContent) {
////        Toast.makeText(this, ""+pdfName.length(), Toast.LENGTH_SHORT).show();
//        List<String> chunks = splitIntoChunks(pdfContent);
//        for (int i = 0; i < chunks.size(); i++) {
//            String chunk = chunks.get(i);
//            String chunkName = pdfName + "_chunk_" + i;
//            Map<String, Object> data = new HashMap<>();
//            data.put("chunk", chunk);
//
//            db.collection("PDFdata")
//                    .document(chunkName)
//                    .set(data)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "Chunk " + chunkName + " successfully written!");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e(TAG, "Error writing chunk " + chunkName, e);
//                        }
//                    });
//        }
//    }
//
//    public void getChunkedPDFData(String pdfName, final OnDataLoadedListener listener) {
//        db.collection("PDFdata")
//                .whereEqualTo("pdfName", pdfName)
//                .get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (DocumentSnapshot document : queryDocumentSnapshots) {
//                        String chunk = document.getString("chunk");
//                        stringBuilder.append(chunk);
//                    }
////                    Toast.makeText(this, ""+stringBuilder.length(), Toast.LENGTH_SHORT).show();
//
//                    if (listener != null) {
//                        listener.onDataLoaded(stringBuilder.toString());
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "Error getting chunked PDF data", e);
//                    if (listener != null) {
//                        listener.onDataLoadFailed(e);
//                    }
//                });
//    }

//    private List<String> splitIntoChunks(String content) {
//        List<String> chunks = new ArrayList<>();
//        for (int i = 0; i < content.length(); i += CHUNK_SIZE) {
//            chunks.add(content.substring(i, Math.min(i + CHUNK_SIZE, content.length())));
//        }
//        return chunks;
//    }
//
//    @Override
//    public void onDataLoaded(String data) {
//
//    }
//
//    @Override
//    public void onDataLoadFailed(Exception e) {
//
//    }
//
//    public interface OnDataLoadedListener {
//        void onDataLoaded(String data);
//
//        void onDataLoadFailed(Exception e);
//    }




}
