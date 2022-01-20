package com.daatstudios.cosmic_glow;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {


    public static boolean RUNNING_WISHLIST_QUERY = false;
    public static boolean RUNNING_CART_QUERY = false;
    public static boolean RUNNING_Rating_QUERY = false;
    TextView badgeCount;

    public static boolean fromSearch = false;

    private Button couponReedemBtn;
    private LinearLayout cartBtn;
    private TextView cartTxt;
    public static MenuItem cartItem;

    private boolean in_stock = false;

    //productImage
    private ViewPager productImagesViewPager, productDetailsViewPager;
    private TabLayout viewPagerIndicator, productDetailsTabLayout;

    //Title
    private TextView ProductTitle;
    private TextView product_price, cutted_price;

    //COD
    private ImageView codIndicatorIM;
    private TextView codIndicatorTxt;
    private DocumentSnapshot documentSnapshot;

    //description
    private ConstraintLayout specLayout, detailsLayout;
    private String descriptionData;
    private String productOtherDetails;
    private int tabPostiton = -1;
    private List<productSpecsModel> productSpecsModelList = new ArrayList<>();
    private TextView productdetailsonly;

    //rewards
    private TextView rewardTitle, rewardBody;

    ///RATING
    public static LinearLayout ratenowLayoutContainer;
    private Button buyNow;
    public static int initaialRating;
    ///Coupon Dialog
    public static TextView couponTitle, CouponBody, CouponVAlidity;
    public static RecyclerView RecylerView;
    private static LinearLayout selected;
    private Dialog loadingdialog;

    FirebaseFirestore firebaseFirestore;
    public static String productID;
    FirebaseAuth firebaseAuth;

    List<String> rtags;
    TextView ct;

    public static FloatingActionButton addtoWL;
    public static Boolean ALREADY_ADDED_TO_WISH_LIST = false;
    public static Boolean ALREADY_ADDED_TO_CART_LIST = false;


    String name="";
    String price="";
    String mrp="";
    String des="";
    String im1="";
    String im2="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        ProductTitle = findViewById(R.id.productTitle);
        product_price = findViewById(R.id.productPrice);
        cutted_price = findViewById(R.id.mrp);
        codIndicatorIM = findViewById(R.id.codindicator);
        codIndicatorTxt = findViewById(R.id.codtext);
        rewardTitle = findViewById(R.id.reward_name);
        rewardBody = findViewById(R.id.rewardDisccription);
        specLayout = findViewById(R.id.TABLAYOUT);
        detailsLayout = findViewById(R.id.ProductDetailsContainer);
        productdetailsonly = findViewById(R.id.ProductsDetailsBody);
        cartBtn = findViewById(R.id.add_to_cart_btn);
        cartTxt = findViewById(R.id.cartText);

        ct = (TextView) cartBtn.getChildAt(0);

        badgeCount = (TextView) findViewById(R.id.badgeCount);


//        couponReedemBtn = findViewById(R.id.open_redeem_btn);
        firebaseFirestore = FirebaseFirestore.getInstance();
        productImagesViewPager = findViewById(R.id.productImagesViewPager);
        viewPagerIndicator = findViewById(R.id.ViewpagerIndicator);
        addtoWL = findViewById(R.id.addtoWishListButton);
        buyNow = findViewById(R.id.buy_now_btn);
        productDetailsTabLayout = findViewById(R.id.productDetailsTablayout);
        productDetailsViewPager = findViewById(R.id.productDetailsViewPager);

        initaialRating = -1;


        loadingdialog = new Dialog(ProductDetailsActivity.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingdialog.show();

        //productImages
        final List<String> productImages = new ArrayList<>();

        productID = getIntent().getStringExtra("product_ID");


        String url = "https://etched-stitches.000webhostapp.com/products.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object = jsonArray.getJSONObject(0);
                             name = object.getString("product_title");
                             price = object.getString("product_price");
                             mrp = object.getString("cutted_price");
                             des = object.getString("product_description");
                             im1 = object.getString("product_image_1");
                             im2 = object.getString("product_image_2");

                            ProductTitle.setText(name);
                            product_price.setText("Rs."+price+"/-");
                            cutted_price.setText("Rs."+mrp+"/-");
                            specLayout.setVisibility(View.GONE);
                            detailsLayout.setVisibility(View.VISIBLE);
                            productdetailsonly.setText(des);
                            productImages.add(im1);
                            productImages.add(im2);

                            if (dbQuaries.wishlist.contains(productID)) {
                                ALREADY_ADDED_TO_WISH_LIST = true;
                                addtoWL.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                            } else {
                                ALREADY_ADDED_TO_WISH_LIST = false;
                            }

                            ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                            productImagesViewPager.setAdapter(productImagesAdapter);
                            loadingdialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                map.put("id", productID);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbQuaries.cartlist.contains(productID)) {
                    Toast.makeText(ProductDetailsActivity.this, "Already in cart :)", Toast.LENGTH_SHORT).show();
                } else {
                    ALREADY_ADDED_TO_CART_LIST = true;
                    String url = "https://etched-stitches.000webhostapp.com/addCart.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("Added Successfully")) {
                                        Toast.makeText(ProductDetailsActivity.this, "Added to cart successfully :)", Toast.LENGTH_SHORT).show();
                                        dbQuaries.cartlist.add(productID);
                                        badgeCount.setVisibility(View.VISIBLE);
                                        if (dbQuaries.cartlist.size() < 99) {
                                            badgeCount.setText(String.valueOf(dbQuaries.cartlist.size()));
                                        } else {
                                            badgeCount.setText(("99+"));
                                        }
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
                            map.put("pid", productID);
                            map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailsActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (dbQuaries.wishlist.size() == 0) {
                dbQuaries.loadWishlist(ProductDetailsActivity.this, loadingdialog, false);
            } else {
                loadingdialog.dismiss();
            }
        } else {
            loadingdialog.dismiss();
        }


        if (dbQuaries.cartlist.contains(productID)) {
            ALREADY_ADDED_TO_CART_LIST = true;
        } else {
            ALREADY_ADDED_TO_CART_LIST = false;
        }
        if (dbQuaries.wishlist.contains(productID)) {
            ALREADY_ADDED_TO_WISH_LIST = true;
            addtoWL.setSupportImageTintList(getResources().getColorStateList(R.color.red));
        } else {
            addtoWL.setSupportImageTintList(getResources().getColorStateList(R.color.grey));
            ALREADY_ADDED_TO_WISH_LIST = false;
        }

        addtoWL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ALREADY_ADDED_TO_WISH_LIST) {
                    String url = "https://etched-stitches.000webhostapp.com/addWishlist.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("Added Successfully")) {
                                        addtoWL.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                                        Toast.makeText(ProductDetailsActivity.this, "Added to wishlist :)", Toast.LENGTH_SHORT).show();
                                        ALREADY_ADDED_TO_WISH_LIST = true;
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
                            map.put("pid", productID);
                            map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailsActivity.this);
                    requestQueue.add(stringRequest);
                } else {
                    String url = "https://etched-stitches.000webhostapp.com/removeWishlist.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("Removed Successfully")) {
                                        addtoWL.setSupportImageTintList(getResources().getColorStateList(R.color.light_gray_a9a9a9));
                                        Toast.makeText(ProductDetailsActivity.this, "Removed from wishlist :(", Toast.LENGTH_SHORT).show();
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
                            map.put("pid", productID);
                            map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailsActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingdialog.show();
                delivery.fromCart = false;
                delivery.cartItemsModelList.clear();
                delivery.cartItemsModelList = new ArrayList<>();
                delivery.cartItemsModelList.add(new
                        CartItemsModel(
                        CartItemsModel.CART_ITEM_LAYOUT,
                        im1
                        , (long) 0 //offers pplied
                        , (long) 0
                        , (long) 1 //quantity
                        , (long) 0 //coupons applied
                        ,name
                        , price
                        , mrp
                        , productID
                        , true
                        , (long) 50
                        , (long) 500
                ));
                delivery.cartItemsModelList.add(new CartItemsModel(CartItemsModel.CART_TOTAL_PRICE));


                if (dbQuaries.addressesModelList.size() == 0) {
                    loadingdialog.dismiss();
                    Intent intent = new Intent(ProductDetailsActivity.this, addnewaddress.class);
                    intent.putExtra("E","F");
                    intent.putExtra("INTENT","deliveryIntent");
                    startActivity(intent);
                } else {
                    loadingdialog.dismiss();
                    Intent intent = new Intent(ProductDetailsActivity.this, delivery.class);
                    startActivity(intent);
                }
            }
        });


        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPostiton = tab.getPosition();

                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static void openRecylerView() {
        if (RecylerView.getVisibility() == View.GONE) {
            RecylerView.setVisibility(View.VISIBLE);
            selected.setVisibility(View.GONE);
        } else {
            RecylerView.setVisibility(View.GONE);
            selected.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart, menu);
        cartItem = menu.findItem(R.id.main_cart);
        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badgeIcon);
        badgeIcon.setImageResource(R.drawable.cart);
        badgeCount = cartItem.getActionView().findViewById(R.id.badgeCount);
        if (dbQuaries.cartlist.size() == 0) {
            dbQuaries.loadCartList(ProductDetailsActivity.this, loadingdialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
        } else {
            badgeCount.setVisibility(View.VISIBLE);
            if (dbQuaries.cartlist.size() < 99) {
                badgeCount.setText(String.valueOf(dbQuaries.cartlist.size()));
            } else {
                badgeCount.setText(("99+"));
            }
        }
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_notifications) {
            Intent intent = new Intent(ProductDetailsActivity.this, searchActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.main_cart) {
            Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
        if (dbQuaries.wishlist.size() == 0) {
            dbQuaries.loadWishlist(ProductDetailsActivity.this, loadingdialog, false);
        } else {
            loadingdialog.dismiss();
        }
        if (dbQuaries.cartlist.contains(productID)) {
            ALREADY_ADDED_TO_CART_LIST = true;
        } else {
            ALREADY_ADDED_TO_CART_LIST = false;
        }
        if (dbQuaries.wishlist.contains(productID)) {
            ALREADY_ADDED_TO_WISH_LIST = true;
            addtoWL.setSupportImageTintList(getResources().getColorStateList(R.color.red));
        } else {
            addtoWL.setSupportImageTintList(getResources().getColorStateList(R.color.grey));
            ALREADY_ADDED_TO_WISH_LIST = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fromSearch = false;
    }
}