package com.company.gtuinstantprep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.company.gtuinstantprep.databinding.ActivityAllBranchesBinding;
import com.company.gtuinstantprep.databinding.ActivityAllSemestersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllSemestersActivity extends BaseDrawerActivity {

    RecyclerView semesterListRecyclerView;
    SemesterListAdapter adapter;
    ArrayList<Semesters> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    ActivityAllSemestersBinding activityAllSemestersBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllSemestersBinding = ActivityAllSemestersBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_all_semesters); //BaseDrawer...
        setContentView(activityAllSemestersBinding.getRoot()); //AppCompat....
        allocateActivityTitle(BranchListAdapter.getBranchName());


        list = new ArrayList<>();
        semesterListRecyclerView = findViewById(R.id.semesterListRecyclerView);
        semesterListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        semesterListRecyclerView.setHasFixedSize(true);
        adapter = new SemesterListAdapter(AllSemestersActivity.this, list);
        semesterListRecyclerView.setAdapter(adapter);

        databaseReference.child("Semesters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.removeAll(list);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Semesters semester = dataSnapshot.getValue(Semesters.class);
                    list.add(semester);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}