package com.daatstudios.cosmic_glow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class otpVerificationActivity extends AppCompatActivity {

    private EditText enteredOtp;

    private TextView displayTxt;
    private Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        getSupportActionBar().hide();

        enteredOtp = findViewById(R.id.otp);
        displayTxt = findViewById(R.id.numberforOtp);
        verify = findViewById(R.id.verifiyOtp);
        String number = getIntent().getStringExtra("number");

        displayTxt.setText("Verification code has been sent to +91 " + number);

    }
}