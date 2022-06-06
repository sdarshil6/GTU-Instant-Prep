package com.company.gtuinstantprep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.company.gtuinstantprep.databinding.ActivityAllBranchesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllBranchesActivity extends BaseDrawerActivity {

    RecyclerView branchListRecyclerView;
    ArrayList<Branches> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    BranchListAdapter adapter;

    ActivityAllBranchesBinding activityAllBranchesBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllBranchesBinding = ActivityAllBranchesBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_all_branches); //BaseDrawer...
        setContentView(activityAllBranchesBinding.getRoot()); //AppCompat....
        allocateActivityTitle("Select your Engineering Branch");




        branchListRecyclerView = findViewById(R.id.branchListRecyclerView);
        branchListRecyclerView.setHasFixedSize(true);
        branchListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();



        databaseReference.child("Branches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.removeAll(list);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Branches branch = dataSnapshot.getValue(Branches.class);
                    list.add(branch);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new BranchListAdapter(this, list);
        branchListRecyclerView.setAdapter(adapter);






    }
}