package com.company.gtuinstantprep;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.BranchNameHolder>{

    Context context;
    ArrayList<Branches> list;

    private static String branchName;
    public static String getBranchName(){
        return branchName;
    }

    public BranchListAdapter(Context context, ArrayList<Branches> list){

        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public BranchNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_card_design, parent, false);
        return new BranchNameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchNameHolder holder, int position) {
        holder.tv_branchName.setText(list.get(position).getName());
        /*holder.tv_branchName.setSelected(true);
        holder.tv_branchName.setHorizontallyScrolling(true);*/


        holder.branch_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, AllSemestersActivity.class);
                branchName = list.get(holder.getAdapterPosition()).getName();
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


    // gtu-instant-prep@gtu-instant-prep.iam.gserviceaccount.com

    public static class BranchNameHolder extends RecyclerView.ViewHolder {

        private TextView tv_branchName;
        private CardView branch_cardview;

        public BranchNameHolder(@NonNull View itemView) {
            super(itemView);
            tv_branchName = itemView.findViewById(R.id.tv_branchName);

            branch_cardview = itemView.findViewById(R.id.branch_cardview);
        }
    }

}
