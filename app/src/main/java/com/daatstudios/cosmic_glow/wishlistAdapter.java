package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class wishlistAdapter extends RecyclerView.Adapter<wishlistAdapter.ViewHolder> {

    List<wishlistModel> wishlistModelList;
    private Boolean wishlist;
    private boolean freomSearch;

    public wishlistAdapter(List<wishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    public boolean isFreomSearch() {
        return freomSearch;
    }

    public void setFreomSearch(boolean freomSearch) {
        this.freomSearch = freomSearch;
    }

    public List<wishlistModel> getWishlistModelList() {
        return wishlistModelList;
    }

    public void setWishlistModelList(List<wishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String productID = wishlistModelList.get(position).getProductID();
        String resource = wishlistModelList.get(position).getProductImageWL();
        String title = wishlistModelList.get(position).getProductTtileWl();
        String price = wishlistModelList.get(position).getProductPriceWL();
        String cutted = wishlistModelList.get(position).getCutterPriceWL();
        boolean payments = wishlistModelList.get(position).getPaymentMethod();
        String rate = wishlistModelList.get(position).getRatingWL();
        long totalRatings = wishlistModelList.get(position).getTotalRatings();
        long freecoupons = wishlistModelList.get(position).getFreecouponWL();
        boolean in_stock = wishlistModelList.get(position).isIn_stock();
        holder.setData(productID, resource, price, title, totalRatings, freecoupons, rate, payments, cutted, position, in_stock);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage, couponIcon, deleteBtn;
        private TextView productTitle, freecoupons, productPrice, cuttedPrice, paymentOptions;
        private View divider;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImageWishlist);


            productTitle = itemView.findViewById(R.id.productTitleWishlist);

            productPrice = itemView.findViewById(R.id.productPriceWishList);
            cuttedPrice = itemView.findViewById(R.id.cuttedPriceWishList);
            paymentOptions = itemView.findViewById(R.id.paymentsOptions);
            deleteBtn = itemView.findViewById(R.id.deleteItemWL);


        }

        private void setData(final String productID, String image, String price, String title, long totalRating, long freecouponNo,
                             String rate, boolean payments, String cutPrice, final int index, boolean in_stock) {
            Glide.with(itemView.getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);
            productPrice.setText("Rs." + price + "/-");
//            if (freecouponNo != 0 && in_stock) {
//                couponIcon.setVisibility(View.VISIBLE);
//                if (freecouponNo == 1) {
//                    freecoupons.setText("Free" + freecouponNo + "coupon");
//                } else {
//                    freecoupons.setText("Free" + freecouponNo + "coupons");
//                }
//            } else {
//                couponIcon.setVisibility(View.INVISIBLE);
//                freecoupons.setVisibility(View.INVISIBLE);
//            }
            if (in_stock) {
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                cuttedPrice.setVisibility(View.VISIBLE);
//                freecoupons.setVisibility(View.VISIBLE);

                if (payments == true) {
                    paymentOptions.setVisibility(View.VISIBLE);
                    paymentOptions.setText("Cash on delivery Available");
                } else {
                    paymentOptions.setVisibility(View.VISIBLE);
                    paymentOptions.setText("Cash on delivery not Available");

                }
                cuttedPrice.setText("Rs." + cutPrice + "/-");
            } else {
                productPrice.setText("Out of stock!");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                cuttedPrice.setVisibility(View.GONE);
                paymentOptions.setVisibility(View.GONE);
            }
            productTitle.setText(title);

            if (wishlist) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.RUNNING_WISHLIST_QUERY) {
                        ProductDetailsActivity.RUNNING_WISHLIST_QUERY = true;
                        dbQuaries.removeFromWishList(index, itemView.getContext());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (freomSearch) {
                        ProductDetailsActivity.fromSearch = true;
                    }
                    Intent intent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    intent.putExtra("product_ID", productID);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
