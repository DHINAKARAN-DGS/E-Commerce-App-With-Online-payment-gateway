package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalScrollAdapter extends RecyclerView.Adapter<HorizontalScrollAdapter.ViewHolder> {

    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getProductImage();
        String title = horizontalProductScrollModelList.get(position).getProductName();
        String price = horizontalProductScrollModelList.get(position).getProductPrice();
        String productID = horizontalProductScrollModelList.get(position).getProductId();

        holder.setProductDataHP(productID, resource, title, "", price);
    }

    @Override
    public int getItemCount() {
        if (horizontalProductScrollModelList.size() >= 8) {
            return 8;
        } else {
            return horizontalProductScrollModelList.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ProductImage;
        private TextView productTitle, productPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.HorizontalProductImage);
            productTitle = itemView.findViewById(R.id.HorizontalScrollProductName);
            productPrice = itemView.findViewById(R.id.HorizontalScrollProductPrize);

        }

        private void setProductDataHP(final String productID, String resource, String title, String discription, String price) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.drawable.placeholder).into(ProductImage);
            productTitle.setText(title);
            productPrice.setText("Rs." + price + "/-");
            if (!title.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        intent.putExtra("product_ID", productID);
                        itemView.getContext().startActivity(intent);
                    }
                });
            }
        }


    }
}
