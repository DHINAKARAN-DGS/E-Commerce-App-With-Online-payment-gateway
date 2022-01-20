package com.daatstudios.cosmic_glow;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daatstudios.cosmic_glow.MyAccount.MANAGE_ADDRESSES;
import static com.daatstudios.cosmic_glow.delivery.SELECT_ADDRESS;
import static com.daatstudios.cosmic_glow.myaddresses.refreshitem;

public class addressesAdapter extends RecyclerView.Adapter<addressesAdapter.ViewHolder> {

    private List<addressesModel> addressesModelList;
    private int MODE;
    private int preselectedposition;
    private boolean refresh = false;
    private Dialog loadingdialog;

    public addressesAdapter(List<addressesModel> addressesModelList, int MODE, Dialog loadingdialog) {
        this.addressesModelList = addressesModelList;
        this.MODE = MODE;
        preselectedposition = dbQuaries.selectedAddresses;
        this.loadingdialog = loadingdialog;

    }

    @NonNull
    @Override
    public addressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_container_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addressesAdapter.ViewHolder holder, int position) {
        String number = addressesModelList.get(position).getPhone();
        String onumber = addressesModelList.get(position).getOnumber();
        String name = addressesModelList.get(position).getName();
        String flatno = addressesModelList.get(position).getFlatno();
        String locality = addressesModelList.get(position).getLocality();
        String city = addressesModelList.get(position).getCity();
        String pincode = addressesModelList.get(position).getPincode();
        String land = addressesModelList.get(position).getLandmark();
        boolean selected = addressesModelList.get(position).getSelected();
        holder.setData(name,number,onumber,flatno,locality,city,land,pincode,selected,position);
    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, addresses, pincode;
        private LinearLayout optionsCon;
        private ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            addresses = itemView.findViewById(R.id.addresses);
            pincode = itemView.findViewById(R.id.pincode);
            icon = itemView.findViewById(R.id.iconView);
            optionsCon = itemView.findViewById(R.id.optionContainer);
        }

        private void setData(String namevalue, String number,String onumber,String flatno,String locality,String city,String landmark,String pincodevalue,boolean selected, final int position) {

            if (onumber.equals("Alternate number not Provided")) {
                name.setText(namevalue + " No. " + number);
            }else{
                name.setText(namevalue + " No. " + number + " or " + onumber);
            }
            String addressesvalue = "No: " + flatno
                    + "," + locality
                    + "," + city
                    + ",Landmark: " + landmark;
            addresses.setText(addressesvalue);
            pincode.setText(pincodevalue);

            if (MODE == SELECT_ADDRESS) {
                icon.setImageResource(R.drawable.tick);
                if (selected) {
                    icon.setVisibility(View.VISIBLE);
                    preselectedposition = position;
                } else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preselectedposition != position) {
                            addressesModelList.get(position).setSelected(true);
                            addressesModelList.get(preselectedposition).setSelected(false);
                            refreshitem(preselectedposition, position);
                            preselectedposition = position;
                            dbQuaries.selectedAddresses = position;
                        }
                    }
                });
            } else if (MODE == MANAGE_ADDRESSES) {
                optionsCon.setVisibility(View.GONE);
                optionsCon.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//edit
                        Intent intent = new Intent(itemView.getContext(), addnewaddress.class);
                        intent.putExtra("INTENT", "update_address");
                        intent.putExtra("index", position);
                        itemView.getContext().startActivity(intent);
                        refresh = false;
                    }
                });
                optionsCon.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//remove
                        loadingdialog.show();
                        Map<String, Object> addresses = new HashMap<>();
                        int x = 0;
                        int selected = -1;
                        for (int i = 0; i < dbQuaries.addressesModelList.size(); i++) {
                            if (i != position) {
                                x++;
                                addresses.put("fullname_" + x, addressesModelList.get(i).getName());
                                addresses.put("city_" + x, addressesModelList.get(i).getCity());
                                addresses.put("flatno_" + x, addressesModelList.get(i).getFlatno());
                                addresses.put("locality_" + x, addressesModelList.get(i).getLocality());
                                addresses.put("alter_mobile_no_" + x, addressesModelList.get(i).getOnumber());
                                addresses.put("landmark_" + x, addressesModelList.get(i).getLandmark());
                                addresses.put("pincode_" + x, addressesModelList.get(i).getPincode());
                                if (addressesModelList.get(position).getSelected()) {
                                    if (position - 1 >= 0) {
                                        if (x == position) {
                                            addresses.put("selected_" + x, true);
                                            selected = x;
                                        }else{
                                            addresses.put("selected_" + x, addressesModelList.get(i).getSelected());
                                        }
                                    } else {
                                        if (x == 1) {
                                            addresses.put("selected_" + x, true);
                                            selected = x;
                                        }else{
                                            addresses.put("selected_" + x, addressesModelList.get(i).getSelected());
                                        }
                                    }
                                } else {
                                    addresses.put("selected_" + x, addressesModelList.get(i).getSelected());
                                    if (addressesModelList.get(i).getSelected()){
                                        selected=x;
                                    }
                                }

                            }
                        }
                        addresses.put("list_size", x);
                        final int finalSelected = selected;
                        FirebaseFirestore.getInstance().collection("USERS")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("USERDATA")
                                .document("MY_ADDRESSES")
                                .set(addresses).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    dbQuaries.addressesModelList.remove(position);
                                    if (finalSelected != -1) {
                                        dbQuaries.selectedAddresses = finalSelected - 1;
                                        dbQuaries.addressesModelList.get(finalSelected - 1).setSelected(true);
                                    }else if (dbQuaries.addressesModelList.size()==0){
                                        dbQuaries.selectedAddresses=-1;
                                    }
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(itemView.getContext(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                                loadingdialog.dismiss();
                            }
                        });
                        refresh = false;
                    }
                });
                icon.setImageResource(R.drawable.options);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsCon.setVisibility(View.VISIBLE);
                        if (refresh) {
                            refreshitem(preselectedposition, preselectedposition);
                        } else {
                            refresh = true;
                        }

                        preselectedposition = position;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshitem(preselectedposition, preselectedposition);
                        preselectedposition = -1;
                    }
                });
            }

        }
    }

}

