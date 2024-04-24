package com.example.dfcsproject.IncomingRequests;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dfcsproject.AESUtils;
import com.example.dfcsproject.Doctor.InvestorModel;
import com.example.dfcsproject.Lab.Dashboard;
import com.example.dfcsproject.R;
import com.example.dfcsproject.ViewPDF;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.LeadData>{

    List<RequestModel> dataholder2;


    private FirebaseFirestore db;
    private static final String TAG = "PDFDataManager";
    private static final int CHUNK_SIZE = 500;
    private static final int PICK_PDF_FILE = 1;
    public BottomSheetDialog bottomSheetDialog1;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;



    AlertDialog.Builder builder ;



    Context context;
    final String sharedPreferencesFileTitle = "ecoview";
    public MentorAdapter(List<RequestModel> dataholder2, Context context) {
        this.dataholder2 = dataholder2;
        this.context = context;

    }


    public void setFilteredList(List<RequestModel> filteredList) {
        Log.println(Log.DEBUG,"debug", "Finally"+filteredList);
        this.dataholder2 = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LeadData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        init();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mentorcard  ,parent,false);
        return new LeadData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadData holder, int position) {



        holder.name.setText(dataholder2.get(position).getUser_name());
        holder.bio.setText(dataholder2.get(position).getUser_bio());
//        holder.name.setText(dataholder2.get(position).getName());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                 bottomSheetDialog1= new BottomSheetDialog(
                        context,R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(context)
                        .inflate(
                                R.layout.layout_bottom_sheet, holder.bottomsheet
                        );


                TextView name = bottomSheetView.findViewById(R.id.med_name);
                TextView bio = bottomSheetView.findViewById(R.id.bio);


                name.setText(dataholder2.get(position).getUser_name());
                bio.setText(dataholder2.get(position).getUser_bio());

                RecyclerView recyclerView = bottomSheetView.findViewById(R.id.sevices);

                TextView viewdoc,sendtomedical,sendtolab;

                viewdoc  = bottomSheetView.findViewById(R.id.viewDocument);
                sendtolab = bottomSheetView.findViewById(R.id.sendtolab);
                sendtomedical = bottomSheetView.findViewById(R.id.sendtomedical);


                viewdoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent  = new Intent(context, ViewPDF.class);
                        intent.putExtra("pdfcontent",dataholder2.get(position).getPdfdata());
                        context.startActivity(intent);

                    }
                });

                sendtolab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                            processPdfByteArray(dataholder2.get(position).getPdfdata(),position);
                        showSpinnerDialog(position);
//                        bottomSheetDialog1.dismiss();


                    }
                });
                sendtomedical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        processmedical(dataholder2.get(position).getPdfdata(),position);
                        showSpinnerDialogmedical(position);
//                        bottomSheetDialog1.dismiss();

                    }
                });












//                List<MentorServicesModel> data_list;
//                MentorServicesAdpter pageInfoAdapter;
//                LinearLayoutManager layoutManager;
//
//                layoutManager = new LinearLayoutManager(context);
//
//                layoutManager.setOrientation(RecyclerView.VERTICAL);
//                recyclerView.setLayoutManager(layoutManager);
//
//
//                data_list = new ArrayList<>();
//                data_list.add(new MentorServicesModel());
//                data_list.add(new MentorServicesModel());
////                data_list.add(new InvestorModel());
////                data_list.add(new InvestorModel());
////                data_list.add(new InvestorModel());
////                data_list.add(new InvestorModel());
//
//                pageInfoAdapter = new MentorServicesAdpter(data_list, context);
//                recyclerView.setAdapter(pageInfoAdapter);








                bottomSheetDialog1.setContentView(bottomSheetView);
                bottomSheetDialog1.show();


            }
        });
