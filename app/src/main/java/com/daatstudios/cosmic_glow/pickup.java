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

public class pickup extends AppCompatActivity {
    private EditText name, pick, drop, task, number,time,date;
    private Button submit;
    private Dialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);

        getSupportActionBar().setTitle("Pickup Drop service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingdialog = new Dialog(pickup.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        name = findViewById(R.id.pickname);
        pick = findViewById(R.id.pickuplocation);
        drop = findViewById(R.id.pickdroplocation);
        task = findViewById(R.id.task);
        number = findViewById(R.id.pickno);
        time = findViewById(R.id.pickTime);
        date = findViewById(R.id.pickDate);
        submit = findViewById(R.id.picksumbit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingdialog.show();
                if (!date.getText().equals("")) {
                    if (!time.getText().equals("")) {
                        if (!name.getText().equals("")) {
                            if (!pick.getText().equals("")) {
                                if (!drop.getText().equals("")) {
                                    if (!task.getText().equals("")) {
                                        if (!number.getText().equals("") && number.getText().toString().length() > 9) {
                                            Map<String, Object> update = new HashMap<>();
                                            update.put("c_name", name.getText().toString());
                                            update.put("c_number", number.getText().toString());
                                            update.put("task", task.getText().toString());
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
                                        task.setError("Required");
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
                }else{
                    loadingdialog.dismiss();
                    date.setError("Enter the date of pickup");
                }
            }
        });


    }

    private void updatefirebase(Map<String,Object> map) {
        FirebaseFirestore.getInstance().collection("PANDA_PICK")
                .document()
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> tasks) {
                if (tasks.isSuccessful()){
                    name.setText("");
                    task.setText("");
                    pick.setText("");
                    drop.setText("");
                    number.setText("");
                    loadingdialog.dismiss();
                    startActivity(new Intent(pickup.this,PandaPick.class));
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