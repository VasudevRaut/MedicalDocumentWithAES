package com.example.dfcsproject.Lab;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dfcsproject.Lab.Dashboard;
import com.example.dfcsproject.Doctor.InvestorModel;
import com.example.dfcsproject.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;


public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.LeadData>{

    List<InvestorModel> dataholder2;




    private OnItemClickListener listener;


    AlertDialog.Builder builder ;
    Activity activity;



    Context context;
    final String sharedPreferencesFileTitle = "ecoview";
    public MentorAdapter(List<InvestorModel> dataholder2, Context context, Activity activity) {
        this.dataholder2 = dataholder2;
        this.context = context;
        this.activity = activity;

    }

    public interface OnItemClickListener {
        void onItemClick(String emails,String user_n,String user_b);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void setFilteredList(List<InvestorModel> filteredList) {
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

        holder.experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    // Pass the email value to the listener
                    listener.onItemClick("item.getEmail()","vasu raut","expperience and good knowledge");
                }
                // Get the SharedPreferences object
                SharedPreferences sharedPreferences = context.getSharedPreferences("hospital", Context.MODE_PRIVATE);

// Get the SharedPreferences Editor to modify SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

// Put a value into SharedPreferences
                editor.putString("key_email", "example@example.com");

// Apply changes
                editor.apply();




                final BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(
                        context,R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(context)
                        .inflate(
                                R.layout.layout_bottom_sheetdoctor, holder.bottomsheet
                        );


                TextView name = bottomSheetView.findViewById(R.id.med_name);
                TextView bio = bottomSheetView.findViewById(R.id.bio);
                name.setText(dataholder2.get(position).getUser_name());
//                Toast.makeText(activity, ""+dataholder2.get(position).getUser_bio(), Toast.LENGTH_SHORT).show();
                bio.setText(dataholder2.get(position).getUser_bio());

                TextView uplode = bottomSheetView.findViewById(R.id.uplode);

                uplode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                        Toast.makeText(context, "Vasudev", Toast.LENGTH_SHORT).show();
                        ((Dashboard) activity).pickPdfFile();




                    }
                });






//                name.setText(dataholder2.get(position).getFirst_name()+ " "+ dataholder2.get(position).getLast_name());
//                bio.setText(dataholder2.get(position).getBio());

                RecyclerView recyclerView = bottomSheetView.findViewById(R.id.sevices);














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
        TextView experience;
        LinearLayout bottomsheet;
        public LeadData(@NonNull View itemView)
        {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            bottomsheet = itemView.findViewById(R.id.bottomsheetcontainer);
            experience = itemView.findViewById(R.id.expperience);
//
            name = itemView.findViewById(R.id.med_name);
            bio = itemView.findViewById(R.id.bio);

        }
    }


























}
