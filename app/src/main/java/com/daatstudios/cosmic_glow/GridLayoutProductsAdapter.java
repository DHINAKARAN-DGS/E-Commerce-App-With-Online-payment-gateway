package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridLayoutProductsAdapter extends BaseAdapter
{
    List<HorizontalProductScrollModel>horizontalProductScrollModelListGrid;

    public GridLayoutProductsAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelListGrid)
    {
        this.horizontalProductScrollModelListGrid = horizontalProductScrollModelListGrid;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModelListGrid.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        final View view ;
        if (convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item,null);
            view.setElevation(0);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(),ProductDetailsActivity.class);
                    intent.putExtra("product_ID",horizontalProductScrollModelListGrid.get(position).getProductId());
                     parent.getContext().startActivity(intent);
                }
            });

            ImageView GridProductImage = view.findViewById(R.id.HorizontalProductImage);
            TextView GridPNAME = view.findViewById(R.id.HorizontalScrollProductName);
            TextView GridProductPrice = view.findViewById(R.id.HorizontalScrollProductPrize);

            Glide.with(view.getContext()).load(horizontalProductScrollModelListGrid.get(position).getProductImage())
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(GridProductImage);

            GridPNAME.setText(horizontalProductScrollModelListGrid.get(position).getProductName());
            GridProductPrice.setText("Rs."+horizontalProductScrollModelListGrid.get(position).getProductPrice()+"/-");



        }
        else
        {
            view = convertView;
        }
        return view;
    }
}
