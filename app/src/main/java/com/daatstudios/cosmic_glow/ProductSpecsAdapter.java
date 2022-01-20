package com.daatstudios.cosmic_glow;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSpecsAdapter extends RecyclerView.Adapter<ProductSpecsAdapter.ViewHolder> {

    private List<productSpecsModel> productSpecsModelList ;


    public ProductSpecsAdapter(List<productSpecsModel> productSpecsModelList) {
        this.productSpecsModelList = productSpecsModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecsModelList.get(position).getType()) {
            case 0:
                return productSpecsModel.SPECS_TITLE;
            case 1:
                return productSpecsModel.SPECS_BODY;
            default:
        }
        return -1;
    }

    @NonNull
    @Override
    public ProductSpecsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case productSpecsModel.SPECS_TITLE:
                TextView title = new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins
                        (setDp(16, parent.getContext())
                                , setDp(16, parent.getContext())
                                , setDp(16, parent.getContext())
                                , setDp(8, parent.getContext()));
                title.setLayoutParams(layoutParams);
                return new ViewHolder(title);
            case productSpecsModel.SPECS_BODY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specs_item_layout, parent, false);
                return new ViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecsAdapter.ViewHolder holder, int position) {

        switch (productSpecsModelList.get(position).getType()) {
            case productSpecsModel.SPECS_TITLE:
                holder.setTitle(productSpecsModelList.get(position).getTitle());
                break;
            case productSpecsModel.SPECS_BODY:
                String freatureTitle = productSpecsModelList.get(position).getFeatureName();
                String freatureDetail = productSpecsModelList.get(position).getFeatureValue();
                holder.setFeatures(freatureTitle, freatureDetail);
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return productSpecsModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView featureName, featureValue, title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        private void setTitle(String TitleTxt) {
            title = (TextView) itemView;
            title.setText(TitleTxt);
        }

        private void setFeatures(String featureTitle, String featureDetail) {
            featureName = itemView.findViewById(R.id.featureName);
            featureValue = itemView.findViewById(R.id.featureValue);
            featureName.setText(featureTitle);
            featureValue.setText(featureDetail);
        }

    }

    private int setDp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
