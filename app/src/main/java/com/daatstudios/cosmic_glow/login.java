package com.daatstudios.cosmic_glow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class login extends AppCompatActivity

{

    private static final String TAG = "LoginRegisterActivity";
    int AUTHUI_REQUEST_CODE = 1500;
    private Button login;
    FirebaseAuth firebaseAuth;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        constraintLayout = findViewById(R.id.afterLogIn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            constraintLayout.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        }


        login = findViewById(R.id.loginBtn);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(this, DashboardActivity.class));
            Animatoo.animateFade(login.this);
            this.finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build(),
                        new AuthUI.IdpConfig.EmailBuilder().build()
                );

                Intent intent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.firebasebg)
                        .setAlwaysShowSignInMethodScreen(true)
                        .setIsSmartLockEnabled(true)
                        .setTheme(R.style.FirebaseBackground)
                        .build();

                startActivityForResult(intent, AUTHUI_REQUEST_CODE);

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHUI_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // We have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Checking for User (New/Old)
//                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
//                    //This is a New User
//
//                }
//                else
//                {
//                    //This is a returning user
//
//                }


                Intent intent = new Intent(this, DashboardActivity.class);
                    Animatoo.animateFade(login.this);
                    startActivity(intent);
                    this.finish();
                Map<Object,String> userData = new HashMap<>();
                userData.put("fullName",firebaseAuth.getCurrentUser().getDisplayName());

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("USERS").add(userData);
            }
            else {
                // Signing in failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {
                    Toast.makeText(this, " the user has cancelled the sign in request", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ERROR:"+response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}