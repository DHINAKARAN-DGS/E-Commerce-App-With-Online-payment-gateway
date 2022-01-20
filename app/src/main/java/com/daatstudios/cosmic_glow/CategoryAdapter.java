package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
{

    private List<CategoryModel>CategoryModelList;
    private int lastPosition = -1;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.CategoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorie_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        String ICON = CategoryModelList.get(position).getCategoryIconlink();
        String NAME = CategoryModelList.get(position).getCategory_name();
        holder.setCategory(NAME,position);
        holder.setCatIcon(ICON);
        if (lastPosition<position){
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return CategoryModelList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        private ImageView CatIcon;
        private TextView CatName;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            CatIcon = itemView.findViewById(R.id.category_icon);
            CatName = itemView.findViewById(R.id.category_name);

        }

        private void setCatIcon(String iconUrl)
        {
            if (!iconUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(CatIcon);
            }else
            {
                CatIcon.setImageResource(R.drawable.home);
            }

            }
        private void setCategory(final String name,final int position)
        {
            CatName.setText(name);
            if (!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            Intent intent = new Intent(itemView.getContext(),ViewAllCat.class);
                            intent.putExtra("C",name.toUpperCase());
                            itemView.getContext().startActivity(intent);
                        }

                    }
                });
            }
        }
    }


}
