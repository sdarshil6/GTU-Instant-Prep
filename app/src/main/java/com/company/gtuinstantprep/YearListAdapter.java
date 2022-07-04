package com.company.gtuinstantprep;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import java.util.Arrays;

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

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

                view.startAnimation(clickAnimation());
                Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
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

                String result = "";

                // Traverse the string.
                boolean v = true;
                for (int i = 0; i < subjectName.length(); i++)
                {
                    // If it is space, set v as true.
                    if (subjectName.charAt(i) == ' ')
                    {
                        v = true;
                    }

                    // Else check if v is true or not.
                    // If true, copy character in output
                    // string and set v as false.
                    else if (subjectName.charAt(i) != ' ' && v == true)
                    {
                        result += (subjectName.charAt(i));
                        v = false;
                    }
                }


                final File finalFile = new File(rootPath, result + " " + yearName + ".pdf");

                storageReference.child(branchName).child(semesterName).child(subjectName).child(yearName + ".pdf").getFile(finalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        final PendingIntent contentIntent;

                        Toast.makeText(context, "PDF Downloaded", Toast.LENGTH_LONG).show();

                        /*String downloadFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "GTU PREP Downloads" + "/"
                                + yearName + ".pdf" + "/";
                        Uri uri = Uri.parse(downloadFilePath);*/

                        Uri uri = FileProvider.getUriForFile(context, "com.company.gtuinstantprep", finalFile);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri,"application/pdf");

                        /*intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);*/

                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ){
                            contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                        }
                        else {
                            contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        }



                        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.squarebolt);
                        String bigText = context.getString(R.string.notificationBigText_channel1_part1) + " " + yearName + " " + context.getString(R.string.notificationBigText_channel1_part2);
                        Notification notification1 = new NotificationCompat.Builder(context, NotificationCreator.CHANNEL1_ID)
                                .setSmallIcon(R.drawable.squarebolt)
                                .setColor(context.getColor(R.color.black))
                                .setContentTitle(context.getString(R.string.notificationTitle_channel1))
                                .setContentText(context.getString(R.string.notificationContentText_channel1))
                                //.setLargeIcon(largeIcon)
                                .setShowWhen(true)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText).setSummaryText(context.getString(R.string.notificationSummaryText_channel1)))
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setAutoCancel(true)
                                .setContentIntent(contentIntent)
                                .build();

                        notificationManagerCompat.notify(1, notification1);


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

    public AlphaAnimation clickAnimation(){

        return new AlphaAnimation(1F, 0.3F);

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
