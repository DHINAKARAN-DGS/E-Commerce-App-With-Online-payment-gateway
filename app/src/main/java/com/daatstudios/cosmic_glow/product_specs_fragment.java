package com.daatstudios.cosmic_glow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link product_specs_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class product_specs_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public List<productSpecsModel> productSpecsModelList;
    private RecyclerView specRV;
    public product_specs_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pRODUCTsPECSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static product_specs_fragment newInstance(String param1, String param2) {
        product_specs_fragment fragment = new product_specs_fragment();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_p_r_o_d_u_c_ts_p_e_c_s, container, false);
        specRV = view.findViewById(R.id.productSpecificationRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        specRV.setLayoutManager(linearLayoutManager);

//        productSpecsModelList.add(new productSpecsModel(0,"GENERAL"));
//        productSpecsModelList.add(new productSpecsModel(1,"Ram","4GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"Ram","4GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"Ram","4GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"Ram","4GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"ROM","256GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"ROM","256GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"ROM","256GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"ROM","256GB"));
//        productSpecsModelList.add(new productSpecsModel(0,"FROM  BRAND"));
//        productSpecsModelList.add(new productSpecsModel(1,"LOL","4GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"LOL","4GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"LOL","4GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"BRAND","256GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"BRAND","256GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"BRAND","256GB"));
//        productSpecsModelList.add(new productSpecsModel(1,"BRAND","256GB"));

        ProductSpecsAdapter productSpecsAdapter = new ProductSpecsAdapter(productSpecsModelList);
        productSpecsAdapter.notifyDataSetChanged();
        specRV.setAdapter(productSpecsAdapter);
        return view;
    }
}