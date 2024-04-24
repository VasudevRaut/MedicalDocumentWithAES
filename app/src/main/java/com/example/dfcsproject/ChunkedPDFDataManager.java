package com.example.dfcsproject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkedPDFDataManager {

    private static final String TAG = "ChunkedPDFDataManager";
    private static final int CHUNK_SIZE = 500; // Adjust chunk size as needed
    private FirebaseFirestore db;

    public ChunkedPDFDataManager() {
        db = FirebaseFirestore.getInstance();
    }

    public void addChunkedPDFData(String pdfName, String pdfContent) {
        List<String> chunks = splitIntoChunks(pdfContent);
        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);
            String chunkName = pdfName + "_chunk_" + i;
            db.collection("PDFdata")
                    .document(chunkName)
                    .set(Collections.singletonMap("chunk", chunk))
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Chunk " + chunkName + " successfully written!"))
                    .addOnFailureListener(e -> Log.e(TAG, "Error writing chunk " + chunkName, e));
        }
    }

    public void getChunkedPDFData(String pdfName, final OnDataLoadedListener listener) {
        db.collection("PDFdata")
                .whereEqualTo("pdfName", pdfName)
                .orderBy("chunkName", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> chunks = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        chunks.add(document.getString("chunk"));
                    }
                    String concatenatedData = concatenateChunks(chunks);
                    if (listener != null) {
                        listener.onDataLoaded(concatenatedData);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting chunked PDF data", e);
                    if (listener != null) {
                        listener.onDataLoadFailed(e);
                    }
                });
    }

    private List<String> splitIntoChunks(String content) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < content.length(); i += CHUNK_SIZE) {
            chunks.add(content.substring(i, Math.min(i + CHUNK_SIZE, content.length())));
        }
        return chunks;
    }

    private String concatenateChunks(List<String> chunks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String chunk : chunks) {
            stringBuilder.append(chunk);
        }
        return stringBuilder.toString();
    }

    public interface OnDataLoadedListener {
        void onDataLoaded(String data);

        void onDataLoadFailed(Exception e);
    }
}
