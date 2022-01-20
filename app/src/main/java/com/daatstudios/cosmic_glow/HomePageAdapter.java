package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    List<HomeModel> homeModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private int lastPosition = -1;


    public HomePageAdapter(List<HomeModel> homeModelList) {
        this.homeModelList = homeModelList;
        this.recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homeModelList.get(position).getType()) {
            case 0:
                return HomeModel.BannerSlider;
            case 1:
                return HomeModel.STRIP_AD;
            case 2:
                return HomeModel.HORIZONTAL_PRODUCT_LAYOUT;
            case 3:
                return HomeModel.GRID_PRODUCT_LAYOUT;
            default:
                return -1;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeModel.BannerSlider:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_ad, parent, false);
                return new bannerSliderViewHolder(bannerSliderView);

            case HomeModel.STRIP_AD:
                View stripView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdView(stripView);

            case HomeModel.HORIZONTAL_PRODUCT_LAYOUT:
                View horizontalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new horizontalScrollViewHolder(horizontalView);

            case HomeModel.GRID_PRODUCT_LAYOUT:
                View gridView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_products_view, parent, false);
                return new GRIDPRODUCTVIEWHOLDER(gridView2);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homeModelList.get(position).getType()) {
            case HomeModel.BannerSlider:
                List<SliderModel> sliderModelList = homeModelList.get(position).getSliderModelList();
                ((bannerSliderViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomeModel.STRIP_AD:
                String resource = homeModelList.get(position).getResource();
                ((StripAdView) holder).setStripAd(resource);
                break;
            case HomeModel.HORIZONTAL_PRODUCT_LAYOUT:
                String HorizontalTitle = homeModelList.get(position).getTitle();
                String color = homeModelList.get(position).getColor();
                List<wishlistModel> viewAllProductsList = homeModelList.get(position).getViewAllProductsList();
                List<HorizontalProductScrollModel> horizontalProductScrollModel = homeModelList.get(position).getHorizontalProductScrollModelList();
                ((horizontalScrollViewHolder) holder).sethorizontalProductsLayout(HorizontalTitle, color, viewAllProductsList, horizontalProductScrollModel);
                break;

            case HomeModel.GRID_PRODUCT_LAYOUT:
                String GridTitle = homeModelList.get(position).getTitle();
                String bgcolor = homeModelList.get(position).getColor();
                int index = homeModelList.get(position).getIndex();
                List<HorizontalProductScrollModel> GridProductScrollModel = homeModelList.get(position).getHorizontalProductScrollModelList();
                ((GRIDPRODUCTVIEWHOLDER) holder).setGridProductLayout(GridTitle, bgcolor, GridProductScrollModel, index);
                break;
            default:
                return;
        }
        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }

    public class bannerSliderViewHolder extends RecyclerView.ViewHolder {
        private ViewPager viewPager2;
        private int currentPage;
        private Timer timer;
        private static final long TIMER_BANNER = 3000;
        private static final long PERIOD_TIME = 3000;
        private List<SliderModel> arrangeList;

        public bannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager2 = itemView.findViewById(R.id.bannerSlider);
        }

        ////////////VIEW PAGER
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
            currentPage = 2;
            if (timer != null) {
                timer.cancel();
            }
            arrangeList = new ArrayList<>();
            for (int x = 0; x < sliderModelList.size(); x++) {
                arrangeList.add(x, sliderModelList.get(x));

            }
            arrangeList.add(0, sliderModelList.get(sliderModelList.size() - 2));
            arrangeList.add(1, sliderModelList.get(sliderModelList.size() - 1));
            arrangeList.add(sliderModelList.get(0));
            arrangeList.add(sliderModelList.get(1));


            SliderAdapter sliderAdapter = new SliderAdapter(arrangeList);
            viewPager2.setAdapter(sliderAdapter);
            viewPager2.setClipToPadding(false);
            viewPager2.setPageMargin(20);


            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        pageLooper(arrangeList);
                    }
                }
            };
            viewPager2.setOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(arrangeList);
            viewPager2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangeList);
                    stopBanner();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(arrangeList);
                    }
                    return false;
                }
            });
        }

        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                viewPager2.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                viewPager2.setCurrentItem(currentPage, false);
            }
        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    viewPager2.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, TIMER_BANNER, PERIOD_TIME);


        }

        private void stopBanner() {
            timer.cancel();
        }
        //////////////VIEW PAGER
    }

    public class StripAdView extends RecyclerView.ViewHolder {
        //STRIP AD
        private ImageView stripImage;
        private ConstraintLayout stripLayout;

        //STRIP AD
        public StripAdView(@NonNull View itemView) {
            super(itemView);
            stripImage = itemView.findViewById(R.id.stripAdView);
            stripLayout = itemView.findViewById(R.id.stripAdContainer);

        }

        private void setStripAd(String resource) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.drawable.placeholder).into(stripImage);
        }
    }

    public class horizontalScrollViewHolder extends RecyclerView.ViewHolder {


        private TextView layoutName;
        private Button ViewAllHorizontalView;
        private RecyclerView HorizontalProductsView;
        private ConstraintLayout constraintLayout;

        public horizontalScrollViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutName = itemView.findViewById(R.id.dealsOfDay);
            ViewAllHorizontalView = (Button) itemView.findViewById(R.id.ViewAllDealsOfTheDay);
            HorizontalProductsView = (RecyclerView) itemView.findViewById(R.id.horizontalProductsSv);
            HorizontalProductsView.setRecycledViewPool(recycledViewPool);
            constraintLayout = itemView.findViewById(R.id.hSCL);
        }

        private void sethorizontalProductsLayout(final String title, String color, final List<wishlistModel> viewAllProductList, final List<HorizontalProductScrollModel> horizontalProductScrollModelsList) {
            layoutName.setText(title);
            constraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

            for (final HorizontalProductScrollModel model : horizontalProductScrollModelsList) {
                if (!model.getProductId().isEmpty() && model.getProductName().isEmpty()) {
                    firestore.collection("PRODUCTS")
                            .document(model.getProductId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                model.setProductName(task.getResult().getString("product_title"));
                                model.setProductPrice(task.getResult().getString("product_price"));
                                model.setProductImage(task.getResult().getString("product_image_1"));

                                wishlistModel wishlistModel = viewAllProductList.get(horizontalProductScrollModelsList.indexOf(model));

                                wishlistModel.setTotalRatings(0);
                                wishlistModel.setRatingWL("");
                                wishlistModel.setProductTtileWl(task.getResult().getString("product_title"));
                                wishlistModel.setCutterPriceWL(task.getResult().getString("cutted_price"));
                                wishlistModel.setProductImageWL(task.getResult().getString("product_image_1"));
                                wishlistModel.setPaymentMethod(task.getResult().getBoolean("COD"));
                                wishlistModel.setFreecouponWL(0);
                                wishlistModel.setIn_stock(task.getResult().getLong("stock_quantity") > 0);

                                if (horizontalProductScrollModelsList.indexOf(model) == horizontalProductScrollModelsList.size() - 1) {

                                    if (HorizontalProductsView.getAdapter() != null) {
                                        HorizontalProductsView.getAdapter().notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(itemView.getContext(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                            } else {
                                Toast.makeText(itemView.getContext(), "E", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            if (horizontalProductScrollModelsList.size() > 8) {
                ViewAllHorizontalView.setVisibility(View.VISIBLE);

                ViewAllHorizontalView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewAllActivity.wishlistModelList = viewAllProductList;
                        Intent viewallintent = new Intent(itemView.getContext(), viewAllActivity.class);
                        viewallintent.putExtra("Layout_code", 0);
                        viewallintent.putExtra("title", title);
                        itemView.getContext().startActivity(viewallintent);
                    }
                });
            } else {
                ViewAllHorizontalView.setVisibility(View.INVISIBLE);
            }

            HorizontalScrollAdapter horizontalScrollAdapter = new HorizontalScrollAdapter(horizontalProductScrollModelsList);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
            HorizontalProductsView.setLayoutManager(linearLayoutManager2);
            HorizontalProductsView.setAdapter(horizontalScrollAdapter);
            horizontalScrollAdapter.notifyDataSetChanged();
        }

    }

    public class GRIDPRODUCTVIEWHOLDER extends RecyclerView.ViewHolder {
        private GridLayout gridProductLayout;
        private TextView gridLayoutTitle;
        private Button GridopenAll;
        private ConstraintLayout gridColoe;

        public GRIDPRODUCTVIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.GridLayoutTitle);
            GridopenAll = itemView.findViewById(R.id.GridLayoutViewAll);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
            gridColoe = itemView.findViewById(R.id.gridCL);
        }

        private void setGridProductLayout(final String title, final String bgcolor, final List<HorizontalProductScrollModel> horizontalProductScrollModelsList, final int index) {
            gridLayoutTitle.setText(title);
            gridColoe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bgcolor)));

            for (final HorizontalProductScrollModel model : horizontalProductScrollModelsList) {
                if (!model.getProductId().isEmpty() && model.getProductName().isEmpty()) {
                    System.out.println("Hi"+model.getProductId());
                    String url = "https://etched-stitches.000webhostapp.com/getGridProducts.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        System.out.println("GProducts" + response);
                                        JSONArray array = new JSONArray(response);
                                        for (int x = 0; x < array.length(); x++)
                                        {
                                            JSONObject object = array.getJSONObject(x);
                                            System.out.println("GProducts" + response);
                                            model.setProductName(object.getString("product_title"));
                                            model.setProductDiscription(object.getString("product_discription"));
                                            model.setProductImage(object.getString("product_image_1"));
                                            model.setProductPrice(object.getString("product_price"));
                                        }
                                        for (int x =0;x<4;x++)
                                        {
                                            System.out.println("Hi123"+horizontalProductScrollModelsList.get(x).getProductImage());
                                            ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.HorizontalProductImage);
                                            TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.HorizontalScrollProductName);
                                            TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.HorizontalScrollProductPrize);

                                            Glide.with(itemView.getContext()).load(horizontalProductScrollModelsList.get(x).getProductImage())
                                                    .apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);

                                            productTitle.setText(horizontalProductScrollModelsList.get(x).getProductName());
                                            productPrice.setText("Rs."+horizontalProductScrollModelsList.get(x).getProductPrice()+"/-");
                                            gridProductLayout.getChildAt(x).setBackgroundResource(R.color.white);

                                            final int finalX = x;
                                            gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (!title.equals("")){
                                                        Intent intent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                                                        intent.putExtra("product_ID",horizontalProductScrollModelsList.get(finalX).getProductId());
                                                        itemView.getContext().startActivity(intent);
                                                    }
                                                }
                                            });

                                        }
                                        //setGridData(bgcolor, title, horizontalProductScrollModelsList, index);
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
                            Map<String, String> parms = new HashMap<String, String>();
                            parms.put("id", model.getProductId());
                            return parms;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
                    requestQueue.add(stringRequest);


                    if (horizontalProductScrollModelsList.indexOf(model) == horizontalProductScrollModelsList.size() - 1) {
                        setGridData(title, bgcolor, horizontalProductScrollModelsList, index);
                        if (!title.equals("")) {
                            GridopenAll.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewAllActivity.horizontalProductScrollModelsList = horizontalProductScrollModelsList;
                                    Intent viewallintent = new Intent(itemView.getContext(), viewAllActivity.class);
                                    viewallintent.putExtra("Layout_code", 1);
                                    viewallintent.putExtra("title", title);
                                    itemView.getContext().startActivity(viewallintent);
                                }
                            });
                        } else {
                            setGridProductLayout(title, bgcolor, horizontalProductScrollModelsList, index);
                        }
                    }
                }
            }

        }

        private void setGridData(final String c, final String title, final List<HorizontalProductScrollModel> horizontalProductScrollModelsList, int index) {

        }
    }



}
