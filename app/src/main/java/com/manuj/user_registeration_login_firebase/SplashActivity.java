package com.manuj.user_registeration_login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    Message msg;
    TextView txtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtname = findViewById(R.id.nameText);
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.splash_name);

        txtname.startAnimation(animation);


        getSupportActionBar().hide();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            handler.sendEmptyMessageDelayed(111, 3500);
        } else {
            handler.sendEmptyMessageDelayed(222, 3500);
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 111) {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
            }
        }
    };
}

