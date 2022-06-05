package com.company.gtuinstantprep;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.SubjectNameHolder> {

    Context context;
    ArrayList<Subjects> list;

    private static String subjectName;
    public static String getSubjectName(){

        return subjectName;

    }

    public SubjectListAdapter(Context context, ArrayList<Subjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubjectNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_card_design, parent, false);
        return new SubjectNameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectNameHolder holder, int position) {

        holder.tv_subjectName.setText(list.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context.getApplicationContext(), AllYearsActivity.class);
                subjectName = list.get(holder.getAdapterPosition()).getName();
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SubjectNameHolder extends RecyclerView.ViewHolder {

        TextView tv_subjectName;
        CardView cardView;

        public SubjectNameHolder(@NonNull View itemView) {
            super(itemView);
            tv_subjectName = itemView.findViewById(R.id.tv_subjectName);
            cardView = itemView.findViewById(R.id.subject_cardview);
        }
    }
}
