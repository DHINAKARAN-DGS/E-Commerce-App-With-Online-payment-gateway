package com.daatstudios.cosmic_glow;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrderItemsAdapter extends RecyclerView.Adapter<MyOrderItemsAdapter.ViewHolder> {

    private List<MyOrderItemsModel> myOrderItemsModelList;
    private Dialog loadingdialog;
    private SimpleDateFormat simpleDateFormat;

    public MyOrderItemsAdapter(List<MyOrderItemsModel> myOrderItemsModelList, Dialog loadingdialog) {
        this.loadingdialog = loadingdialog;
        this.myOrderItemsModelList = myOrderItemsModelList;
    }

    @NonNull
    @Override
    public MyOrderItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderItemsAdapter.ViewHolder holder, int position) {
        String resource = myOrderItemsModelList.get(position).getProduct_image_orders();
        int rating = myOrderItemsModelList.get(position).getRating();
        String productName = myOrderItemsModelList.get(position).getProductTitleOrder();
        String deliveryStatus = myOrderItemsModelList.get(position).getDeliveryStatus();
        String date = myOrderItemsModelList.get(position).getDate();
        String city = myOrderItemsModelList.get(position).getCity();
        String price = myOrderItemsModelList.get(position).getProductPrice();
        String qty = myOrderItemsModelList.get(position).getProductQty();
        String delivery = myOrderItemsModelList.get(position).getDeliveryPrice();
        String productId = myOrderItemsModelList.get(position).getProductID();
        String payment = myOrderItemsModelList.get(position).getPaymentMethod();
        String orderID = myOrderItemsModelList.get(position).getOrderID();
        holder.setData(resource, productName, deliveryStatus, rating, date, city, price, qty, delivery, productId, position, payment, orderID);
    }

    @Override
    public int getItemCount() {
        return myOrderItemsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitleOrder, deliveryStatus, citytxt, pricetxt, totalAmount, paymentTxt, orderTxt;
        private ImageView  deliveryIndicator, s1, s2, s3, s4, s5;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productTitleOrder = itemView.findViewById(R.id.ProductTitleOrders);
            deliveryStatus = itemView.findViewById(R.id.orderDeliveredDate);
            citytxt = itemView.findViewById(R.id.deliveryCity);
            pricetxt = itemView.findViewById(R.id.deliveryprice);
            totalAmount = itemView.findViewById(R.id.totalAmountOrder);
            paymentTxt = itemView.findViewById(R.id.paymentsTxt);
            orderTxt = itemView.findViewById(R.id.orderIDText);

        }

        int rate = 0;

        private void setData(String resource, String ordersTitle, String deliverySTATUS, final int rating, String date, String city, String price, String qty, String delivery, final String productID, final int position, String payment, String orderID) {
            String[] parent = ordersTitle.split(",");
            String[] parent2 = qty.split(",");
            String[] parent3 = price.split(",");
            StringBuilder name= new StringBuilder();
            int prices= 0;
            for (int x=0;x<parent.length;x++){
                if (x>1) {
                    name.append(parent[x]).append(" x ").append(parent2[x]).append(", ");
                }else{
                    name.append(parent[x]).append(" x ").append(parent2[x]);
                }
                prices=prices+Integer.parseInt(parent3[x]);
            }
            productTitleOrder.setText(name.toString());
            deliveryStatus.setText("Ordered on " + date);
            pricetxt.setText("Rs." + prices + "/-");
            switch (deliverySTATUS) {
                case "Ordered":
                    citytxt.setText("Ordered to " + city);
                    break;
                case "Cancelled":
                    citytxt.setText("Cancelled");
                    break;
                case "Shipped":
                    citytxt.setText("Shipped to " + city);
                    break;
                case "Delivered":
                    citytxt.setText("Delivered to " + city);
                    break;
                default:
                    citytxt.setText("Data Not available");
            }
            orderTxt.setText("order ID - " + orderID);
//            int totalPrice = Integer.parseInt(price) * Integer.parseInt(String.valueOf(qty));

//            if (totalPrice >= 500) {
//                totalAmount.setText("Total amount Rs." + totalPrice + "/-");
//            } else {
//                totalPrice = totalPrice + 40;
//                totalAmount.setText("Total amount Rs." + totalPrice + "/-*");
//            }
            totalAmount.setText("Delivery charge: "+delivery);


            paymentTxt.setText("Payment mode: " + payment);
            /////rateing

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingdialog = new Dialog(itemView.getContext());
                    loadingdialog.setContentView(R.layout.thank_you_layout);
                    loadingdialog.setCancelable(true);
                    loadingdialog.getWindow().setBackgroundDrawable(itemView.getContext().getDrawable(R.drawable.slider_bg));
                    loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    loadingdialog.show();
                    TextView contactUS = loadingdialog.findViewById(R.id.ContactUS);
                    contactUS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(itemView.getContext(), contactUsActivity.class);
                            itemView.getContext().startActivity(intent);
                        }
                    });

                }
            });

            /////rateing
        }

    }
}