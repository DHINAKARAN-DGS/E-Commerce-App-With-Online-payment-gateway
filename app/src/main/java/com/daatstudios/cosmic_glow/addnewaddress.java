package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addnewaddress extends AppCompatActivity {


    private Button saveBTN;
    private Dialog loadingdialog;
    private EditText name, phone, city, locality, flatno, pincode, landmark, onumber;
    private boolean UPDATEADDRESSES = false;
    private addressesModel addressesModel;
    private int position;

    String edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add a new address");

        edit = getIntent().getStringExtra("E");

        name = findViewById(R.id.user_name);
        phone = findViewById(R.id.userPhoneNumber);
        city = findViewById(R.id.userCity);
        locality = findViewById(R.id.userLocality);
        flatno = findViewById(R.id.userBuildingNo);
        pincode = findViewById(R.id.UserPincode);
        landmark = findViewById(R.id.userLandmark);
        onumber = findViewById(R.id.userOptionalPhone);

        loadingdialog = new Dialog(addnewaddress.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (edit.equals("T")) {
            loadingdialog.show();
            String url = "https://etched-stitches.000webhostapp.com/getAddresses.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                JSONObject object = array.getJSONObject(0);
                                name.setText(object.get("fullname").toString());
                                phone.setText(object.get("mobile_no").toString());
                                city.setText(object.get("city").toString());
                                locality.setText(object.get("locality").toString());
                                flatno.setText(object.get("flatno").toString());
                                pincode.setText(object.get("pincode").toString());
                                landmark.setText(object.get("landmark").toString());
                                onumber.setText(object.get("alter_mobile_no").toString());
                                loadingdialog.dismiss();
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
                    map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        saveBTN = findViewById(R.id.save_btn);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkinputs();
            }
        });

    }

    private void chkinputs() {
        if (!name.getText().toString().isEmpty()) {
            if (!phone.getText().toString().isEmpty() && phone.getText().length() == 10) {
                if (!city.getText().toString().isEmpty()) {
                    if (!locality.getText().toString().isEmpty()) {
                        if (!flatno.getText().toString().isEmpty()) {
                            if (!pincode.getText().toString().isEmpty() && (pincode.getText().toString().length() == 6)) {
                                if (!landmark.getText().toString().isEmpty()) {
                                    if (edit.equals("T")) {
                                        loadingdialog.show();
                                        String url = "https://etched-stitches.000webhostapp.com/updateaddresses.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(addnewaddress.this, response, Toast.LENGTH_SHORT).show();
                                                        System.out.println(response);
                                                        loadingdialog.dismiss();
                                                        dbQuaries.addressesModelList.clear();
                                                        dbQuaries.addressesModelList.add(new addressesModel(
                                                                name.getText().toString(),
                                                                phone.getText().toString(),
                                                                city.getText().toString(),
                                                                locality.getText().toString(),
                                                                flatno.getText().toString(),
                                                                pincode.getText().toString(),
                                                                landmark.getText().toString(),
                                                                onumber.getText().toString(),
                                                                true));
                                                        if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")){
                                                            Intent intent = new Intent(addnewaddress.this,delivery.class);
                                                            startActivity(intent);
                                                        }
                                                        finish();
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                loadingdialog.dismiss();
                                                Toast.makeText(addnewaddress.this, error.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> addAddresses = new HashMap<>();
                                                addAddresses.put("mobile_no", phone.getText().toString());
                                                if (!onumber.getText().toString().equals("")) {
                                                    addAddresses.put("alter_mobile_no", onumber.getText().toString());
                                                } else {
                                                    addAddresses.put("alter_mobile_no", "Alternate number not Provided");
                                                }
                                                addAddresses.put("fullname", name.getText().toString());
                                                addAddresses.put("city", city.getText().toString());
                                                addAddresses.put("locality", locality.getText().toString());
                                                addAddresses.put("flatno", flatno.getText().toString());
                                                addAddresses.put("pincode", pincode.getText().toString());
                                                addAddresses.put("landmark", landmark.getText().toString());
                                                addAddresses.put("user_ID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                return addAddresses;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(addnewaddress.this);
                                        requestQueue.add(stringRequest);
                                    } else {
                                        loadingdialog.show();
                                        String url = "https://etched-stitches.000webhostapp.com/addAddresses.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(addnewaddress.this, response, Toast.LENGTH_SHORT).show();
                                                        System.out.println("aa"+response);
                                                        dbQuaries.addressesModelList.clear();
                                                        dbQuaries.addressesModelList.add(new addressesModel(
                                                                name.getText().toString(),
                                                                phone.getText().toString(),
                                                                city.getText().toString(),
                                                                locality.getText().toString(),
                                                                flatno.getText().toString(),
                                                                pincode.getText().toString(),
                                                                landmark.getText().toString(),
                                                                onumber.getText().toString(),
                                                                true));
                                                        loadingdialog.dismiss();
                                                        if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")){
                                                            Intent intent = new Intent(addnewaddress.this,delivery.class);
                                                            startActivity(intent);
                                                        }
                                                        finish();
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                loadingdialog.dismiss();
                                                System.out.println(error.toString());
                                                Toast.makeText(addnewaddress.this, error.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> addAddresses = new HashMap<>();
                                                addAddresses.put("mobile_no", phone.getText().toString());
                                                if (!onumber.getText().toString().equals("")) {
                                                    addAddresses.put("alter_mobile_no", onumber.getText().toString());
                                                } else {
                                                    addAddresses.put("alter_mobile_no", "Alternate number not Provided");
                                                }
                                                addAddresses.put("fullname", name.getText().toString());
                                                addAddresses.put("city", city.getText().toString());
                                                addAddresses.put("locality", locality.getText().toString());
                                                addAddresses.put("flatno", flatno.getText().toString());
                                                addAddresses.put("pincode", pincode.getText().toString());
                                                addAddresses.put("landmark", landmark.getText().toString());
                                                addAddresses.put("user_ID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                return addAddresses;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(addnewaddress.this);
                                        requestQueue.add(stringRequest);
                                    }
                                } else {
                                    landmark.setError("This is an mandatory field");
                                }
                            } else {
                                loadingdialog.dismiss();
                                pincode.setError("This is an mandatory field");
                                Toast.makeText(addnewaddress.this, "Please provide valid pincode", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            flatno.setError("This is an mandatory field");
                        }
                    } else {
                        locality.setError("This is an mandatory field");
                    }
                } else {
                    city.setError("This is an mandatory field");
                }
            } else {
                phone.setError("This is an mandatory field");
                Toast.makeText(addnewaddress.this, "Please provide valid phone number", Toast.LENGTH_SHORT).show();
            }
        } else {
            name.setError("This is an mandatory field");
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
}