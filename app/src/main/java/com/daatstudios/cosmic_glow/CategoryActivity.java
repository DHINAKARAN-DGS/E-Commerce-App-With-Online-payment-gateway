package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daatstudios.cosmic_glow.R;

import java.util.ArrayList;
import java.util.List;

import static com.daatstudios.cosmic_glow.dbQuaries.lists;
import static com.daatstudios.cosmic_glow.dbQuaries.loadCate;
import static com.daatstudios.cosmic_glow.dbQuaries.loadFragmentData;
import static com.daatstudios.cosmic_glow.dbQuaries.loadedCategoriesNames;


public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclarView;
    HomePageAdapter homePageAdapter;
    private List<HomeModel> homeModelListfake = new ArrayList<>();
    Button view;
    String title;
    Dialog loadingdialog;
    public static wishlistAdapter wishlistAdapterforC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        view = findViewById(R.id.viewall);
        categoryRecyclarView = findViewById(R.id.CategoryRecyclarView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, ViewAllCat.class);
                intent.putExtra("C", title);
                startActivity(intent);
            }
        });

        loadingdialog = new Dialog(this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclarView.setLayoutManager(layoutManager);

        System.out.println("Hi" + title);


        dbQuaries.loadCate(this, true, title.replace("DEV", ""));


//        wishlistAdapterforC = new wishlistAdapter(, true);
        categoryRecyclarView.setAdapter(wishlistAdapterforC);
        wishlistAdapterforC.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_search) {
            Intent intent = new Intent(CategoryActivity.this, searchActivity.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}