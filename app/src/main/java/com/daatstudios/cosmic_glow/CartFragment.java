package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private Button continueBtn;

    public CartFragment() {
        // Required empty public constructor
    }



    private RecyclerView cartItemsRV;
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment



        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartItemsRV = view.findViewById(R.id.cartItemsRV);

        continueBtn = view.findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),delivery.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemsRV.setLayoutManager(layoutManager);

        List<CartItemsModel>CartItemsModelList = new ArrayList<>();
//        CartItemsModelList.add(new CartItemsModel(0,R.drawable.test2,2,2,5,1,"IPHONE","55,556","60,000"));
//        CartItemsModelList.add(new CartItemsModel(0,R.drawable.test2,5,3,5,1,"IPHONE","55,556","60,000"));
//        CartItemsModelList.add(new CartItemsModel(0,R.drawable.test2,2,0,5,2,"IPHONE","55,556","60,000"));
//        CartItemsModelList.add(new CartItemsModel(1,"3","555555","FREE","9999","555555"));

//        CartAdapter cartAdapter = new CartAdapter(CartItemsModelList);
//        cartItemsRV.setAdapter(cartAdapter);
//        cartAdapter.notifyDataSetChanged();


        return view;

    }


}