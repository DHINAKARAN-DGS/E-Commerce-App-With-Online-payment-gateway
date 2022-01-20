package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccount extends AppCompatActivity {
    private Button viewallAdderesses, signout, orders, wishlistBtn;
    public static final int MANAGE_ADDRESSES = 1;
    private FirebaseAuth firebaseAuth;
    private TextView name, email, addressesname, addresses, pincode,developerPage;
    private LinearLayout layout;
    private Dialog loadingdialog;
    private TextView recentOrderStatus;
    private LinearLayout recentOrdersContainer, layoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Account");

        recentOrderStatus = findViewById(R.id.wishilist);
        orders = findViewById(R.id.recentOrderBtn);
        wishlistBtn = findViewById(R.id.wishlistBtn);
        signout = findViewById(R.id.signout);
        addresses = findViewById(R.id.address_fulladdresses);
        pincode = findViewById(R.id.address_pincode);
        addressesname = findViewById(R.id.addresses_fullname);
        developerPage=findViewById(R.id.developercontact);

        loadingdialog = new Dialog(MyAccount.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        developerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://daat.tech"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, Orders.class);
                startActivity(intent);
            }
        });
        wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, wishlist.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                AuthUI.getInstance()
                        .signOut(MyAccount.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dbQuaries.clearData();
                            Intent intent = new Intent(MyAccount.this, RegisterActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MyAccount.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        viewallAdderesses = findViewById(R.id.view_all_addresses_btn);

        loadingdialog.show();
        loadingdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loadingdialog.setOnDismissListener(null);
                if (dbQuaries.addressesModelList.size() == 0) {
                    addressesname.setText("No addresses stored");
                    addresses.setText("-");
                    pincode.setText("-");
                    viewallAdderesses.setText("Add address");
                } else {
                    viewallAdderesses.setText("Edit address");
                    setAddresses();
                }
            }
        });
        dbQuaries.loadAddresses(MyAccount.this, loadingdialog, false);



        viewallAdderesses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewallAdderesses.getText().toString().equals("Add address")) {
                    Intent intent = new Intent(MyAccount.this, addnewaddress.class);
                    intent.putExtra("E","F");
                    intent.putExtra("INTENT","");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MyAccount.this, addnewaddress.class);
                    intent.putExtra("E","T");
                    intent.putExtra("INTENT","");
                    startActivity(intent);
                }
            }
        });

        layout = findViewById(R.id.layoutContainer);
        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);


        name.setText(dbQuaries.uname);
        email.setText(dbQuaries.uemail);




    }

    private void setAddresses() {
        String nameTxt = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getName();
        String number = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getPhone();
        String ano = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getOnumber();
        String flatno = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getFlatno();
        String city = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getCity();
        String locality = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getLocality();
        String landmark = dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getLandmark();
        String pincodevalue =dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).getPincode();

        addressesname.setText(nameTxt + " No. " + number + " or " + ano);
        String addressesvalue = "No: " + flatno
                + "," + locality
                + "," + city
                + ",Landmark: " + landmark;
        addresses.setText(addressesvalue);
        pincode.setText(pincodevalue);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        dbQuaries.loadOrders(MyAccount.this,null,loadingdialog);
        if (dbQuaries.addressesModelList.size() <= 0) {
            addressesname.setText("No addresses stored");
            addresses.setText("-");
            pincode.setText("-");
        } else {
            setAddresses();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}