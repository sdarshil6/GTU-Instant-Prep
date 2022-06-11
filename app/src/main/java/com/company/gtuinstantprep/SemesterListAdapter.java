package com.company.gtuinstantprep;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SemesterListAdapter extends RecyclerView.Adapter<SemesterListAdapter.SemesterNameHolder> {

    Context context;
    ArrayList<Semesters> list;

    private static String semesterName;
    public static String getSemeseterName(){
        return semesterName;
    }

    public SemesterListAdapter(Context context, ArrayList<Semesters> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SemesterNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_card_design, parent, false);
        return new SemesterNameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterNameHolder holder, int position) {

        holder.tv_semesterName.setText(list.get(position).getName());
        holder.semester_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context.getApplicationContext(), AllSubjectsActivity.class);
                semesterName = list.get(holder.getAdapterPosition()).getName();
                view.startAnimation(clickAnimation());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public AlphaAnimation clickAnimation(){

        return new AlphaAnimation(1F, 0.3F);

    }

    public static class SemesterNameHolder extends RecyclerView.ViewHolder {

        TextView tv_semesterName;
        CardView semester_cardview;

        public SemesterNameHolder(@NonNull View itemView) {
            super(itemView);
            tv_semesterName = itemView.findViewById(R.id.tv_semesterName);
            semester_cardview = itemView.findViewById(R.id.semester_cardview);
        }
    }



}
