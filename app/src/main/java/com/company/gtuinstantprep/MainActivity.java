package com.company.gtuinstantprep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText et_emailLogin;
    EditText et_passwordLogin;
    TextView tv_forgotPassword;
    Button bt_login;
    TextView tv_registerNow;

    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        et_emailLogin = findViewById(R.id.et_emailLogin);
        et_passwordLogin = findViewById(R.id.et_passwordLogin);
        tv_forgotPassword = findViewById(R.id.tv_forgotPassword);
        bt_login = findViewById(R.id.bt_login);
        tv_registerNow = findViewById(R.id.tv_registerNow);

        tv_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();

            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = et_emailLogin.getText().toString();
                String userPassword = et_passwordLogin.getText().toString();

                firebaseSignIn(userEmail, userPassword);

            }
        });

        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    private void firebaseSignIn(String userEmail, String userPassword){

        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(MainActivity.this, "Sign In Successfull", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, AllBranchesActivity.class);
                    startActivity(i);
                    finish();

                }
                else{

                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){

            Intent i = new Intent(MainActivity.this, AllBranchesActivity.class);
            startActivity(i);
            finish();

        }
    }
}