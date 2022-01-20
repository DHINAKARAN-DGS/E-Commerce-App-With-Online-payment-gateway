package com.daatstudios.cosmic_glow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daatstudios.cosmic_glow.ui.home.HomeFragment;

public class orderConformed extends AppCompatActivity {

    private ImageView continueBtn;
    private TextView conformationID,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_conformed);

        continueBtn=findViewById(R.id.continueShopping);
        conformationID = findViewById(R.id.deliveryOrderID);
        contact = findViewById(R.id.contactShop);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+919840749192"));
                startActivity(callIntent);
            }
        });

        String order_ID = getIntent().getStringExtra("order_ID");
        conformationID.setText("ORDER ID: "+order_ID);
        getSupportActionBar().hide();
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(orderConformed.this,NewDashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                HomeFragment.swipeRefreshLayout.setRefreshing(true);
                dbQuaries.cartlist.clear();
                dbQuaries.cartItemsModelList.clear();
                dbQuaries.clearData();
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(orderConformed.this,DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}