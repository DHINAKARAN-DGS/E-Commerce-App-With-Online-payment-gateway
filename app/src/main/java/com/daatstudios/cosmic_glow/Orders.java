package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

public class Orders extends AppCompatActivity
{
    private RecyclerView myOrdersRV;
    public static MyOrderItemsAdapter  myOrderItemsAdapter;
    private  Dialog loadingdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Orders");

        loadingdialog = new Dialog(Orders.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        myOrdersRV = findViewById(R.id.MyOrderRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrdersRV.setLayoutManager(layoutManager);

        myOrderItemsAdapter = new MyOrderItemsAdapter(dbQuaries.orderItemsModelList,loadingdialog);
        myOrdersRV.setAdapter(myOrderItemsAdapter);
        myOrderItemsAdapter.notifyDataSetChanged();

       if (dbQuaries.orderItemsModelList.size()==0){
            dbQuaries.loadOrders(Orders.this,myOrderItemsAdapter,loadingdialog);
       }



    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        myOrderItemsAdapter.notifyDataSetChanged();
    }
}