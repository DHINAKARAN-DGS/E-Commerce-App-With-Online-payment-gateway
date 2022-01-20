package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.List;

public class viewAllActivity extends AppCompatActivity {


    private RecyclerView recyclerViewvA;
    private GridView gridView;
    public  static List<HorizontalProductScrollModel> horizontalProductScrollModelsList;
    public  static List<wishlistModel>wishlistModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        recyclerViewvA = findViewById(R.id.recyclerView);
        gridView = findViewById(R.id.gridView);

        int layoutCode = getIntent().getIntExtra("Layout_code",-1);

        if (layoutCode == 0) {

            recyclerViewvA.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerViewvA.setLayoutManager(layoutManager);




            wishlistAdapter adapter = new wishlistAdapter(wishlistModelList, false);
            recyclerViewvA.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (layoutCode == 1) {
            gridView.setVisibility(View.VISIBLE);


            GridLayoutProductsAdapter gridLayoutProductsAdapter = new GridLayoutProductsAdapter(horizontalProductScrollModelsList);
            gridView.setAdapter(gridLayoutProductsAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}