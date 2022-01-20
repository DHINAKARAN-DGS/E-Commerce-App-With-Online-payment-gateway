package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BikeTaxi extends AppCompatActivity {
    List<PickModel> pickModelList=new ArrayList<>();
    PickAdapter adapter;
    RecyclerView recyclerView;
    Button pandapickBtn,btn2;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_taxi);

        pandapickBtn=findViewById(R.id.bikepickBtn);
        btn2=findViewById(R.id.bikepickBtn2);
        linearLayout=findViewById(R.id.bikelinear);

        pandapickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BikeTaxi.this,bike.class));
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BikeTaxi.this,bike.class));
            }
        });

        fetchFirebase();
        recyclerView = findViewById(R.id.bikeRV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter = new PickAdapter(pickModelList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        getSupportActionBar().setTitle("Bike Taxi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void fetchFirebase(){
        FirebaseFirestore.getInstance().collection("BIKE_TAXI").whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        pickModelList.add(new PickModel(
                                documentSnapshot.getString("c_name")
                                ,documentSnapshot.getString("pickup_Location")
                                ,documentSnapshot.getString("drop_location")
                                ,documentSnapshot.getString("time")+"--"+documentSnapshot.getString("date")
                                ,""
                                ,documentSnapshot.getString("c_number")
                                ,documentSnapshot.getString("order_status")
                        ));
                    }if (pickModelList.size()!=0){
                        linearLayout.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        btn2.setVisibility(View.VISIBLE);
                    }else{
                        linearLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        btn2.setVisibility(View.INVISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}