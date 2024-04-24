package com.example.dfcsproject.IncomingRequests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dfcsproject.Doctor.InvestorModel;
import com.example.dfcsproject.R;
import com.example.dfcsproject.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.pdf.XfaForm;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    RecyclerView upcomingrecycler;
    List<InvestorModel> data;
    List<RequestModel> data_list;
    private MentorAdapter pageInfoAdapter;
    LinearLayoutManager layoutManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard4);
        upcomingrecycler = findViewById(R.id.mentorlist);
        layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        upcomingrecycler.setLayoutManager(layoutManager);

//        fetchUserInfo();
        data_list = new ArrayList<>();


//        data_list.addAll(data);
//        data_list.add(new InvestorModel());
//        data_list.add(new InvestorModel());
//        data_list.add(new InvestorModel());
//        data_list.add(new InvestorModel());
//        data_list.add(new InvestorModel());
//        data_list.add(new InvestorModel());
//        data_list.add(new InvestorModel());


//
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        data_list = new ArrayList<>();
        firebaseFirestore.collection("Request")
                .whereEqualTo("user_email", firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        List<RequestModel>  users= queryDocumentSnapshots.toObjects(RequestModel.class);
//                        Toast.makeText(Dashboard.this, ""+users.size(), Toast.LENGTH_SHORT).show();
                        data_list.addAll(users);
//
//                        for(int i = 0 ; i < users.size();i++)
//                        {
////                            data_list.add(new User(users.get(i).getUser_name(),);
                            pageInfoAdapter = new MentorAdapter(data_list, Dashboard.this);
                            upcomingrecycler.setAdapter(pageInfoAdapter);

//                        }



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




























//
//        pageInfoAdapter = new MentorAdapter(data_list, Dashboard.this);
//        upcomingrecycler.setAdapter(pageInfoAdapter);


    }




























}