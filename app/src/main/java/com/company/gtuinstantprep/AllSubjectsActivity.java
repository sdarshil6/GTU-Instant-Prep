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

public class AllSubjectsActivity extends AppCompatActivity {

    RecyclerView subjectListRecyclerView;
    SubjectListAdapter adapter;
    ArrayList<Subjects> list;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);
        subjectListRecyclerView = findViewById(R.id.subjectListRecyclerView);
        subjectListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectListRecyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new SubjectListAdapter(this, list);
        subjectListRecyclerView.setAdapter(adapter);

        databaseReference.child("Subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.removeAll(list);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Subjects subject = dataSnapshot.getValue(Subjects.class);
                    list.add(subject);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}