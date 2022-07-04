package com.company.gtuinstantprep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText et_fullname;
    EditText et_emailRegister;
    EditText et_passwordRegister;
    EditText et_confirmPassword;
    Button bt_register;
    TextView tv_TC;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        et_fullname = findViewById(R.id.et_fullname);
        et_emailRegister = findViewById(R.id.et_emailRegister);
        et_passwordRegister = findViewById(R.id.et_passwordRegister);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);
        bt_register = findViewById(R.id.bt_register);
        tv_TC = findViewById(R.id.tv_TC);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_passwordRegister.getText().toString().equals(et_confirmPassword.getText().toString())) {


                    String userEmail = et_emailRegister.getText().toString();
                    String userPassword = et_passwordRegister.getText().toString();

                    firebaseSignUp(userEmail, userPassword);
                }
                else{

                    Toast.makeText(RegisterActivity.this, "Check your password and try again", Toast.LENGTH_LONG).show();

                }

            }
        });

        tv_TC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }

    public void firebaseSignUp(String userEmail, String userPassword){

        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sign Up Successful. A verification link has been sent to your email", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{

                                Toast.makeText(RegisterActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
                else{

                    Toast.makeText(RegisterActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}