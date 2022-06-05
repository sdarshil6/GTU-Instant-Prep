package com.company.gtuinstantprep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SpashhScreenActivity extends AppCompatActivity {

    TextView tv_gtu;
    ImageView iv_bolt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spashh_screen);
        tv_gtu = findViewById(R.id.tv_gtu);
        iv_bolt = findViewById(R.id.iv_bolt);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        tv_gtu.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        iv_bolt.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));

        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(SpashhScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }.start();

    }
}