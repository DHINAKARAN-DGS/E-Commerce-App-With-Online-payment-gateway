package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MyRewards extends AppCompatActivity
{
    private RecyclerView rewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rewards);


        getSupportActionBar().setTitle("My rewards");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rewards = findViewById(R.id.myrewardsRV);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rewards.setLayoutManager(layoutManager);

        List<rewardsModel> rewardsModelList = new ArrayList<>();
        rewardsModelList.add(new rewardsModel("20 % DISCOUNT","Valid only for 2 days","20 % on products above Rs.500/-"));
        rewardsModelList.add(new rewardsModel("30 % DISCOUNT","Valid only for 2 days","30 % on products above Rs.5000/-"));
        rewardsModelList.add(new rewardsModel("40 % DISCOUNT","Valid only for 2 days","40 % on products above Rs.50000/-"));
        rewardsModelList.add(new rewardsModel("50 % DISCOUNT","Valid only for 2 days","50 % on products above Rs.500000/-"));
        rewardsModelList.add(new rewardsModel("50 % DISCOUNT","Valid only for 2 days","50 % on products above Rs.500000/-"));
        rewardsModelList.add(new rewardsModel("50 % DISCOUNT","Valid only for 2 days","50 % on products above Rs.500000/-"));
        rewardsModelList.add(new rewardsModel("50 % DISCOUNT","Valid only for 2 days","50 % on products above Rs.500000/-"));
        rewardsModelList.add(new rewardsModel("50 % DISCOUNT","Valid only for 2 days","50 % on products above Rs.500000/-"));
        rewardsModelList.add(new rewardsModel("50 % DISCOUNT","Valid only for 2 days","50 % on products above Rs.500000/-"));

        rewardAdapter rewardAdapter = new rewardAdapter(rewardsModelList,false);
        rewards.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId() == android.R.id.home);
        {
            finish();
        }
        return true;
    }
}