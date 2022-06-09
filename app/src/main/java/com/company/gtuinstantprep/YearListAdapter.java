package com.company.gtuinstantprep;

import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class YearListAdapter extends RecyclerView.Adapter<YearListAdapter.YearNameHolder> {

    Context context;
    ArrayList<Years> list;
    /*private static String yearName;
    private static String getSemeseterName(){
        return yearName;
    }*/

    String branchName = BranchListAdapter.getBranchName();
    String semesterName = SemesterListAdapter.getSemeseterName();
    String subjectName = SubjectListAdapter.getSubjectName();

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    public YearListAdapter(Context context, ArrayList<Years> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public YearNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.year_card_design, parent, false);
        return new YearNameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YearNameHolder holder, int position) {

        holder.tv_yearName.setText(list.get(position).getName());
        holder.year_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yearName = list.get(holder.getAdapterPosition()).getName();
                String folderName = "GTU PREP Downloads";

                File rootPath = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)), folderName);
                if(!rootPath.exists()){
                    boolean mkdirs = rootPath.mkdirs();
                    Log.i("hello", "RESULT = " + mkdirs);
                }
                else{

                    Log.i("hellos", "exists");

                }

                final File finalFile = new File(rootPath, yearName + ".pdf");

                storageReference.child(branchName).child(semesterName).child(subjectName).child(yearName + ".pdf").getFile(finalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(context, "PDF Downloaded", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class YearNameHolder extends RecyclerView.ViewHolder {

        TextView tv_yearName;
        CardView year_cardview;

        public YearNameHolder(@NonNull View itemView) {
            super(itemView);
            tv_yearName = itemView.findViewById(R.id.tv_yearName);
            year_cardview = itemView.findViewById(R.id.year_cardview);
        }
    }
}