//






    }




    @Override
    public int getItemCount() {
        return dataholder2.size();
    }


    class LeadData extends RecyclerView.ViewHolder
    {
        LinearLayout card;
        TextView name,bio;
        LinearLayout bottomsheet;
        public LeadData(@NonNull View itemView)
        {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            bottomsheet = itemView.findViewById(R.id.bottomsheetcontainer);
//
            name = itemView.findViewById(R.id.med_name);
            bio = itemView.findViewById(R.id.bio);

        }
    }






    private void processPdfByteArray(String data,int position,String email) {


        try {

            String keyString = "MySecretKey12345";
//            byte[] encryptedPdf = AESUtils.encrypt(pdfByteArray, keyString);

//            String sn =  Base64.encodeToString(encryptedPdf, Base64.DEFAULT);


//            SharedPreferences sharedPreferences = getSharedPreferences("my_shared_prefs", MODE_PRIVATE);



            LocalDateTime currentDateTime = LocalDateTime.now();

            // Create a formatter to format the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format the date and time using the formatter
            String formattedDateTime = currentDateTime.format(formatter);

            db = FirebaseFirestore.getInstance();
            Map<String, Object> pdfData = new HashMap<>();
            pdfData.put("pdfContent", data);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();

            String id = UUID.randomUUID().toString();
            db.collection("Request")
                    .document(id)
                    .set(new RequestModel(data,email,firebaseAuth.getCurrentUser().getEmail(),formattedDateTime,dataholder2.get(position).getUser_name(),dataholder2.get(position).getUser_bio(),"usertolab"))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "PDF data successfully written!");
                            Toast.makeText(context, "Uplode Successfuly", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog1.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
//                            Toast.makeText(Dashboard.this, "Something wrong", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(context, "In ", Toast.LENGTH_SHORT).show();

//                            Log.e(TAG, "Error writing PDF data", e);
                        }
                    });


        } catch (Exception e) {
//            Toast.makeText(context, "In "+e.getMessage(), Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }



    }


    private void processmedical(String data,int position,String email) {


        try {

            String keyString = "MySecretKey12345";
//            byte[] encryptedPdf = AESUtils.encrypt(pdfByteArray, keyString);

//            String sn =  Base64.encodeToString(encryptedPdf, Base64.DEFAULT);


//            SharedPreferences sharedPreferences = getSharedPreferences("my_shared_prefs", MODE_PRIVATE);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();

            LocalDateTime currentDateTime = LocalDateTime.now();

            // Create a formatter to format the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format the date and time using the formatter
            String formattedDateTime = currentDateTime.format(formatter);

            db = FirebaseFirestore.getInstance();
            Map<String, Object> pdfData = new HashMap<>();
            pdfData.put("pdfContent", data);
            String id = UUID.randomUUID().toString();
            db.collection("Request")
                    .document(id)
                    .set(new RequestModel(data,email,firebaseAuth.getCurrentUser().getEmail(),formattedDateTime,dataholder2.get(position).getUser_name(),dataholder2.get(position).getUser_bio(),"usertomedical"))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "PDF data successfully written!");
                            Toast.makeText(context, "Uplode Successfuly", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog1.dismiss();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
//                            Toast.makeText(Dashboard.this, "Something wrong", Toast.LENGTH_SHORT).show();

//                            Log.e(TAG, "Error writing PDF data", e);
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }



    }



    private void showSpinnerDialog(int positionss) {
        // Define an array of items for the spinner
//        final String[] values = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

//        data_list = new ArrayList<>();
        firebaseFirestore.collection("User")
                .whereEqualTo("user_type", "lab")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        List<InvestorModel>  users= queryDocumentSnapshots.toObjects(InvestorModel.class);
                        List<String> userNames = new ArrayList<>();
                        for (InvestorModel userr : users) {
                            userNames.add(userr.getUser_email());
                        }



                        final Spinner spinner = new Spinner(context);

                        // Create an ArrayAdapter using the string array and a default spinner layout
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, userNames);

                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Apply the adapter to the spinner
                        spinner.setAdapter(adapter);

                        // Create a new AlertDialog builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Choose an option")
                                .setView(spinner)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Get the selected item from the spinner
                                        String selectedItem = userNames.get(spinner.getSelectedItemPosition());

                                        processPdfByteArray(dataholder2.get(positionss).getPdfdata(),positionss,selectedItem);









