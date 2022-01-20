package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {
    public static RecyclerView cartItemsRV;
    private Button continueBtn;
    private TextView totalAmountTxt;
    public static Dialog loadingdialog;
    public static CartAdapter cartAdapter;
    private LinearLayout linearLayout;
    private TextView dummy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setTitle("Cart");

        delivery.fromCart = true;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingdialog = new Dialog(CartActivity.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingdialog.show();

        dbQuaries.cartlist.clear();
        dbQuaries.cartItemsModelList.clear();
        dbQuaries.loadCartList(CartActivity.this, loadingdialog, true, new TextView(CartActivity.this), new TextView(CartActivity.this));


        linearLayout = findViewById(R.id.linearLayout5);


        totalAmountTxt = findViewById(R.id.cartPriceTotalAmount);


        cartItemsRV = findViewById(R.id.cartItemsRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemsRV.setLayoutManager(layoutManager);


        cartAdapter = new CartAdapter(dbQuaries.cartItemsModelList, totalAmountTxt, true, false, false);
        cartItemsRV.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        loadingdialog.show();
    }
}