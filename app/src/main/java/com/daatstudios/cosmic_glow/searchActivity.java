package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daatstudios.cosmic_glow.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class searchActivity extends AppCompatActivity {

    private androidx.appcompat.widget.SearchView searchView;
    private TextView textView;
    private RecyclerView recyclerView;
    List<wishlistModel> wishlistModelList = new ArrayList<>();
    wishlistAdapter adapter;
    StringBuilder searchStr = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        searchView = findViewById(R.id.searchView);
        textView = findViewById(R.id.noproductsTxt);
        recyclerView = findViewById(R.id.searchRV);

        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(searchActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new wishlistAdapter(wishlistModelList, false);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String[] que = query.split(" ");
                searchStr=  new StringBuilder();
                for (int x = 0; x < que.length; x++) {
                    if (x > 0) {
                        searchStr.append("|");
                    }
                    searchStr.append(que[x]);
                }
                searchM(searchStr.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void searchM(String que) {
        wishlistModelList.clear();
        String url = "https://etched-stitches.000webhostapp.com/searchQuery.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object = array.getJSONObject(x);
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
                                        , object.getString("id")
                                        , true));
                                adapter.notifyDataSetChanged();
                            }
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
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("q", que);
                System.out.println("aaaa"+que);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(searchActivity.this);
        requestQueue.add(stringRequest);
    }
}