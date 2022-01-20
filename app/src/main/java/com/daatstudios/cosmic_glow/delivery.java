package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daatstudios.cosmic_glow.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class delivery extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "";
    private RecyclerView deliveryRV;
    FirebaseFirestore firebaseFirestore;
    private TextView newADDBtn;
    Button continueBtn;
    public static final int SELECT_ADDRESS = 0;

    public static Dialog loadingDialog, paymentDialog;
    ImageButton razorpay;
    ImageButton cashOD;
    public static Dialog loadingdialog;
    private String paymentMethod = "RAZORPAY";

    private boolean successesResponse = false;
    public static boolean fromCart;
    final int UPI_PAYMENT = 0;
    String order_ID = UUID.randomUUID().toString().substring(0, 22);


    public static boolean getQtyIDs = true;
    public static CartAdapter cartAdapter;


    public static List<CartItemsModel> cartItemsModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getSupportActionBar().setTitle("Delivery Confirmation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseFirestore = FirebaseFirestore.getInstance();
        getQtyIDs = true;

        continueBtn = findViewById(R.id.cart_continue_btn);


        loadingDialog = new Dialog(delivery.this);
        loadingDialog.setContentView(R.layout.loading_prog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        paymentDialog = new Dialog(delivery.this);
        paymentDialog.setContentView(R.layout.payments_options);
        paymentDialog.setCancelable(true);
        paymentDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        paymentDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        razorpay = paymentDialog.findViewById(R.id.paytmtype);
        cashOD = paymentDialog.findViewById(R.id.codtype);


        razorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = "RAZORPAY";
                placeOrder();
            }
        });
        cashOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = "COD";
                placeOrder();
            }
        });


        deliveryRV = findViewById(R.id.deliveryRecyclarView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRV.setLayoutManager(layoutManager);


        cartAdapter = new CartAdapter(cartItemsModelList, new TextView(delivery.this), false, true, true);
        deliveryRV.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        loadingdialog = new Dialog(delivery.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void startPayment() {
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Cosmic Glow");
            options.put("description", "ORDERID : " + order_ID);
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            String payment = CartAdapter.totalAmount.getText().toString().substring(3, CartAdapter.totalAmount.getText().length() - 2);
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response " + resultCode);
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {

        String str = data.get(0);
        Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
        String paymentCancel = "";
        if (str == null) str = "discard";
        String status = "";
        String approvalRefNo = "";
        String response[] = str.split("&");
        for (int i = 0; i < response.length; i++) {
            String equalStr[] = response[i].split("=");
            if (equalStr.length >= 2) {
                if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                    status = equalStr[1].toLowerCase();
                } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                    approvalRefNo = equalStr[1];
                }
            } else {
                paymentCancel = "Payment cancelled by user.";
            }
        }
        if (status.equals("success")) {
            //Code to handle successful transaction here.
            Toast.makeText(delivery.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "payment successfull: " + approvalRefNo);
        } else if ("Payment cancelled by user.".equals(paymentCancel)) {
            Toast.makeText(delivery.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "Cancelled by user: " + approvalRefNo);
        } else {
            Toast.makeText(delivery.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            Log.e("UPI", "failed payment: " + approvalRefNo);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void conformationActivity() {
        getQtyIDs = false;
        if (fromCart) {
            loadingdialog.show();
            String url = "https://etched-stitches.000webhostapp.com/deleteCart.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Success")) {
                                Toast.makeText(delivery.this, " :) ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(delivery.this, orderConformed.class);
                                intent.putExtra("order_ID", order_ID);
                                startActivity(intent);
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.toString());
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(delivery.this);
            requestQueue.add(stringRequest);
        } else {
        Intent intent = new Intent(delivery.this, orderConformed.class);
        intent.putExtra("order_ID", order_ID);
        startActivity(intent);
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Transaction success! ", Toast.LENGTH_SHORT).show();
        successesResponse = true;
        loadingDialog.show();
        String url = "https://etched-stitches.000webhostapp.com/updateOrders.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(delivery.this, response, Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                        loadingdialog.dismiss();
                        conformationActivity();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingdialog.dismiss();
                Toast.makeText(delivery.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> orderDetails = new HashMap<>();
                orderDetails.put("order_ID", order_ID);
                orderDetails.put("payment_status", "Paid");
                return orderDetails;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(delivery.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG, "error code " + String.valueOf(i) + " -- Payment failed " + s.toString());
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();
    }

    private void placeOrder() {
        loadingDialog.show();
        StringBuilder productnames = new StringBuilder();
        StringBuilder productqtys = new StringBuilder();
        StringBuilder productprice = new StringBuilder();
        StringBuilder productids = new StringBuilder();
        StringBuilder productmrp = new StringBuilder();

        int a =0;

        for (final CartItemsModel cartItemsModel : cartItemsModelList) {
            if (cartItemsModel.getType() == CartItemsModel.CART_ITEM_LAYOUT) {
                a++;
                if (a>1) {
                    productnames.append(",").append(cartItemsModel.getProduct_title());
                    productids.append(",").append(cartItemsModel.getProductID());
                    productqtys.append(",").append(cartItemsModel.getProduct_quantity());
                    productprice.append(",").append(cartItemsModel.getProduct_price());
                    productmrp.append(",").append(cartItemsModel.getCuttedPrice());
                }else{
                    productnames.append(cartItemsModel.getProduct_title());
                    productids.append(cartItemsModel.getProductID());
                    productqtys.append(cartItemsModel.getProduct_quantity());
                    productprice.append(cartItemsModel.getProduct_price());
                    productmrp.append(cartItemsModel.getCuttedPrice());
                }
            } else {
                String total_items = String.valueOf(cartItemsModel.getTotalItems());
                String total_items_price = String.valueOf(cartItemsModel.getTotalItemPrice());
                String total_amount = String.valueOf(cartItemsModel.getTotalAmount());
                String payment_status = "not paid";
                String addresses = CartAdapter.fullAddresses.getText().toString();
                String fullname = CartAdapter.fullName.getText().toString();
                String pincode = CartAdapter.pincode.getText().toString();

                String url = "https://etched-stitches.000webhostapp.com/addOrders.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(delivery.this, response, Toast.LENGTH_SHORT).show();
                                System.out.println(response);
                                loadingdialog.dismiss();
                                if (paymentMethod.equals("RAZORPAY")) {
                                    razorpay();
                                } else {
                                    cod();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingdialog.dismiss();
                        Toast.makeText(delivery.this, error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        Map<String, String> orderDetails = new HashMap<>();
                        orderDetails.put("order_ID", order_ID);
                        orderDetails.put("product_IDs", productids.toString());
                        orderDetails.put("user_ID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        orderDetails.put("qtys", productqtys.toString());

                        orderDetails.put("cutted_prices", productmrp.toString());

                        orderDetails.put("product_prices", productprice.toString());
                        orderDetails.put("payment_method", paymentMethod);
                        orderDetails.put("payment_status", payment_status);
                        orderDetails.put("product_titles", productnames.toString());
                        orderDetails.put("delivery_price", cartItemsModel.getDeliveryPrice());
                        orderDetails.put("order_status", "Ordered");
                        orderDetails.put("date", currentDate+" "+currentTime);
                        orderDetails.put("address", addresses);
                        orderDetails.put("pincode", pincode);
                        orderDetails.put("fullname", fullname);
                        orderDetails.put("saved_amount", String.valueOf(cartItemsModel.getSavedAmount()));
                        orderDetails.put("total_items", total_items);
                        orderDetails.put("total_items_price", total_items_price);
                        orderDetails.put("totalamount", total_amount);


                        return orderDetails;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(delivery.this);
                requestQueue.add(stringRequest);
            }
        }


    }

    private void razorpay() {
        getQtyIDs = false;
        paymentDialog.dismiss();
        startPayment();
    }

    private void cod() {
        getQtyIDs = false;
        paymentDialog.dismiss();
        successesResponse = true;
        loadingDialog.show();
        conformationActivity();

    }
}