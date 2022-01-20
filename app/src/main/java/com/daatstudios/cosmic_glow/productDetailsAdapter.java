package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class productDetailsAdapter extends FragmentPagerAdapter
{

    private int totaltabs;
    private String productDescription,productOtherDetails;
    private List<productSpecsModel>productSpecsModelList;

    public productDetailsAdapter(@NonNull FragmentManager fm,  int totaltabs, String productDescription, String productOtherDetails, List<productSpecsModel> productSpecsModelList) {
        super(fm);
        this.totaltabs = totaltabs;
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productSpecsModelList = productSpecsModelList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProductDiscriptionFragment productDiscriptionFragment1 = new ProductDiscriptionFragment();
                productDiscriptionFragment1.body = productDescription;
                return productDiscriptionFragment1;
            case 1:
                product_specs_fragment pRODUCTsPECSFragment = new product_specs_fragment();
                pRODUCTsPECSFragment.productSpecsModelList =productSpecsModelList;
                return pRODUCTsPECSFragment;
            case 2:
                ProductDiscriptionFragment productDiscriptionFragment2 = new ProductDiscriptionFragment();
                productDiscriptionFragment2.body = productOtherDetails;
                return productDiscriptionFragment2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
