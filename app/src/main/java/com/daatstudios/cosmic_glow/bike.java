package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class bike extends AppCompatActivity {

    EditText name, number, time, date, drop, pick;
    private Button submit;
    Dialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bike Taxi");

        loadingdialog = new Dialog(bike.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        name = findViewById(R.id.bikename);
        number = findViewById(R.id.bikeno);
        time = findViewById(R.id.biketime);
        date = findViewById(R.id.bikedate);
        drop = findViewById(R.id.bikedroplocation);
        pick = findViewById(R.id.bikepickuplocation);
        submit = findViewById(R.id.bikesubmitbutton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingdialog.show();
                if (!date.getText().equals("")) {
                    if (!time.getText().equals("")) {
                        if (!name.getText().equals("")) {
                            if (!pick.getText().equals("")) {
                                if (!drop.getText().equals("")) {
                                    if (!number.getText().equals("") && number.getText().toString().length() > 9) {
                                        Map<String, Object> update = new HashMap<>();
                                        update.put("c_name", name.getText().toString());
                                        update.put("c_number", number.getText().toString());
                                        update.put("drop_location", drop.getText().toString());
                                        update.put("pickup_Location", pick.getText().toString());
                                        update.put("time", time.getText().toString());
                                        update.put("date", date.getText().toString());
                                        update.put("order_status","Ordered");
                                        update.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                        updatefirebase(update);
                                    } else {
                                        loadingdialog.dismiss();
                                        number.setError("Required || enter your 10 digit number");
                                    }

                                } else {
                                    loadingdialog.dismiss();
                                    drop.setError("Required");
                                }
                            } else {
                                loadingdialog.dismiss();
                                pick.setError("Required");
                            }
                        } else {
                            loadingdialog.dismiss();
                            name.setError("Requires");
                        }
                    } else {
                        loadingdialog.dismiss();
                        time.setError("Enter the time of pickup");
                    }
                } else {
                    loadingdialog.dismiss();
                    date.setError("Enter the date of pickup");
                }
            }
        });


    }

    private void updatefirebase(Map<String, Object> map) {
        FirebaseFirestore.getInstance().collection("BIKE_TAXI")
                .document()
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> tasks) {
                        if (tasks.isSuccessful()) {
                            name.setText("");
                            pick.setText("");
                            drop.setText("");
                            number.setText("");
                            loadingdialog.dismiss();
                            startActivity(new Intent(bike.this, BikeTaxi.class));
                            finish();
                        }
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}