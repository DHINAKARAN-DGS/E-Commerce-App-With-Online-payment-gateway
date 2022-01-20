package com.daatstudios.cosmic_glow;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daatstudios.cosmic_glow.ProductDetailsActivity.addtoWL;
import static com.daatstudios.cosmic_glow.ProductDetailsActivity.productID;


public class dbQuaries {

    public static FirebaseFirestore firestore;
    public static String uemail, uname;

    public static boolean done254 = false;

    public static List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();

    public static List<List<HomeModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();

    public static List<String> wishlist = new ArrayList<>();
    public static List<wishlistModel> wishlistModelList = new ArrayList<>();

    public static List<String> myRatedIDs = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    public static List<String> cartlist = new ArrayList<>();
    public static List<String> varients = new ArrayList<>();
    public static List<String> idnum = new ArrayList<>();
    public static List<CartItemsModel> cartItemsModelList = new ArrayList<>();

    public static List<MyOrderItemsModel> orderItemsModelList = new ArrayList<>();

    public static List<addressesModel> addressesModelList = new ArrayList<>();
    public static int selectedAddresses = 0;
    static List<String> ids = new ArrayList<>();

    static List<HorizontalProductScrollModel> rcList = new ArrayList<>();
    static List<wishlistModel> allp = new ArrayList<>();
    static HomePageAdapter adapter;

