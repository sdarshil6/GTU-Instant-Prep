package com.company.gtuinstantprep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText et_email_fp;
    Button bt_sendResetLink;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        et_email_fp = findViewById(R.id.et_email_fp);
        bt_sendResetLink = findViewById(R.id.bt_sendResetLink);

        bt_sendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = et_email_fp.getText().toString();
                onClickSendResetLink(userEmail);

            }
        });
    }

    public void onClickSendResetLink(String userEmail){

        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(ForgotPasswordActivity.this, "Password Reset Link has been sent to your email address", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
                else{

                    Toast.makeText(ForgotPasswordActivity.this, "ERROR", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}