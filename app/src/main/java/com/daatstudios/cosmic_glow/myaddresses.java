package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.daatstudios.cosmic_glow.delivery.SELECT_ADDRESS;

public class myaddresses extends AppCompatActivity {

    private int preSelectedAddressesPosition;
    private RecyclerView address;
    private Button deliveryhereBtn;
    private LinearLayout addnewAdd;
    private TextView addressesSaved;
    FirebaseFirestore firestore;
    private static addressesAdapter addressesAdapter;
    private Dialog loadingdialog;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddresses);

        getSupportActionBar().setTitle("My addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();

        deliveryhereBtn = findViewById(R.id.deliver_here_btn);
        addnewAdd = findViewById(R.id.add_new_address_btn);
        addressesSaved = findViewById(R.id.addressesSaved);
        preSelectedAddressesPosition = dbQuaries.selectedAddresses;

        loadingdialog = new Dialog(myaddresses.this);
        loadingdialog.setContentView(R.layout.loading_prog);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                addressesSaved.setText(String.valueOf(dbQuaries.addressesModelList.size()) + " saved addresses");
            }
        });

        addnewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myaddresses.this, addnewaddress.class);
                if (mode!=SELECT_ADDRESS) {
                    intent.putExtra("INTENT", "manage");
                }else{
                    intent.putExtra("INTENT", "null");
                }
                startActivity(intent);
            }
        });




        address = findViewById(R.id.addressesRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        address.setLayoutManager(layoutManager);


        mode = getIntent().getIntExtra("MODE", -1);
        if (mode == SELECT_ADDRESS) {
            deliveryhereBtn.setVisibility(View.VISIBLE);
        } else
            deliveryhereBtn.setVisibility(View.GONE);
        addressesAdapter = new addressesAdapter(dbQuaries.addressesModelList, mode,loadingdialog);
        address.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) address.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();

        deliveryhereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbQuaries.selectedAddresses != preSelectedAddressesPosition) {
                    final int previousAddressesIndex = preSelectedAddressesPosition;
                    loadingdialog.show();
                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_" + String.valueOf(preSelectedAddressesPosition + 1), false);
                    updateSelection.put("selected_" + String.valueOf(dbQuaries.selectedAddresses + 1), true);

                    preSelectedAddressesPosition = dbQuaries.selectedAddresses;

                    firestore.collection("USERS")
                            .document(FirebaseAuth.getInstance().getUid())
                            .collection("USERDATA")
                            .document("MY_ADDRESSES")
                            .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                delivery.cartAdapter.notifyDataSetChanged();
                                finish();
                            } else {
                                preSelectedAddressesPosition = previousAddressesIndex;
                                Toast.makeText(myaddresses.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }loadingdialog.dismiss();
                        }
                    });
                }else{
                    finish();
                }
            }
        });

    }

    public static void refreshitem(int deselect, int select) {
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mode==SELECT_ADDRESS) {
                if (dbQuaries.selectedAddresses != preSelectedAddressesPosition) {
                    dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).setSelected(false);
                    dbQuaries.addressesModelList.get(preSelectedAddressesPosition).setSelected(true);
                    dbQuaries.selectedAddresses = preSelectedAddressesPosition;
                }
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mode==SELECT_ADDRESS) {
            if (dbQuaries.selectedAddresses != preSelectedAddressesPosition) {
                dbQuaries.addressesModelList.get(dbQuaries.selectedAddresses).setSelected(false);
                dbQuaries.addressesModelList.get(preSelectedAddressesPosition).setSelected(true);
                dbQuaries.selectedAddresses = preSelectedAddressesPosition;
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        addressesSaved.setText(String.valueOf(dbQuaries.addressesModelList.size()) + " saved addresses");
        super.onStart();
    }
}