    public static void loadCate(Context context,  boolean a,String cname) {
        String url = "https://etched-stitches.000webhostapp.com/getCatProducts.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object = array.getJSONObject(x);
                                if (a) {
                                    ViewAllCat.models.clear();
                                    final String PRODUCTID = object.get("product_ID").toString();
                                    if (!PRODUCTID.equals("")) {
                                        String url = "https://etched-stitches.000webhostapp.com/products.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new com.android.volley.Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONArray jsonArray = new JSONArray(response);
                                                            JSONObject object = jsonArray.getJSONObject(0);
                                                            ViewAllCat.models.add(new
                                                                    wishlistModel(
                                                                    object.get("product_image_1").toString(),
                                                                    0
                                                                    , (long) 0
                                                                    , object.get("product_title").toString()
                                                                    , object.get("product_price").toString()
                                                                    , object.get("cutted_price").toString()
                                                                    , ""
                                                                    , (boolean) true
                                                                    , PRODUCTID
                                                                    , true));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        ViewAllCat.adapter.notifyDataSetChanged();
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
                                                map.put("id", PRODUCTID);
                                                return map;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                                        requestQueue.add(stringRequest);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("cn", cname);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    public static void getProducts(Context context, int index, RecyclerView recyclerView) {
        HomeFragment.swipeRefreshLayout.setRefreshing(true);
        String url = "https://etched-stitches.000webhostapp.com/getGrid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            List<HorizontalProductScrollModel> GridProductScrollModelList = new ArrayList<>();
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object = array.getJSONObject(x);
                                System.out.println("Products" + response);
                                GridProductScrollModelList.add(new
                                        HorizontalProductScrollModel("",
                                        object.getString("PID")
                                        , ""
                                        , ""
                                        , ""
                                ));
                                adapter.notifyDataSetChanged();
                            }
                            lists.get(index).add(new HomeModel(3, "#ffffff", "title2", GridProductScrollModelList, 0));
                            adapter = new HomePageAdapter(lists.get(index));
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        HomeFragment.swipeRefreshLayout.setRefreshing(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void getBanners(final Context context, int index, RecyclerView recyclerView) {
        HomeFragment.swipeRefreshLayout.setRefreshing(true);
        String url = "https://etched-stitches.000webhostapp.com/getBanners.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            List<SliderModel> sliderModellist = new ArrayList<>();
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object = array.getJSONObject(x);
                                sliderModellist.add(new SliderModel(object.getString("banner"), object.getString("action")));
                                System.out.println(response);
                                adapter.notifyDataSetChanged();
                            }
                            lists.get(index).add(new HomeModel(0, sliderModellist));
                            adapter = new HomePageAdapter(lists.get(0));
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            getProducts(context, index, recyclerView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        HomeFragment.swipeRefreshLayout.setRefreshing(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void loadCategories(final RecyclerView catRV, final Context context) {
        categoryModelList.clear();
        HomeFragment.swipeRefreshLayout.setRefreshing(true);
        String url = "https://etched-stitches.000webhostapp.com/getCategories.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object = array.getJSONObject(x);
                                System.out.println("Cat" + response);
                                categoryModelList.add(new CategoryModel(object.get("category_icon").toString(), object.get("category_name").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            catRV.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        HomeFragment.swipeRefreshLayout.setRefreshing(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void loadFragmentData(final RecyclerView homeRV, final Context context, final int index, final String categoryName) {
        getBanners(context, 0, homeRV);
        adapter = new HomePageAdapter(lists.get(0));
        homeRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        HomeFragment.swipeRefreshLayout.setRefreshing(false);
    }

    public static void loadWishlist(Context context, Dialog dialog, boolean a) {
        wishlist.clear();
        dialog.show();
        String url = "https://etched-stitches.000webhostapp.com/getWishlist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object = array.getJSONObject(x);
                                System.out.println("123Products" + response);
                                wishlist.add(object.getString("product_ID"));
                                if (a) {
                                    wishlistModelList.clear();
                                    final String PRODUCTID = object.get("product_ID").toString();
                                    if (!PRODUCTID.equals("")) {
                                        String url = "https://etched-stitches.000webhostapp.com/products.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new com.android.volley.Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONArray jsonArray = new JSONArray(response);
                                                            JSONObject object = jsonArray.getJSONObject(0);
                                                            wishlistModelList.add(new
                                                                    wishlistModel(
                                                                    object.get("product_image_1").toString(),
                                                                    0
                                                                    , (long) 0
                                                                    , object.get("product_title").toString()
                                                                    , object.get("product_price").toString()
                                                                    , object.get("cutted_price").toString()
                                                                    , ""
                                                                    , (boolean) true
                                                                    , PRODUCTID
                                                                    , true));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        com.daatstudios.cosmic_glow.wishlist.wishlistAdapter.notifyDataSetChanged();
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
                                                map.put("id", PRODUCTID);
                                                return map;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                                        requestQueue.add(stringRequest);
                                    }
                                }
                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public static void removeFromWishList(final int index, final Context context) {
        final String removeProductID = wishlist.get(index);
        wishlist.remove(index);
        String url = "https://etched-stitches.000webhostapp.com/removeWishlist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Removed Successfully")) {
                            if (wishlistModelList.size() != 0) {
                                wishlistModelList.remove(index);
                                com.daatstudios.cosmic_glow.wishlist.wishlistAdapter.notifyDataSetChanged();
                            }
                            com.daatstudios.cosmic_glow.wishlist.wishlistAdapter.notifyDataSetChanged();
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISH_LIST = false;
                            Toast.makeText(context, "Removed Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            if (addtoWL != null) {
                                addtoWL.setSupportImageTintList(context.getResources().getColorStateList(R.color.red));
                            }
                            wishlist.add(index, removeProductID);
                            Toast.makeText(context, "Error: " + response, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.RUNNING_WISHLIST_QUERY = false;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("pid", removeProductID);
                map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductsData, final TextView cartCount, final TextView cartTotalAmount) {
        dialog.show();
        cartlist.clear();
        cartItemsModelList.clear();
        String url = "https://etched-stitches.000webhostapp.com/getCartlist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object = array.getJSONObject(x);
                                System.out.println("123Products" + response);
                                cartlist.add(object.getString("product_ID"));
                                if (dbQuaries.cartlist.contains(ProductDetailsActivity.productID)) {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART_LIST = true;
                                } else {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART_LIST = false;
                                }
                                if (loadProductsData) {
                                    final String PRODUCTID = object.get("product_ID").toString();
                                    if (!PRODUCTID.equals("")) {
                                        String url = "https://etched-stitches.000webhostapp.com/products.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new com.android.volley.Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONArray jsonArray = new JSONArray(response);
                                                            JSONObject object = jsonArray.getJSONObject(0);
                                                            int index = 0;
                                                            cartItemsModelList.add(index, new
                                                                    CartItemsModel(
                                                                    CartItemsModel.CART_ITEM_LAYOUT,
                                                                    object.getString("product_image_1"),
                                                                    (long) 0,//coupons
                                                                    (long) 0,//qty
                                                                    (long) 1,//qty
                                                                    (long) 0,
                                                                    object.getString("product_title"),
                                                                    object.getString("product_price"),
                                                                    object.getString("cutted_price"),
                                                                    PRODUCTID,
                                                                    true,
                                                                    (long) 50,
                                                                    (long) 0));

                                                            if (cartlist.size() == 0) {
                                                                cartItemsModelList.clear();
                                                            }
                                                            CartActivity.cartAdapter.notifyDataSetChanged();
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
                                                map.put("id", PRODUCTID);
                                                return map;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                                        requestQueue.add(stringRequest);
                                    }
                                }
                                if (cartlist.size() != 0) {
                                    cartCount.setVisibility(View.VISIBLE);
                                } else {
                                    cartCount.setVisibility(View.INVISIBLE);
                                }
                                if (dbQuaries.cartlist.size() < 99) {
                                    cartCount.setText(String.valueOf(dbQuaries.cartlist.size()));
                                } else {
                                    cartCount.setText(("99+"));
                                }
                            }
                            if (cartlist.size() >= 1) {
                                cartItemsModelList.add(new CartItemsModel(CartItemsModel.CART_TOTAL_PRICE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        dialog.dismiss();
    }

    public static void removeFromCartList(final int index, final Context context, final TextView cartTotalAmount) {
        final String removeProductID = cartlist.get(index);
        cartlist.remove(index);
        String url = "https://etched-stitches.000webhostapp.com/removeCartlist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Removed Successfully")) {
                            if (cartItemsModelList.size() != 0) {
                                cartItemsModelList.remove(index);
                                CartActivity.cartAdapter.notifyDataSetChanged();
                            }
                            CartActivity.cartAdapter.notifyDataSetChanged();
                            if (cartlist.size() == 0) {
                                CartActivity.cartItemsRV.setVisibility(View.GONE);
                            }
                            if (cartlist.size() == 0) {
                                CartActivity.cartAdapter.notifyDataSetChanged();
                            }
                            Toast.makeText(context, "Removed Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            cartlist.add(index, removeProductID);
                            Toast.makeText(context, "Error: " + response, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.RUNNING_CART_QUERY = false;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("pid", removeProductID);
                map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    public static void loadAddresses(final Context context, final Dialog loading, final boolean gotoDeliveryActivity) {
        addressesModelList.clear();
        loading.show();
        String url = "https://etched-stitches.000webhostapp.com/getAddresses.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("No data")){
                            addressesModelList.clear();
                        }else {
                            try {
                                JSONArray array = new JSONArray(response);
                                JSONObject object = array.getJSONObject(0);
                                addressesModelList.add(new
                                        addressesModel(object.get("fullname").toString()
                                        , object.get("mobile_no").toString()
                                        , object.get("city").toString()
                                        , object.get("locality").toString()
                                        , object.get("flatno").toString()
                                        , object.get("pincode").toString()
                                        , object.get("landmark").toString()
                                        , object.get("alter_mobile_no").toString()
                                        , true
                                ));
                                if (gotoDeliveryActivity) {
                                    Intent intent = new Intent(context, delivery.class);
                                    context.startActivity(intent);
                                }
                                if (delivery.fromCart && addressesModelList.size() == 0) {
                                    Intent intent = new Intent(context, addnewaddress.class);
                                    intent.putExtra("E", "F");
                                    intent.putExtra("INTENT", "deliveryIntent");
                                    context.startActivity(intent);
                                }
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loading.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
                loading.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public static void loadOrders(final Context context, @Nullable final MyOrderItemsAdapter orderItemsAdapter, final Dialog loadingDialog) {
        orderItemsModelList.clear();
        loadingDialog.show();

        String url = "https://etched-stitches.000webhostapp.com/getOrders.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int x=0;x<array.length();x++) {
                                JSONObject object = array.getJSONObject(x);
                                orderItemsModelList.add(new
                                        MyOrderItemsModel("id"
                                        , ""
                                        , object.getString("product_titles")
                                        , object.getString("order_status")
                                        , object.getString("cutted_prices")
                                        , object.getString("order_ID")
                                        , object.getString("product_prices")
                                        , object.getString("qtys")
                                        , object.getString("payment_method")
                                        , object.getString("user_ID")
                                        , object.getString("date")
                                        , object.getString("address")
                                        , object.getString("delivery_price")));
                            }
                            loadingDialog.dismiss();
                            orderItemsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> maps = new HashMap<>();
                maps.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return maps;
            }
        };

        HomeFragment.swipeRefreshLayout.setRefreshing(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        wishlist.clear();
        wishlistModelList.clear();
        cartlist.clear();
        cartItemsModelList.clear();
        myRatedIDs.clear();
        myRating.clear();
        addressesModelList.clear();
        orderItemsModelList.clear();
        ids.clear();
        rcList.clear();
        allp.clear();
        varients.clear();
    }


}