//                                        Toast.makeText(context, ""+selectedItem, Toast.LENGTH_SHORT).show();
                                        // Do something with the selected item
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // User cancelled the dialog
                                    }
                                });

                        // Create and show the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();





////                        Toast.makeText(Dashboard.this, ""+users.size(), Toast.LENGTH_SHORT).show();
////                        data_list.addAll(users);
////
////                        for(int i = 0 ; i < users.size();i++)
////                        {
//////                            data_list.add(new User(users.get(i).getUser_name(),);
////                        Toast.makeText(Dashboard.this, "3", Toast.LENGTH_SHORT).show();
////                        pageInfoAdapter = new com.example.dfcsproject.Lab.MentorAdapter(data_list, com.example.dfcsproject.Lab.Dashboard.this, Dashboard.this);
////                        upcomingrecycler.setAdapter(pageInfoAdapter);
////                        pageInfoAdapter.setOnItemClickListener(new com.example.dfcsproject.Lab.MentorAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(String emails,String user_n,String user_b) {
//                                // Pass the email value to the Adapter
////                                Toast.makeText(Dashboard.this, "mail"+ emails, Toast.LENGTH_SHORT).show();
//                                email = emails;
//                                user_name  = user_n;
//                                user_bio = user_b;
//                            }
//                        });

//                        }



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });









    }








    private void showSpinnerDialogmedical(int positionss) {
        // Define an array of items for the spinner
//        final String[] values = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

//        data_list = new ArrayList<>();
        firebaseFirestore.collection("User")
                .whereEqualTo("user_type", "medical")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        List<InvestorModel>  users= queryDocumentSnapshots.toObjects(InvestorModel.class);
                        List<String> userNames = new ArrayList<>();
                        for (InvestorModel userr : users) {
                            userNames.add(userr.getUser_email());
                        }



                        final Spinner spinner = new Spinner(context);

                        // Create an ArrayAdapter using the string array and a default spinner layout
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, userNames);

                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Apply the adapter to the spinner
                        spinner.setAdapter(adapter);

                        // Create a new AlertDialog builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Choose an option")
                                .setView(spinner)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Get the selected item from the spinner
                                        String selectedItem = userNames.get(spinner.getSelectedItemPosition());

                                        processmedical(dataholder2.get(positionss).getPdfdata(),positionss,selectedItem);









//                                        Toast.makeText(context, ""+selectedItem, Toast.LENGTH_SHORT).show();
                                        // Do something with the selected item
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // User cancelled the dialog
                                    }
                                });

                        // Create and show the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();





////                        Toast.makeText(Dashboard.this, ""+users.size(), Toast.LENGTH_SHORT).show();
////                        data_list.addAll(users);
////
////                        for(int i = 0 ; i < users.size();i++)
////                        {
//////                            data_list.add(new User(users.get(i).getUser_name(),);
////                        Toast.makeText(Dashboard.this, "3", Toast.LENGTH_SHORT).show();
////                        pageInfoAdapter = new com.example.dfcsproject.Lab.MentorAdapter(data_list, com.example.dfcsproject.Lab.Dashboard.this, Dashboard.this);
////                        upcomingrecycler.setAdapter(pageInfoAdapter);
////                        pageInfoAdapter.setOnItemClickListener(new com.example.dfcsproject.Lab.MentorAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(String emails,String user_n,String user_b) {
//                                // Pass the email value to the Adapter
////                                Toast.makeText(Dashboard.this, "mail"+ emails, Toast.LENGTH_SHORT).show();
//                                email = emails;
//                                user_name  = user_n;
//                                user_bio = user_b;
//                            }
//                        });

//                        }



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });









    }









}
