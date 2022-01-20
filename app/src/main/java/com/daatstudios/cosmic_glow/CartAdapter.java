package com.daatstudios.cosmic_glow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.daatstudios.cosmic_glow.delivery.SELECT_ADDRESS;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemsModel> cartItemsModelList;
    private TextView cartTotalAmount;
    private boolean showDeleteBtn, placeORDER, addres;
    private LinearLayout couponLayout;
    public static TextView fullName, fullAddresses, pincode, totalAmount;

    public CartAdapter(List<CartItemsModel> cartItemsModelList, TextView cartTotalAmount, boolean showDeleteBtn, boolean placeOrder, boolean addres) {
        this.cartItemsModelList = cartItemsModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showDeleteBtn = showDeleteBtn;
        this.placeORDER = placeOrder;
        this.addres = addres;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemsModelList.get(position).getType()) {
            case 0:
                return CartItemsModel.CART_ITEM_LAYOUT;
            case 1:
                return CartItemsModel.CART_TOTAL_PRICE;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CartItemsModel.CART_ITEM_LAYOUT:
                View cartItemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout, viewGroup, false);
                return new cartItemViewHolder(cartItemview);
            case CartItemsModel.CART_TOTAL_PRICE:
                View cartTotalAmountView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_latout, viewGroup, false);
                return new cartTotalAmountViewHolder(cartTotalAmountView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemsModelList.get(position).getType()) {
            case CartItemsModel.CART_ITEM_LAYOUT:
                String productID = cartItemsModelList.get(position).getProductID();
                String resource = cartItemsModelList.get(position).getProduct_image();
                String title = cartItemsModelList.get(position).getProduct_title();
                Long freeCoupoons = cartItemsModelList.get(position).getFree_coupons();
                String productPrice = cartItemsModelList.get(position).getProduct_price();
                String cuttedPrice = cartItemsModelList.get(position).getCuttedPrice();
                Long offersApplied = cartItemsModelList.get(position).getOffersApplied();
                boolean inStock = cartItemsModelList.get(position).isInStock();
                Long productQuantity = cartItemsModelList.get(position).getProduct_quantity();
                Long maxQuantity = cartItemsModelList.get(position).getMaxQuantity();
                boolean qtyError = cartItemsModelList.get(position).isQtrError();
                List<String> qtyIds = cartItemsModelList.get(position).getQuantityIds();
                long stockQty = cartItemsModelList.get(position).getStockQuantity();
                ((cartItemViewHolder) viewHolder).setItemDetails(productID, resource, title, freeCoupoons, productPrice, cuttedPrice, offersApplied, position, inStock, String.valueOf(productQuantity), maxQuantity, qtyError, qtyIds, stockQty);
                break;

            case CartItemsModel.CART_TOTAL_PRICE:

                int totalItems = 0;
                int totalItemsPrice = 0;
                int totalAmount;
                int savedAmount = 0;
                String deliveryCharge;
                Dialog loadingdialog;

                for (int x = 0; x < cartItemsModelList.size(); x++) {
                    if (cartItemsModelList.get(x).isInStock()) {
                        int QTY = Integer.parseInt(String.valueOf(cartItemsModelList.get(x).getProduct_quantity()));
                        totalItems = totalItems + QTY;
                        totalItemsPrice = totalItemsPrice + Integer.parseInt(cartItemsModelList.get(x).getProduct_price()) * QTY;
                        if (!TextUtils.isEmpty(cartItemsModelList.get(x).getCuttedPrice())) {
                            savedAmount = savedAmount + (Integer.parseInt(cartItemsModelList.get(x).getCuttedPrice()) - Integer.parseInt(cartItemsModelList.get(x).getProduct_price()));

                        }
                    }
                }

                if (totalItemsPrice > 999) {
                    deliveryCharge = "FREE";
                    totalAmount = totalItemsPrice;
                } else {
                    deliveryCharge = "40";
                    totalAmount = totalItemsPrice + 40;
                }
                cartItemsModelList.get(position).setTotalItems(totalItems);
                cartItemsModelList.get(position).setTotalItemPrice(totalItemsPrice);
                cartItemsModelList.get(position).setDeliveryPrice(deliveryCharge);
                cartItemsModelList.get(position).setTotalAmount(totalAmount);
                cartItemsModelList.get(position).setSavedAmount(savedAmount);


                ((cartTotalAmountViewHolder) viewHolder).setTotalAmount(totalItems, totalItemsPrice, deliveryCharge, totalAmount, savedAmount);

                break;
            default:
                return;

        }
    }

    @Override
    public int getItemCount() {
        return cartItemsModelList.size();
    }

    class cartItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView removeBtn;
        private ImageView productImage, freecouponsImageView;
        private TextView productTitle, freeCoupons, productPrice, cuttedPrice, offersApplied, couponsApplied, productQuantity;


        public cartItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImagesCart);
            productTitle = itemView.findViewById(R.id.productTextCart);
            productPrice = itemView.findViewById(R.id.singleProductPrice);
            cuttedPrice = itemView.findViewById(R.id.mrpofSingleProduct);
            productQuantity = itemView.findViewById(R.id.deliveryQtyTextView);
            removeBtn = itemView.findViewById(R.id.RemoveItemText);
        }

        private void setItemDetails(final String productID, String resource, String title, Long freeCouponsNo,
                                    String productPriceText, String mrpcuttedPrice, Long offersAppliedNO,
                                    final int position, boolean inStock, final String quantity, final Long max_quantity, boolean qteError, final List<String> qtyIds, final long stockQty) {
            Glide.with(itemView.getContext()).load(resource)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder))
                    .into(productImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    intent.putExtra("product_ID", productID);
                    itemView.getContext().startActivity(intent);
                }
            });


            productTitle.setText(title);

            if (inStock) {
//                if (freeCouponsNo > 0) {
//                    freeCoupons.setVisibility(View.VISIBLE);
//                    freecouponsImageView.setVisibility(View.VISIBLE);
//                    if (freeCouponsNo == 1) {
//                        freeCoupons.setText("Free" + freeCouponsNo + "Coupon");
//                    } else {
//                        freeCoupons.setText("Free" + freeCouponsNo + "Coupons");
//                    }
//                } else {
//                    freeCoupons.setVisibility(View.INVISIBLE);
//                    freecouponsImageView.setVisibility(View.INVISIBLE);
//                }
                productPrice.setText("Rs." + productPriceText + "/-");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                cuttedPrice.setText("Rs." + mrpcuttedPrice + "/-");
//                couponLayout.setVisibility(View.VISIBLE);
                productQuantity.setText("Qty: " + quantity);
                if (!showDeleteBtn) {
                    if (qteError) {
                        productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                        productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red)));
                    } else {
                        productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                        productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.black)));
                    }
                }
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(itemView.getContext());
                        dialog.setContentView(R.layout.quanty_dialogue);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);


                        final EditText quantity_No = dialog.findViewById(R.id.quantityCount);

                        quantity_No.setHint("Max " + String.valueOf(max_quantity));

                        Button cancelBTn = dialog.findViewById(R.id.cancel_btn);
                        Button okBTn = dialog.findViewById(R.id.ok_btn);

                        cancelBTn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        okBTn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(quantity_No.getText())) {
                                    if (itemView.getContext() instanceof DashboardActivity) {
                                        dbQuaries.cartItemsModelList.get(position).setProduct_quantity(Long.valueOf(quantity_No.getText().toString()));
                                        dialog.dismiss();
                                    } else {
                                        if (delivery.fromCart) {
                                            dbQuaries.cartItemsModelList.get(position).setProduct_quantity(Long.valueOf(quantity_No.getText().toString()));
                                        } else {
                                            delivery.cartItemsModelList.get(position).setProduct_quantity(Long.valueOf(quantity_No.getText().toString()));
                                        }
                                        productQuantity.setText("Qty: " + quantity_No.getText());
                                        notifyItemChanged(cartItemsModelList.size() - 1);
                                        dialog.dismiss();
                                    }
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            } else {
                productPrice.setText("Out of stock!");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                cuttedPrice.setText(" ");
//                couponLayout.setVisibility(View.GONE);
                productQuantity.setText("Qty: " + 0);
                productQuantity.setTextColor(Color.parseColor("#50000000"));
                productQuantity.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#50000000")));
                productQuantity.setCompoundDrawables(null, null, null, null);
//                freeCoupons.setVisibility(View.INVISIBLE);

//                freecouponsImageView.setVisibility(View.INVISIBLE);
//                couponsApplied.setVisibility(View.GONE);
//                offersApplied.setVisibility(View.GONE);
            }

            if (showDeleteBtn) {
                removeBtn.setVisibility(View.VISIBLE);
            } else {
                removeBtn.setVisibility(View.INVISIBLE);
            }

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.RUNNING_CART_QUERY) {
                        ProductDetailsActivity.RUNNING_CART_QUERY = true;
                        dbQuaries.removeFromCartList(position, itemView.getContext(), cartTotalAmount);
                    }
                }
            });
        }

    }

    class cartTotalAmountViewHolder extends RecyclerView.ViewHolder {
        private TextView totalItem, totalItemPrice, deliveryPrice, savedAmount, newADDBtn;
        private Button continueBtn;
        private TextView addresTxt;
        private String name, number, ano;


        public cartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItem = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_charge);
            totalAmount = itemView.findViewById(R.id.total_price_items);
            savedAmount = itemView.findViewById(R.id.saved_amount);
            addresTxt = itemView.findViewById(R.id.fullname);
        }

        private void setTotalAmount(int itemCount, int itemsPrice, String DeliveryCharges, int TotalAmountINCART, int SAVEDAMOUNT) {
            totalItem.setText("Price(" + itemCount + " items)");
            totalItemPrice.setText("Rs." + itemsPrice + "/-");
            if (deliveryPrice.equals("FREE")) {
                deliveryPrice.setText(DeliveryCharges);
            } else {
                deliveryPrice.setText("Rs." + DeliveryCharges + "/-");
            }
            totalAmount.setText("Rs." + TotalAmountINCART + "/-");
            savedAmount.setText("You saved Rs." + SAVEDAMOUNT + "/- on this order");

            continueBtn = itemView.findViewById(R.id.cart_continue_btn);
            if (placeORDER) {
                continueBtn.setText("Place Order");
                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean allProductsAvaillable = true;
                        if (allProductsAvaillable) {
                            delivery.paymentDialog.show();
                        } else {
                            Toast.makeText(itemView.getContext(), "Sorry all products may not be available try changing the quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delivery.cartItemsModelList = new ArrayList<>();
                        delivery.fromCart = true;
                        for (int x = 0; x < dbQuaries.cartItemsModelList.size(); x++) {
                            CartItemsModel cartItemsModel = dbQuaries.cartItemsModelList.get(x);
                            if (cartItemsModel.isInStock()) {
                                delivery.cartItemsModelList.add(cartItemsModel);
                            }
                        }
                        CartActivity.loadingdialog.show();
                        System.out.println("qwe");
                        delivery.cartItemsModelList.add(new CartItemsModel(CartItemsModel.CART_TOTAL_PRICE));
                        if (dbQuaries.addressesModelList.size() == 0) {
                            dbQuaries.loadAddresses(itemView.getContext(), new Dialog(itemView.getContext()), true);
                        } else {
                            CartActivity.loadingdialog.dismiss();
                            Intent intent = new Intent(itemView.getContext(), delivery.class);
                            itemView.getContext().startActivity(intent);
                        }

                    }
                });
            }
            if (addres) {
                LinearLayout layout = (LinearLayout) addresTxt.getParent().getParent();
                layout.setVisibility(View.VISIBLE);
                fullName = itemView.findViewById(R.id.fullname);
                fullAddresses = itemView.findViewById(R.id.fuaddress);
                pincode = itemView.findViewById(R.id.pincode);
                name = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getName();
                number = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getPhone();
                ano = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getOnumber();
                fullName.setText(name + " No. " + number + " or " + ano);
                String addresses = "No: " + dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getFlatno()
                        + "," + dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getLocality()
                        + "," + dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getCity()
                        + ",Landmark: " + dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getLandmark();
                fullAddresses.setText(addresses);
                pincode.setText(dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getPincode());
                newADDBtn = itemView.findViewById(R.id.change_or_add_address_btn);
                newADDBtn.setText("Edit address");
                newADDBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delivery.getQtyIDs = false;
                        Intent intent = new Intent(itemView.getContext(), addnewaddress.class);
                        intent.putExtra("E","T");
                        intent.putExtra("INTENT","deliveryIntent");
                        itemView.getContext().startActivity(intent);
                        ((Activity)itemView.getContext()).finish();
                    }
                });
            } else {
                LinearLayout layout = (LinearLayout) addresTxt.getParent().getParent();
                layout.setVisibility(View.GONE);
            }

        }
    }


}