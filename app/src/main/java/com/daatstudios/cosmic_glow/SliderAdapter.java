package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class SliderAdapter extends PagerAdapter
{
  private List<SliderModel>sliderModelList;

  public SliderAdapter(List<SliderModel> sliderModelList) {
    this.sliderModelList = sliderModelList;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, final int position)
  {
    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
    ImageView banner = view.findViewById(R.id.bannerSlideImage);
    Glide.with(container.getContext()).load(sliderModelList.get(position).getBanner()).apply(new RequestOptions()).placeholder(R.drawable.placeholder).into(banner);
    container.addView(view,0);
    ConstraintLayout bannerLayout = view.findViewById(R.id.banner_container);
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(),ProductDetailsActivity.class);
        intent.putExtra("product_ID",sliderModelList.get(position).getAction());
        view.getContext().startActivity(intent);
      }
    });

    return view;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
  {
    return view == object;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
  {
    container.removeView((View) object) ;
  }

  @Override
  public int getCount() {
    return sliderModelList.size();
  }
}
