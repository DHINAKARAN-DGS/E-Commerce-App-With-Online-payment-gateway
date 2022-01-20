package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

public class wishlist extends AppCompatActivity
{
    private RecyclerView wishListRV;
    private Dialog loadingdialog;
    public static wishlistAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Wishlist");

        loadingdialog = new Dialog(wishlist.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingdialog.show();

        wishListRV = findViewById(R.id.wishlistRecView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        wishListRV.setLayoutManager(layoutManager);


        if (dbQuaries.wishlistModelList.size() == 0){
            dbQuaries.wishlist.clear();
            dbQuaries.loadWishlist(wishlist.this,loadingdialog,true);
        }else{
            loadingdialog.dismiss();
        }

        wishlistAdapter = new wishlistAdapter(dbQuaries.wishlistModelList,true);
        wishListRV.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }return true;
    }
}