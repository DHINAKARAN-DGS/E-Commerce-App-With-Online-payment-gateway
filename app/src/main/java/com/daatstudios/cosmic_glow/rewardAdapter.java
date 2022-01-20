package com.daatstudios.cosmic_glow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rewardAdapter extends RecyclerView.Adapter<rewardAdapter.ViewHolder>
{

    private List<rewardsModel> rewardsModelList;
    private Boolean useMiniLayout = false;

    public rewardAdapter(List<rewardsModel> rewardsModelList,Boolean useMiniLayout) {
        this.rewardsModelList = rewardsModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup position, int viewType) {
        View view;
        if (useMiniLayout == true)
        { view   = LayoutInflater.from(position.getContext()).inflate(R.layout.rewards_items_mini,position,false);}
        view = LayoutInflater.from(position.getContext()).inflate(R.layout.myrewards_layout,position,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = rewardsModelList.get(position).getTitle();
        String body = rewardsModelList.get(position).getBody();
        String validity = rewardsModelList.get(position).getValidity();

        holder.setData(title,body,validity);

    }

    @Override
    public int getItemCount() {
        return rewardsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Coupontitle,Couponvalidity,copounBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Coupontitle = itemView.findViewById(R.id.couponTitle);
            Couponvalidity = itemView.findViewById(R.id.couponValidity);
            copounBody = itemView.findViewById(R.id.couponBody);
        }

        private void setData(final String couponTitle, final String couponValidity, final String couponBody)
        {
            Coupontitle.setText(couponTitle);
            Couponvalidity.setText(couponValidity);
            copounBody.setText(couponBody);

            if (useMiniLayout)
            {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailsActivity.couponTitle.setText(couponTitle);
                        ProductDetailsActivity.CouponBody.setText(couponBody);
                        ProductDetailsActivity.CouponVAlidity.setText(couponValidity);
                        ProductDetailsActivity.openRecylerView();
                    }
                });

            }
        }

    }
}
