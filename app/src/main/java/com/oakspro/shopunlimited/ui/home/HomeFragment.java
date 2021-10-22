package com.oakspro.shopunlimited.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oakspro.shopunlimited.CategoryAdapter;
import com.oakspro.shopunlimited.CategoryModel;
import com.oakspro.shopunlimited.R;
import com.oakspro.shopunlimited.SliderImgAdapter;
import com.oakspro.shopunlimited.SliderItem;
import com.oakspro.shopunlimited.databinding.FragmentHomeBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    ArrayList<CategoryModel> arrayList=new ArrayList<>();
    ArrayList<SliderItem> bannerList=new ArrayList<>();
    CategoryAdapter adapter;
    SliderView sliderView;
    SliderImgAdapter imgAdapter;
    private String cat_api="https://oakspro.com/projects/project35/deepu/shopUnlimited/category_api.php";
    private String banner_api="https://oakspro.com/projects/project35/deepu/shopUnlimited/banner_api.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView=root.findViewById(R.id.cat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getCategoriesFromServer();


        sliderView =root.findViewById(R.id.ads_slider);

        SliderImgAdapter adapter = new SliderImgAdapter(getContext());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        getBannersFromServer();


        return root;
    }

    private void getBannersFromServer() {
        StringRequest request_banner=new StringRequest(Request.Method.POST, banner_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    String status=object.getString("status");
                    if (status.equals("1")){
                        bannerList.clear();
                        JSONArray jsonArray_b=object.getJSONArray("banners");
                        for (int i=0; i<jsonArray_b.length(); i++){
                            JSONObject object2=jsonArray_b.getJSONObject(i);

                            SliderItem sliderItem=new SliderItem();
                            sliderItem.setAd_id(object2.getString("ad_id"));
                            sliderItem.setAd_link(object2.getString("ad_link"));
                            sliderItem.setAd_img(object2.getString("ad_img"));
                            //bannerList.add(sliderItem);
                            imgAdapter.addItem(sliderItem);

                        }
                        adapter=new CategoryAdapter(getContext(), arrayList);
                        recyclerView.setAdapter(adapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("package", getActivity().getPackageName());
                return data;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getContext());
        requestQueue.add(request_banner);



    }

    private void getCategoriesFromServer() {
        StringRequest request=new StringRequest(Request.Method.POST, cat_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("1")){

                        arrayList.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray("category");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object=jsonArray.getJSONObject(i);

                            CategoryModel categoryModel=new CategoryModel();
                            categoryModel.setCat_id(object.getString("cat_id"));
                            categoryModel.setCat_name(object.getString("cat_name"));
                            categoryModel.setCat_pic(object.getString("cat_pic"));
                            arrayList.add(categoryModel);
                        }
                        adapter=new CategoryAdapter(getContext(), arrayList);
                        recyclerView.setAdapter(adapter);


                    }else {
                        Toast.makeText(getContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> cat=new HashMap<>();
                cat.put("package", getActivity().getPackageName()); //com.oakspro.shopunlimited
                return cat;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}