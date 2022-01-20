package com.daatstudios.cosmic_glow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import static maes.tech.intentanim.CustomIntent.customType;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logoTxt);
        Glide.with(MainActivity.this).load(R.drawable.twogif).into(logo);


        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();


        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(2500);
                }

                catch(Exception e)
                {
                    e.printStackTrace();
                }

                finally
                {
                    if (firebaseAuth.getCurrentUser()!=null) {
                        startActivity(new Intent(MainActivity.this,NewDashboardActivity.class));
                        customType(MainActivity.this,"fadein-to-fadeout");
                    }
                    else {
                        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                        customType(MainActivity.this,"bottom-to-up");
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}