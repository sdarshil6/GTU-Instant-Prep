package com.company.gtuinstantprep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllYearsActivity extends AppCompatActivity {

    RecyclerView yearListRecyclerView;
    ArrayList<Years> list;
    YearListAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_years);

        list = new ArrayList<>();
        yearListRecyclerView = findViewById(R.id.yearListRecyclerView);
        yearListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        yearListRecyclerView.setHasFixedSize(true);
        adapter = new YearListAdapter(this, list);
        yearListRecyclerView.setAdapter(adapter);

        databaseReference.child("Years").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.removeAll(list);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Years year = dataSnapshot.getValue(Years.class);
                    list.add(year);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}